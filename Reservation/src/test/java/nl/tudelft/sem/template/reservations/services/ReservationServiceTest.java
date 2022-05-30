package nl.tudelft.sem.template.reservations.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.factory.FieldReservationFactory;
import nl.tudelft.sem.template.reservations.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class ReservationServiceTest {

    @InjectMocks
    @Spy
    transient ReservationService service;

    @Mock
    transient ReservationRepository repository;

    @Mock
    transient FieldReservationFactory fieldFactory;

    private static FieldReservation fieldReservation;
    private static final String FOOBAR = "foobar";
    private static final String INVALID_TIME_MESSAGE =
            "Start or end time is outside of operating hours";

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);


        fieldReservation = new FieldReservation();
        fieldReservation.setId(1);
        fieldReservation.setStartingTime(LocalDateTime.MIN.withHour(10));
        fieldReservation.setEndingTime(LocalDateTime.MAX.withHour(12));
        fieldReservation.setUser(FOOBAR);
    }

    @Test
    void saveNormalReservationTest() {
        when(fieldFactory.createReservation(Mockito.any())).thenReturn(fieldReservation);

        Reservation res = service.save(FOOBAR, fieldFactory);

        verify(fieldFactory, times(1)).createReservation(FOOBAR);
        verify(fieldFactory, times(1)).validate(fieldReservation);
        assertEquals(fieldReservation.getId(), res.getId());
    }

    @Test
    void saveReservationAfterClosingTimeTest() {
        fieldReservation.setEndingTime(LocalDateTime.MAX);
        when(fieldFactory.createReservation(Mockito.any())).thenReturn(fieldReservation);

        ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> service.save(FOOBAR, fieldFactory)
        );

        verify(fieldFactory, times(1)).createReservation(FOOBAR);
        verify(fieldFactory, times(0)).validate(fieldReservation);
        assertEquals(INVALID_TIME_MESSAGE, thrown.getMessage());
    }

    @Test
    void saveReservationBeforeOpeningTimeTest() {
        fieldReservation.setStartingTime(LocalDateTime.MIN);
        when(fieldFactory.createReservation(Mockito.any())).thenReturn(fieldReservation);

        ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> service.save(FOOBAR, fieldFactory)
        );

        verify(fieldFactory, times(1)).createReservation(FOOBAR);
        verify(fieldFactory, times(0)).validate(fieldReservation);
        assertEquals(INVALID_TIME_MESSAGE, thrown.getMessage());
    }

    @Test
    void saveReservationWrongTimesTest() {
        fieldReservation.setStartingTime(LocalDateTime.MAX);
        fieldReservation.setEndingTime(LocalDateTime.MIN);
        when(fieldFactory.createReservation(Mockito.any())).thenReturn(fieldReservation);

        ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> service.save(FOOBAR, fieldFactory)
        );

        verify(fieldFactory, times(1)).createReservation(FOOBAR);
        verify(fieldFactory, times(0)).validate(fieldReservation);
        assertEquals(INVALID_TIME_MESSAGE, thrown.getMessage());
    }

    @Test
    void saveReservationUntilElevenTest() {
        fieldReservation.setEndingTime(LocalDateTime.MAX.withHour(23).withMinute(0));
        when(fieldFactory.createReservation(Mockito.any())).thenReturn(fieldReservation);

        Reservation res = service.save(FOOBAR, fieldFactory);

        verify(fieldFactory, times(1)).createReservation(FOOBAR);
        verify(fieldFactory, times(1)).validate(fieldReservation);
        assertEquals(fieldReservation.getId(), res.getId());
    }

    @Test
    void getByIdTest() {
        Optional<Reservation> optional = Optional.of(fieldReservation);
        when(repository.findById(1L)).thenReturn(optional);

        Reservation returned = service.getById(1);

        verify(repository, times(1)).findById(1L);
        assertEquals(fieldReservation, returned);
    }

    @Test
    void getByIdExceptionTest() {
        Optional<Reservation> optional = Optional.empty();
        when(repository.findById(1L)).thenReturn(optional);
        ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> service.getById(1));

        verify(repository, times(1)).findById(1L);
        assertEquals("Reservation does not exist", thrown.getMessage());

    }

    @Test
    void getFieldsTest() {
        ArrayList<FieldReservation> list = new ArrayList<>();
        list.add(fieldReservation);
        when(repository.getFields()).thenReturn(list);

        List<FieldReservation> returned = service.getFields();

        verify(repository, times(1)).getFields();
        assertEquals(list, returned);
    }

    @Test
    void getEquipmentTest() {
        EquipmentReservation equipmentReservation = new EquipmentReservation();
        ArrayList<EquipmentReservation> list = new ArrayList<>();
        list.add(equipmentReservation);
        when(repository.getEquipment()).thenReturn(list);

        List<EquipmentReservation> returned = service.getEquipment();

        verify(repository, times(1)).getEquipment();
        assertEquals(list, returned);
    }

    @Test
    void countByEquipmentTest() {
        when(repository.countByEquipment(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(5);

        int returned = service.countByEquipment("Equipment", LocalDateTime.MIN, LocalDateTime.MAX);

        verify(repository, times(1))
                .countByEquipment("Equipment", LocalDateTime.MIN, LocalDateTime.MAX);
        assertEquals(5, returned);
    }

    @Test
    void countByFieldTest() {
        when(repository.countByField(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(5);

        int returned = service.countByField("Field", LocalDateTime.MIN, LocalDateTime.MAX);

        verify(repository, times(1))
                .countByField("Field", LocalDateTime.MIN, LocalDateTime.MAX);
        assertEquals(5, returned);
    }

    @Test
    void countReservationsTest() {
        when(repository.countReservations(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(5);

        int returned = service.countReservations("User", LocalDate.MIN, LocalDate.MAX);

        verify(repository, times(1))
                .countReservations("User", LocalDate.MIN, LocalDate.MAX);
        assertEquals(5, returned);
    }

}

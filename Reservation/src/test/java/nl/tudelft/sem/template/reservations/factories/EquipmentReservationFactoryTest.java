package nl.tudelft.sem.template.reservations.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.factory.EquipmentReservationFactory;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import nl.tudelft.sem.template.reservations.services.ServiceCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class EquipmentReservationFactoryTest {

    @InjectMocks
    @Spy
    private transient EquipmentReservationFactory factory;

    @Mock
    private transient ReservationService service;

    @Mock
    private transient ServiceCommunication communication;

    private transient ObjectMapper mapper;
    private static EquipmentReservation er;
    private static final String OBJECTMAPPER_MESSAGE = "Object mapper failed";
    private static final String USER_NAME = "waldo";
    private static final String EQUIPMENT_NAME = "fred";


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        er = new EquipmentReservation();
        er.setId(2);
        er.setStartingTime(LocalDateTime.MIN);
        er.setEndingTime(LocalDateTime.MAX);
        er.setUser(USER_NAME);
        er.setName(EQUIPMENT_NAME);
        er.setQuantity(2);
        er.setPaid(false);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void createReservationTest() {
        try {
            String body = mapper.writeValueAsString(er);
            Reservation reservation = factory.createReservation(body);
            assertEquals(er, reservation);

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void createReservationFailureTest() {

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> factory.createReservation(""));

        assertEquals(OBJECTMAPPER_MESSAGE, thrown.getMessage());
    }

    @Test
    void validateCorrectTest() {
        when(communication.getQuantity(EQUIPMENT_NAME)).thenReturn(5);
        when(service.countByEquipment(EQUIPMENT_NAME, LocalDateTime.MIN, LocalDateTime.MAX))
                .thenReturn(2);

        factory.validate(er);

        verify(communication, times(1))
                .getQuantity(EQUIPMENT_NAME);
        verify(service, times(1))
                .countByEquipment(EQUIPMENT_NAME, LocalDateTime.MIN, LocalDateTime.MAX);
        verify(communication, times(1))
                .updateHistory(EQUIPMENT_NAME, USER_NAME);
    }

    @Test
    void validateNotEnoughAvailableTest() {
        when(communication.getQuantity(EQUIPMENT_NAME)).thenReturn(5);
        when(service.countByEquipment(EQUIPMENT_NAME, LocalDateTime.MIN, LocalDateTime.MAX))
                .thenReturn(5);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(er)
        );

        verify(communication, times(1))
                .getQuantity(EQUIPMENT_NAME);
        verify(service, times(1))
                .countByEquipment(EQUIPMENT_NAME, LocalDateTime.MIN, LocalDateTime.MAX);
        verify(communication, times(0))
                .updateHistory(EQUIPMENT_NAME, USER_NAME);
        assertEquals("Item not available", thrown.getMessage());
    }
}

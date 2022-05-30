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
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.entities.User;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.factory.FieldReservationFactory;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import nl.tudelft.sem.template.reservations.services.ServiceCommunication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class FieldReservationFactoryTest {

    @InjectMocks
    @Spy
    private transient FieldReservationFactory factory;

    @Mock
    private transient ReservationService service;

    @Mock
    private transient ServiceCommunication communication;

    private transient ObjectMapper mapper;
    private static FieldReservation fr;
    private static User user1;
    private static User user2;
    private static List<User> team;
    private static final String OBJECTMAPPER_MESSAGE = "Object mapper failed";

    private static final String USER_NAME = "foo";
    private static final String USER_NAME_ONE = "foo1";
    private static final String USER_NAME_TWO = "foo2";
    private static final String FIELD_NAME = "bar";
    private static final String TEAM_NAME = "baz";


    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        fr = new FieldReservation();
        fr.setId(1);
        fr.setStartingTime(LocalDateTime.MIN);
        fr.setEndingTime(LocalDateTime.MAX);
        fr.setUser(USER_NAME);
        fr.setFieldName(FIELD_NAME);
        fr.setTeamName(TEAM_NAME);
        fr.setLesson(false);

        user1 = new User();
        user1.setUserName(USER_NAME_ONE);
        user1.setType(User.UserType.BASIC);
        user2 = new User();
        user2.setUserName(USER_NAME_TWO);
        user2.setType(User.UserType.PREMIUM);

        team = new ArrayList<>();
        team.add(user1);
        team.add(user2);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void createReservationTest() {
        try {
            String body = mapper.writeValueAsString(fr);
            Reservation reservation = factory.createReservation(body);
            assertEquals(fr, reservation);

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
    void validateCorrectNoLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(new ArrayList<>());
        when(communication.getMin(FIELD_NAME)).thenReturn(1);

        fr.setStartingTime(start);
        factory.validate(fr);

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
    }

    @Test
    void validateCorrectNoLessonTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(team);
        when(communication.getMin(FIELD_NAME)).thenReturn(1);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);
        when(service.countReservations(USER_NAME_ONE, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countReservations(USER_NAME_TWO, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(2);

        fr.setStartingTime(start);
        factory.validate(fr);

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        verify(service, times(1))
                .countReservations(USER_NAME_ONE, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countReservations(USER_NAME_TWO, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
    }

    @Test
    void validateCorrectLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MAX.withHour(12);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, end))
                .thenReturn(0);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);

        fr.setLesson(true);
        fr.setEndingTime(end);
        factory.validate(fr);

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, end);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
    }

    @Test
    void validateNotEnoughForFieldNoLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(new ArrayList<>());
        when(communication.getMin(FIELD_NAME)).thenReturn(5);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
        assertEquals("Not enough players for field", thrown.getMessage());
    }

    @Test
    void validateBeforeNormalReservationsNoLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN;

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(new ArrayList<>());
        when(communication.getMin(FIELD_NAME)).thenReturn(1);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(0))
                .getTeam(TEAM_NAME);
        verify(communication, times(0))
                .getMin(FIELD_NAME);
        assertEquals("Could not reserve field", thrown.getMessage());
    }

    @Test
    void validateAlreadyBookedNoLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(1);
        when(communication.getTeam(TEAM_NAME)).thenReturn(new ArrayList<>());
        when(communication.getMin(FIELD_NAME)).thenReturn(1);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(0))
                .getTeam(TEAM_NAME);
        verify(communication, times(0))
                .getMin(FIELD_NAME);
        assertEquals("Could not reserve field", thrown.getMessage());
    }

    @Test
    void validateTeamToSmallNoLessonTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(team);
        when(communication.getMin(FIELD_NAME)).thenReturn(3);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);
        when(service.countReservations(USER_NAME_ONE, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countReservations(USER_NAME_TWO, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(2);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        verify(service, times(0))
                .countReservations(USER_NAME_ONE, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(0))
                .countReservations(USER_NAME_TWO, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        assertEquals("Team is not the correct size for field", thrown.getMessage());
    }

    @Test
    void validateTeamToBigNoLessonTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(team);
        when(communication.getMin(FIELD_NAME)).thenReturn(1);
        when(communication.getMax(FIELD_NAME)).thenReturn(1);
        when(service.countReservations(USER_NAME_ONE, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countReservations(USER_NAME_TWO, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(2);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        verify(service, times(0))
                .countReservations(USER_NAME_ONE, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(0))
                .countReservations(USER_NAME_TWO, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        assertEquals("Team is not the correct size for field", thrown.getMessage());
    }

    @Test
    void validateTooManyNoLessonTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(team);
        when(communication.getMin(FIELD_NAME)).thenReturn(1);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);
        when(service.countReservations(USER_NAME_ONE, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(1);
        when(service.countReservations(USER_NAME_TWO, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(2);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(1))
                .getTeam(TEAM_NAME);
        verify(communication, times(1))
                .getMin(FIELD_NAME);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        verify(service, times(1))
                .countReservations(USER_NAME_ONE, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(0))
                .countReservations(USER_NAME_TWO, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        assertEquals("A team member has too many reservations", thrown.getMessage());
    }

    @Test
    void validateTooManyNoLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN.withHour(18);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(1);
        when(service.countByField(FIELD_NAME, start, LocalDateTime.MAX))
                .thenReturn(0);
        when(communication.getTeam(TEAM_NAME)).thenReturn(new ArrayList<>());
        when(communication.getMin(FIELD_NAME)).thenReturn(1);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);

        fr.setStartingTime(start);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(0))
                .countByField(FIELD_NAME, start, LocalDateTime.MAX);
        verify(communication, times(0))
                .getTeam(TEAM_NAME);
        verify(communication, times(0))
                .getMin(FIELD_NAME);
        verify(communication, times(0))
                .getMax(FIELD_NAME);
        assertEquals("Too many reservations made today", thrown.getMessage());
    }

    @Test
    void validateAlreadyBookedLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MAX.withHour(12);

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, end))
                .thenReturn(5);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);

        fr.setLesson(true);
        fr.setEndingTime(end);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, end);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        assertEquals("The lesson is full", thrown.getMessage());
    }

    @Test
    void validatetooLateLessonNoTeamTest() {
        LocalDateTime start = LocalDateTime.MIN;
        LocalDateTime end = LocalDateTime.MAX;

        when(service.countReservations(USER_NAME, start.toLocalDate(),
                start.toLocalDate().plusDays(1))).thenReturn(0);
        when(service.countByField(FIELD_NAME, start, end))
                .thenReturn(0);
        when(communication.getMax(FIELD_NAME)).thenReturn(5);

        fr.setLesson(true);

        final ReservationInputException thrown = assertThrows(
                ReservationInputException.class,
                () -> factory.validate(fr)
        );

        verify(service, times(1))
                .countReservations(USER_NAME, start.toLocalDate(),
                        start.toLocalDate().plusDays(1));
        verify(service, times(1))
                .countByField(FIELD_NAME, start, end);
        verify(communication, times(1))
                .getMax(FIELD_NAME);
        assertEquals("The lesson is outside of lesson hours", thrown.getMessage());
    }

}

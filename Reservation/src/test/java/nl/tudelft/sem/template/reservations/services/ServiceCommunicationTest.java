package nl.tudelft.sem.template.reservations.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.User;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ServiceCommunicationTest {

    @InjectMocks
    @Spy
    transient ServiceCommunication serviceCommunication;

    @Mock
    transient RestTemplate restTemplate;

    private static final String FIELD_CONNECTION_MESSAGE =
            "Failed to connect to field service";
    private static final String EQUIPMENT_CONNECTION_MESSAGE =
            "Failed to connect to equipment service";
    private static final String USER_CONNECTION_MESSAGE =
            "Failed to connect to user service";

    private static final String GET_MAX_BY_NAME_URL = "http://localhost:8082/getMaxByName/field1";
    private static final String GET_MIN_BY_NAME_URL = "http://localhost:8082/getMinByName/field1";
    private static final String GET_PREMIUM_URL = "http://localhost:8084/isPremium/user1";
    private static final String GET_TEAM_BY_NAME_URL = "http://localhost:8084/getTeamByName/team1";
    private static final String GET_CAPACITY_URL = "http://localhost:8081/getCapacity/equipment1";

    private static final String FIELD_NAME = "field1";
    private static final String USER_NAME = "user1";
    private static final String EQUIPMENT_NAME = "equipment1";

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void getMaxTest() {
        when(restTemplate.getForObject(GET_MAX_BY_NAME_URL, Integer.class)).thenReturn(5);

        int returned = serviceCommunication.getMax(FIELD_NAME);

        verify(restTemplate, times(1))
                .getForObject(GET_MAX_BY_NAME_URL, Integer.class);
        assertEquals(5, returned);
    }

    @Test
    void getMaxExceptionTest() {
        when(restTemplate.getForObject(GET_MAX_BY_NAME_URL, Integer.class))
                .thenThrow(RestClientException.class);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.getMax(FIELD_NAME)
        );

        verify(restTemplate, times(1))
                .getForObject(GET_MAX_BY_NAME_URL, Integer.class);
        assertEquals(FIELD_CONNECTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void getMinTest() {
        when(restTemplate.getForObject(GET_MIN_BY_NAME_URL, Integer.class)).thenReturn(5);

        int returned = serviceCommunication.getMin(FIELD_NAME);

        verify(restTemplate, times(1))
                .getForObject(GET_MIN_BY_NAME_URL, Integer.class);
        assertEquals(5, returned);
    }

    @Test
    void getMinExceptionTest() {
        when(restTemplate.getForObject(GET_MIN_BY_NAME_URL, Integer.class))
                .thenThrow(RestClientException.class);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.getMin(FIELD_NAME)
        );

        verify(restTemplate, times(1))
                .getForObject(GET_MIN_BY_NAME_URL, Integer.class);
        assertEquals(FIELD_CONNECTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void getPremiumTest() {
        when(restTemplate.getForObject(GET_PREMIUM_URL, Boolean.class)).thenReturn(true);

        boolean returned = serviceCommunication.getPremium(USER_NAME);

        verify(restTemplate, times(1))
                .getForObject(GET_PREMIUM_URL, Boolean.class);
        assertTrue(returned);
    }

    @Test
    void getPremiumExceptionTest() {
        when(restTemplate.getForObject(GET_PREMIUM_URL, Boolean.class))
                .thenThrow(RestClientException.class);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.getPremium(USER_NAME)
        );

        verify(restTemplate, times(1))
                .getForObject(GET_PREMIUM_URL, Boolean.class);
        assertEquals(USER_CONNECTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void getTeamTest() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User());
        when(restTemplate.getForObject(GET_TEAM_BY_NAME_URL, List.class))
                .thenReturn(list);

        List<User> returned = serviceCommunication.getTeam("team1");

        verify(restTemplate, times(1))
                .getForObject(GET_TEAM_BY_NAME_URL, List.class);
        assertEquals(list, returned);
    }

    @Test
    void getTeamNullTest() {
        ArrayList<User> list = new ArrayList<>();
        list.add(new User());
        when(restTemplate.getForObject(GET_TEAM_BY_NAME_URL, List.class))
                .thenReturn(list);

        List<User> returned = serviceCommunication.getTeam(null);

        verify(restTemplate, times(0))
                .getForObject(GET_TEAM_BY_NAME_URL, List.class);
        assertEquals(new ArrayList<>(), returned);
    }

    @Test
    void getTeamExceptionTest() {
        when(restTemplate.getForObject(GET_TEAM_BY_NAME_URL, List.class))
                .thenThrow(RestClientException.class);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.getTeam("team1")
        );

        verify(restTemplate, times(1))
                .getForObject(GET_TEAM_BY_NAME_URL, List.class);
        assertEquals(USER_CONNECTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void getQuantityTest() {
        when(restTemplate.getForObject(GET_CAPACITY_URL, Integer.class)).thenReturn(10);

        int returned = serviceCommunication.getQuantity(EQUIPMENT_NAME);

        verify(restTemplate, times(1))
                .getForObject(GET_CAPACITY_URL, Integer.class);
        assertEquals(10, returned);
    }

    @Test
    void getQuantityExceptionTest() {
        when(restTemplate.getForObject(GET_CAPACITY_URL, Integer.class))
                .thenThrow(RestClientException.class);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.getQuantity(EQUIPMENT_NAME)
        );

        verify(restTemplate, times(1))
                .getForObject(GET_CAPACITY_URL, Integer.class);
        assertEquals(EQUIPMENT_CONNECTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void updateHistoryTest() {
        serviceCommunication.updateHistory(EQUIPMENT_NAME, USER_NAME);

        verify(restTemplate, times(1))
                .put("http://localhost:8081/addUser/", EQUIPMENT_NAME, USER_NAME);
    }

    @Test
    void updateHistoryExceptionTest() {
        doThrow(RestClientException.class).when(restTemplate)
                .put("http://localhost:8081/addUser/", EQUIPMENT_NAME, USER_NAME);

        ReservationServerException thrown = assertThrows(
                ReservationServerException.class,
                () -> serviceCommunication.updateHistory(EQUIPMENT_NAME, USER_NAME)
        );

        verify(restTemplate, times(1))
                .put("http://localhost:8081/addUser/", EQUIPMENT_NAME, USER_NAME);
        assertEquals(EQUIPMENT_CONNECTION_MESSAGE, thrown.getMessage());
    }
}

package nl.tudelft.sem.template.reservations.services;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.User;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class ServiceCommunication {
    private final transient RestTemplate restTemplate;

    public ServiceCommunication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Gets the max capacity of a field.
     *
     * @param fieldName field name.
     * @return max capacity.
     */
    public int getMax(String fieldName) {
        try {
            return restTemplate.getForObject("http://localhost:8082/getMaxByName/" + fieldName, Integer.class);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to field service");
        }
    }

    /**
     * Gets the min capacity of a field.
     *
     * @param fieldName field name.
     * @return min capacity.
     */
    public int getMin(String fieldName) {
        try {
            return restTemplate.getForObject("http://localhost:8082/getMinByName/" + fieldName, Integer.class);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to field service");
        }
    }

    /**
     * Gets if a user is premium.
     *
     * @param username username.
     * @return boolean.
     */
    public boolean getPremium(String username) {
        try {
            return restTemplate.getForObject("http://localhost:8084/isPremium/" + username, Boolean.class);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to user service");
        }
    }


    /**
     * Gets a team.
     *
     * @param teamName team name.
     * @return team.
     */
    public List<User> getTeam(String teamName) {
        if (teamName == null) {
            return new ArrayList<>();
        }
        try {
            return restTemplate.getForObject("http://localhost:8084/getTeamByName/" + teamName, List.class);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to user service");
        }
    }

    /**
     * Gets the quantity of equipment.
     *
     * @param equipmentName equipment name.
     * @return quantity.
     */
    public int getQuantity(String equipmentName) {
        try {
            return restTemplate.getForObject("http://localhost:8081/getCapacity/" + equipmentName, Integer.class);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to equipment service");
        }
    }

    /**
     * Gets the quantity of equipment.
     *
     * @param equipmentName equipment name.
     */
    public void updateHistory(String equipmentName, String user) {
        try {
            restTemplate.put("http://localhost:8081/addUser/", equipmentName, user);
        } catch (RestClientException e) {
            throw new ReservationServerException("Failed to connect to equipment service");
        }
    }
}

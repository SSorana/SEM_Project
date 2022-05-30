package nl.tudelft.sem.template.reservations.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;

public class Mapper {

    /**
     * Maps the request body to a field reservation.
     *
     * @param body request body.
     * @return field reservation.
     */
    public static <Reservation> Reservation createReservation(String body,
                                                              TypeReference<Reservation> typeReference) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(body, typeReference);
        } catch (JsonProcessingException e) {
            throw new ReservationServerException("Object mapper failed");
        }
    }
}

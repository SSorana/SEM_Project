package nl.tudelft.sem.template.cli.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.util.List;
import nl.tudelft.sem.template.cli.model.EquipmentResContainer;
import nl.tudelft.sem.template.cli.model.FieldResContainer;

/**
 * Sends requests to the reservation microservice.
 */
public class ReservationRequest extends SendRequest {

    /**
     * Gets all the field reservations.
     *
     * @return list of field reservations.
     */
    public static List<FieldResContainer> getFieldRes() throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            String response = sendGet(URI.create("http://localhost:8083/getFields"));
            return objectMapper.readValue(response, new TypeReference<List<FieldResContainer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Gets all the equipment reservations.
     *
     * @return list of equipment reservations.
     */
    public static List<EquipmentResContainer> getEquipmentRes() throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            String response = sendGet(URI.create("http://localhost:8083/getEquipment"));
            return objectMapper.readValue(response,
                    new TypeReference<List<EquipmentResContainer>>() {
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Makes a field reservation.
     *
     * @param container field reservation.
     * @return reservation id.
     */
    public static int makeFieldRes(FieldResContainer container) throws RuntimeException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            String json = mapper.writeValueAsString(container);
            String resposne = sendPost(URI.create("http://localhost:8083/makeFieldRes"), json);
            return mapper.readValue(resposne, new TypeReference<Integer>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Makes an equipment reservation.
     *
     * @param container equipment reservation.
     * @return reservation id.
     */
    public static int makeEquipmentRes(EquipmentResContainer container) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            String json = mapper.writeValueAsString(container);
            String resposne = sendPost(URI.create("http://localhost:8083/makeEquipmentRes"), json);
            return mapper.readValue(resposne, new TypeReference<Integer>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

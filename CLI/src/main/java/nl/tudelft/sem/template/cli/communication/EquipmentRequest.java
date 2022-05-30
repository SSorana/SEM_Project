package nl.tudelft.sem.template.cli.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import nl.tudelft.sem.template.cli.model.EquipmentContainer;

/**
 * Sends requests to the equipment microservice.
 */
public class EquipmentRequest extends SendRequest {

    /**
     * Gets all the equipment in the database.
     *
     * @return list of equipment.
     */
    public static List<EquipmentContainer> getAllEquipment() {
        String response = sendGet(URI.create("http://localhost:8081/getAll"));
        if (response == null) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            return objectMapper.readValue(response, new TypeReference<List<EquipmentContainer>>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

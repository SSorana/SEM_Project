package nl.tudelft.sem.template.cli.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import nl.tudelft.sem.template.cli.model.FieldContainer;

/**
 * Sends requests to the field microservice.
 */
public class FieldRequest extends SendRequest {

    /**
     * Gets all the fields in the database.
     *
     * @return list of fields.
     */
    public static List<FieldContainer> getAllFields() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            String response = sendGet(URI.create("http://localhost:8082/getAll"));
            return objectMapper.readValue(response, new TypeReference<List<FieldContainer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}

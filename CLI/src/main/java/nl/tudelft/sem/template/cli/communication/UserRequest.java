package nl.tudelft.sem.template.cli.communication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import nl.tudelft.sem.template.cli.model.UserContainer;

/**
 * Sends requests to the user microservice.
 */
public class UserRequest extends SendRequest {

    /**
     * Gets a user.
     *
     * @param username username of the user.
     * @return user.
     */
    public static UserContainer getUser(String username) throws RuntimeException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            String response = sendGet(URI.create("http://localhost:8084/getByUsername/"
                    + URLEncoder.encode(username, StandardCharsets.UTF_8)));

            return objectMapper.readValue(response, new TypeReference<UserContainer>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Creates a user.
     *
     * @param username username.
     * @param password password.
     * @return boolean.
     */
    public static boolean createUser(String username, String password) {
        return sendPost(URI.create("http://localhost:8084/createAccount/"
                + URLEncoder.encode(username, StandardCharsets.UTF_8)
                + "/"
                + URLEncoder.encode(password, StandardCharsets.UTF_8))) != null;
    }

    /**
     * Gets all users.
     *
     * @return list of users.
     */
    public static List<UserContainer> getAllUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        try {
            String response = sendGet(URI.create("http://localhost:8084/getAll"));
            return objectMapper.readValue(response, new TypeReference<List<UserContainer>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Subscribes to a basic account.
     *
     * @param username username.
     * @return acknowledgement.
     */
    public static boolean subscribeBasic(String username) {
        return sendPut(URI.create("http://localhost:8084/subscribeBasic/"
                + URLEncoder.encode(username, StandardCharsets.UTF_8))) != null;
    }

    /**
     * Subscribes to a premium account.
     *
     * @param username username.
     * @return acknowledgement.
     */
    public static boolean subscribePremium(String username) {
        return sendPut(URI.create("http://localhost:8084/subscribePremium/"
                + URLEncoder.encode(username, StandardCharsets.UTF_8))) != null;
    }

}

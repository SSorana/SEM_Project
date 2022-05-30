package nl.tudelft.sem.template.cli.communication;

import static nl.tudelft.sem.template.cli.constants.CliConstants.OK_STATUS;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import nl.tudelft.sem.template.cli.exceptions.SendException;
import nl.tudelft.sem.template.cli.exceptions.StatusException;

/**
 * Abstract class that houses the generic send logic.
 */
public abstract class SendRequest {
    protected static HttpClient client = HttpClient.newBuilder().build();

    /**
     * Generic send method for a get.
     *
     * @param uri uri of endpoint.
     * @return response body.
     */
    protected static String sendGet(URI uri) throws RuntimeException {
        HttpRequest request = HttpRequest.newBuilder().GET().uri(uri).build();
        return sendRequest(request);
    }

    /**
     * Generic send method for a post.
     *
     * @param uri  uri of endpoint.
     * @param json post body.
     * @return response body.
     */
    protected static String sendPost(URI uri, String json) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .uri(uri)
                .build();

        return sendRequest(request);
    }

    /**
     * Generic send method for a post without a body.
     *
     * @param uri uri of endpoint.
     * @return response body.
     */
    protected static String sendPost(URI uri) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .uri(uri)
                .build();

        return sendRequest(request);
    }

    /**
     * Generic send method for a put.
     *
     * @param uri uri of endpoint.
     * @return response body.
     */
    protected static String sendPut(URI uri) {
        HttpRequest request = HttpRequest
                .newBuilder()
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .uri(uri)
                .build();

        return sendRequest(request);

    }

    private static String sendRequest(HttpRequest request) {
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != OK_STATUS) {
                throw new StatusException("Status: " + response
                    + " Error message: " + response.body());
            }
            return response.body();

        } catch (IOException | InterruptedException e) {
            throw new SendException("Sending the request failed", e);
        }
    }

}

package nl.tudelft.sem.template.cli.exceptions;

/**
 * Exception for sending requests failing.
 */
public class SendException extends RuntimeException {

    public static final long serialVersionUID = -7593754069141821069L;

    public SendException(String message, Throwable err) {
        super(message, err);
    }
}

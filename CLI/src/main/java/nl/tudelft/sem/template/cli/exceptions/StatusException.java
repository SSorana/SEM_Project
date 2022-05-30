package nl.tudelft.sem.template.cli.exceptions;

/**
 * Exception for non 200 response statuses.
 */
public class StatusException extends RuntimeException {

    public static final long serialVersionUID = 794488858536022239L;

    public StatusException(String message) {
        super(message);
    }
}

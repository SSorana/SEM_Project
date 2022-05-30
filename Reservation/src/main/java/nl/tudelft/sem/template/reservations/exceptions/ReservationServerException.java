package nl.tudelft.sem.template.reservations.exceptions;

public class ReservationServerException extends RuntimeException {

    public static final long serialVersionUID = -4452041836339657842L;

    public ReservationServerException(String message) {
        super(message);
    }
}

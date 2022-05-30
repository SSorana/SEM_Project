package nl.tudelft.sem.template.reservations.exceptions;

public class ReservationInputException extends RuntimeException {

    public static final long serialVersionUID = 5452345777860075637L;

    public ReservationInputException(String message) {
        super(message);
    }
}

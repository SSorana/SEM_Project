package nl.tudelft.sem.template.reservations.factory;

import nl.tudelft.sem.template.reservations.entities.Reservation;


/**
 * Interface for factory methods.
 */
public interface ReservationFactory {

    /**
     * creates a Reservation.
     *
     * @param body request body.
     * @return reservation.
     */
    Reservation createReservation(String body);

    void validate(Reservation reservation);
}


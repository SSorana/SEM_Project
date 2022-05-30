package nl.tudelft.sem.template.reservations.factory;

import com.fasterxml.jackson.core.type.TypeReference;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import nl.tudelft.sem.template.reservations.services.ServiceCommunication;

/**
 * Factory for an equipment reservation.
 */
public class EquipmentReservationFactory implements ReservationFactory {

    private final transient ReservationService reservationService;
    private final transient ServiceCommunication serviceCommunication;


    /**
     * Instantiates a new Field reservation factory.
     */
    public EquipmentReservationFactory(
            ReservationService reservationService, ServiceCommunication serviceCommunication) {
        this.reservationService = reservationService;
        this.serviceCommunication = serviceCommunication;

    }

    /**
     * Maps the request body to an equipment reservation.
     *
     * @param body request body.
     * @return equipment reservation.
     */
    public Reservation createReservation(String body) {
        return Mapper.createReservation(body, new TypeReference<EquipmentReservation>() {
        });
    }

    /**
     * Validates the parameters of the reservation request.
     *
     * @param reservation reservation.
     */
    public void validate(Reservation reservation) {
        EquipmentReservation equipmentReservation = (EquipmentReservation) reservation;
        int quantity = serviceCommunication.getQuantity(equipmentReservation.getName());
        int count = reservationService.countByEquipment(equipmentReservation.getName(),
                equipmentReservation.getStartingTime(), equipmentReservation.getEndingTime());

        if (quantity - count - equipmentReservation.getQuantity() < 0) {
            throw new ReservationInputException("Item not available");
        }
        serviceCommunication.updateHistory(equipmentReservation.getName(),
                equipmentReservation.getUser());
    }
}

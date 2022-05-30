package nl.tudelft.sem.template.reservations.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.factory.ReservationFactory;
import nl.tudelft.sem.template.reservations.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * The type Reservation service.
 */
@Service
public class ReservationService {
    private final transient ReservationRepository reservationRepository;

    @Autowired
    private transient RestTemplate restTemplate;

    /**
     * Instantiates a new Reservation service.
     *
     * @param reservationRepository the reservation repository
     */
    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    /**
     * Saves a reservation.
     *
     * @param body    request body.
     * @param factory reservation factory.
     * @return reservation.
     */
    public Reservation save(String body, ReservationFactory factory) {
        Reservation reservation = factory.createReservation(body);
        checkReservations(reservation.getUser(),
                reservation.getStartingTime(), reservation.getEndingTime());
        factory.validate(reservation);
        reservationRepository.save(reservation);
        return reservation;

    }

    /**
     * Check reservations boolean.
     *
     * @param userName the username
     * @param start    the start
     * @param end      the end
     */
    private void checkReservations(String userName, LocalDateTime start, LocalDateTime end) {
        if (end.isBefore(start) || start.getHour() < 9
                || end.getHour() >= 23 && end.getMinute() > 0) {
            throw new ReservationInputException("Start or end time is outside of operating hours");
        }
    }

    /**
     * Gets by id.
     *
     * @param id the id
     * @return the by name
     */
    public Reservation getById(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        if (optionalReservation.isPresent()) {
            return optionalReservation.get();
        } else {
            throw new ReservationInputException("Reservation does not exist");
        }
    }

    /**
     * Gets all field reservations.
     *
     * @return list of field reservations.
     */
    public List<FieldReservation> getFields() {
        return reservationRepository.getFields();
    }

    /**
     * Gets all equipment reservations.
     *
     * @return list of equipment reservations.
     */
    public List<EquipmentReservation> getEquipment() {
        return reservationRepository.getEquipment();
    }

    /**
     * Counts how much equipment is reserved in a timespan.
     *
     * @param equipmentName equipment name.
     * @param start start time.
     * @param end end time.
     * @return amount of equipment gone.
     */
    public int countByEquipment(String equipmentName, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.countByEquipment(equipmentName, start, end);
    }

    /**
     * Checks if a field is already in use.
     *
     * @param fieldName field name.
     * @param start start time.
     * @param end end time.
     * @return how many times a field is reserved during a timespan.
     */
    public int countByField(String fieldName, LocalDateTime start, LocalDateTime end) {
        return reservationRepository.countByField(fieldName, start, end);
    }

    /**
     * Counts how many reservations a user has during a day.
     *
     * @param userName username.
     * @param startDate start time.
     * @param endDate end time.
     * @return how many reservations on a day.
     */
    public int countReservations(String userName, LocalDate startDate, LocalDate endDate) {
        return reservationRepository.countReservations(userName, startDate, endDate);
    }

}

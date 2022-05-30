package nl.tudelft.sem.template.reservations.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import nl.tudelft.sem.template.reservations.entities.User;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import nl.tudelft.sem.template.reservations.services.ServiceCommunication;
import org.springframework.stereotype.Component;

/**
 * Factory for a field reservation.
 */
@Component
public class FieldReservationFactory implements ReservationFactory {

    private final transient ReservationService reservationService;
    private final transient ServiceCommunication serviceCommunication;
    private static final int START_NORMAL_RESERVATIONS = 16;

    /**
     * Instantiates a new Field reservation factory.
     */
    public FieldReservationFactory(
            ReservationService reservationService, ServiceCommunication serviceCommunication) {
        this.reservationService = reservationService;
        this.serviceCommunication = serviceCommunication;
    }

    /**
     * Maps the request body to a field reservation.
     *
     * @param body request body.
     * @return field reservation.
     */
    @Override
    public Reservation createReservation(String body) {
        return Mapper.createReservation(body, new TypeReference<FieldReservation>() {
        });
    }

    /**
     * Validates the parameters of the reservation request.
     *
     * @param reservation reservation.
     */
    public void validate(Reservation reservation) {
        FieldReservation fieldReservation = (FieldReservation) reservation;
        if (!checkNumberOfReservations(serviceCommunication.getPremium(fieldReservation.getUser()),
                fieldReservation.getUser(), fieldReservation.getStartingTime())) {
            throw new ReservationInputException("Too many reservations made today");
        }

        String fieldName = fieldReservation.getFieldName();
        int count = reservationService
                .countByField(fieldName, fieldReservation.getStartingTime(),
                        fieldReservation.getEndingTime());
        if (fieldReservation.isLesson()) {
            validateLesson(fieldReservation, serviceCommunication.getMax(fieldName), count);
        } else {
            if (count > 0 || reservation.getStartingTime().getHour() < START_NORMAL_RESERVATIONS) {
                throw new ReservationInputException("Could not reserve field");
            } else {
                List<User> team = serviceCommunication.getTeam(fieldReservation.getTeamName());
                int min = serviceCommunication.getMin(fieldName);
                if (team.isEmpty()) {
                    int i = 1;
                    if (min > i) {
                        throw new ReservationInputException("Not enough players for field");
                    }
                } else {
                    validateTeam(fieldReservation,
                            serviceCommunication.getMax(fieldName), min, team);
                }
            }
        }
    }


    private void validateTeam(FieldReservation fr, int max, int min, List<User> team) {

        if (team.size() < min || team.size() > max) {
            throw new ReservationInputException("Team is not the correct size for field");
        }
        for (User u : team) {
            boolean isPremium = u.getType().equals(User.UserType.PREMIUM);
            if (!checkNumberOfReservations(isPremium,
                    u.getUserName(), fr.getStartingTime())) {
                throw new ReservationInputException("A team member has too many reservations");
            }
        }
    }


    private void validateLesson(FieldReservation fr, int max, int count) {
        if (count + 1 > max) {
            throw new ReservationInputException("The lesson is full");
        } else if (fr.getEndingTime().getHour() > START_NORMAL_RESERVATIONS) {
            throw new ReservationInputException("The lesson is outside of lesson hours");
        }
    }

    private boolean checkNumberOfReservations(boolean isPremium,
                                              String userName,
                                              LocalDateTime start) {

        LocalDate endDate = start.toLocalDate().plusDays(1);
        int numReservationsFound = reservationService
                .countReservations(userName, start.toLocalDate(), endDate);
        if (isPremium) {
            numReservationsFound -= 2;
        }
        return numReservationsFound <= 0;

    }
}

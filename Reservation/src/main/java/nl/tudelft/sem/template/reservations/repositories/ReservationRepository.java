package nl.tudelft.sem.template.reservations.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Reservation repository.
 */
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {


    /**
     * Counts how many reservations are at the same time
     * as the new reservation.
     *
     * @param fieldName the field name.
     * @return amount of reservations.
     */
    @Transactional
    @Query(value = "SELECT count(1) "
            + "FROM field_reservation AS fr "
            + "JOIN reservation AS r ON fr.reservation_id = r.reservation_id "
            + "WHERE fr.field_name = ?1 "
            + "AND ((r.starting_time <= ?2 AND r.ending_time > ?2) "
            + "OR (r.starting_time < ?3 AND r.ending_time >= ?3) "
            + "OR (r.starting_time > ?2 AND r.ending_time < ?3)) ",
            nativeQuery = true)
    int countByField(String fieldName, LocalDateTime start, LocalDateTime end);

    @Transactional
    @Query(value = "SELECT SUM(er.quantity) "
            + "FROM equipment_reservation AS er "
            + "JOIN reservation AS r ON er.reservation_id = r.reservation_id "
            + "WHERE er.equipment_name = ?1 "
            + "AND ((r.starting_time <= ?2 AND r.ending_time > ?2) "
            + "OR (r.starting_time < ?3 AND r.ending_time >= ?3) "
            + "OR (r.starting_time > ?2 AND r.ending_time < ?3)) ",
            nativeQuery = true)
    int countByEquipment(String equipmentName, LocalDateTime start, LocalDateTime end);

    /**
     * Count the amount of reservations
     * a user has made on a day.
     *
     * @param userName the username.
     * @return the int
     */
    @Transactional
    @Query(value = "SELECT count(1) "
            + "FROM reservation AS r "
            + "WHERE r.user_name = ?1 "
            + "AND r.starting_time >= ?2 "
            + "AND r.starting_time < ?3 ", nativeQuery = true)
    int countReservations(String userName, LocalDate startDate, LocalDate endDate);

    /**
     * Get all equipment reservations.
     *
     * @return list of equipment reservations.
     */
    @Transactional
    @Query(value = "SELECT * "
            + "FROM equipment_reservation AS er JOIN reservation AS r "
            + "ON er.reservation_id = r.reservation_id", nativeQuery = true)
    List<EquipmentReservation> getEquipment();

    /**
     * Get all field reservations.
     *
     * @return list of field reservations.
     */
    @Transactional
    @Query(value = "SELECT * "
            + "FROM field_reservation AS fr JOIN reservation AS r "
            + "ON fr.reservation_id = r.reservation_id", nativeQuery = true)
    List<FieldReservation> getFields();
}

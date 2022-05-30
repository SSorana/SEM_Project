package nl.tudelft.sem.template.reservations.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class ReservationRepositoryIntegrationTest {

    static FieldReservation fr;
    static EquipmentReservation er;
    static final LocalDateTime STARTING_TIME = LocalDateTime
            .of(2022, 10, 10, 16, 0);

    static final LocalDateTime ENDING_TIME = LocalDateTime
            .of(2022, 10, 10, 17, 0);

    @Autowired
    private transient ReservationRepository repository;

    @BeforeEach
    void setup() {
        fr = new FieldReservation();
        fr.setId(1L);
        fr.setStartingTime(STARTING_TIME);
        fr.setEndingTime(ENDING_TIME);
        fr.setUser("Kieran");
        fr.setFieldName("foo");
        fr.setTeamName("bar");
        fr.setLesson(true);

        er = new EquipmentReservation();
        er.setId(1L);
        er.setStartingTime(STARTING_TIME);
        er.setEndingTime(ENDING_TIME);
        er.setUser("Kieran");
        er.setName("foo");
        er.setQuantity(10);
        er.setPaid(true);
    }

    @Test
    void testSave() {
        repository.save(fr);
        List<Reservation> res = repository.findAll();
        res.forEach(x -> x.setId(1));
        assertEquals(List.of(fr), res);
    }

    @Test
    void testGetEquipment() {
        repository.save(er);
        List<EquipmentReservation> res = repository.getEquipment();
        res.forEach(x -> x.setId(1));
        assertEquals(List.of(er), res);
    }

    @Test
    void testGetField() {
        repository.save(fr);
        List<FieldReservation> res = repository.getFields();
        res.forEach(x -> x.setId(1));
        assertEquals(List.of(fr), res);
    }

    @Test
    void testCountReservations() {
        repository.save(er);
        repository.save(fr);
        assertEquals(2, repository
                .countReservations(fr.getUser(), STARTING_TIME.toLocalDate(),
                        ENDING_TIME.toLocalDate().plusDays(1)));
    }

    @Test
    void testCountFieldReservations() {
        repository.save(fr);
        assertEquals(1, repository
                .countByField(fr.getFieldName(), STARTING_TIME,
                        ENDING_TIME));

        assertEquals(1, repository
                .countByField(fr.getFieldName(), STARTING_TIME.minusMinutes(30),
                        ENDING_TIME.minusMinutes(30)));

        assertEquals(1, repository
                .countByField(fr.getFieldName(), STARTING_TIME.plusMinutes(30),
                        ENDING_TIME.plusMinutes(30)));

        assertEquals(0, repository
                .countByField(fr.getFieldName(), STARTING_TIME.plusHours(1),
                        ENDING_TIME.plusHours(1)));
    }


}

package nl.tudelft.sem.template.reservations.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for equipment reservation entity.
 */
class EquipmentReservationTest {

    static int id = 1;
    static LocalDateTime starting_time = LocalDateTime.MIN;
    static LocalDateTime ending_time = LocalDateTime.MAX;
    static boolean isPaid = true;
    static String user = "foobar";
    static String equipmentName = "foo";
    static int quantity = 10;
    static EquipmentReservation reservation;

    @BeforeEach
    void setup() {
        reservation = new EquipmentReservation();
        reservation.setId(id);
        reservation.setStartingTime(starting_time);
        reservation.setEndingTime(ending_time);
        reservation.setUser(user);
        reservation.setPaid(isPaid);
        reservation.setName(equipmentName);
        reservation.setQuantity(quantity);
    }

    @Test
    void noArgsConstructorTest() {
        EquipmentReservation reservation = new EquipmentReservation();
        assertNotNull(reservation);
    }

    @Test
    void getIsPaidTest() {
        assertEquals(isPaid, reservation.isPaid());
    }

    @Test
    void setIsPaidTest() {
        reservation.setPaid(false);
        assertFalse(reservation.isPaid());
    }

    @Test
    void getEquipmentName() {
        assertEquals(equipmentName, reservation.getName());
    }

    @Test
    void setEquipmentNameTest() {
        reservation.setName("bar");
        assertEquals("bar", reservation.getName());
    }

    @Test
    void getQuantity() {
        assertEquals(quantity, reservation.getQuantity());
    }

    @Test
    void setTeamNameTest() {
        reservation.setQuantity(5);
        assertEquals(5, reservation.getQuantity());
    }

    @Test
    void toStringTest() {
        String string = "Reservation{id=1, starting time=-999999999-01-01T00:00, "
                + "ending time=+999999999-12-31T23:59:59.999999999, "
                + "user=foobar} EquipmentReservation{isPaid=true, name=foo, quantity=10}";
        assertEquals(string, reservation.toString());
    }

    @Test
    void equalsSameObjectTest() {
        assertEquals(reservation, reservation);
    }


    @Test
    void notEqualsTest() {
        EquipmentReservation reservation2 = new EquipmentReservation();
        reservation2.setId(id);
        reservation2.setStartingTime(starting_time);
        reservation2.setEndingTime(ending_time);
        reservation2.setUser(user);
        reservation2.setPaid(isPaid);
        reservation2.setName(equipmentName);
        reservation2.setQuantity(5);
        assertNotEquals(reservation, reservation2);

        reservation2.setPaid(!isPaid);
        assertNotEquals(reservation, reservation2);

        reservation2.setPaid(isPaid);
        reservation2.setQuantity(quantity);
        reservation2.setName("bar");
        assertNotEquals(reservation, reservation2);

        reservation2.setName(equipmentName);
        reservation2.setId(3);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    void hashcodeTest() {
        assertEquals(-1432514342, reservation.hashCode());
    }

    @Test
    void equalsDifferentObjectTest() {
        EquipmentReservation reservation2 = new EquipmentReservation();
        reservation2.setId(id);
        reservation2.setStartingTime(starting_time);
        reservation2.setEndingTime(ending_time);
        reservation2.setUser(user);
        reservation2.setPaid(isPaid);
        reservation2.setName(equipmentName);
        reservation2.setQuantity(quantity);
        assertEquals(reservation, reservation2);
    }

    @Test
    void equalsNullOrDifferentObjectTest() {
        Object o = null;
        assertNotEquals(reservation, o);

        o = "";
        assertNotEquals(reservation, o);
    }
}

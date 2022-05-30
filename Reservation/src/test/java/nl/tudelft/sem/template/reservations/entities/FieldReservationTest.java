package nl.tudelft.sem.template.reservations.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for field reservation entity and abstract reservation entity.
 */
class FieldReservationTest {

    static final String BAR = "bar";
    static int id = 1;
    static LocalDateTime starting_time = LocalDateTime.MIN;
    static LocalDateTime ending_time = LocalDateTime.MAX;
    static boolean isLesson = true;
    static String user = "foobar";
    static String fieldName = "foo";
    static String teamName = BAR;
    static FieldReservation reservation;

    @BeforeEach
    void setup() {
        reservation = new FieldReservation();
        reservation.setId(id);
        reservation.setStartingTime(starting_time);
        reservation.setEndingTime(ending_time);
        reservation.setUser(user);
        reservation.setLesson(isLesson);
        reservation.setFieldName(fieldName);
        reservation.setTeamName(teamName);
    }

    @Test
    void noArgsConstructorTest() {
        FieldReservation reservation = new FieldReservation();
        assertNotNull(reservation);
    }

    @Test
    void getIdTest() {
        assertEquals(id, reservation.getId());
    }

    @Test
    void setIdTest() {
        reservation.setId(2);
        assertEquals(2, reservation.getId());
    }

    @Test
    void getStartTest() {
        assertEquals(starting_time, reservation.getStartingTime());
    }

    @Test
    void setStartTest() {
        reservation.setStartingTime(ending_time);
        assertEquals(ending_time, reservation.getStartingTime());
    }

    @Test
    void getEndTest() {
        assertEquals(ending_time, reservation.getEndingTime());
    }

    @Test
    void setEndTest() {
        reservation.setEndingTime(starting_time);
        assertEquals(starting_time, reservation.getEndingTime());
    }

    @Test
    void getUserTest() {
        assertEquals(user, reservation.getUser());
    }

    @Test
    void setUserTest() {
        reservation.setUser(BAR);
        assertEquals(BAR, reservation.getUser());
    }

    @Test
    void getIsLessonTest() {
        assertEquals(isLesson, reservation.isLesson());
    }

    @Test
    void setIsLessonTest() {
        reservation.setLesson(false);
        assertFalse(reservation.isLesson());
    }

    @Test
    void getFieldNameTest() {
        assertEquals(fieldName, reservation.getFieldName());
    }

    @Test
    void setFieldNameTest() {
        reservation.setFieldName(BAR);
        assertEquals(BAR, reservation.getFieldName());
    }

    @Test
    void getTeamNameTest() {
        assertEquals(teamName, reservation.getTeamName());
    }

    @Test
    void setTeamNameTest() {
        reservation.setTeamName("foo");
        assertEquals("foo", reservation.getTeamName());
    }

    @Test
    void toStringTest() {
        String string = "Reservation{id=1, starting time=-999999999-01-01T00:00, "
                + "ending time=+999999999-12-31T23:59:59.999999999, user=foobar} "
                + "FieldReservation{isLesson=true, fieldName=foo, teamName=bar}";
        assertEquals(string, reservation.toString());
    }

    @Test
    void equalsSameObjectTest() {
        assertEquals(reservation, reservation);
    }

    @Test
    void notEqualsTest() {
        FieldReservation reservation2 = new FieldReservation();
        reservation2.setId(id);
        reservation2.setStartingTime(starting_time);
        reservation2.setEndingTime(ending_time);
        reservation2.setUser("wobble");
        reservation2.setLesson(!isLesson);
        reservation2.setFieldName(teamName);
        reservation2.setTeamName(fieldName);
        assertNotEquals(reservation, reservation2);

        reservation2.setEndingTime(starting_time);
        assertNotEquals(reservation, reservation2);

        reservation2.setStartingTime(ending_time);
        assertNotEquals(reservation, reservation2);

        reservation2.setId(2);
        assertNotEquals(reservation, reservation2);

    }

    @Test
    void notEquals2Test() {
        FieldReservation reservation2 = new FieldReservation();
        reservation2.setId(id);
        reservation2.setStartingTime(starting_time);
        reservation2.setEndingTime(ending_time);
        reservation2.setUser(user);
        reservation2.setLesson(isLesson);
        reservation2.setFieldName(fieldName);
        reservation2.setTeamName(fieldName);
        assertNotEquals(reservation, reservation2);

        reservation2.setLesson(!isLesson);
        assertNotEquals(reservation, reservation2);

        reservation2.setLesson(isLesson);
        reservation2.setFieldName(teamName);
        assertNotEquals(reservation, reservation2);
    }

    @Test
    void hashcodeTest() {
        assertEquals(-1432417053, reservation.hashCode());
    }

    @Test
    void equalsDifferentObjectTest() {
        FieldReservation reservation2 = new FieldReservation();
        reservation2.setId(id);
        reservation2.setStartingTime(starting_time);
        reservation2.setEndingTime(ending_time);
        reservation2.setUser(user);
        reservation2.setLesson(isLesson);
        reservation2.setFieldName(fieldName);
        reservation2.setTeamName(teamName);
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


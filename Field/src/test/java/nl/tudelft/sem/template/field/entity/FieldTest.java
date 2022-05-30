package nl.tudelft.sem.template.field.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import nl.tudelft.sem.template.field.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldTest {
    static String testName = "name";
    static String testSportType = "sportType";
    static Field field;
    static Field field1;
    static Field field2;

    @BeforeEach
    void setup() {
        field = new Field();
        field.setName(testName);
        field.setMinCapacity(5);
        field.setMaxCapacity(50);
        field.setSportType(testSportType);
        field.setHall(false);

        field2 = new Field();
        field2.setName(testName);
        field2.setMinCapacity(5);
        field2.setMaxCapacity(50);
        field2.setSportType(testSportType);
        field2.setHall(false);

        field1 = new Field();
        field1.setName(testName);
        field1.setMinCapacity(10);
        field1.setMaxCapacity(70);
        field1.setSportType("tennis");
        field1.setHall(false);
    }

    @Test
    void noArgsConstructorTest() {
        assertNotNull(field);
    }

    @Test
    void getName() {
        assertEquals(testName, field.getName());
    }

    @Test
    void setName() {
        field.setName("hockeyField");
        assertEquals("hockeyField", field.getName());
    }

    @Test
    void getMinCapacity() {
        assertEquals(5, field.getMinCapacity());
    }

    @Test
    void setMinCapacity() {
        field.setMinCapacity(20);
        assertEquals(20, field.getMinCapacity());
    }

    @Test
    void getMaxCapacity() {
        assertEquals(50, field.getMaxCapacity());
    }

    @Test
    void setMaxCapacity() {
        field.setMaxCapacity(100);
        assertEquals(100, field.getMaxCapacity());
    }

    @Test
    void getSportType() {
        assertEquals(testSportType, field.getSportType());
    }

    @Test
    void setSportType() {
        field.setSportType("Tennis");
        assertEquals("Tennis", field.getSportType());
    }

    @Test
    void getIsHall() {
        field.setHall(true);
        assertTrue(field.isHall());
    }

    @Test
    void setHall() {
        field.setHall(true);
        assertTrue(field.isHall());
    }

    @Test
    void testToString() {
        String string = "Field{name='name', minCapacity=5, maxCapacity=50, sportType='sportType'}";
        assertEquals(string, field.toString());
    }

    @Test
    void testEqualsSameObject() {
        assertEquals(field, field);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(field, field1);

        field2.setSportType("hockey");
        assertNotEquals(field, field2);

        field2.setName("foo");
        assertNotEquals(field, field2);

        field2.setMaxCapacity(Integer.MAX_VALUE);
        assertNotEquals(field, field2);

        field2.setMaxCapacity(Integer.MIN_VALUE);
        assertNotEquals(field, field2);

    }

    @Test
    void testEqualsDifferentObject() {
        assertEquals(field2, field);
    }

    @Test
    void testHashCodeEquals() {
        assertEquals(field2.hashCode(), field.hashCode());
    }

    @Test
    void testHashCodeNotEquals() {
        assertNotEquals(field1.hashCode(), field.hashCode());
    }

    @Test
    void testDifferentObjectOrNull() {
        Object o = null;
        assertNotEquals(field, o);
        o = "";
        assertNotEquals(field, o);
    }
}
package nl.tudelft.sem.template.equipment.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EquipmentTest {

    private static final String nameTestString = "name";
    private static final String sportTypeTestString = "sportType";
    private static List<String> previousUsersTestList;
    private static Equipment equipment;
    private static Equipment equipment2;

    @BeforeAll
    static void init() {
        previousUsersTestList = new ArrayList<>();
        previousUsersTestList.add("User 1");
        previousUsersTestList.add("User 2");
    }

    @BeforeEach
    void initEach() {
        equipment = new Equipment();
        equipment.setName(nameTestString);
        equipment.setCapacity(10);
        equipment.setSportType(sportTypeTestString);
        equipment.setPrice(1.99);
        equipment.setPreviousUsers(previousUsersTestList);

        equipment2 = new Equipment();
        equipment2.setName(nameTestString);
        equipment2.setCapacity(10);
        equipment2.setSportType(sportTypeTestString);
        equipment2.setPrice(1.99);
        equipment2.setPreviousUsers(previousUsersTestList);
    }

    @Test
    void noArgsConstructorTest() {
        Equipment equipment = new Equipment();
        assertNotNull(equipment);
    }

    @Test
    void fiveArgsConstructorTest() {
        assertNotNull(equipment);
    }

    @Test
    void getName() {
        assertEquals(nameTestString, equipment.getName());
    }

    @Test
    void setName() {
        equipment.setName("newName");
        assertEquals("newName", equipment.getName());
    }

    @Test
    void getCapacity() {
        assertEquals(10, equipment.getCapacity());
    }

    @Test
    void setCapacity() {
        equipment.setCapacity(20);
        assertEquals(20, equipment.getCapacity());
    }

    @Test
    void getSportType() {
        assertEquals(sportTypeTestString, equipment.getSportType());
    }

    @Test
    void setSportType() {
        equipment.setSportType("newSportType");
        assertEquals("newSportType", equipment.getSportType());
    }

    @Test
    void getPrice() {
        assertEquals(1.99, equipment.getPrice());
    }

    @Test
    void setPrice() {
        equipment.setPrice(3.49);
        assertEquals(3.49, equipment.getPrice());
    }

    @Test
    void getPreviousUsers() {
        assertEquals(previousUsersTestList, equipment.getPreviousUsers());
    }

    @Test
    void setPreviousUsers() {
        List<String> previousUsers = new ArrayList<>();
        previousUsers.add("User 3");
        equipment.setPreviousUsers(previousUsers);
        assertEquals(previousUsers, equipment.getPreviousUsers());
    }

    @Test
    void addPreviousUser() {
        equipment.addPreviousUser("User 3");
        List<String> test = new ArrayList<>();
        test.add("User 1");
        test.add("User 2");
        test.add("User 3");
        assertEquals(test, equipment.getPreviousUsers());
    }

    @Test
    void addPreviousUserNull() {
        equipment.setPreviousUsers(new ArrayList<>());
        equipment.addPreviousUser("User");
        List<String> test = new ArrayList<>();
        test.add("User");
        assertEquals(test, equipment.getPreviousUsers());
    }

    @Test
    void testToString() {
        String string = "Equipment{name='name', capacity=10, sportType='sportType', "
                + "price=1.99, previousUsers=[User 1, User 2]}";
        assertEquals(string, equipment.toString());
    }

    @Test
    void testEqualsSame() {
        assertEquals(equipment, equipment);
    }

    @Test
    void testEqualsOther() {
        assertEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentCapacity() {
        equipment.setCapacity(5);
        assertNotEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentPrice() {
        equipment.setPrice(2.99);
        assertNotEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentName() {
        equipment.setName("name 2");
        assertNotEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentSportType() {
        equipment.setSportType("sportType 2");
        assertNotEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentPreviousUsers() {
        equipment.setPreviousUsers(new ArrayList<>());
        assertNotEquals(equipment, equipment2);
    }

    @Test
    void testEqualsDifferentClass() {
        assertNotEquals(equipment, new ArrayList<>());
    }

    @Test
    void testHashCodeEquals() {
        assertEquals(equipment.hashCode(), equipment2.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        equipment.setCapacity(5);
        assertNotEquals(equipment.hashCode(), equipment2.hashCode());
    }

    @Test
    public void testHashCodeNameNull() {
        equipment.setName(null);
        assertNotEquals(equipment.hashCode(), equipment2.hashCode());
    }

    @Test
    public void testHashCodeSportTypeNull() {
        equipment.setSportType(null);
        assertNotEquals(equipment.hashCode(), equipment2.hashCode());
    }

    @Test
    public void testHashCodePreviousUsersNull() {
        equipment.setPreviousUsers(null);
        assertNotEquals(equipment.hashCode(), equipment2.hashCode());
    }
}
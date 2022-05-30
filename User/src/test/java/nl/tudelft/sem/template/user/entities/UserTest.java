package nl.tudelft.sem.template.user.entities;

import static nl.tudelft.sem.template.user.entities.User.UserType.BASIC;
import static nl.tudelft.sem.template.user.entities.User.UserType.PREMIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserTest {

    private static User user;
    private static User user1;
    private static User user2;


    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        user = new User();
        user.setUserName("jasetho");
        user.setPassword("pass");
        user.setType(BASIC);
        user.setLoginAttempt(1);

        user1 = new User();
        user1.setUserName("slack");
        user1.setPassword("ing");
        user1.setType(BASIC);

        user2 = new User();
        user2.setUserName("slack");
        user2.setPassword("ing");
        user2.setType(BASIC);

    }

    @Test
    public void constructorTest() {
        assertNotNull(user);
    }

    @Test
    public void getUserNameTest() {
        assertEquals("jasetho", user.getUserName());
    }

    @Test
    public void getPasswordTest() {
        assertEquals("pass", user.getPassword());
    }

    @Test
    public void getTypeTest() {
        assertEquals(BASIC, user.getType());
    }

    @Test
    public void getLoginTest() {
        assertEquals(1, user.getLoginAttempt());
    }

    @Test
    void testToString() {
        String string = "User{userName='jasetho', password='pass', type=BASIC}";

        assertEquals(string, user.toString());
    }

    @Test
    void testEqualsSame() {
        assertEquals(user, user);
    }

    @Test
    void testNotEqualsOtherClassOrNull() {
        Object o = null;
        assertNotEquals(user, o);

        o = "";
        assertNotEquals(user, o);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(user, user1);

        user2.setType(PREMIUM);
        assertNotEquals(user1, user2);

        user2.setPassword("foo");
        assertNotEquals(user1, user2);

        user2.setUserName("bar");
        assertNotEquals(user1, user2);
    }

    @Test
    void testHashCodeEquals() {
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void testHashCodeNotEquals() {
        assertNotEquals(user.hashCode(), user1.hashCode());
    }
}
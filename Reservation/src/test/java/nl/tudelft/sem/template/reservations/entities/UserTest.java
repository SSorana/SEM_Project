package nl.tudelft.sem.template.reservations.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Tests for user dto.
 */
public class UserTest {
    static final String FOOBAR = "foobar";
    static String username = "foo";
    static String password = "bar";
    static  User.UserType userType = User.UserType.BASIC;
    transient User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setType(userType);
    }

    @Test
    void noArgsConstructorTest() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    void getUsernameTest() {
        assertEquals(username, user.getUserName());
    }

    @Test
    void setUsernameTest() {
        user.setUserName(FOOBAR);
        assertEquals(FOOBAR, user.getUserName());
    }

    @Test
    void getPasswordTest() {
        assertEquals(password, user.getPassword());
    }

    @Test
    void setPasswordTest() {
        user.setPassword(FOOBAR);
        assertEquals(FOOBAR, user.getPassword());
    }

    @Test
    void getTypeTest() {
        assertEquals(userType, user.getType());
    }

    @Test
    void setTypeTest() {
        user.setType(User.UserType.PREMIUM);
        assertEquals(User.UserType.PREMIUM, user.getType());

        user.setType(User.UserType.ADMIN);
        assertEquals(User.UserType.ADMIN, user.getType());
    }

    @Test
    void toStringTest() {
        String string = "User{userName='foo', password='bar', type=BASIC}";
        assertEquals(string, user.toString());
    }

    @Test
    void equalsSameObjectTest() {
        assertEquals(user, user);
    }

    @Test
    void notEqualsTest() {
        User user2 = new User();
        user2.setUserName(username);
        user2.setPassword(password);
        user2.setType(User.UserType.ADMIN);
        assertNotEquals(user, user2);

        user2.setPassword(username);
        assertNotEquals(user, user2);

        user2.setUserName(password);
        assertNotEquals(user, user2);

    }

    @Test
    void hashcodeTest() {
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    void equalsDifferentObjectTest() {
        User user2 = new User();
        user2.setUserName(username);
        user2.setPassword(password);
        user2.setType(userType);
        assertEquals(user, user2);
    }

    @Test
    void equalsNullOrDifferentObjectTest() {

        Object o = null;
        assertNotEquals(user, o);

        o = "";
        assertNotEquals(user, o);
    }

}

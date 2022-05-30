package nl.tudelft.sem.template.reservations.entities;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

/**
 * The type User.
 */
@Getter
@Setter
public class User {

    private String userName;

    private String password;

    private UserType type;

    /**
     * The enum User type.
     */
    public enum UserType {
        /**
         * Basic user type.
         */
        BASIC,
        /**
         * Premium user type.
         */
        PREMIUM,
        /**
         * Admin user type.
         */
        ADMIN
    }

    /**
     * Instantiates a new User.
     */
    public User() {
    }

    @Override
    public String toString() {
        return "User{"
                + "userName='"
                + userName
                + '\''
                + ", password='"
                + password
                + '\''
                + ", type="
                + type
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userName.equals(user.userName)
                && password.equals(user.password)
                && type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, type);
    }
}


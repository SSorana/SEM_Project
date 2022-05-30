package nl.tudelft.sem.template.user.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "loginAttempt")
    private Integer loginAttempt;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type")
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
        return this.userName.equals(user.userName)
                && this.password.equals(user.password)
                && this.type.equals(user.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, type);
    }
}


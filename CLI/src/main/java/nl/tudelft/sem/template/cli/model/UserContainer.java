package nl.tudelft.sem.template.cli.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContainer {

    private String userName;
    private String password;
    private UserType type;

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

}

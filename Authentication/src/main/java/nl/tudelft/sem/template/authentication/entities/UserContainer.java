package nl.tudelft.sem.template.authentication.entities;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserContainer {
    private String userName;
    private String password;
    private Role type;
    private int loginAttempt;

    public List<String> getRole() {
        return List.of(type.toString());
    }

    public enum Role {
        BASIC, PREMIUM, ADMIN
    }
}

package nl.tudelft.sem.template.authentication.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String username;
    private String password;
}

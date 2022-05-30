package nl.tudelft.sem.template.authentication.service;

import javax.servlet.http.HttpServletRequest;
import nl.tudelft.sem.template.authentication.entities.UserRequest;

public interface LoginServiceInterface {

    String login(UserRequest userRequest, HttpServletRequest request);

}

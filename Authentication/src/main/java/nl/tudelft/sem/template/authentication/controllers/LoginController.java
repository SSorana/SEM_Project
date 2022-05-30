package nl.tudelft.sem.template.authentication.controllers;

import static org.springframework.http.ResponseEntity.ok;

import javax.servlet.http.HttpServletRequest;
import nl.tudelft.sem.template.authentication.entities.UserRequest;
import nl.tudelft.sem.template.authentication.service.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/authentication")
public class LoginController {

    private final transient LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Login entry point for a user.
     *
     * @param userRequest container of username and password.
     * @param request     http request.
     * @return token if login is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserRequest userRequest,
                                            HttpServletRequest request) {
        try {
            return ok().body(loginService.login(userRequest, request));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/test")
    public String test() {
        return "test done";
    }
}

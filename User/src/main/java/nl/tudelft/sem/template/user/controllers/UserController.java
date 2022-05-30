package nl.tudelft.sem.template.user.controllers;

import java.util.List;
import nl.tudelft.sem.template.user.entities.User;
import nl.tudelft.sem.template.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The type User controller.
 */
@RestController
@RequestMapping(path = "/")
public class UserController {

    private final transient UserService userService;

    /**
     * Instantiates a new User controller.
     *
     * @param userService the user service
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping("getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    /**
     * Subscribe basic.
     *
     * @param userName the username
     */
    @PutMapping("subscribeBasic/{userName}")
    public String subscribeBasic(@PathVariable String userName) {

        userService.subscribeBasic(userName);
        return "Saved";
    }

    /**
     * Subscribe premium.
     *
     * @param userName the user name
     */
    @PutMapping("subscribePremium/{userName}")
    public String subscribePremium(@PathVariable String userName) {

        userService.subscribePremium(userName);
        return "Saved";
    }

    /**
     * Gets by username.
     *
     * @param userName the username
     * @return the by username
     */
    @GetMapping("getByUsername/{userName}")
    public ResponseEntity<?> getByUsername(@PathVariable String userName) {

        try {
            User user = userService.getByUsername(userName);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a new account.
     *
     * @param userName the username
     * @param password the password
     */
    @PostMapping("createAccount/{userName}/{password}")
    public String createAccount(@PathVariable String userName, @PathVariable String password) {
        userService.createAccount(userName, password);
        return "Account made";
    }

    /**
     * Is premium boolean.
     *
     * @param userName the user name
     * @return the boolean
     */
    @GetMapping("isPremium/{userName}")
    public boolean isPremium(@PathVariable String userName) {
        return userService.isPremium(userName);
    }


}

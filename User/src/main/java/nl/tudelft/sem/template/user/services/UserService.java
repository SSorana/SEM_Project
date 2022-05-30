package nl.tudelft.sem.template.user.services;

import static nl.tudelft.sem.template.user.entities.User.UserType.BASIC;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.user.entities.User;
import nl.tudelft.sem.template.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * The type User service.
 */
@Service
public class UserService {

    private final transient UserRepository userRepository;

    /**
     * Instantiates a new User service.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    /**
     * Gets by username.
     *
     * @param userName the username
     * @return the by username
     */
    public User getByUsername(String userName) {
        User user =  userRepository.getByUsername(userName);
        if (user == null) {
            throw  new RuntimeException("User does not exist");
        }
        return user;
    }
    
    /**
     * Subscribe basic.
     *
     * @param userName the user name
     */
    public String subscribeBasic(String userName) {

        userRepository.subscribeBasic(userName);
        return "Saved";
    }

    /**
     * Subscribe premium.
     *
     * @param userName the user name
     */
    public String subscribePremium(String userName) {

        userRepository.subscribePremium(userName);
        return "Saved";
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    public List<User> getAll() {

        return userRepository.findAll();
    }

    /**
     * Create a new account.
     *
     * @param userName the username
     * @param password the password
     */
    public String createAccount(String userName, String password) {
        User user = new User();
        user.setUserName(userName);

        password = BCrypt.hashpw(password, BCrypt.gensalt());
        user.setPassword(password);
        user.setType(BASIC);
        user.setLoginAttempt(0);
        userRepository.save(user);
        return "Account made";
    }

    /**
     * Is premium boolean.
     *
     * @param userName the username
     * @return the boolean
     */
    public boolean isPremium(String userName) {
        int count = userRepository.isPremium(userName);
        return count == 1;
    }
}

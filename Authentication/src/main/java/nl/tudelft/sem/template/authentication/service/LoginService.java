package nl.tudelft.sem.template.authentication.service;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.servlet.http.HttpServletRequest;
import nl.tudelft.sem.template.authentication.entities.UserContainer;
import nl.tudelft.sem.template.authentication.entities.UserRequest;
import nl.tudelft.sem.template.authentication.security.jwt.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional("transactionManager")
@SuppressWarnings("PMD")
public class LoginService implements LoginServiceInterface {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    RequestService requestService;


    @Override
    public String login(UserRequest userRequest, HttpServletRequest request) {

        LOGGER.info("LOGIN PROCESS STARTED ::::");

        UserContainer user = fetchUserDetails.apply(userRequest);

        validateUserUsername.accept(user);

        validatePassword.accept(userRequest, user);

        //
        String jwtToken = jwtTokenProvider.createToken(userRequest.getUsername(), request);


        return jwtToken;
    }

    //function still to be done: get the data of the user
    private Function<UserRequest, UserContainer> fetchUserDetails = (userRequest) -> {
        return requestService.getByUsername(userRequest.getUsername()).orElse(null);
    };

    private Consumer<UserContainer> validateUserUsername = (user) -> {
        if (Objects.isNull(user)) {
            throw new RuntimeException("Invalid username");
        }


        LOGGER.info(":::: ADMIN USERNAME VALIDATED ::::");
    };


    private BiConsumer<UserRequest, UserContainer> validatePassword = (userRequest, user) -> {

        LOGGER.info(":::: ADMIN PASSWORD VALIDATION ::::");

        if (BCrypt.checkpw(userRequest.getPassword(), user.getPassword())) {
            user.setLoginAttempt(0);
            //requestService.updateUser(user);
        } else {
            user.setLoginAttempt(user.getLoginAttempt() + 1);

            if (user.getLoginAttempt() >= 3) {

                throw new RuntimeException("Too many wrong password attempts");

            }

            LOGGER.debug("INCORRECT PASSWORD...");

            throw new RuntimeException("Incorrect password");

        }

        LOGGER.info(":::: ADMIN PASSWORD VALIDATED ::::");
    };

}



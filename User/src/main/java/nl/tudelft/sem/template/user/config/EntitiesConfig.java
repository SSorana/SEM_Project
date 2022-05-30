package nl.tudelft.sem.template.user.config;

import nl.tudelft.sem.template.user.entities.User;
import nl.tudelft.sem.template.user.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;


@Configuration
public class EntitiesConfig {

    /**
     * Command line runner.
     *
     * @param userRepository  the user repository.
     * @return the command line runner.
     */
    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = new User();
            user.setUserName("Kieran");
            user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
            user.setType(User.UserType.ADMIN);
            user.setLoginAttempt(0);
            userRepository.save(user);
        };
    }
}


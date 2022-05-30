package nl.tudelft.sem.template.reservations.config;

import nl.tudelft.sem.template.reservations.repositories.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EntitiesConfig {

    /**
     * Command line runner command line runner.
     *
     * @param reservationRepository        the user repository
     * @return the command line runner
     */
    @Bean
    CommandLineRunner commandLineRunner(ReservationRepository reservationRepository) {
        return args -> {
        };
    }
}


package nl.tudelft.sem.template.field.config;


import nl.tudelft.sem.template.field.entities.Field;
import nl.tudelft.sem.template.field.repositories.FieldRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntitiesConfig {

    /**
     * Command line runner command line runner.
     *
     * @param userRepository        the user repository
     * @return the command line runner
     */
    @Bean
    CommandLineRunner commandLineRunner(FieldRepository fieldRepository) {
        return args -> {
            Field field1 = new Field();
            field1.setName("iets");
            field1.setMinCapacity(0);
            field1.setMaxCapacity(5);
            field1.setSportType("hockey");
            field1.setHall(true);
            fieldRepository.save(field1);
        };
    }
}


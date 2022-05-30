package nl.tudelft.sem.template.equipment.config;


import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EntitiesConfig {

    /**
     * Command line runner command line runner.
     *
     * @param equipmentRepository        the user repository
     * @return the command line runner
     */
    @Bean
    CommandLineRunner commandLineRunner(EquipmentRepository equipmentRepository) {
        return args -> {
            Equipment e1 = new Equipment();
            e1.setName("stick");
            e1.setCapacity(10);
            equipmentRepository.save(e1);
            Equipment e2 = new Equipment();
            e2.setName("stick5");
            e2.setCapacity(100);
            equipmentRepository.save(e2);
        };
    }
}


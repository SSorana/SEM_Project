package nl.tudelft.sem.template.equipment.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class EquipmentRepositoryIntegrationTests {
    static Equipment equipment;

    @Autowired
    private transient EquipmentRepository repository;

    @BeforeEach
    void setup() {
        equipment = new Equipment();
        equipment.setName("foo");
        equipment.setSportType("bar");
        equipment.setPrice(2.0);
        equipment.setCapacity(10);
        equipment.setPreviousUsers(new ArrayList<>());
    }

    @Test
    void testGetByName() {
        repository.save(equipment);
        assertEquals(equipment, repository.getByName(equipment.getName()).get());
    }

    @Test
    void testFindAll() {
        List<Equipment> list = new ArrayList<>();
        list.add(equipment);
        repository.save(equipment);
        assertEquals(list, repository.findAll());
    }
}

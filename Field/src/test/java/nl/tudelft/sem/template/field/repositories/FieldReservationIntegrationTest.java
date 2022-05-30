package nl.tudelft.sem.template.field.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.field.entities.Field;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class FieldReservationIntegrationTest {

    static Field field;

    @Autowired
    private transient FieldRepository repository;

    @BeforeEach
    void setup() {
        field = new Field();
        field.setName("foo");
        field.setMinCapacity(0);
        field.setMaxCapacity(10);
        field.setSportType("hockey");
        field.setHall(true);
    }

    @Test
    void getByNameTest() {
        repository.save(field);
        assertEquals(field, repository.getByName(field.getName()));
    }

    @Test
    void getMinByNameTest() {
        repository.save(field);
        assertEquals(field.getMinCapacity(), repository.getMinByName(field.getName()));
    }

    @Test
    void getMaxByNameTest() {
        repository.save(field);
        assertEquals(field.getMaxCapacity(), repository.getMaxByName(field.getName()));
    }

}

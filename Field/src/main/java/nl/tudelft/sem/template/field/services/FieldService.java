package nl.tudelft.sem.template.field.services;

import java.util.List;
import nl.tudelft.sem.template.field.entities.Field;
import nl.tudelft.sem.template.field.repositories.FieldRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.stereotype.Service;

/**
 * The type Field service.
 */
@Service
public class FieldService {
    
    private final transient FieldRepository fieldRepository;

    /**
     * Instantiates a new Field service.
     *
     * @param fieldRepository the field repository
     */
    @Autowired
    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }


    /**
     * Gets all.
     *
     * @return the all
     */
    public List<Field> getAll() {
        return fieldRepository.findAll();
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    public Field getByName(String name) {
        return fieldRepository.getByName(name);
    }

    /**
     * Gets min by name.
     *
     * @param name the name
     * @return the min by name
     */
    public int getMinByName(String name) {
        return fieldRepository.getMinByName(name);
    }

    /**
     * Gets max by name.
     *
     * @param name the name
     * @return the max by name
     */
    public int getMaxByName(String name) {
        return fieldRepository.getMaxByName(name);
    }
}
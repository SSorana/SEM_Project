package nl.tudelft.sem.template.field.controllers;

import java.util.List;
import nl.tudelft.sem.template.field.entities.Field;
import nl.tudelft.sem.template.field.services.FieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Transient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Field controller.
 */
@RestController
@RequestMapping(path = "/")
public class FieldController {

    private final transient FieldService fieldService;

    /**
     * Instantiates a new Field controller.
     *
     * @param fieldService the field service
     */
    @Autowired
    public FieldController(FieldService fieldService) {
        this.fieldService = fieldService;
    }

    /**
     * Gets all.
     *
     * @return the all
     */
    @GetMapping("getAll")
    public List<Field> getAll() {
        return fieldService.getAll();
    }

    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    @GetMapping("getByName/{name}")
    public Field getByName(@PathVariable String name) {
        return fieldService.getByName(name);
    }

    /**
     * Gets min by name.
     *
     * @param name the name
     * @return the min by name
     */
    @GetMapping("getMinByName/{name}")
    public int getMinByName(@PathVariable String name) {
        return fieldService.getMinByName(name);
    }

    /**
     * Gets max by name.
     *
     * @param name the name
     * @return the max by name
     */
    @GetMapping("getMaxByName/{name}")
    public int getMaxByName(@PathVariable String name) {
        return fieldService.getMaxByName(name);
    }
}
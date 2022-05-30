package nl.tudelft.sem.template.equipment.controllers;

import java.util.List;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.exceptions.EquipmentInputException;
import nl.tudelft.sem.template.equipment.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Equipment controller.
 */
@RestController
@RequestMapping(path = "/")
public class EquipmentController {

    private final transient EquipmentService equipmentService;

    /**
     * Instantiates a new Equipment controller.
     *
     * @param equipmentService the equipment service
     */
    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    /**
     * Gets all pieces of equipment.
     *
     * @return All pieces of equipment
     */
    @GetMapping("getAll")
    public ResponseEntity<?> getAll() {

        return new ResponseEntity<>(equipmentService.getAll(), HttpStatus.OK);
    }

    /**
     * Gets a specific piece of equipment by name.
     *
     * @param name the name
     * @return the piece of equipment, null if equipment is not found
     */
    @GetMapping("getByName/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name) {
        try {
            Equipment equipment = equipmentService.getByName(name);
            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (EquipmentInputException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Get the list of previous users for the piece of equipment with the given name.
     *
     * @param name Name of the equipment
     * @return List of previous users
     */
    @GetMapping("admin/getHistory/{name}")
    public ResponseEntity<?> getHistory(@PathVariable String name) {
        try {
            List<String> history = equipmentService.getHistory(name);
            return new ResponseEntity<>(history, HttpStatus.OK);
        } catch (EquipmentInputException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Add a user to the list of previous users for the piece of equipment with the given name.
     *
     * @param name     Name of the equipment
     * @param userName Name of the user
     * @return Updated piece of equipment
     */
    @PutMapping("addUser/{name}/{userName}")
    public ResponseEntity<?> addUser(@PathVariable String name, @PathVariable String userName) {
        try {
            Equipment equipment = equipmentService.addUser(name, userName);
            return new ResponseEntity<>(equipment, HttpStatus.OK);
        } catch (EquipmentInputException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Gets capacity of the piece of equipment with the given name.
     *
     * @param name Name of the equipment
     * @return Capacity of the equipment, 0 if equipment is not found
     */
    @GetMapping("getCapacity/{name}")
    public ResponseEntity<?> getCapacity(@PathVariable String name) {

        return new ResponseEntity<>(equipmentService.getCapacity(name), HttpStatus.OK);
    }
}

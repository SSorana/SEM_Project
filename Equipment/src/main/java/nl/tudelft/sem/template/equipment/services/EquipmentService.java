package nl.tudelft.sem.template.equipment.services;

import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.exceptions.EquipmentInputException;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Equipment service.
 */
@Service
public class EquipmentService {

    private final transient EquipmentRepository equipmentRepository;

    /**
     * Instantiates a new Equipment service.
     *
     * @param equipmentRepository the equipment repository
     */
    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }


    /**
     * Gets all pieces of equipment.
     *
     * @return All pieces of equipment
     */
    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    /**
     * Gets a specific piece of equipment by name.
     *
     * @param name the name
     * @return the piece of equipment
     */
    public Equipment getByName(String name) {
        Optional<Equipment> equipment = equipmentRepository.getByName(name);
        return equipment.orElseThrow(() -> new EquipmentInputException("Equipment does not exist"));
    }

    /**
     * Get the list of previous users for the piece of equipment with the given name.
     *
     * @param name Name of the equipment
     * @return List of previous users
     */
    public List<String> getHistory(String name) {
        Optional<Equipment> equipment = equipmentRepository.getByName(name);
        if (equipment.isPresent()) {
            return equipment.get().getPreviousUsers();
        }
        throw new EquipmentInputException("Equipment does not exist");
    }

    /**
     * Add a user to the list of previous users for the piece of equipment with the given name.
     *
     * @param name     Name of the equipment
     * @param userName Name of the user
     * @return Updated piece of equipment
     */
    public Equipment addUser(String name, String userName) {
        Optional<Equipment> equipmentOptional = equipmentRepository.getByName(name);
        if (equipmentOptional.isPresent()) {
            Equipment equipment = equipmentOptional.get();
            equipment.addPreviousUser(userName);
            equipmentRepository.save(equipment);
            return equipment;
        }
        throw new EquipmentInputException("Equipment does not exist");
    }

    /**
     * Gets capacity of the piece of equipment with the given name.
     *
     * @param name Name of the equipment
     * @return Capacity of the equipment, 0 if equipment is not found
     */
    public int getCapacity(String name) {
        Optional<Equipment> equipmentOptional = equipmentRepository.getByName(name);
        if (equipmentOptional.isPresent()) {
            return equipmentOptional.get().getCapacity();
        }
        return 0;
    }
}

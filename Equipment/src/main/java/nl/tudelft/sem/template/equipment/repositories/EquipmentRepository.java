package nl.tudelft.sem.template.equipment.repositories;

import java.util.Optional;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Equipment repository.
 */
@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {


    /**
     * Gets a piece of equipment by name.
     *
     * @param name Name of the equipment
     * @return Piece of equipment with the given name
     */
    Optional<Equipment> getByName(String name);
}

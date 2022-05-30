package nl.tudelft.sem.template.field.repositories;

import nl.tudelft.sem.template.field.entities.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Field repository.
 */
public interface FieldRepository extends JpaRepository<Field, Long> {


    /**
     * Gets by name.
     *
     * @param name the name
     * @return the by name
     */
    @Transactional
    @Query(value = "SELECT * FROM Field WHERE name = ?1", nativeQuery = true)
    Field getByName(String name);

    /**
     * Gets max by name.
     *
     * @param name the name
     * @return the max by name
     */
    @Transactional
    @Query(value = "SELECT max_capacity FROM Field WHERE name = ?1", nativeQuery = true)
    int getMaxByName(String name);

    /**
     * Gets min by name.
     *
     * @param name the name
     * @return the min by name
     */
    @Transactional
    @Query(value = "SELECT min_capacity FROM Field WHERE name = ?1", nativeQuery = true)
    int getMinByName(String name);
}
package nl.tudelft.sem.template.user.repositories;

import nl.tudelft.sem.template.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface User repository.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Subscribe basic.
     *
     * @param userName the username
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET type = 0 WHERE user_name = ?1", nativeQuery = true)
    void subscribeBasic(String userName);

    /**
     * Subscribe premium.
     *
     * @param userName the username
     */
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE users SET type = 1 WHERE user_name = ?1", nativeQuery = true)
    void subscribePremium(String userName);

    /**
     * Gets by username.
     *
     * @param userName the user name
     * @return the by username
     */
    @Transactional
    @Query(value = "SELECT * FROM users WHERE user_name = ?1", nativeQuery = true)
    User getByUsername(String userName);

    /**
     * Is premium int.
     *
     * @param userName the user name
     * @return the int
     */
    @Transactional
    @Query(value = "SELECT count(*) "
            + "FROM users "
            + "WHERE user_name = ?1 "
            + "AND type = 1", nativeQuery = true)
    int isPremium(String userName);
}




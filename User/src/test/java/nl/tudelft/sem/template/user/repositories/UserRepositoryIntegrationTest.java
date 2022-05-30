package nl.tudelft.sem.template.user.repositories;

import static nl.tudelft.sem.template.user.entities.User.UserType.BASIC;
import static nl.tudelft.sem.template.user.entities.User.UserType.PREMIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.sem.template.user.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    static User user;

    @Autowired
    private transient UserRepository userRepository;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUserName("foo");
        user.setPassword("bar");
        user.setLoginAttempt(0);
        user.setType(BASIC);
    }

    @Test
    void testGetByName() {
        userRepository.save(user);
        assertEquals(user, userRepository.getByUsername(user.getUserName()));
    }

    @Test
    void testSubscribePremium() {
        userRepository.save(user);
        userRepository.subscribePremium(user.getUserName());
        User user1 = userRepository.getByUsername(user.getUserName());
        assertEquals(PREMIUM, user1.getType());
    }

    @Test
    void testSubscribeBasic() {
        user.setType(PREMIUM);
        userRepository.save(user);
        userRepository.subscribeBasic(user.getUserName());
        User user1 = userRepository.getByUsername(user.getUserName());
        assertEquals(BASIC, user1.getType());
    }

    @Test
    void testIsPremium() {
        user.setType(PREMIUM);
        userRepository.save(user);
        int res = userRepository.isPremium(user.getUserName());
        assertEquals(1, res);
    }

    @Test
    void testIsNotPremium() {
        userRepository.save(user);
        int res = userRepository.isPremium(user.getUserName());
        assertEquals(0, res);
    }

}

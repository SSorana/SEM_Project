package nl.tudelft.sem.template.user.services;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static nl.tudelft.sem.template.user.entities.User.UserType.BASIC;
import static nl.tudelft.sem.template.user.entities.User.UserType.PREMIUM;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.user.entities.User;
import nl.tudelft.sem.template.user.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class UserServiceTest {

    private static User user;
    private static User user1;
    private static final String USERNAME = "jasetho";
    private static final String CREATE_RESPONSE = "Account made";

    @Mock
    transient UserRepository userRepository;

    @InjectMocks
    transient UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setUserName("jasetho");
        user.setPassword("pass");
        user.setType(BASIC);

        user1 = new User();
        user1.setUserName("foo");
        user1.setPassword("bar");
        user1.setType(PREMIUM);
    }

    @Test
    void createAccountTest() {
        String response = userService.createAccount("userName", "password");

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(argumentCaptor.capture());
        assertEquals("userName", argumentCaptor.getValue().getUserName());
        assertTrue(BCrypt.checkpw("password", argumentCaptor.getValue().getPassword()));
        assertEquals(BASIC, argumentCaptor.getValue().getType());
        assertEquals(0, argumentCaptor.getValue().getLoginAttempt());

        assertEquals(CREATE_RESPONSE, response);
    }

    @Test
    void getByUsernameTest() {
        when(userRepository.getByUsername(USERNAME)).thenReturn(user);
        assertEquals(userService.getByUsername(USERNAME), user);
    }

    @Test
    void getByUsernameExceptionTest() {
        when(userRepository.getByUsername(USERNAME)).thenReturn(null);
        RuntimeException thrown = assertThrows(RuntimeException.class,
                () -> userService.getByUsername(USERNAME));

        assertEquals("User does not exist", thrown.getMessage());
    }

    @Test
    void subscribeBasicTest() {
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        String response = userService.subscribeBasic(user.getUserName());
        verify(userRepository).subscribeBasic(user.getUserName());
        assertEquals("Saved", response);
    }

    @Test
    void subscribePremiumTest() {
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        String response = userService.subscribePremium(user.getUserName());
        verify(userRepository).subscribePremium(user.getUserName());
        assertEquals("Saved", response);
    }

    @Test
    public void getAllTest() {
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);

        when(userRepository.findAll()).thenReturn(list);
        Iterable<User> response = userService.getAll();
        assertEquals(list, response);
    }

    @Test
    public void isPremiumTest() {
        when(userRepository.isPremium(user1.getUserName())).thenReturn(1);
        boolean response = userService.isPremium(user1.getUserName());
        assertEquals(TRUE, response);
    }

    @Test
    public void isPremiumFalseTest() {
        when(userRepository.isPremium(user1.getUserName())).thenReturn(0);
        boolean response = userService.isPremium(user1.getUserName());
        assertEquals(FALSE, response);
    }
}


package nl.tudelft.sem.template.user.controllers;

import static nl.tudelft.sem.template.user.entities.User.UserType.BASIC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.user.entities.User;
import nl.tudelft.sem.template.user.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;


@WebMvcTest(controllers = UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USERNAME = "jasetho";
    private transient User user;
    private transient User user1;
    private static final String returnMessage = "Saved";

    @Autowired
    private transient MockMvc mvc;

    @InjectMocks
    transient UserController userController;

    @MockBean
    private transient UserService userService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUserName(USERNAME);
        user.setType(BASIC);
        user.setPassword("pass");

        user1 = new User();
        user1.setUserName("ayoo");
        user1.setType(BASIC);
        user1.setPassword("passer");
    }

    @Test
    void getByUsernameTest() throws Exception {
        Mockito.doReturn(user)
                .when(userService).getByUsername(USERNAME);

        mvc.perform(get("/getByUsername/jasetho"))
                .andExpect(status().isOk());

        verify(userService, times(1)).getByUsername(USERNAME);
    }

    @Test
    void getByUsernameExceptionTest() throws Exception {
        Mockito.doThrow(new RuntimeException("User does not exist"))
                .when(userService).getByUsername(USERNAME);

        mvc.perform(get("/getByUsername/jasetho"))
                .andExpect(status().is4xxClientError());

        verify(userService, times(1)).getByUsername(USERNAME);
    }

    @Test
    void getAllTest() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(user);
        list.add(user1);

        when(userService.getAll()).thenReturn(list);

        mvc.perform(get("/getAll")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(userService, times(1)).getAll();
    }

    @Test
    void subscribeBasicTest() throws Exception {
        doReturn(returnMessage)
                .when(userService).subscribeBasic(USERNAME);

        MvcResult result = mvc.perform(put("/subscribeBasic/jasetho")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(returnMessage, content);

        verify(userService, times(1)).subscribeBasic(USERNAME);
    }

    @Test
    void subscribePremium() throws Exception {
        doReturn(returnMessage)
                .when(userService).subscribePremium(USERNAME);


        MvcResult result = mvc.perform(put("/subscribePremium/jasetho")
                            .contentType(MediaType.APPLICATION_JSON))
                            .andDo(MockMvcResultHandlers.print())
                            .andExpect(status().isOk())
                            .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals(returnMessage, content);

        verify(userService, times(1)).subscribePremium(USERNAME);
    }

    @Test
    void isPremiumTest() throws Exception {
        when(userService.isPremium(user.getUserName())).thenReturn(false);

        mvc.perform(get("/isPremium/jasetho"))
                .andExpect(status().isOk());

        verify(userService, times(1)).isPremium(user.getUserName());
        assertFalse(userService.isPremium(user.getUserName()));
    }

    @Test
    void createAccountTest() throws Exception {
        when(userService.createAccount(Mockito.any(), Mockito.any())).thenReturn("Account made");

        MvcResult result = mvc.perform(post("/createAccount/foo/bar")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                        .andExpect(status().isOk())
                        .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("Account made", content);

        verify(userService, times(1)).createAccount("foo", "bar");
    }

}

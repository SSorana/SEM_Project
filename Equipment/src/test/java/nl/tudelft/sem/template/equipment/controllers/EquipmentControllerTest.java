package nl.tudelft.sem.template.equipment.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.exceptions.EquipmentInputException;
import nl.tudelft.sem.template.equipment.services.EquipmentService;
import org.junit.jupiter.api.BeforeAll;
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


@WebMvcTest(controllers = EquipmentController.class)
@ExtendWith(MockitoExtension.class)
public class EquipmentControllerTest {

    static List<String> testList;
    static String equipmentTestString = "Equipment 1";

    static Equipment equipment1 = new Equipment();
    static Equipment equipment2 = new Equipment();
    static final String EXCEPTION_MESSAGE = "Equipment does not exist";


    @Autowired
    private transient MockMvc mockMvc;

    @InjectMocks
    EquipmentController equipmentController;

    @MockBean
    private transient EquipmentService equipmentService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        equipment1.setName(equipmentTestString);
        equipment1.setCapacity(10);
        equipment1.setSportType("Sporttype 1");
        equipment1.setPrice(1.99);
        equipment1.setPreviousUsers(new ArrayList<>());

        equipment2.setName("Equipment 2");
        equipment2.setCapacity(15);
        equipment2.setSportType("Sporttype 2");
        equipment2.setPrice(2.99);
        equipment2.setPreviousUsers(new ArrayList<>());

    }

    @BeforeAll
    static void initAll() {
        testList = new ArrayList<>();
        testList.add("User 1");
        testList.add("User 2");
    }

    @Test
    void constructorTest() {
        EquipmentController equipmentController = new EquipmentController(equipmentService);
        assertNotNull(equipmentController);
    }

    @Test
    void getAllTest() throws Exception {
        List<Equipment> equipmentList = new ArrayList<>();
        equipmentList.add(equipment1);
        equipmentList.add(equipment2);

        Mockito.when(equipmentService.getAll()).thenReturn(equipmentList);

        MvcResult result = mockMvc.perform(get("/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("[{\"name\":\"Equipment 1\",\"capacity\":10,"
                + "\"sportType\":\"Sporttype 1\",\"price\":1.99,\"previousUsers\":[]},"
                + "{\"name\":\"Equipment 2\",\"capacity\":15,"
                + "\"sportType\":\"Sporttype 2\",\"price\":2.99,\"previousUsers\":[]}]", content);
    }

    @Test
    void getByNameTest() throws Exception {
        Mockito.when(equipmentService.getByName(equipmentTestString)).thenReturn(equipment1);

        MvcResult result = mockMvc.perform(get("/getByName/Equipment 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"name\":\"Equipment 1\",\"capacity\":10,"
                + "\"sportType\":\"Sporttype 1\",\"price\":1.99,\"previousUsers\":[]}", content);
    }

    @Test
    void getByNameExceptionTest() throws Exception {
        Mockito.when(equipmentService
                .getByName(equipmentTestString))
                .thenThrow(new EquipmentInputException(EXCEPTION_MESSAGE));

        MvcResult result = mockMvc.perform(get("/getByName/Equipment 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(equipmentService).getByName(equipmentTestString);
        String content = result.getResponse().getContentAsString();
        assertEquals(EXCEPTION_MESSAGE, content);
    }

    @Test
    void getHistoryTest() throws Exception {
        Mockito.when(equipmentService.getHistory(equipmentTestString)).thenReturn(testList);

        MvcResult result = mockMvc.perform(get("/admin/getHistory/Equipment 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("[\"User 1\",\"User 2\"]", content);
    }

    @Test
    void getHistoryExceptionTest() throws Exception {
        Mockito.when(equipmentService.getHistory(equipmentTestString))
                .thenThrow(new EquipmentInputException(EXCEPTION_MESSAGE));

        MvcResult result = mockMvc.perform(get("/admin/getHistory/Equipment 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(equipmentService).getHistory(equipmentTestString);
        String content = result.getResponse().getContentAsString();
        assertEquals(EXCEPTION_MESSAGE, content);
    }

    @Test
    void addUserTest() throws Exception {
        Mockito
                .when(equipmentService.addUser(equipmentTestString, "User 1"))
                .thenReturn(equipment1);

        MvcResult result = mockMvc.perform(put("/addUser/Equipment 1/User 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("{\"name\":\"Equipment 1\",\"capacity\":10,"
                + "\"sportType\":\"Sporttype 1\",\"price\":1.99,\"previousUsers\":[]}", content);
    }

    @Test
    void addUserExceptionTest() throws Exception {
        Mockito.when(equipmentService.addUser(equipmentTestString, "User"))
                .thenThrow(new EquipmentInputException(EXCEPTION_MESSAGE));

        MvcResult result = mockMvc.perform(put("/addUser/Equipment 1/User")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andReturn();

        verify(equipmentService).addUser(equipmentTestString, "User");
        String content = result.getResponse().getContentAsString();
        assertEquals(EXCEPTION_MESSAGE, content);
    }

    @Test
    void getCapacityTest() throws Exception {
        Mockito.when(equipmentService.getCapacity(equipmentTestString)).thenReturn(5);

        MvcResult result = mockMvc.perform(get("/getCapacity/Equipment 1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertEquals("5", content);
    }
}
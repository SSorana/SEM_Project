package nl.tudelft.sem.template.field.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.field.entities.Field;
import nl.tudelft.sem.template.field.services.FieldService;
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
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = FieldController.class)
@ExtendWith(MockitoExtension.class)
public class FieldControllerTest {

    static Field field;
    private static final String USERNAME = "foo";

    @Autowired
    private transient MockMvc mvc;

    @InjectMocks
    transient FieldController fieldController;

    @MockBean
    private transient FieldService fieldService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        field = new Field();
        field.setName(USERNAME);
        field.setMinCapacity(0);
        field.setMaxCapacity(20);
        field.setSportType("soccer");
        field.setHall(false);
    }

    @Test
    void getAllTest() throws Exception {
        List<Field> fields = new ArrayList<>();
        fields.add(field);
        when(fieldService.getAll()).thenReturn(fields);

        String response = mvc.perform(get("/getAll"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(fieldService, times(1)).getAll();
        assertEquals("[{\"name\":\"foo\",\"minCapacity\":0,\"maxCapacity\":20,"
                + "\"sportType\":\"soccer\",\"hall\":false}]", response);
    }

    @Test
    void getByUsernameTest() throws Exception {
        Mockito.doReturn(field)
                .when(fieldService).getByName(USERNAME);

        String response = mvc.perform(get("/getByName/" + USERNAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(fieldService, times(1)).getByName(USERNAME);
        assertEquals("{\"name\":\"foo\",\"minCapacity\":0,"
                + "\"maxCapacity\":20,\"sportType\":\"soccer\",\"hall\":false}", response);
    }

    @Test
    void getMinByUsernameTest() throws Exception {
        Mockito.doReturn(1)
                .when(fieldService).getMinByName(USERNAME);

        String response = mvc.perform(get("/getMinByName/" + USERNAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(fieldService, times(1)).getMinByName(USERNAME);
        assertEquals("1", response);
    }

    @Test
    void getMaxByUsernameTest() throws Exception {
        Mockito.doReturn(1)
                .when(fieldService).getMaxByName(USERNAME);

        String response = mvc.perform(get("/getMaxByName/" + USERNAME))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        verify(fieldService, times(1)).getMaxByName(USERNAME);
        assertEquals("1", response);
    }
}

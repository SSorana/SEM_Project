package nl.tudelft.sem.template.reservations.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.exceptions.ReservationInputException;
import nl.tudelft.sem.template.reservations.exceptions.ReservationServerException;
import nl.tudelft.sem.template.reservations.services.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Tests for reservation controller.
 */
@WebMvcTest(controllers = ReservationController.class)
@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private static FieldReservation fr;
    private static EquipmentReservation er;
    private static final String FOOBAR = "foobar";
    private transient ObjectMapper mapper;


    @Autowired
    private transient MockMvc mvc;

    @MockBean
    private transient ReservationService service;

    @BeforeEach
    void setup() {
        fr = new FieldReservation();
        fr.setId(1);
        fr.setStartingTime(LocalDateTime.MIN);
        fr.setEndingTime(LocalDateTime.MAX);
        fr.setUser("foo");
        fr.setFieldName("bar");
        fr.setTeamName("baz");
        fr.setLesson(false);

        er = new EquipmentReservation();
        er.setId(2);
        er.setStartingTime(LocalDateTime.MIN);
        er.setEndingTime(LocalDateTime.MAX);
        er.setUser("waldo");
        er.setName("fred");
        er.setQuantity(10);
        er.setPaid(false);

        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void makeEquipmentReservationTest() throws Exception {

        try {
            String json = mapper.writeValueAsString(er);

            Mockito.when(service.save(Mockito.any(), Mockito.any())).thenReturn(er);

            String response =
                    mvc.perform(post("/makeEquipmentRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().isOk())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, String.valueOf(er.getId()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void makeEquipmentReservationServerExceptionTest() throws Exception {

        try {
            String json = mapper.writeValueAsString(er);

            Mockito.when(service.save(Mockito.any(), Mockito.any()))
                    .thenThrow(new ReservationServerException(FOOBAR));

            String response =
                    mvc.perform(post("/makeEquipmentRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().is5xxServerError())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, FOOBAR);
        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void makeEquipmentReservationInputExceptionTest() throws Exception {

        try {
            String json = mapper.writeValueAsString(er);

            Mockito.when(service.save(Mockito.any(), Mockito.any()))
                    .thenThrow(new ReservationInputException(FOOBAR));

            String response =
                    mvc.perform(post("/makeEquipmentRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().is4xxClientError())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, FOOBAR);

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void makeFieldReservationTest() throws Exception {
        try {
            String json = mapper.writeValueAsString(fr);

            Mockito.when(service.save(Mockito.any(), Mockito.any())).thenReturn(fr);

            String response =
                    mvc.perform(post("/makeFieldRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().isOk())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, String.valueOf(fr.getId()));

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void makeFieldReservationServerExceptionTest() throws Exception {
        try {
            String json = mapper.writeValueAsString(fr);

            Mockito.when(service.save(Mockito.any(), Mockito.any()))
                    .thenThrow(new ReservationServerException(FOOBAR));

            String response =
                    mvc.perform(post("/makeFieldRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().is5xxServerError())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, FOOBAR);

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void makeFieldReservationInputExceptionTest() throws Exception {
        try {
            String json = mapper.writeValueAsString(fr);

            Mockito.when(service.save(Mockito.any(), Mockito.any()))
                    .thenThrow(new ReservationInputException(FOOBAR));

            String response =
                    mvc.perform(post("/makeFieldRes")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json))
                            .andExpect(status().is4xxClientError())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

            assertEquals(response, FOOBAR);

        } catch (JsonProcessingException e) {
            throw new RuntimeException();
        }
    }

    @Test
    void getFieldResTest() throws Exception {
        ArrayList<FieldReservation> list = new ArrayList<>();
        list.add(fr);
        Mockito.when(service.getFields()).thenReturn(list);

        String response = mvc.perform(get("/getFields")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("[{\"id\":1,\"startingTime\":\"-999999999-01-01T00:00:00\","
                + "\"endingTime\":\"+999999999-12-31T23:59:59.999999999\",\"user\":\"foo\","
                + "\"fieldName\":\"bar\",\"teamName\":\"baz\",\"lesson\":false}]", response);
    }

    @Test
    void getEquipmentResTest() throws Exception {
        ArrayList<EquipmentReservation> list = new ArrayList<>();
        list.add(er);
        Mockito.when(service.getEquipment()).thenReturn(list);

        String response = mvc.perform(get("/getEquipment")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("[{\"id\":2,\"startingTime\":\"-999999999-01-01T00:00:00\","
                + "\"endingTime\":\"+999999999-12-31T23:59:59.999999999\",\"user\":\"waldo\","
                + "\"name\":\"fred\",\"quantity\":10,\"paid\":false}]", response);
    }

    @Test
    void getByIdTest() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(fr);

        String response = mvc.perform(get("/getById/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertEquals("{\"id\":1,\"startingTime\":\"-999999999-01-01T00:00:00\","
                + "\"endingTime\":\"+999999999-12-31T23:59:59.999999999\",\"user\":\"foo\","
                + "\"fieldName\":\"bar\",\"teamName\":\"baz\",\"lesson\":false}", response);
    }
}
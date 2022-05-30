package nl.tudelft.sem.template.field.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import nl.tudelft.sem.template.field.entities.Field;
import nl.tudelft.sem.template.field.repositories.FieldRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivestreams.Publisher;

import java.util.ArrayList;
import java.util.List;


@RunWith(MockitoJUnitRunner.class)
class FieldServiceTest {

    private static final String NAME = "foo";


    @Mock
    transient FieldRepository fieldRepository;

    @InjectMocks
    transient FieldService fieldService;

    static Field field;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        field = new Field();
        field.setName(NAME);
        field.setHall(true);
        field.setSportType("sportType");
        field.setMaxCapacity(30);
        field.setMinCapacity(5);
    }


    @Test
    void getAllTest() {
        List<Field> listOfFields = new ArrayList<>();
        listOfFields.add(field);
        when(fieldRepository.findAll()).thenReturn(listOfFields);
        List<Field> result = fieldService.getAll();
        verify(fieldRepository, times(1)).findAll();
        assertEquals(listOfFields, result);
    }

    @Test
    void getByNameTest() {
        when(fieldRepository.getByName(NAME)).thenReturn(field);
        Field testField = fieldService.getByName(NAME);
        verify(fieldRepository, times(1)).getByName(NAME);
        assertEquals(field, testField);
    }

    @Test
    void getMinByNameTest() {
        when(fieldRepository.getMinByName(NAME)).thenReturn(5);
        int result = fieldService.getMinByName(NAME);
        verify(fieldRepository, times(1)).getMinByName(NAME);
        assertEquals(5, result);
    }

    @Test
    void getMaxByNameTest() {
        when(fieldRepository.getMaxByName(NAME)).thenReturn(30);
        int result = fieldService.getMaxByName(NAME);
        verify(fieldRepository, times(1)).getMaxByName(NAME);
        assertEquals(30, result);
    }
}
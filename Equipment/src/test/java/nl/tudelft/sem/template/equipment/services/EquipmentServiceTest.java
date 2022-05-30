package nl.tudelft.sem.template.equipment.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.exceptions.EquipmentInputException;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EquipmentServiceTest {

    @Mock
    transient EquipmentRepository equipmentRepository;

    @InjectMocks
    transient EquipmentService equipmentService;

    static String nameTestString = "Equipment 1";
    static Equipment equipment;
    static final String EXCEPTION_MESSAGE = "Equipment does not exist";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        equipment = new Equipment();
        equipment.setName(nameTestString);
        equipment.setCapacity(10);
        equipment.setSportType("sportType");
        equipment.setPrice(1.99);
    }

    @Test
    void constructorTest() {
        EquipmentService equipmentService = new EquipmentService(equipmentRepository);
        assertNotNull(equipmentService);
    }

    @Test
    void getAllTest() {
        List<Equipment> list = new ArrayList<>();
        list.add(equipment);
        when(equipmentRepository.findAll()).thenReturn(list);

        List<Equipment> returned = equipmentService.getAll();
        verify(equipmentRepository).findAll();
        assertEquals(list, returned);
    }

    @Test
    void getByNameTest() {
        Optional<Equipment> optional = Optional.of(equipment);
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        Equipment returnedEquipment = equipmentService.getByName(nameTestString);
        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(equipment, returnedEquipment);
    }

    @Test
    void getByNameExceptionTest() {
        Optional<Equipment> optional = Optional.empty();
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        EquipmentInputException thrown = assertThrows(
                EquipmentInputException.class,
                () -> equipmentService.getByName(nameTestString));

        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(EXCEPTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void seeHistoryTest() {
        List<String> previousUsers = new ArrayList<>();
        previousUsers.add("User 1");
        previousUsers.add("User 2");
        equipment.setPreviousUsers(previousUsers);
        Optional<Equipment> optional = Optional.of(equipment);
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        List<String> returnedList = equipmentService.getHistory(nameTestString);
        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(previousUsers, returnedList);
    }

    @Test
    void seeHistoryExceptionTest() {
        Optional<Equipment> optional = Optional.empty();
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        EquipmentInputException thrown = assertThrows(
                EquipmentInputException.class,
                () -> equipmentService.getHistory(nameTestString));

        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(EXCEPTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void addUserTest() {
        Equipment copy = new Equipment();
        copy.setName(nameTestString);
        copy.setCapacity(10);
        copy.setSportType("sportType");
        copy.setPrice(1.99);
        Optional<Equipment> optional = Optional.of(copy);
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        equipment.addPreviousUser("Username 1");

        Equipment returned = equipmentService.addUser(nameTestString, "Username 1");
        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(equipment, returned);
    }

    @Test
    void addUserExceptionTest() {
        Optional<Equipment> optional = Optional.empty();
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        EquipmentInputException thrown = assertThrows(
                EquipmentInputException.class,
                () -> equipmentService.addUser(nameTestString, "Username 1"));

        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(EXCEPTION_MESSAGE, thrown.getMessage());
    }

    @Test
    void getCapacityTest() {
        Optional<Equipment> optional = Optional.of(equipment);
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        int returned = equipmentService.getCapacity(nameTestString);
        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(10, returned);
    }

    @Test
    void getCapacityNullTest() {
        Optional<Equipment> optional = Optional.empty();
        when(equipmentRepository.getByName(nameTestString)).thenReturn(optional);

        int returned = equipmentService.getCapacity(nameTestString);
        verify(equipmentRepository).getByName(nameTestString);
        assertEquals(0, returned);
    }

}
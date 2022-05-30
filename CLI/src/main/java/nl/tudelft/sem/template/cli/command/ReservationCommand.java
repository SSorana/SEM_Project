package nl.tudelft.sem.template.cli.command;

import static nl.tudelft.sem.template.cli.constants.CliConstants.RESERVATION_SUCCESS;
import static org.springframework.shell.standard.ShellOption.NULL;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import nl.tudelft.sem.template.cli.communication.ReservationRequest;
import nl.tudelft.sem.template.cli.model.EquipmentResContainer;
import nl.tudelft.sem.template.cli.model.FieldResContainer;
import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.TableModel;



/**
 * All commands related to the field microservice.
 */
@ShellComponent
public class ReservationCommand extends BuildTable {

    @Autowired
    transient ShellHelper helper;

    /**
     * Prints all the field reservations currently in the database.
     */
    @ShellMethod("Get all field reservations")
    public void getFieldRes() {
        List<FieldResContainer> fields;
        try {
            fields = ReservationRequest.getFieldRes();

            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("id", "Id");
            headers.put("startingTime", "Starting Time");
            headers.put("endingTime", "Ending Time");
            headers.put("user", "User");
            headers.put("lesson", "Is Lesson");
            headers.put("fieldName", "Field Name");
            headers.put("teamName", "Team Name");

            TableModel model = new BeanListTableModel<>(fields, headers);

            getItem(model);

        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Prints all the equipment reservations currently in the database.
     */
    @ShellMethod("Get all equipment reservations")
    public void getEquipmentRes() {
        List<EquipmentResContainer> equipment;

        try {
            equipment = ReservationRequest.getEquipmentRes();

            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("id", "Id");
            headers.put("startingTime", "Starting Time");
            headers.put("endingTime", "Ending Time");
            headers.put("user", "User");
            headers.put("paid", "Is Paid");
            headers.put("name", "Equipment Name");
            headers.put("quantity", "Quantity");

            TableModel model = new BeanListTableModel<>(equipment, headers);

            getItem(model);

        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Makes a field reservation.
     *
     * @param user      user making the reservation.
     * @param isLesson  is it a lesson or not.
     * @param fieldName name of the field.
     * @param teamName  name of the team.
     * @param start     start time.
     * @param end       end time.
     */
    @ShellMethod("Make field reservation")
    public void makeFieldRes(
            @ShellOption({"--user"}) String user,
            @ShellOption({"--isLesson"}) boolean isLesson,
            @ShellOption({"--fieldName"}) String fieldName,
            @ShellOption(defaultValue = NULL, value = {"--teamName"}) String teamName,
            @ShellOption({"--start"}) int start,
            @ShellOption({"--end"}) int end) {
        FieldResContainer container = new FieldResContainer();
        container.setUser(user);
        container.setLesson(isLesson);
        container.setFieldName(fieldName);
        container.setTeamName(teamName);
        LocalDateTime startingTime = LocalDateTime.of(2021, 12, 23, 17, 0);
        LocalDateTime endingTime = LocalDateTime.of(2021, 12, 23, 18, 0);
        startingTime = startingTime.withHour(start);
        endingTime = endingTime.withHour(end);
        container.setStartingTime(startingTime);
        container.setEndingTime(endingTime);

        try {
            int id = ReservationRequest.makeFieldRes(container);
            helper.print(RESERVATION_SUCCESS + id);
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Makes an equipment reservation.
     *
     * @param user          user making the reservation.
     * @param isPaid        should the equipment be paid for.
     * @param equipmentName name of the equipment.
     * @param quantity      how much equipment.
     * @param start         start time.
     * @param end           end time.
     */
    @ShellMethod("Make equipment reservation")
    public void makeEquipmentRes(@ShellOption({"--user"}) String user,
                                 @ShellOption({"--isPaid"}) boolean isPaid,
                                 @ShellOption({"--equipmentName"}) String equipmentName,
                                 @ShellOption({"--quantity"}) int quantity,
                                 @ShellOption({"--start"}) int start,
                                 @ShellOption({"--end"}) int end) {
        EquipmentResContainer container = new EquipmentResContainer();
        container.setUser(user);
        container.setPaid(isPaid);
        container.setName(equipmentName);
        container.setQuantity(quantity);
        LocalDateTime startingTime = LocalDateTime.of(2021, 12, 24, 17, 0);
        LocalDateTime endingTime = LocalDateTime.of(2021, 12, 24, 18, 0);
        startingTime = startingTime.withHour(start);
        endingTime = endingTime.withHour(end);
        container.setStartingTime(startingTime);
        container.setEndingTime(endingTime);

        try {
            int id = ReservationRequest.makeEquipmentRes(container);
            helper.print(RESERVATION_SUCCESS + id);
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }
}

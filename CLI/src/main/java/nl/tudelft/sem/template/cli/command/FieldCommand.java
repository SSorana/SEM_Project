package nl.tudelft.sem.template.cli.command;

import java.util.LinkedHashMap;
import java.util.List;
import nl.tudelft.sem.template.cli.communication.FieldRequest;
import nl.tudelft.sem.template.cli.model.FieldContainer;
import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.TableModel;

/**
 * All commands related to the field microservice.
 */
@ShellComponent
public class FieldCommand extends BuildTable {

    @Autowired
    transient ShellHelper helper;

    /**
     * Prints all the fields currently in the database.
     */
    @ShellMethod("Get all fields")
    public void getFields() {
        try {
            List<FieldContainer> fields = FieldRequest.getAllFields();
            LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
            headers.put("name", "Name");
            headers.put("minCapacity", "Min Capacity");
            headers.put("maxCapacity", "Max Capacity");
            headers.put("sportType", "Sport Type");
            headers.put("hall", "Is Hall");
            TableModel model = new BeanListTableModel<>(fields, headers);

            getItem(model);

        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }
}

package nl.tudelft.sem.template.cli.command;

import java.util.LinkedHashMap;
import java.util.List;
import nl.tudelft.sem.template.cli.communication.EquipmentRequest;
import nl.tudelft.sem.template.cli.model.EquipmentContainer;
import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

/**
 * All commands related to the equipment microservice.
 */
@ShellComponent
public class EquipmentCommand {

    @Autowired
    transient ShellHelper helper;

    /**
     * Prints all the equipment currently in the database.
     */
    @ShellMethod("Get all equipment")
    public void getEquipment() {
        try {
            List<EquipmentContainer> equipment = EquipmentRequest.getAllEquipment();
            if (equipment != null) {
                LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
                headers.put("name", "Name");
                headers.put("capacity", "Quantity");
                headers.put("sportType", "Sport Type");
                headers.put("price", "Price");
                headers.put("previousUsers", "Previous Users");
                TableModel model = new BeanListTableModel<>(equipment, headers);

                TableBuilder tableBuilder = new TableBuilder(model);
                tableBuilder.addInnerBorder(BorderStyle.fancy_light);
                tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
                helper.print(tableBuilder.build().render(80));
            }
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }

    }
}

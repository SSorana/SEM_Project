package nl.tudelft.sem.template.cli.command;

import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

public class BuildTable {

    @Autowired
    transient ShellHelper helper;

    /**
     *Builds table for collected item (field, equipment or user).
     *
     *@param table table model. 
     */
    public void getItem(TableModel table) {
        TableBuilder tableBuilder = new TableBuilder(table);
        tableBuilder.addInnerBorder(BorderStyle.fancy_light);
        tableBuilder.addHeaderBorder(BorderStyle.fancy_double);
        helper.print(tableBuilder.build().render(80));
    }
}

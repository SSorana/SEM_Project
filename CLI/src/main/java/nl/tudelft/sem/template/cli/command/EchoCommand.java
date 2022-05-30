package nl.tudelft.sem.template.cli.command;

import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

/**
 * Echoes the name inputted.
 */
@ShellComponent
public class EchoCommand {

    @Autowired
    transient ShellHelper helper;

    /**
     * Echos name in a greeting.
     *
     * @param name name to echo.
     * @return greeting.
     */
    @ShellMethod("Echos name")
    public String echo(@ShellOption({"-N", "--name"}) String name) {
        return helper.getSuccessMessage(String.format("Hello %s!", name));
    }
}

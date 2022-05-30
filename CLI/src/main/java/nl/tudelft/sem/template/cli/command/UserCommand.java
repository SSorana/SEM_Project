package nl.tudelft.sem.template.cli.command;

import java.util.LinkedHashMap;
import java.util.List;
import nl.tudelft.sem.template.cli.communication.UserRequest;
import nl.tudelft.sem.template.cli.model.UserContainer;
import nl.tudelft.sem.template.cli.shell.ShellHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.TableModel;

/**
 * All commands related to the user microservice.
 */
@ShellComponent
public class UserCommand extends BuildTable {

    @Autowired
    transient ShellHelper helper;

    /**
     * Creates an account.
     *
     * @param name     username.
     * @param password password.
     */
    @ShellMethod("Create an account")
    public void createAccount(@ShellOption({"-N", "--name"}) String name,
                              @ShellOption({"-P", "--pass"}) String password) {
        try {
            if (!UserRequest.createUser(name, password)) {
                helper.printError("Creating a user has failed");
            }
            helper.print(String
                    .format("Account created with name %s and password %s.", name, password));
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Gets a user based on username.
     *
     * @param name username.
     */
    @ShellMethod("Get a username")
    public void getUsername(@ShellOption({"-N", "--name"}) String name) {
        try {
            UserContainer user = UserRequest.getUser(name);
            if (user == null) {
                helper.printError("Getting a user has failed");
            }

            helper.print(String.format("Account found with name %s, password %s and type %s.",
                    user.getUserName(), user.getPassword(), user.getType()));
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Subscribes to a premium account.
     *
     * @param name username.
     */
    @ShellMethod("Subscribe to a premium account")
    public void subPremium(@ShellOption({"-N", "--username"}) String name) {

        try {
            if (!UserRequest.subscribePremium(name)) {
                helper.printError("Changing the subscription has failed");
            }
            helper.print(String.format("%s has changed to a Premium subscription", name));
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Subscribes to a basic account.
     *
     * @param name username.
     */
    @ShellMethod("Subscribe to a basic account")
    public void subBasic(@ShellOption({"-N", "--username"}) String name) {
        try {
            if (!UserRequest.subscribeBasic(name)) {
                helper.printError("Changing the subscription has failed");
            }
            helper.print(String.format("%s has changed to a Basic subscription", name));
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }

    /**
     * Gets all users in the database.
     */
    @ShellMethod("Get all users")
    public void getUsers() {
        try {
            List<UserContainer> users = UserRequest.getAllUsers();
            if (users != null) {
                LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
                headers.put("userName", "Username");
                headers.put("password", "Password");
                headers.put("type", "Subscription");
                TableModel model = new BeanListTableModel<>(users, headers);

                getItem(model);

            }
        } catch (RuntimeException e) {
            helper.printError(e.getMessage());
        }
    }
}


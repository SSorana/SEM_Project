package nl.tudelft.sem.template.cli.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentContainer {
    private String name;
    private int capacity;
    private String sportType;
    private double price;
    private List<String> previousUsers;
}

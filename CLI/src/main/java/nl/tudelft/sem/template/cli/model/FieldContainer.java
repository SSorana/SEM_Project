package nl.tudelft.sem.template.cli.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FieldContainer {
    private String name;
    private int minCapacity;
    private int maxCapacity;
    private String sportType;
    private boolean hall;

}

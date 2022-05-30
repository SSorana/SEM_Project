package nl.tudelft.sem.template.cli.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentResContainer {
    private long id;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private String user;
    private boolean paid;
    private String name;
    private int quantity;
}

package nl.tudelft.sem.template.cli.model;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FieldResContainer {

    private long id;
    private LocalDateTime startingTime;
    private LocalDateTime endingTime;
    private String user;
    private boolean lesson;
    private String fieldName;
    private String teamName;
}

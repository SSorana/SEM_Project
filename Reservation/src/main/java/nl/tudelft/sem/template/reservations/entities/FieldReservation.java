package nl.tudelft.sem.template.reservations.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;


/**
 * The type Field reservation.
 */
@Entity
@DiscriminatorValue("field")
@Getter
@Setter
public class FieldReservation extends nl.tudelft.sem.template.reservations.entities.Reservation {

    @Column(name = "is_lesson")
    private boolean isLesson;

    @Column(name = "field_name")
    private String fieldName;

    @Column(name = "team_name")
    private String teamName;

    /**
     * Create new field reservation.
     */
    public FieldReservation() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FieldReservation)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }

        FieldReservation that = (FieldReservation) o;

        if (isLesson != that.isLesson) {
            return false;
        }
        if (!Objects.equals(fieldName, that.fieldName)) {
            return false;
        }
        return Objects.equals(teamName, that.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isLesson, fieldName, teamName);
    }

    @Override
    public String toString() {
        return super.toString() +  " FieldReservation{"
                + "isLesson="
                + isLesson
                + ", fieldName="
                + fieldName
                + ", teamName="
                + teamName
                + "}";
    }
}


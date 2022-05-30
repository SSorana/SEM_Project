package nl.tudelft.sem.template.field.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Field")
@Getter
@Setter
public class Field {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "minCapacity")
    private int minCapacity;

    @Column(name = "maxCapacity")
    private int maxCapacity;

    @Column(name = "sportType")
    private String sportType;

    @Column(name = "isHall")
    private boolean isHall;

    /**
     * Create new Field.
     */
    public Field() {
    }

    @Override
    public String toString() {
        return "Field{"
                + "name='" + name + '\''
                + ", minCapacity=" + minCapacity
                + ", maxCapacity=" + maxCapacity
                + ", sportType='" + sportType + '\''
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        return minCapacity == field.minCapacity && maxCapacity == field.maxCapacity
                && Objects.equals(name, field.name)
                && Objects.equals(sportType, field.sportType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, minCapacity, maxCapacity, sportType);
    }
}

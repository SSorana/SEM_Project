package nl.tudelft.sem.template.equipment.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Equipment")
@Getter
@Setter
public class Equipment {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "sportType")
    private String sportType;

    @Column(name = "price")
    private double price;

    @Column(name = "previousUsers")
    @ElementCollection
    private List<String> previousUsers;

    /**
     * Create new equipment.
     */
    public Equipment() {
    }

    /**
     * Add a user to the list of previous users.
     *
     * @param previousUser ID of the user
     */
    public void addPreviousUser(String previousUser) {
        if (this.previousUsers == null) {
            this.previousUsers = new ArrayList<>();
        }
        this.previousUsers.add(previousUser);
    }

    @Override
    public String toString() {
        return "Equipment{"
                + "name='" + name + '\''
                + ", capacity=" + capacity
                + ", sportType='" + sportType + '\''
                + ", price=" + price
                + ", previousUsers=" + previousUsers
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Equipment)) {
            return false;
        }

        Equipment equipment = (Equipment) o;

        if (capacity != equipment.capacity) {
            return false;
        }
        if (Double.compare(equipment.price, price) != 0) {
            return false;
        }
        if (!Objects.equals(name, equipment.name)) {
            return false;
        }
        if (!Objects.equals(sportType, equipment.sportType)) {
            return false;
        }
        return Objects.equals(previousUsers, equipment.previousUsers);
    }

    @Override
    public int hashCode() {
        int result;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + capacity;
        result = 31 * result + (sportType != null ? sportType.hashCode() : 0);
        long temp;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (previousUsers != null ? previousUsers.hashCode() : 0);
        return result;
    }
}

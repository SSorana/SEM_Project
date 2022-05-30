package nl.tudelft.sem.template.reservations.entities;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Equipment reservation.
 */
@Entity
@DiscriminatorValue("equipment")
@Getter
@Setter
public class EquipmentReservation extends Reservation {

    @Column(name = "is_paid")
    private boolean isPaid;

    @Column(name = "equipment_name")
    private String name;

    @Column(name = "quantity")
    private Integer quantity;


    /**
     * Create new field reservation.
     */
    public EquipmentReservation() {
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
            return false;
        }
        EquipmentReservation that = (EquipmentReservation) o;
        return isPaid == that.isPaid
                && quantity.equals(that.quantity)
                && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isPaid, name, quantity);
    }

    @Override
    public String toString() {
        return super.toString() + " EquipmentReservation{"
                + "isPaid=" + isPaid
                + ", name="
                + name
                + ", quantity="
                + quantity
                + "}";
    }
}





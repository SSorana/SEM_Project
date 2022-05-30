package nl.tudelft.sem.template.reservations.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * The type Reservation.
 */
@Entity
@Table(name = "Reservation")
@Inheritance(
        strategy = InheritanceType.JOINED
)
@DiscriminatorColumn(name = "reservation_type")
@Getter
@Setter
public abstract class Reservation {

    @Id
    @GeneratedValue
    @Column(name = "reservation_id")
    private long id;

    @Column(name = "starting_time")
    private LocalDateTime startingTime;

    @Column(name = "ending_time")
    private LocalDateTime endingTime;

    @Column(name = "user_name")
    private String user;

    /**
     * Create new reservation.
     */
    public Reservation() {
    }


    @Override
    public String toString() {
        return "Reservation{"
                + "id="
                + id
                + ", starting time="
                + startingTime
                + ", ending time="
                + endingTime
                + ", user="
                + user
                + "}";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reservation that = (Reservation) o;
        return id == that.id
                && startingTime.equals(that.startingTime)
                && endingTime.equals(that.endingTime)
                && user.equals(that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startingTime, endingTime, user);
    }
}


package dumitru.adrian.myreservationapp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Program.
 */
@Entity
@Table(name = "T_PROGRAM")
public class Program implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "day")
    private String day;

    @Column(name = "status")
    private String status;

    @Temporal(TemporalType.TIME)
    @Column(name = "start_hour")
    private Date start_hour;

    @Temporal(TemporalType.TIME)
    @Column(name = "end_hour")
    private Date end_hour;

    @ManyToOne
    private Restaurant restaurant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStart_hour() { return start_hour; }

    public void setStart_hour(Date start_hour) {
        this.start_hour = start_hour;
    }

    public Date getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(Date end_hour) {
        this.end_hour = end_hour;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Program program = (Program) o;

        if ( ! Objects.equals(id, program.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Program{" +
                "id=" + id +
                ", day='" + day + "'" +
                ", status='" + status + "'" +
                ", start_hour='" + start_hour + "'" +
                ", end_hour='" + end_hour + "'" +
                '}';
    }
}

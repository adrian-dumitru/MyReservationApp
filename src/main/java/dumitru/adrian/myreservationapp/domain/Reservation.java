package dumitru.adrian.myreservationapp.domain;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A Reservation.
 */
@Entity
@Table(name = "T_RESERVATION")
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "day", nullable = false)
    private Date day;

    @Temporal(TemporalType.DATE)
    @Column(name = "finish", nullable = false)
    private Date finish;

    @Temporal(TemporalType.TIME)
    @Column(name = "start_hour")
    private Date start_hour;

    @Temporal(TemporalType.TIME)
    @Column(name = "end_hour")
    private Date end_hour;

    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "tables")
    private Integer tables;

    @Min(value = 1)
    @Max(value = 100)
    @Column(name = "persons")
    private Integer persons;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    private User user;

    @OneToOne
    private Reservation_tables reservation_tables;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public Date getFinish() {
        return finish;
    }

    public void setFinish(Date finish) {
        this.finish = finish;
    }

    public Date getStart_hour() {
        return start_hour;
    }

    public void setStart_hour(Date start_hour) {
        this.start_hour = start_hour;
    }

    public Date getEnd_hour() {
        return end_hour;
    }

    public void setEnd_hour(Date end_hour) {
        this.end_hour = end_hour;
    }

    public Integer getTables() {
        return tables;
    }

    public void setTables(Integer tables) {
        this.tables = tables;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Reservation_tables getReservation_tables() {
        return reservation_tables;
    }

    public void setReservation_tables(Reservation_tables reservation_tables) {
        this.reservation_tables = reservation_tables;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservation reservation = (Reservation) o;

        if ( ! Objects.equals(id, reservation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", day='" + day + "'" +
                ", finish='" + finish + "'" +
                ", start_hour='" + start_hour + "'" +
                ", end_hour='" + end_hour + "'" +
                ", tables='" + tables + "'" +
                ", persons='" + persons + "'" +
                ", comment='" + comment + "'" +
                '}';
    }
}

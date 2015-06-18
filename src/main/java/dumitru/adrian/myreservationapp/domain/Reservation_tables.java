package dumitru.adrian.myreservationapp.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A Reservation_tables.
 */
@Entity
@Table(name = "T_RESERVATION_TABLES")
public class Reservation_tables implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "two_persons_table")
    private Integer two_persons_table;

    @Column(name = "four_persons_table")
    private Integer four_persons_table;

    @Column(name = "six_persons_table")
    private Integer six_persons_table;

    private Reservation_tables(){};

    public Reservation_tables(ArrayList<Integer> tables){
        two_persons_table = tables.get(0);
        four_persons_table = tables.get(1);
        six_persons_table = tables.get(2);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTwo_persons_table() {
        return two_persons_table;
    }

    public void setTwo_persons_table(Integer two_persons_table) {
        this.two_persons_table = two_persons_table;
    }

    public Integer getFour_persons_table() {
        return four_persons_table;
    }

    public void setFour_persons_table(Integer four_persons_table) {
        this.four_persons_table = four_persons_table;
    }

    public Integer getSix_persons_table() {
        return six_persons_table;
    }

    public void setSix_persons_table(Integer six_persons_table) {
        this.six_persons_table = six_persons_table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservation_tables reservation_tables = (Reservation_tables) o;

        if ( ! Objects.equals(id, reservation_tables.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reservation_tables{" +
                "id=" + id +
                ", two_persons_table='" + two_persons_table + "'" +
                ", four_persons_table='" + four_persons_table + "'" +
                ", six_persons_table='" + six_persons_table + "'" +
                '}';
    }
}

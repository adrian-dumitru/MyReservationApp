package dumitru.adrian.myreservationapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Restaurant_tables.
 */
@Entity
@Table(name = "T_RESTAURANT_TABLES")
public class Restaurant_tables implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "two_persons_table")
    private Integer two_persons_table;

    @Column(name = "four_persons_table")
    private Integer four_persons_table;

    @Column(name = "six_persons_table")
    private Integer six_persons_table;

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

        Restaurant_tables restaurant_tables = (Restaurant_tables) o;

        if ( ! Objects.equals(id, restaurant_tables.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Restaurant_tables{" +
                "id=" + id +
                ", two_persons_table='" + two_persons_table + "'" +
                ", four_persons_table='" + four_persons_table + "'" +
                ", six_persons_table='" + six_persons_table + "'" +
                '}';
    }
}

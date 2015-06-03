package dumitru.adrian.myreservationapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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

    @Column(name = "monday_start")
    private String mondayStart;

    @Column(name = "monday_end")
    private String mondayEnd;

    @Column(name = "tuesday_start")
    private String tuesdayStart;

    @Column(name = "tuesday_end")
    private String tuesdayEnd;

    @Column(name = "wednesday_start")
    private String wednesdayStart;

    @Column(name = "wednesday_end")
    private String wednesdayEnd;

    @Column(name = "thursday_start")
    private String thursdayStart;

    @Column(name = "thursday_end")
    private String thursdayEnd;

    @Column(name = "friday_start")
    private String fridayStart;

    @Column(name = "friday_end")
    private String fridayEnd;

    @Column(name = "saturday_start")
    private String saturdayStart;

    @Column(name = "saturday_end")
    private String saturdayEnd;

    @Column(name = "sunday_start")
    private String sundayStart;

    @Column(name = "sunday_end")
    private String sundayEnd;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMondayStart() {
        return mondayStart;
    }

    public void setMondayStart(String mondayStart) {
        this.mondayStart = mondayStart;
    }

    public String getMondayEnd() {
        return mondayEnd;
    }

    public void setMondayEnd(String mondayEnd) {
        this.mondayEnd = mondayEnd;
    }

    public String getTuesdayStart() {
        return tuesdayStart;
    }

    public void setTuesdayStart(String tuesdayStart) {
        this.tuesdayStart = tuesdayStart;
    }

    public String getTuesdayEnd() {
        return tuesdayEnd;
    }

    public void setTuesdayEnd(String tuesdayEnd) {
        this.tuesdayEnd = tuesdayEnd;
    }

    public String getWednesdayStart() {
        return wednesdayStart;
    }

    public void setWednesdayStart(String wednesdayStart) {
        this.wednesdayStart = wednesdayStart;
    }

    public String getWednesdayEnd() {
        return wednesdayEnd;
    }

    public void setWednesdayEnd(String wednesdayEnd) {
        this.wednesdayEnd = wednesdayEnd;
    }

    public String getThursdayStart() {
        return thursdayStart;
    }

    public void setThursdayStart(String thursdayStart) {
        this.thursdayStart = thursdayStart;
    }

    public String getThursdayEnd() {
        return thursdayEnd;
    }

    public void setThursdayEnd(String thursdayEnd) {
        this.thursdayEnd = thursdayEnd;
    }

    public String getFridayStart() {
        return fridayStart;
    }

    public void setFridayStart(String fridayStart) {
        this.fridayStart = fridayStart;
    }

    public String getFridayEnd() {
        return fridayEnd;
    }

    public void setFridayEnd(String fridayEnd) {
        this.fridayEnd = fridayEnd;
    }

    public String getSaturdayStart() {
        return saturdayStart;
    }

    public void setSaturdayStart(String saturdayStart) {
        this.saturdayStart = saturdayStart;
    }

    public String getSaturdayEnd() {
        return saturdayEnd;
    }

    public void setSaturdayEnd(String saturdayEnd) {
        this.saturdayEnd = saturdayEnd;
    }

    public String getSundayStart() {
        return sundayStart;
    }

    public void setSundayStart(String sundayStart) {
        this.sundayStart = sundayStart;
    }

    public String getSundayEnd() {
        return sundayEnd;
    }

    public void setSundayEnd(String sundayEnd) {
        this.sundayEnd = sundayEnd;
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
                ", mondayStart='" + mondayStart + "'" +
                ", mondayEnd='" + mondayEnd + "'" +
                ", tuesdayStart='" + tuesdayStart + "'" +
                ", tuesdayEnd='" + tuesdayEnd + "'" +
                ", wednesdayStart='" + wednesdayStart + "'" +
                ", wednesdayEnd='" + wednesdayEnd + "'" +
                ", thursdayStart='" + thursdayStart + "'" +
                ", thursdayEnd='" + thursdayEnd + "'" +
                ", fridayStart='" + fridayStart + "'" +
                ", fridayEnd='" + fridayEnd + "'" +
                ", saturdayStart='" + saturdayStart + "'" +
                ", saturdayEnd='" + saturdayEnd + "'" +
                ", sundayStart='" + sundayStart + "'" +
                ", sundayEnd='" + sundayEnd + "'" +
                '}';
    }
}

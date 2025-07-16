package pl.kurs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class CalculationEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String input;
    private Timestamp date;

    public CalculationEvent() {
    }

    public CalculationEvent(String input, Timestamp date) {
        this.input = input;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationEvent event = (CalculationEvent) o;
        return Objects.equals(id, event.id) && Objects.equals(input, event.input) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, input, date);
    }

    @Override
    public String toString() {
        return "CalculationEvent{" +
                "id=" + id +
                ", input='" + input + '\'' +
                ", date=" + date +
                '}';
    }
}

package app.axisrin.cinema.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String address;
    private String workTime;
    private int totalCinemaHall;

    public Cinema() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public int getTotalCinemaHall() {
        return totalCinemaHall;
    }

    public void setTotalCinemaHall(int totalCinemaHall) {
        this.totalCinemaHall = totalCinemaHall;
    }
}

package app.axisrin.cinema.entities;

import lombok.Data;

@Data
public class Place {
    private int number;
    private Long idUser;
    private boolean isTaken;

    public Place() {
    }

    public Place(int number, Long idUser, boolean isTaken) {
        this.number = number;
        this.idUser = idUser;
        this.isTaken = isTaken;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}

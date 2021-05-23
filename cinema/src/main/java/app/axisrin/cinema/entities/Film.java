package app.axisrin.cinema.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameFilm;
    private String descriptionFilm;
    private Date firstShowDate;
    private Date lastShowDate;
    private String tagFilm;
    
    public Film() {
    }

    public Film(String nameFilm, String descriptionFilm, Date firstShowDate, Date lastShowDate, String tagFilm) {
        this.nameFilm = nameFilm;
        this.descriptionFilm = descriptionFilm;
        this.firstShowDate = firstShowDate;
        this.lastShowDate = lastShowDate;
        this.tagFilm = tagFilm;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameFilm() {
        return nameFilm;
    }

    public void setNameFilm(String nameFilm) {
        this.nameFilm = nameFilm;
    }

    public String getDescriptionFilm() {
        return descriptionFilm;
    }

    public void setDescriptionFilm(String descriptionFilm) {
        this.descriptionFilm = descriptionFilm;
    }

    public Date getFirstShowDate() {
        return firstShowDate;
    }

    public void setFirstShowDate(Date firstShowDate) {
        this.firstShowDate = firstShowDate;
    }

    public Date getLastShowDate() {
        return lastShowDate;
    }

    public void setLastShowDate(Date lastShowDate) {
        this.lastShowDate = lastShowDate;
    }

    public String getTagFilm() {
        return tagFilm;
    }

    public void setTagFilm(String tagFilm) {
        this.tagFilm = tagFilm;
    }
}

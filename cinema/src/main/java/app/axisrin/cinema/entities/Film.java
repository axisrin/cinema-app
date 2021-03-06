package app.axisrin.cinema.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nameFilm;
    private String descriptionFilm;
    private Date firstShowDate;
    private Date lastShowDate;
    private String tagFilm;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String authorName;
    private String filename;
    private String placeFilm;
    private String authorTel;
    private int costFilm;
    private int freePlaces;
    private int boughtPlaces = 0;
    private int currentPlaces = this.freePlaces - this.boughtPlaces;

    public Film() {
    }

    public Film(String nameFilm, String descriptionFilm, Date firstShowDate, Date lastShowDate, String tagFilm, User author, String authorName, String filename, String placeFilm, String authorTel, int costFilm, int freePlaces, int boughtPlaces) {
        this.nameFilm = nameFilm;
        this.descriptionFilm = descriptionFilm;
        this.firstShowDate = firstShowDate;
        this.lastShowDate = lastShowDate;
        this.tagFilm = tagFilm;
        this.author = author;
        this.authorName = authorName;
        this.filename = filename;
        this.placeFilm = placeFilm;
        this.authorTel = authorTel;
        this.costFilm = costFilm;
        this.freePlaces = freePlaces;
        this.boughtPlaces = boughtPlaces;
    }

    public Film(String nameFilm, String descriptionFilm, Date firstDate, Date lastDate, String tagFilm, User user, String placeFilm, int costFilm) {
        this.nameFilm = nameFilm;
        this.descriptionFilm = descriptionFilm;
        this.firstShowDate = firstDate;
        this.lastShowDate = lastDate;
        this.tagFilm = tagFilm;
        this.placeFilm = placeFilm;
        this.author = user;
        this.authorName = user.getUsername();
        this.authorTel = user.getPhone();
        this.costFilm = costFilm;
    }

    public Film(String nameFilm, String descriptionFilm, Date firstShowDate, Date lastShowDate, String tagFilm, User author, String authorName, String filename, String placeFilm) {
        this.nameFilm = nameFilm;
        this.descriptionFilm = descriptionFilm;
        this.firstShowDate = firstShowDate;
        this.lastShowDate = lastShowDate;
        this.tagFilm = tagFilm;
        this.author = author;
        this.authorName = authorName;
        this.filename = filename;
        this.placeFilm = placeFilm;
    }


    public Film(String nameFilm, String descriptionFilm, Date firstShowDate, Date lastShowDate, String tagFilm, User user) {
        this.nameFilm = nameFilm;
        this.descriptionFilm = descriptionFilm;
        this.firstShowDate = firstShowDate;
        this.lastShowDate = lastShowDate;
        this.tagFilm = tagFilm;
        this.author = user;
        this.authorName = user.getUsername();
    }

    public int getCurrentPlaces() {
        return currentPlaces;
    }

    public void setCurrentPlaces(int currentPlaces) {
        this.currentPlaces = currentPlaces;
    }

    public int getFreePlaces() {
        return this.freePlaces - this.boughtPlaces;
    }

    public void setFreePlaces(int freePlaces) {
        this.freePlaces = freePlaces;
    }

    public int getBoughtPlaces() {
        return boughtPlaces;
    }

    public void setBoughtPlaces(int boughtPlaces) {
        this.boughtPlaces = boughtPlaces;
    }

    public String getPlaceFilm() {
        return placeFilm;
    }

    public void setPlaceFilm(String placeFilm) {
        this.placeFilm = placeFilm;
    }

    public String getAuthorTel() {
        return authorTel;
    }

    public void setAuthorTel(String authorTel) {
        this.authorTel = authorTel;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public int getCostFilm() {
        return costFilm;
    }

    public void setCostFilm(int costFilm) {
        this.costFilm = costFilm;
    }
}

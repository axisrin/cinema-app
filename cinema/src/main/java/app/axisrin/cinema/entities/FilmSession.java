package app.axisrin.cinema.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class FilmSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date sessionDate;
    private int totalTickets;
    private double sessionTotalPrice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "film_id")
    private Film sessionFilm;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User userSeller;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "buyer_id")
    private User userBuyer;

    private String sellerUsername;
    private String buyerUsername;
    private String buyerPhone;

    public FilmSession() {
    }


    public FilmSession(Date sessionDate, int totalTickets, double sessionTotalPrice, Film sessionFilm, User userSeller, User userBuyer) {
        this.sessionDate = sessionDate;
        this.totalTickets = totalTickets;
        this.sessionTotalPrice = sessionTotalPrice;
        this.sessionFilm = sessionFilm;
        this.userSeller = userSeller;
        this.userBuyer = userBuyer;
        this.sellerUsername = this.userSeller.getUsername();
        this.buyerUsername = this.userBuyer.getUsername();
        this.buyerPhone = this.userBuyer.getPhone();
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getSellerUsername() {
        return sellerUsername;
    }

    public void setSellerUsername(String sellerUsername) {
        this.sellerUsername = sellerUsername;
    }

    public String getBuyerUsername() {
        return buyerUsername;
    }

    public void setBuyerUsername(String buyerUsername) {
        this.buyerUsername = buyerUsername;
    }

    public Film getSessionFilm() {
        return sessionFilm;
    }

    public void setSessionFilm(Film sessionFilm) {
        this.sessionFilm = sessionFilm;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }

    public double getSessionTotalPrice() {
        return sessionTotalPrice;
    }

    public void setSessionTotalPrice(double sessionTotalPrice) {
        this.sessionTotalPrice = sessionTotalPrice;
    }

    public User getUserSeller() {
        return userSeller;
    }

    public void setUserSeller(User userSeller) {
        this.userSeller = userSeller;
    }

    public User getUserBuyer() {
        return userBuyer;
    }

    public void setUserBuyer(User userBuyer) {
        this.userBuyer = userBuyer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(Date sessionDate) {
        this.sessionDate = sessionDate;
    }
}

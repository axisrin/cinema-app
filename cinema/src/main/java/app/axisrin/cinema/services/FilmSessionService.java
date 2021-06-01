package app.axisrin.cinema.services;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.FilmSession;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.FilmSessionRepo;
import app.axisrin.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.Optional;

@Service
public class FilmSessionService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private FilmRepo filmRepo;
    @Autowired
    private FilmSessionRepo filmSessionRepo;

    public String addOrderTicketAndSignalBack(Long film_id, Date orderDate, int totalTicks, User buyer, User seller, String username) {
        Film film = filmRepo.getById(film_id);
        if (filmSessionRepo.findBySessionFilmAndUserBuyer(film,buyer) != null)
            return "Ошибка! Вы уже оформляли заказ! Попробуйте его переоформить!";
        if (film.getFreePlaces() < totalTicks || totalTicks <= 0)
            return "Ошибка! Недопустимое количество мест!";
        if (film.getFirstShowDate().after(orderDate) || film.getLastShowDate().before(orderDate))
            return "Ошибка! Неправильно выбрана дата!";
        film.setBoughtPlaces(film.getBoughtPlaces() + totalTicks);
        double totalCost = film.getCostFilm()*totalTicks;
        FilmSession filmSession = new FilmSession(orderDate,totalTicks,totalCost,film,seller,buyer);
        if (!username.isEmpty() && !username.equals(buyer.getUsername()))
            filmSession.setBuyerUsername(username);
        filmSessionRepo.save(filmSession);
        filmRepo.save(film);
        return "Заказ успешно оформлен! Ожидайте, с вами свяжется автор показа! Заказ сможете посмотреть на главной странице!";
    }

    public String deleteFilmSessionService(Long filmSId) {
        Optional<FilmSession> filmSession = filmSessionRepo.findById(filmSId);
        Film sessionFilm = filmSession.get().getSessionFilm();
//        sessionFilm.setFreePlaces(sessionFilm.getFreePlaces() + filmSession.get().getTotalTickets());
        sessionFilm.setBoughtPlaces(sessionFilm.getBoughtPlaces() - filmSession.get().getTotalTickets());
        filmRepo.save(sessionFilm);
        filmSessionRepo.delete(filmSession.get());
        System.out.println("Заказ на фильм " + filmSession.get().getSessionFilm().getNameFilm() + " успешно удалён!");
        return "Заказ на фильм " + filmSession.get().getSessionFilm().getNameFilm() + " успешно удалён!";
    }

}

package app.axisrin.cinema.services;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.FilmSession;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.FilmSessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilmService {
    @Autowired
    FilmRepo filmRepo;
    @Autowired
    FilmSessionRepo filmSessionRepo;

    public String deleteFilm(Film film) {
        String nameF = film.getNameFilm();
        for (FilmSession fDSession : filmSessionRepo.findBySessionFilm(film)) {
            filmSessionRepo.delete(fDSession);
        }
        filmRepo.delete(film);
        return "Film " + nameF + " Succesful deleted!";
    }
}

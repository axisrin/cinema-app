package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.FilmSession;
import app.axisrin.cinema.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FilmSessionRepo extends JpaRepository<FilmSession, Long> {
    FilmSession findBySessionFilmAndUserBuyer(Film sessionFilm,User buyerUser);
    List<FilmSession> findBySessionFilm(Film sessionFilm);
}

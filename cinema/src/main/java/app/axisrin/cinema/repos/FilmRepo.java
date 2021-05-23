package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface FilmRepo extends JpaRepository<Film, Long> {
    List<Film> findByTagFilm(String tag);
    List<Film> findByFirstShowDateLessThanEqualAndLastShowDateGreaterThanEqual(Date firstShowDate, Date lastShowDate);
}

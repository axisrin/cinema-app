package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.Film;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

public interface FilmRepo extends JpaRepository<Film, Long> {
    List<Film> findByTagFilm(String tag);
    List<Film> findByFirstShowDateLessThanEqualAndLastShowDateGreaterThanEqual(Date firstShowDate, Date lastShowDate);
}

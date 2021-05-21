package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmRepo extends JpaRepository<Film, Long> {
}

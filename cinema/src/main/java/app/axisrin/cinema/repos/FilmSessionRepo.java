package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.FilmSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmSessionRepo extends JpaRepository<FilmSession, Long> {
}

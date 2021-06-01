package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepo extends JpaRepository<Cinema, Long> {
}

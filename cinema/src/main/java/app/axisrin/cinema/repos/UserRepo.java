package app.axisrin.cinema.repos;

import app.axisrin.cinema.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    List<User> findByEmail(String email);
    User findByUsername(String username);
}

package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.FilmSession;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.FilmSessionRepo;
import app.axisrin.cinema.repos.UserRepo;
import app.axisrin.cinema.services.FilmSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/filmSessions")
public class FilmSessionController {
    @Autowired
    FilmSessionService filmSessionService;
    @Autowired
    UserRepo userRepo;
    @Autowired
    FilmRepo filmRepo;
    @Autowired
    FilmSessionRepo filmSessionRepo;

    @GetMapping("/delete/{filmSession}")
    public String deleteSession(
            @PathVariable FilmSession filmSession,
            Model model) {
        String message = filmSessionService.deleteFilmSessionService(filmSession.getId());
        List<User> users = userRepo.findAll();
        List<Film> films = filmRepo.findAll();
        List<FilmSession> filmSessions = filmSessionRepo.findAll();
        model.addAttribute("users",users);
        model.addAttribute("filmes", films);
        model.addAttribute("filmSessions", filmSessions);
        model.addAttribute("sucDelMes", message);
        return "main";
    }
}

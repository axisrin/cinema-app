package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    FilmRepo filmRepo;

    @GetMapping
    public String getUserInfo(Model model) {
        List<User> users = userRepo.findAll();
        List<Film> films = filmRepo.findAll();
        model.addAttribute("users",users);
        model.addAttribute("filmes", films);
        return "main";
    }

}

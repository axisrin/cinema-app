package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/filmes")
public class FilmController {
    @Autowired
    FilmRepo filmRepo;

    @GetMapping
    public String showFilmes(Model model) {
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute(filmes);
        System.out.println("meth showFilmes filmes is " + filmes.toString());
        return "films";
    }

    @PostMapping
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public String addFilmes(@RequestParam String nameFilm,
                            @RequestParam String descriptionFilm,
                            @RequestParam Date firstDate,
                            @RequestParam Date lastDate,
                            Model model) {
        Film film = new Film(nameFilm, descriptionFilm, firstDate, lastDate);
        filmRepo.save(film);
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute(filmes);
        return "films";
    }

}

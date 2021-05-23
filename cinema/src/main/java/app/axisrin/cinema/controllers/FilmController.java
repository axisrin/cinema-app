package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.repos.FilmRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("/filmes")
public class FilmController {
    @Autowired
    FilmRepo filmRepo;

    @GetMapping
    public String showFilmes(Model model) {
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute("filmes",filmes);
        System.out.println("meth showFilmes filmes is " + filmes.toString());
        return "films";
    }

    @PostMapping
    public String addFilmes(@RequestParam String nameFilm,
                            @RequestParam String descriptionFilm,
                            @RequestParam String tagFilm,
                            @RequestParam Date firstDate,
                            @RequestParam Date lastDate,
                            Model model) {
        Film film = new Film(nameFilm, descriptionFilm, firstDate, lastDate, tagFilm);
        filmRepo.save(film);
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute("filmes",filmes);
        return "films";
    }

    @PostMapping("/filterGenre")
    public String filter(@RequestParam String filterGenre,
                         Model model) {
        List<Film> filmes;
        if (filterGenre != null && !filterGenre.isEmpty()) {
            filmes = filmRepo.findByTagFilm(filterGenre);
        } else {
            filmes = filmRepo.findAll();
        }
        System.out.println("meth filterGenre lims finded is + " + filmes.toString());
        model.addAttribute("filmes", filmes);
        return "films";
    }

    @PostMapping("filterDate")
    public String filter(@RequestParam Date filterDate,
                         Model model) {
        List<Film> filmes;
        if (filterDate != null) {
            filmes = filmRepo
                    .findByFirstShowDateLessThanEqualAndLastShowDateGreaterThanEqual(filterDate,filterDate);
        } else {
            filmes = filmRepo.findAll();
        }
        System.out.println("meth filterDate lims finded is + " + filmes.toString());
        model.addAttribute("filmes", filmes);
        return "films";
    }
}

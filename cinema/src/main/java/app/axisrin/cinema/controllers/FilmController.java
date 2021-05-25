package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Controller
@RequestMapping("/filmes")
public class FilmController {
    @Autowired
    FilmRepo filmRepo;

    @Autowired
    UserRepo userRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping
    public String showFilmes(Model model) {
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute("filmes",filmes);
        System.out.println("meth showFilmes filmes is " + filmes.toString());
        return "films";
    }

    @PostMapping
    public String addFilmes(@AuthenticationPrincipal User user,
                            @RequestParam String nameFilm,
                            @RequestParam String placeFilm,
                            @RequestParam String descriptionFilm,
                            @RequestParam String tagFilm,
                            @RequestParam Date firstDate,
                            @RequestParam Date lastDate,
                            @RequestParam int costFilm,
                            @RequestParam("file") MultipartFile file,
                            Model model) throws IOException {
        Film film = new Film(nameFilm, descriptionFilm, firstDate, lastDate, tagFilm, user, placeFilm, costFilm);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            film.setFilename(resultFilename);
        }
        film.setAuthorTel(user.getPhone());
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

    @GetMapping("/edit/{film}")
    public String userEditForm(@PathVariable Film film, Model model) {
        model.addAttribute("film", film);
        return "filmEdit";
    }

    @PostMapping("/edit/{film}")
    public String saveFilm(
            @RequestParam("FilmId") Film film,
            @RequestParam String nameFilm,
            @RequestParam String placeFilm,
            @RequestParam String descriptionFilm,
            @RequestParam String tagFilm,
            @RequestParam Date firstDate,
            @RequestParam Date lastDate,
            @RequestParam int costFilm,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        film.setDescriptionFilm(descriptionFilm);
        film.setFirstShowDate(firstDate);
        film.setLastShowDate(lastDate);
        film.setPlaceFilm(placeFilm);
        film.setNameFilm(nameFilm);
        film.setTagFilm(tagFilm);
        film.setCostFilm(costFilm);
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists())
                uploadDir.mkdir();
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + resultFilename));
            film.setFilename(resultFilename);
        }
        filmRepo.save(film);
        return "redirect:/filmes";
    }

    @GetMapping("/buy/{film}")
    public String buyTicket(
            @PathVariable Film film,
            Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("filmes", film);
        return "buyTicket";
    }


}

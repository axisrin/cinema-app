package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Film;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.FilmRepo;
import app.axisrin.cinema.repos.FilmSessionRepo;
import app.axisrin.cinema.repos.UserRepo;
import app.axisrin.cinema.services.FilmService;
import app.axisrin.cinema.services.FilmSessionService;
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
import java.util.ArrayList;
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
    @Autowired
    FilmSessionService filmSessionService;
    @Autowired
    FilmSessionRepo filmSessionRepo;
    @Autowired
    FilmService filmService;

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
                            @RequestParam int freePlaces,
                            @RequestParam Date firstDate,
                            @RequestParam Date lastDate,
                            @RequestParam int costFilm,
                            @RequestParam("file") MultipartFile file,
                            Model model) throws IOException {
        Film film = new Film(nameFilm, descriptionFilm, firstDate, lastDate, tagFilm, user, placeFilm, costFilm);
        if (freePlaces <= 0) {
            model.addAttribute("errorMesg", "Ошибка! Мест должно быть больше 0!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("filmes",filmes);
            return "films";
        }
        if (costFilm < 0) {
            model.addAttribute("errorMesg", "Ошибка! Стоимость билета не может быть меньше 0 рублей!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("filmes",filmes);
            return "films";
        }
        if (firstDate.after(lastDate) || lastDate.before(firstDate)) {
            model.addAttribute("errorMesg", "Ошибка! Дата начала не может быть позже даты конца показа!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("filmes",filmes);
            return "films";
        }
        film.setFreePlaces(freePlaces);
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
        model.addAttribute("errorMesg", "Отлично! Ваш фильм добавлен!");
        return "films";
    }

    @PostMapping("/filterGenre")
    public String filter(@RequestParam String filterGenre,
                         Model model) {
        List<Film> filmes = filmRepo.findAll();
        List<Film> filtredFilmes = new ArrayList<Film>();
        for (Film curF: filmes) {
            if (curF.getTagFilm().toLowerCase().contains(filterGenre.toLowerCase()))
                filtredFilmes.add(curF);
        }
        if (filtredFilmes.isEmpty()) {
            filtredFilmes = filmes;
        }
//        if (filterGenre != null && !filterGenre.isEmpty()) {
//            filmes = filmRepo.findByTagFilm(filterGenre);
//        } else {
//            filmes = filmRepo.findAll();
//        }
        System.out.println("meth filterGenre lims finded is + " + filmes.toString());
        model.addAttribute("filmes", filtredFilmes);
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
            @RequestParam int freePlaces,
            @RequestParam Date firstDate,
            @RequestParam Date lastDate,
            @RequestParam int costFilm,
            @RequestParam("file") MultipartFile file,
            Model model) throws IOException {
        if (freePlaces <= 0) {
            model.addAttribute("errorMesg", "Ошибка! Мест должно быть больше 0!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("film",film);
            return "filmEdit";
        }
        if (costFilm < 0) {
            model.addAttribute("errorMesg", "Ошибка! Стоимость билета не может быть меньше 0 рублей!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("film",film);
            return "filmEdit";
        }
        if (firstDate.after(lastDate) || lastDate.before(firstDate)) {
            model.addAttribute("errorMesg", "Ошибка! Дата начала не может быть позже даты конца показа!");
            List<Film> filmes = filmRepo.findAll();
            model.addAttribute("film",film);
            return "filmEdit";
        }
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

    @GetMapping("/edit/delete/{film}")
    public String deleteFilm(
            @PathVariable Film film,
            Model model) {
        model.addAttribute("messageDeletedFilm" ,filmService.deleteFilm(film));
        List<Film> filmes = filmRepo.findAll();
        model.addAttribute("filmes",filmes);
        System.out.println("meth showFilmes filmes is " + filmes.toString());
        return "films";
    }


    @GetMapping("/buy/{film}")
    public String getBuyTicket(
            @PathVariable Film film,
            Model model) {
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("film", film);
        return "buyTicket";
    }

    @PostMapping("/buy/{film}")
    public String addBuyTicket(
            @AuthenticationPrincipal User user,
            @RequestParam("FilmId") Film film,
            @RequestParam String nameUser,
            @RequestParam int totalTickets,
            @RequestParam Date orderDate,
            Model model) {
        String result = filmSessionService.addOrderTicketAndSignalBack(
                film.getId(),orderDate,totalTickets,user,film.getAuthor(),nameUser
        );
        model.addAttribute("resultMessage", result);
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        model.addAttribute("film", film);
        return "buyTicket";
    }
}

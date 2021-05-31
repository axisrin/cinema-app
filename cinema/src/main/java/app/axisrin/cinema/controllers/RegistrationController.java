package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Role;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.entities.dto.CaptchaResponseDTO;
import app.axisrin.cinema.repos.UserRepo;
import app.axisrin.cinema.services.MailSender;
import app.axisrin.cinema.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.thymeleaf.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    private final static String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Value("${recaptcha.secret}")
    private String secret;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addUser(
            User user,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            Model model) {
        String url = String.format(CAPTCHA_URL, secret, captchaResponse);
        CaptchaResponseDTO response = restTemplate.postForObject(url, Collections.emptyList(), CaptchaResponseDTO.class);

        if (!response.isSuccess()) {
            model.addAttribute("message", "Заполните каптчу правильно!");
            return "registration";
        }
        User userFromDb = userRepo.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.addAttribute("message", "User exists Try to change User Name!");
            return "registration";
        }
        List<User> usersFromDb = userRepo.findByEmail(user.getEmail());
        if (!usersFromDb.isEmpty()){
            model.addAttribute("message", "User exists Try to change Mail!");
            return "registration";
        }
        user.setActivateCode(UUID.randomUUID().toString());
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Привет, %s! \n" +
                            "Ты зарегистрировался(лась) на нашем новом сервисе '.lowfilm' \n" +
                            "Пожалуйста перейди по ссылке, чтобы зарегистрироваться \n" +
                            "http://localhost:8080/registration/activate/%s",
                            user.getUsername(),
                            user.getActivateCode()
            );
            mailSender.send(user.getEmail(), "Activation Code", message);
        }
        user.setActive(false);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        model.addAttribute("messageOfLogin", "Пожалуйста, подтвердите аккаунт, перейдя по ссылке на почте!");
        return "login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);
        if (isActivated) {
            model.addAttribute("messageOfLogin", "Поздравляем! Пользователь активирован!");
        } else {
            model.addAttribute("messageOfLogin", "Сожалеем, но пользователь с данным кодом не найден!");
        }
        return "login";
    }
}

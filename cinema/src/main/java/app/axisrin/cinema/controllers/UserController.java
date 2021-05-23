package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserRepo userRepo;

    @GetMapping
    public String showUsers(Model model) {
        List<User> users = userRepo.findAll();
        System.out.println("method showUsers users is = " + users.toString());
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping
    public String addUsers(@RequestParam String phone,
                           @RequestParam String email,
                           @RequestParam String username,
                           @RequestParam(required = false, defaultValue = "false") boolean isActive,
                           @RequestParam String password,
                           Model model) {
        User user = new User(phone, email, username,password,false);
        if (isActive) {
            user.setActive(true);
        }
        if (!userRepo.findByEmail(email).isEmpty()) {
            model.addAttribute("emailErrorMessage", "True");
            List<User> users = userRepo.findAll();
            model.addAttribute("users", users);
            return "users";
        }
        userRepo.save(user);
        System.out.println("method addUsers user is = " + user.toString());
        List<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/filterEmail")
    public String filterEmail(@RequestParam String filterEmail,
                              Model model) {
        List<User> users;
        if (filterEmail != null && !filterEmail.isEmpty()) {
            users = userRepo.findByEmail(filterEmail);
        } else {
            users = userRepo.findAll();
        }
        System.out.println("method filterEmail user is = " + users.toString());
        model.addAttribute("users", users);
        return "users";
    }
}

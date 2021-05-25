package app.axisrin.cinema.controllers;

import app.axisrin.cinema.entities.Role;
import app.axisrin.cinema.entities.User;
import app.axisrin.cinema.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAuthority('ADMIN')")
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
        User userFromDb = userRepo.findByUsername(username);
        if (userFromDb != null) {
            model.addAttribute("emailErrorMessage", "User exists Try to change User Name!");
            List<User> users = userRepo.findAll();
            model.addAttribute("users", users);
            return "users";
        }
        if (!userRepo.findByEmail(email).isEmpty()) {
            model.addAttribute("emailErrorMessage", "User exists Try to change Email!");
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

    @GetMapping("/edit/{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PostMapping("/edit/{user}")
    public String userSave(
            @RequestParam("UserId") User user,
            @RequestParam(required = false) String newPassword,
            @RequestParam String username,
            @RequestParam String phone,
            @RequestParam String email,
            @RequestParam String oldPassword,
            @RequestParam Map<String, String> form,
            Model model) {
        if (!oldPassword.equals(user.getPassword())) {
            model.addAttribute("message", "Error! Invalid old password!");
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }
        if (!newPassword.isEmpty()) {
            user.setPassword(newPassword);
        }
        User userFromDb = userRepo.findByUsername(username);
        if (userFromDb != null && !userFromDb.getId().equals(user.getId())) {
            model.addAttribute("message", "Error! Name is taken try different!");
            model.addAttribute("user", user);
            model.addAttribute("roles", Role.values());
            return "userEdit";
        }
        boolean flag = false;
        List<User> usersFromFb = userRepo.findByEmail(email);
        if (!usersFromFb.isEmpty()) {
            for (User tempUser : usersFromFb) {
                if (tempUser.getId().equals(user.getId()))
                    flag = true;
            }
            if (!flag) {
                model.addAttribute("message", "Error! Email is taken try different!");
                model.addAttribute("user", user);
                model.addAttribute("roles", Role.values());
                return "userEdit";
            }
        }
        user.setEmail(email);
        user.setUsername(username);
        user.setPhone(phone);
        Set<String> roles = Arrays
                .stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());
        user.getRoles().clear();
        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }
        userRepo.save(user);
        return "redirect:/users";
    }
}

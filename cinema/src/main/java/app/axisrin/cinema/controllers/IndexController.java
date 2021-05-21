package app.axisrin.cinema.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@RequestParam(required = false, defaultValue = "new user") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }

}
package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.guitarplusclonejava.model.User;

@Controller
@RequestMapping(value = {"/","/guitar-plus"})
public class HomeController {
    @GetMapping
    public String home() {
        return "/index";
    }

    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("user", new User());
        return "/Register";
    }
}

package rikkei.academy.guitarplusclonejava.controller;

import groovyjarjarpicocli.CommandLine;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.IMPL.ProductServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = {"/","/guitar-plus"})
public class HomeController {
    IProductService productService = new ProductServiceIMPL();
    @GetMapping("/")
    public String home(Model model, HttpSession session, @ModelAttribute("message") String message) {
        if (message.length() != 0) {
            model.addAttribute("message", message);
        }
        List<Product> hotProductList = productService.getHotProducts();
        model.addAttribute("hotProducts", hotProductList);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("outStandingProducts", productService.getOutStandingProducts());
        return "/index";
    }

    @GetMapping("/register")
    public String toRegister(Model model) {
        model.addAttribute("user", new User());
        return "/Register";
    }

    @GetMapping("/login")
    public String toLogin() {
        return "/Login";
    }
}

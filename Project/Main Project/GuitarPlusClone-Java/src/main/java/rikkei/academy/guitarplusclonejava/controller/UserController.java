package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.IMPL.UserServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
    IUserService userService = new UserServiceIMPL();
    @PostMapping("/register")
    public String register(@ModelAttribute ("user") User enterUser, Model model) {
        // check đăng kí
        if (userService.findUserByEmail(enterUser.getEmail()) == null) {
            // email chưa tồn tại -> tạo mới tài khoản
            userService.save(enterUser);
            model.addAttribute("message", "Đăng kí thành công! Vui lòng đăng nhập!");
            return "/Login";
        } else {
            // email đã tồn tại -> yêu cầu đăng kí lại
            model.addAttribute("message, Đăng kí thất bại! Email đã tồn tại! Đăng nhập hoặc Quay trở lại đăng kí");
            return "/Register";
        }
    }
}

package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.IMPL.ProductServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.UserServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IProductService;
import rikkei.academy.guitarplusclonejava.service.IUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {
    IUserService userService = new UserServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    @PostMapping("/login")
    public String login (HttpServletRequest request,
                         @RequestParam ("email") String email,
                         @RequestParam ("password") String password,
                         Model model,
                         RedirectAttributes redirectAttributes) {
        User userLogin = userService.checkLogin(email, password);
        if (userLogin == null) {
            // đăng nhập sai
            model.addAttribute("messageFailLogin", "Sai tài khoản hoặc mật khẩu! Vui lòng thử lại!");
            return "/Login";
        } else {
            // đăng nhập đúng
            request.getSession().setAttribute("userLogin", userLogin);
            model.addAttribute("hotProducts", productService.getHotProducts());
            model.addAttribute("config", Config.currencyVN);
            model.addAttribute("outStandingProducts", productService.getOutStandingProducts());
            switch (userLogin.getRole()) {
                case 0:
                    return "/admin-views/index";
                default:
                    redirectAttributes.addFlashAttribute("messageSuccess", "Đăng nhập thành công! Mua hàng thôi!");
                    return "redirect:/";
            }
        }
    }
    @PostMapping("/register")
    public String register(@ModelAttribute ("user") User enterUser, Model model, @RequestParam ("re-password") String rePassword) {
        if (enterUser.getBirthDate() == null) {
            enterUser.setBirthDate("1998-01-01");
        }
        System.out.println("in");
        // check đăng kí
        if (userService.findUserByEmail(enterUser.getEmail()) != null || !rePassword.equals(enterUser.getPassword()) || enterUser.getPassword().trim().length() < 8) {

            // email đã tồn tại -> đăng kí lỗi
            model.addAttribute("message", "Đăng kí thất bại! Vui lòng nhập lại");
            return "/Register";
        } else {
            // email chưa tồn tại -> tạo mới user
            enterUser.setRole(1);
            userService.save(enterUser);
            model.addAttribute("messageRegisSuccess", "Đăng kí thành công! Hãy đăng nhập để mua hàng!");
            return "/Login";
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<Map<String, String>> checkEmail(@RequestParam("email") String email) {
        User user = userService.findUserByEmail(email); // Kiểm tra email trong cơ sở dữ liệu
        Map<String, String> response = new HashMap<>();
        if (user != null) { // Nếu email đã tồn tại
            response.put("status", "error");
            response.put("message", "Email đã được sử dụng.");
        } else { // Nếu email chưa tồn tại
            response.put("status", "success");
            response.put("message", "Email chưa được sử dụng.");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request) {
        request.getSession().removeAttribute("userLogin");
        return "redirect:/";
    }

    @GetMapping("/showAllMng")
    public String showAllUser(Model model) {
        List<User> userList = userService.findAll();
        int pageCount = (userList.size()%5)==0?(userList.size()/5):(userList.size()/5 + 1);
        System.out.println(pageCount);
        model.addAttribute("listUsers", userService.findUserByPage(1,5));
        model.addAttribute("pageCount", new Array[pageCount]);
        model.addAttribute("config", new Config());
        model.addAttribute("pageCurrent", 1);
        return "/admin-views/UserManagement/show";
    }

    @GetMapping("/page")
    public String showProductPage(@RequestParam int id, Model model) {
        List<User> userList = userService.findAll();
        int pageCount = (userList.size()%5)==0?(userList.size()/5):(userList.size()/5 + 1);
        model.addAttribute("listUsers", userService.findUserByPage(id,5));
        model.addAttribute("pageCount", new Array[pageCount]);
        model.addAttribute("config", new Config());
        model.addAttribute("pageCurrent", id);
        return "/admin-views/UserManagement/show";
    }

    @GetMapping("/block")
    public String blockUser(@RequestParam int id, @RequestParam int pageCurrent) {
        User updateStatUser = userService.findById(id);
        updateStatUser.setStatus(!updateStatUser.isStatus());
        userService.save(updateStatUser);
        return "redirect:/user/page?id="+ pageCurrent;
    }

    @GetMapping("/home-admin")
    public String home_admin(HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        if(user.getUserId() == -1) {
            return "/admin-views/index";
        } else {
            return "/login";
        }
    }
}

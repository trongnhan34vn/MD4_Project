package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.Cart;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.ICartService;
import rikkei.academy.guitarplusclonejava.service.IMPL.CartServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.ProductServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IProductService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    IProductService productService = new ProductServiceIMPL();
    ICartService cartService = new CartServiceIMPL();

    @PostMapping("/add")
    public String addToCart(@ModelAttribute("cart") Cart newCart, @RequestParam("productId") String productId, HttpSession request, RedirectAttributes redirectAttributes) {
        User user = (User) request.getAttribute("userLogin");
        if (user != null) {
            newCart.setUserId(user.getUserId());
            newCart.setProduct(productService.findById(Integer.parseInt(productId)));
            if (newCart.getQuantity() < 1) {
                newCart.setQuantity(1);
            }
            cartService.save(newCart);
            redirectAttributes.addAttribute("messageSuccess", "Thêm vào giỏ hàng thành công");
        } else {
            redirectAttributes.addAttribute("messageError", "Vui lòng đăng nhập!");
        }
        return "redirect:/product/product-detail?id=" + newCart.getProduct().getProductId();
    }

    @GetMapping("/delete")
    public String deleteCartById(@RequestParam ("idDel") int id) {
        cartService.remove(id);
        return "redirect:/cart/showcart";
    }

    @GetMapping
    public String showCart(Model model, HttpSession request) {
        User currentUser = (User) request.getAttribute("userLogin");
        if (currentUser != null) {
            model.addAttribute("config", Config.currencyVN);
            model.addAttribute("cartList", cartService.findCartByUserId(currentUser.getUserId()));
            return "/user-views/cart";
        } else {
//            model.addAttribute("messageSignUp", "Vui lòng đăng nhập!");
            return "redirect:/";
        }
    }

    @GetMapping("/showcart")
    @ResponseBody
    public List<Cart> showCart(HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        return cartService.findCartByUserId(user.getUserId());
    }

    @PostMapping("/update")
    public @ResponseBody String updateProductQuantity(@RequestBody Object input) {
        String objectString = input.toString();
        objectString.replace("{", "");
        objectString.replace("}", "");
        objectString.replace("," ,"");
        String[] stringArr = objectString.split(" ");
        String cartIdString = stringArr[0].split("-")[1].replace(",", "");
        String quantityString = stringArr[1].split("=")[1].replace("}", "");
        int quantity = Integer.parseInt(quantityString);
        int cartId = Integer.parseInt(cartIdString);
        Cart cartUpdate = cartService.findById(cartId);
        cartUpdate.setQuantity(quantity);
        cartService.updateCartQuantity(cartUpdate);
        return "cập nhật thành công";
    }

    @PostMapping("/updateCartStat")
    public @ResponseBody String updateCartStat (@RequestBody String[] stringId, HttpSession session) {
        System.out.println(Arrays.toString(stringId));
        User user = (User) session.getAttribute("userLogin");
        List<Cart> cartListUser = cartService.findCartByUserId(user.getUserId());
        int[] selectCartId = new int[stringId.length];
        for (int i = 0; i < selectCartId.length; i++) {
            selectCartId[i] = Integer.parseInt(stringId[i]);
        }
        for (Cart cart: cartListUser) {
            cartService.updateCartStatus(cart.getCartId(), checkExist(cart.getCartId(), selectCartId));
        }
        return "cap nhat thanh cong";
    }

    private boolean checkExist(int num, int[] arr) {
        for (int j : arr) {
            if (num == j) {
                return true;
            }
        }
        return false;
    }
}

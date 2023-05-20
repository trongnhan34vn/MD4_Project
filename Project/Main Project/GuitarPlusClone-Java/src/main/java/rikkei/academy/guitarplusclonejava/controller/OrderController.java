package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.Cart;
import rikkei.academy.guitarplusclonejava.model.Order;
import rikkei.academy.guitarplusclonejava.model.OrderDetail;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.ICartService;
import rikkei.academy.guitarplusclonejava.service.IMPL.CartServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.OrderDetailServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.OrderServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IOrderDetailService;
import rikkei.academy.guitarplusclonejava.service.IOrderService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    ICartService cartService = new CartServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrderDetailService orderDetailService = new OrderDetailServiceIMPL();
    @GetMapping("/sendPreOrder")
    public String showOrder(Model model, HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        List<Cart> selectedCart = cartService.findSelectedCartInUserCart(user.getUserId());
        float total = 0;
        for (Cart cart: selectedCart) {
            total = total + cart.getQuantity()*cart.getProduct().getPrice();
        }
        model.addAttribute("listCartPreOrder", selectedCart);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("total", total);
        model.addAttribute("order", new Order());
        return "/user-views/order";
    }

    @PostMapping("/createNewOrder")
    public String createNewOrder(@ModelAttribute Order order, HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        order.setUser(user);
        int lastOrderId = (orderService.createNewOrderReturnThis(order));
        List<Cart> selectedCart = cartService.findSelectedCartInUserCart(user.getUserId());
        for (Cart cart: selectedCart) {
           OrderDetail newOrderDetail = new OrderDetail();
           newOrderDetail.setProduct(cart.getProduct());
           newOrderDetail.setQuantity(cart.getQuantity());
           newOrderDetail.setOrder(orderService.findById(lastOrderId));
           orderDetailService.save(newOrderDetail);
           cartService.remove(cart.getCartId());
        }
        List<OrderDetail> listOrder = orderDetailService.findOrderDetailByOrderId(lastOrderId);
        float total = 0;
        for (OrderDetail orderDetail:listOrder) {
            total = total + orderDetail.getQuantity()*orderDetail.getProduct().getPrice();
        }
        model.addAttribute("listOrder", listOrder);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("total", total);
        return "/user-views/order-success";
    }

}

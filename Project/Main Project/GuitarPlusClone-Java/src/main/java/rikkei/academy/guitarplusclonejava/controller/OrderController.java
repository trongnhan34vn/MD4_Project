package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.guitarplusclonejava.config.Config;
import rikkei.academy.guitarplusclonejava.model.*;
import rikkei.academy.guitarplusclonejava.service.ICartService;
import rikkei.academy.guitarplusclonejava.service.IMPL.CartServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.FeedbackServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.OrderDetailServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.OrderServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IOrderDetailService;
import rikkei.academy.guitarplusclonejava.service.IOrderService;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    FeedbackServiceIMPL feedbackServiceIMPL = new FeedbackServiceIMPL();
    ICartService cartService = new CartServiceIMPL();
    IOrderService orderService = new OrderServiceIMPL();
    IOrderDetailService orderDetailService = new OrderDetailServiceIMPL();

    @GetMapping("/sendPreOrder")
    public String showOrder(Model model, HttpSession session) {
        User user = (User) session.getAttribute("userLogin");
        List<Cart> selectedCart = cartService.findSelectedCartInUserCart(user.getUserId());
        float total = 0;
        for (Cart cart : selectedCart) {
            total = total + cart.getQuantity() * cart.getProduct().getPrice();
        }
        model.addAttribute("listCartPreOrder", selectedCart);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("total", total);
        model.addAttribute("order", new Order());
        return "/user-views/order";
    }

    @PostMapping("/createNewOrder")
    public String createNewOrder(@ModelAttribute Order order, @RequestParam float total, HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        order.setUser(user);
        order.setTotal(total);
        int lastOrderId = (orderService.createNewOrderReturnThis(order));
        List<Cart> selectedCart = cartService.findSelectedCartInUserCart(user.getUserId());
        for (Cart cart : selectedCart) {
            OrderDetail newOrderDetail = new OrderDetail();
            newOrderDetail.setProduct(cart.getProduct());
            newOrderDetail.setQuantity(cart.getQuantity());
            newOrderDetail.setOrder(orderService.findById(lastOrderId));
            orderDetailService.save(newOrderDetail);
            cartService.remove(cart.getCartId());
        }
        List<OrderDetail> listOrder = orderDetailService.findOrderDetailByOrderId(lastOrderId);
        model.addAttribute("listOrderDetail", listOrder);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("total", total);
        model.addAttribute("newOrder", orderService.findById(lastOrderId));
        return "/user-views/order-success";
    }

    @GetMapping("/list-order")
    public String showListOrder(HttpSession session, Model model) {
        User user = (User) session.getAttribute("userLogin");
        List<Order> orderList = orderService.findOrderByUserId(user.getUserId());
        for (Order order: orderList) {
            List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailByOrderId(order.getOrderId());
            order.setOrderDetailList(orderDetailList);
            order.setFeedback(feedbackServiceIMPL.findFeedbackByOrderId(order.getOrderId()));
        }
        model.addAttribute("orderList", orderList);
        model.addAttribute("config", Config.currencyVN);
        return "/user-views/list-order";
    }

    @GetMapping("/order-mng")
    public String showAllOrder(Model model) {
        List<Order> allOrderList = orderService.findAll();
        int pageCount = (allOrderList.size() % 2) == 0 ? (allOrderList.size() / 2) : (allOrderList.size() / 2 + 1);
        List<Order> pagingOrder = orderService.findOrderByPage(1,2);
        for (Order order: pagingOrder) {
            List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailByOrderId(order.getOrderId());
            order.setOrderDetailList(orderDetailList);
        }
        model.addAttribute("orderList", pagingOrder);
        model.addAttribute("pageCount", new Array[pageCount]);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("pageCurrent", 1);
        return "/admin-views/UserManagement/order-management";
    }

    @GetMapping("/page")
    public String showOrderPage(@RequestParam int id, Model model) {
        List<Order> allOrderList = orderService.findAll();
        int pageCount = (allOrderList.size() % 2) == 0 ? (allOrderList.size() / 2) : (allOrderList.size() / 2 + 1);
        List<Order> pagingOrder = orderService.findOrderByPage(id,2);
        for (Order order: pagingOrder) {
            List<OrderDetail> orderDetailList = orderDetailService.findOrderDetailByOrderId(order.getOrderId());
            order.setOrderDetailList(orderDetailList);
        }
        model.addAttribute("orderList", pagingOrder);
        model.addAttribute("pageCount", new Array[pageCount]);
        model.addAttribute("config", Config.currencyVN);
        model.addAttribute("pageCurrent", id);
        return "/admin-views/UserManagement/order-management";
    }

    @GetMapping("/updateStat")
    public String updateStat(@RequestParam int id, @RequestParam int updateStat) {
        Order updateOrder = orderService.findById(id);
        updateOrder.setOrderStatus(updateStat);
        orderService.updateStat(updateOrder);
        return "redirect:/order/order-mng";
    }
}

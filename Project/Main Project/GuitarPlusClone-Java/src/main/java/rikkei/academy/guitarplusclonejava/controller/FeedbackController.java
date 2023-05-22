package rikkei.academy.guitarplusclonejava.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rikkei.academy.guitarplusclonejava.model.Feedback;
import rikkei.academy.guitarplusclonejava.service.IFeedbackService;
import rikkei.academy.guitarplusclonejava.service.IMPL.FeedbackServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IMPL.OrderServiceIMPL;
import rikkei.academy.guitarplusclonejava.service.IOrderService;

import java.util.Arrays;

@Controller
@RequestMapping("/feedback")
public class FeedbackController {
    IOrderService orderService = new OrderServiceIMPL();
    IFeedbackService feedbackService = new FeedbackServiceIMPL();
    @PostMapping("/sendFeed")
    public @ResponseBody void sendFeedback(@RequestBody String[] recieveData) {
        System.out.println(Arrays.toString(recieveData));
        int orderId = Integer.parseInt(recieveData[1]);
        String message = recieveData[2];
        Feedback newFeedback = new Feedback();
        newFeedback.setOrder(orderService.findById(orderId));
        newFeedback.setFeedBackMessage(message);
        feedbackService.save(newFeedback);
    }
}

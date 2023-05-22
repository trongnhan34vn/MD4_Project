package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Order;

import java.util.List;

public interface IOrderService extends IGenericService<Order>{
    int createNewOrderReturnThis(Order order);
    List<Order> findOrderByUserId(int id);
    List<Order> findOrderByPage(int page, int countPage);
    void updateStat(Order order);
}

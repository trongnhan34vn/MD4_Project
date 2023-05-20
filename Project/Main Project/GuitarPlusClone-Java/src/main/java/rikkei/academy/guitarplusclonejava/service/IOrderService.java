package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.Order;

public interface IOrderService extends IGenericService<Order>{
    int createNewOrderReturnThis(Order order);
}

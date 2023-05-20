package rikkei.academy.guitarplusclonejava.service;

import rikkei.academy.guitarplusclonejava.model.OrderDetail;

import java.util.List;

public interface IOrderDetailService extends IGenericService<OrderDetail>{
    List<OrderDetail> findOrderDetailByOrderId(int id);
}

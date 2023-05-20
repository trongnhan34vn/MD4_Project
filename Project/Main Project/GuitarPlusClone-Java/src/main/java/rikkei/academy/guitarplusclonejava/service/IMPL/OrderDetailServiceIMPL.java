package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.OrderDetail;
import rikkei.academy.guitarplusclonejava.service.IOrderDetailService;
import rikkei.academy.guitarplusclonejava.service.IOrderService;
import rikkei.academy.guitarplusclonejava.service.IProductService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailServiceIMPL implements IOrderDetailService {
    IOrderService orderService = new OrderServiceIMPL();
    IProductService productService = new ProductServiceIMPL();
    private static final String QUERY_CREATE_ORDER_DETAIL = "{CALL createOrderDetail(?,?,?)}";
    private static final String QUERY_FIND_OD_BY_OID = "{CALL findOrderDetailByOrderId(?)}";
    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public void save(OrderDetail orderDetail) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_ORDER_DETAIL);
            callableStatement.setInt(1,orderDetail.getOrder().getOrderId());
            callableStatement.setInt(2,orderDetail.getProduct().getProductId());
            callableStatement.setInt(3,orderDetail.getQuantity());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public OrderDetail findById(int id) {
        return null;
    }

    @Override
    public List<OrderDetail> findOrderDetailByOrderId(int id) {
        Connection conn = null;
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_OD_BY_OID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(orderService.findById(resultSet.getInt("OrderId")));
                orderDetail.setProduct(productService.findById(resultSet.getInt("ProductId")));
                orderDetail.setQuantity(resultSet.getInt("Quantity"));
                orderDetail.setOdId(resultSet.getInt("ODId"));
                orderDetailList.add(orderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return orderDetailList;
    }

}

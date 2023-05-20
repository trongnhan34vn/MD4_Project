package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Order;
import rikkei.academy.guitarplusclonejava.service.IOrderService;
import rikkei.academy.guitarplusclonejava.service.IUserService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.List;

public class OrderServiceIMPL implements IOrderService {
    IUserService userService = new UserServiceIMPL();
    private static final String QUERY_CREATE_ORDER = "{CALL createOrder(?,?,?,?)}";
    private static final String QUERY_FIND_BY_ID ="{CALL findOrderById(?)}";
    @Override
    public List<Order> findAll() {
   return null;
    }

    @Override
    public void save(Order order) {

    }
    @Override
    public void remove(int id) {

    }

    @Override
    public Order findById(int id) {
        Connection conn = null;
        Order order = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_ID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                order = new Order();
                order.setOrderId(resultSet.getInt("OrderId"));
                order.setUser(userService.findById(resultSet.getInt("UserId")));
                order.setPhone(resultSet.getString("Phone"));
                order.setOrderDate(resultSet.getString("OrderDate"));
                order.setOrderStatus(resultSet.getBoolean("OrderStatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return order;
    }

    @Override
    public int createNewOrderReturnThis(Order order) {
        Connection conn = null;
        int result = 0;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_ORDER);
            callableStatement.setInt(1, order.getUser().getUserId());
            callableStatement.setString(2, order.getPhone());
            callableStatement.setString(3, order.getAddress());
            callableStatement.registerOutParameter(4, Types.INTEGER);
            callableStatement.executeUpdate();
            result = callableStatement.getInt(4);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }
}

package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Order;
import rikkei.academy.guitarplusclonejava.service.IOrderService;
import rikkei.academy.guitarplusclonejava.service.IUserService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceIMPL implements IOrderService {
    IUserService userService = new UserServiceIMPL();
    private static final String QUERY_CREATE_ORDER = "{CALL createOrder(?,?,?,?,?)}";
    private static final String QUERY_FIND_BY_ID ="{CALL findOrderById(?)}";
    private static final String QUERY_FIND_ORDER_BY_USERID = "{CALL findOrderByUserId(?)}";
    private static final String QUERY_FIND_ORDER_BY_PAGE = "{CALL pagingAdminOrder(?,?)}";
    private static final String QUERY_FINDALL_ORDER = "{CALL findAllOrder()}";
    private static final String QUERY_UPDATE_STAT_ORDER = "{CALL updateStatOrder(?,?)}";
    @Override
    public List<Order> findAll() {
        List<Order> orderList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FINDALL_ORDER);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderId"));
                order.setUser(userService.findById(resultSet.getInt("UserId")));
                order.setPhone(resultSet.getString("Phone"));
                order.setAddress(resultSet.getString("Address"));
                order.setOrderDate(resultSet.getString("OrderDate"));
                order.setOrderStatus(resultSet.getInt("OrderStatus"));
                order.setTotal(resultSet.getFloat("Total"));
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return orderList;
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
                order.setAddress(resultSet.getString("Address"));
                order.setOrderDate(resultSet.getString("OrderDate"));
                order.setOrderStatus(resultSet.getInt("OrderStatus"));
                order.setTotal(resultSet.getFloat("Total"));
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
            callableStatement.setFloat(4, order.getTotal());
            callableStatement.registerOutParameter(5, Types.INTEGER);
            callableStatement.executeUpdate();
            result = callableStatement.getInt(5);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return result;
    }

    @Override
    public List<Order> findOrderByUserId(int id) {
        Connection conn = null;
        List<Order> orderList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_ORDER_BY_USERID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderId"));
                order.setUser(userService.findById(resultSet.getInt("UserId")));
                order.setPhone(resultSet.getString("Phone"));
                order.setAddress(resultSet.getString("Address"));
                order.setOrderDate(resultSet.getString("OrderDate"));
                order.setOrderStatus(resultSet.getInt("OrderStatus"));
                order.setTotal(resultSet.getFloat("Total"));
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return orderList;
    }

    @Override
    public List<Order> findOrderByPage(int page, int countPage) {
        List<Order> orderList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_ORDER_BY_PAGE);
            callableStatement.setInt(1, page);
            callableStatement.setInt(2, countPage);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getInt("OrderId"));
                order.setUser(userService.findById(resultSet.getInt("UserId")));
                order.setPhone(resultSet.getString("Phone"));
                order.setAddress(resultSet.getString("Address"));
                order.setOrderDate(resultSet.getString("OrderDate"));
                order.setOrderStatus(resultSet.getInt("OrderStatus"));
                order.setTotal(resultSet.getFloat("Total"));
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return orderList;
    }

    @Override
    public void updateStat(Order order) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_UPDATE_STAT_ORDER);
            callableStatement.setInt(1, order.getOrderStatus());
            callableStatement.setInt(2, order.getOrderId());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }


}

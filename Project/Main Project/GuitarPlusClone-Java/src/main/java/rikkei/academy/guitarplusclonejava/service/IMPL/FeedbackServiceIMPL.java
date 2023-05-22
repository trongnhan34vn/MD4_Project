package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Feedback;
import rikkei.academy.guitarplusclonejava.service.IFeedbackService;
import rikkei.academy.guitarplusclonejava.service.IOrderService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class FeedbackServiceIMPL implements IFeedbackService {
    private static final String QUERY_CREATE_NEW_FEED = "{CALL createFeed(?,?)}";
    private static final String QUERY_FIND_FEED_BY_ORDER_ID = "{CALL findFeedByOrderId(?)}";
    IOrderService orderService = new OrderServiceIMPL();
    @Override
    public List<Feedback> findAll() {
        return null;
    }

    @Override
    public void save(Feedback feedback) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_NEW_FEED);
            callableStatement.setString(1, feedback.getFeedBackMessage());
            callableStatement.setInt(2, feedback.getOrder().getOrderId());
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
    public Feedback findById(int id) {
        return null;
    }

    @Override
    public Feedback findFeedbackByOrderId(int orderId) {
        Connection conn = null;
        Feedback feedback = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_FEED_BY_ORDER_ID);
            callableStatement.setInt(1,orderId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                feedback = new Feedback();
                feedback.setFeedBackId(resultSet.getInt("FeedbackID"));
                feedback.setOrder(orderService.findById(resultSet.getInt("OrderID")));
                feedback.setFeedBackMessage(resultSet.getString("Message"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return feedback;
    }
}

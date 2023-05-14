package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.User;
import rikkei.academy.guitarplusclonejava.service.IUserService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserServiceIMPL implements IUserService {
    private static final String QUERY_FIND_ALL = "SELECT * FROM User";
    private static final String QUERY_FIND_BY_ID = "{CALL findUserById(?)}";
    private static final String QUERY_FIND_BY_EMAIL = "{CALL findUserByEmail(?)}";
    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("UserID"));
                user.setAvatar(resultSet.getString("Avatar"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getBoolean("Gender"));
                user.setBirthDate(resultSet.getString("BirthDate"));
                user.setPassword(resultSet.getString("Password"));
                user.setRole(resultSet.getInt("Role"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return userList;
    }

    @Override
    public void save(User user) {
        if(findById(user.getUserId())==null) {
            // create
        } else {
            // update
        }
    }

    @Override
    public void remove(int id) {

    }

    @Override
    public User findById(int id) {
        Connection conn = null;
        User user = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_ID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("UserID"));
                user.setAvatar(resultSet.getString("Avatar"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getBoolean("Gender"));
                user.setBirthDate(resultSet.getString("BirthDate"));
                user.setPassword(resultSet.getString("Password"));
                user.setRole(resultSet.getInt("Role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        Connection conn = null;
        User user = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_EMAIL);
            callableStatement.setString(1, email);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt("UserID"));
                user.setAvatar(resultSet.getString("Avatar"));
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
                user.setGender(resultSet.getBoolean("Gender"));
                user.setBirthDate(resultSet.getString("BirthDate"));
                user.setPassword(resultSet.getString("Password"));
                user.setRole(resultSet.getInt("Role"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return user;
    }
}

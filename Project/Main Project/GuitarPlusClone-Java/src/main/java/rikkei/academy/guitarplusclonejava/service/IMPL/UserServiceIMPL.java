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
    private static final String QUERY_FIND_ALL = "SELECT * FROM User where UserID > 0";
    private static final String QUERY_FIND_BY_ID = "{CALL findUserById(?)}";
    private static final String QUERY_FIND_BY_EMAIL = "{CALL findUserByEmail(?)}";
    private static final String QUERY_CREATE_USER = "{CALL createUser(?,?,?,?,?,?,?,?)}";
    private static final String QUERY_CHECK_LOGIN = "{CALL checkLogin(?,?)}";
    private static final String QUERY_FIND_USER_BY_PAGE = "{CALL pagingAdminUSER(?,?)}";
    private static final String QUERY_UPDATE_USER = "{CALL updateUser(?,?,?,?,?,?,?,?,?,?)}";
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
                user.setStatus(resultSet.getBoolean("Status"));
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
            createUser(user);
        } else {
            // update
            updateUser(user);
        }
    }

    private void updateUser(User user) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_UPDATE_USER);
            callableStatement.setInt(1, user.getUserId());
            callableStatement.setString(2, user.getAvatar());
            callableStatement.setString(3, user.getFirstName());
            callableStatement.setString(4, user.getLastName());
            callableStatement.setString(5, user.getEmail());
            callableStatement.setBoolean(6,user.isGender());
            callableStatement.setString(7, user.getBirthDate());
            callableStatement.setString(8, user.getPassword());
            callableStatement.setInt(9, user.getRole());
            callableStatement.setBoolean(10, user.isStatus());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    private void createUser(User user) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_USER);
            callableStatement.setString(1, user.getAvatar());
            callableStatement.setString(2, user.getFirstName());
            callableStatement.setString(3, user.getLastName());
            callableStatement.setString(4, user.getEmail());
            callableStatement.setBoolean(5,user.isGender());
            callableStatement.setString(6, user.getBirthDate());
            callableStatement.setString(7, user.getPassword());
            callableStatement.setInt(8, user.getRole());
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
                user.setStatus(resultSet.getBoolean("Status"));
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
                user.setStatus(resultSet.getBoolean("Status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return user;
    }

    @Override
    public User checkLogin(String email, String password) {
        Connection conn = null;
        User user = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CHECK_LOGIN);
            callableStatement.setString(1, email);
            callableStatement.setString(2, password);
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
                user.setStatus(resultSet.getBoolean("Status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return user;
    }

    @Override
    public List<User> findUserByPage(int page, int countPage) {
        Connection conn = null;
        List<User> userList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_USER_BY_PAGE);
            callableStatement.setInt(1, page);
            callableStatement.setInt(2, countPage);
            ResultSet resultSet = callableStatement.executeQuery();
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
                user.setStatus(resultSet.getBoolean("Status"));
                userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return userList;
    }

}

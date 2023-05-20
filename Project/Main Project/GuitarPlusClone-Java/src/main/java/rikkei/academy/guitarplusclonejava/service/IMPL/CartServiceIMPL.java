package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Cart;
import rikkei.academy.guitarplusclonejava.service.ICartService;
import rikkei.academy.guitarplusclonejava.service.IProductService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartServiceIMPL implements ICartService {
    IProductService productService = new ProductServiceIMPL();
    private static final String QUERY_FIND_CART_BY_USERID = "{CALL findCartByUserId(?)}";
    private static final String QUERY_FIND_PRODUCT_ON_USERCART = "{CALL findProdOnCartUser(?,?)}";
    private static final String QUERY_INSERT_NEW_CART = "{CALL createCart(?,?,?)}";
    private static final String QUERY_FIND_CART_BY_ID = "{CALL findCartById(?)}";
    private static final String QUERY_UPDATE_CART = "{CALL updateCart(?,?)}";
    private static final String QUERY_REMOVE_CART_BY_ID = "{CALL removeCartById(?)}";
    private static final String QUERY_UPDATE_CART_STATUS = "{CALL updateStatusCart(?,?)}";
    private static final String QUERY_FIND_SELECTED_CART = "{CALL findSelectedCartInUserCart(?)}";
    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public void save(Cart cart) {
        Cart isExistCart = findProductOnUserCart(cart.getUserId(), cart.getProduct().getProductId());
        if (isExistCart == null) {
            // save giỏ hàng mới
            System.out.println("tạo mới giỏ hàng");
            createCart(cart);
        } else {
            // update số lượng giỏ hàng cart.getquantity (số lượng mới) + findById(cart.getID).getQuantity (số lượng cũ)
            System.out.println("update số lượng");
            cart.setCartId(isExistCart.getCartId());
            cart.setQuantity(cart.getQuantity() + isExistCart.getQuantity());
            updateCart(cart);
        }
    }

    private void updateCart(Cart cart) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_UPDATE_CART);
            callableStatement.setInt(1, cart.getCartId());
            callableStatement.setInt(2, cart.getQuantity());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    private void createCart(Cart cart) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_INSERT_NEW_CART);
            callableStatement.setInt(1, cart.getUserId());
            callableStatement.setInt(2, cart.getProduct().getProductId());
            callableStatement.setInt(3, cart.getQuantity());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public void remove(int id) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_REMOVE_CART_BY_ID);
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public Cart findById(int id) {
        Connection conn = null;
        Cart cart = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_CART_BY_ID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cart = new Cart();
                cart.setCartId(resultSet.getInt("CartID"));
                cart.setUserId(resultSet.getInt("UserID"));
                cart.setProduct(productService.findById(resultSet.getInt("ProductID")));
                cart.setQuantity(resultSet.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return cart;
    }

    @Override
    public List<Cart> findCartByUserId(int id) {
        List<Cart> cartList = new ArrayList<>();
        Connection conn = null;
        try  {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_CART_BY_USERID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setCartId(resultSet.getInt("CartID"));
                cart.setUserId(resultSet.getInt("UserID"));
                cart.setProduct(productService.findById(resultSet.getInt("ProductID")));
                cart.setQuantity(resultSet.getInt("Quantity"));
                cartList.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return cartList;
    }

    @Override
    public Cart findProductOnUserCart(int uId, int pId) { // kiểm tra product đã tồn tại chưa
        Connection conn = null;
        Cart cart = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_PRODUCT_ON_USERCART);
            callableStatement.setInt(1, uId);
            callableStatement.setInt(2, pId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                cart = new Cart();
                cart.setCartId(resultSet.getInt("CartID"));
                cart.setUserId(resultSet.getInt("UserID"));
                cart.setProduct(productService.findById(resultSet.getInt("ProductID")));
                cart.setQuantity(resultSet.getInt("Quantity"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return cart;
    }

    @Override
    public void updateCartQuantity(Cart cart) {
        updateCart(cart);
    }

    @Override
    public void updateCartStatus(int id, boolean setB) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_UPDATE_CART_STATUS);
            callableStatement.setInt(1, id);
            callableStatement.setBoolean(2, setB);
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public List<Cart> findSelectedCartInUserCart(int userId) {
        List<Cart> cartList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_SELECTED_CART);
            callableStatement.setInt(1, userId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Cart cart = new Cart();
                cart.setCartId(resultSet.getInt("CartID"));
                cart.setUserId(resultSet.getInt("UserID"));
                cart.setProduct(productService.findById(resultSet.getInt("ProductID")));
                cart.setStatus(resultSet.getBoolean("Status"));
                cart.setQuantity(resultSet.getInt("Quantity"));
                cartList.add(cart);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return cartList;
    }


}

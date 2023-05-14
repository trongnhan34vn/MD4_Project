package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Image;
import rikkei.academy.guitarplusclonejava.service.IImageService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ImageServiceIMPL implements IImageService {
    private static final String QUERY_FIND_ALL = "SELECT * FROM Image";
    private static final String QUERY_FIND_BY_PRODUCT_ID = "{CALL findImagesByProductId(?)}";
    private static final String QUERY_FIND_BY_ID = "{CALL findImageById(?)}";
    private static final String QUERY_CREATE_IMG = "{CALL createImage(?,?)}";
    private static final String QUERY_DELETE_BY_ID = "{CALL deleteImageById(?)}";

    @Override
    public List<Image> findAll() {
        List<Image> imageList = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setId(resultSet.getInt("ImageID"));
                image.setUrl(resultSet.getString("URL"));
                image.setProductId(resultSet.getInt("ProductID"));
                imageList.add(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return imageList;
    }

    @Override
    public void save(Image image) {
        if (findById(image.getId()) == null) {
            //create
            createImage(image);
        } else {
            //update
        }
    }

    private void createImage(Image image) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_IMG);
            callableStatement.setString(1, image.getUrl());
            callableStatement.setInt(2, image.getProductId());
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
            CallableStatement callableStatement = conn.prepareCall(QUERY_DELETE_BY_ID);
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public Image findById(int id) {
        Connection conn = null;
        Image image = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_ID);
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                image = new Image();
                image.setProductId(resultSet.getInt("ImageID"));
                image.setUrl(resultSet.getString("URL"));
                image.setProductId(resultSet.getInt("ProductID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return image;
    }

    @Override
    public List<Image> findImagesByProductId(int pId) {
        Connection conn = null;
        List<Image> imageList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_PRODUCT_ID);
            callableStatement.setInt(1, pId);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Image image = new Image();
                image.setId(resultSet.getInt("ImageID"));
                image.setUrl(resultSet.getString("URL"));
                image.setProductId(resultSet.getInt("ProductID"));
                imageList.add(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return imageList;
    }
}

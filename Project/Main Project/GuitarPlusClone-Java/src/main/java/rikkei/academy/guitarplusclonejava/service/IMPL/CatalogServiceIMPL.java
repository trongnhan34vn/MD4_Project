package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Catalog;
import rikkei.academy.guitarplusclonejava.service.ICatalogService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CatalogServiceIMPL implements ICatalogService {
    private static final String QUERY_FIND_ALL = "SELECT * FROM Catalog";
    private static final String QUERY_FIND_BY_ID = "{CALL findCatalogById(?)}";
    @Override
    public List<Catalog> findAll() {
        Connection conn = null;
        List<Catalog> catalogList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Catalog catalog = new Catalog();
                catalog.setCatalogId(resultSet.getInt("CatalogID"));
                catalog.setCatalogName(resultSet.getString("CatalogName"));
                catalog.setStatus(resultSet.getBoolean("CatalogStatus"));
                catalogList.add(catalog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return catalogList;
    }

    @Override
    public void save(Catalog catalog) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public Catalog findById(int id) {
        Connection conn = null;
        Catalog catalog = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_ID);
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();
            while(resultSet.next()) {
                catalog = new Catalog();
                catalog.setCatalogId(resultSet.getInt("CatalogID"));
                catalog.setCatalogName(resultSet.getString("CatalogName"));
                catalog.setStatus(resultSet.getBoolean("CatalogStatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return catalog;
    }
}

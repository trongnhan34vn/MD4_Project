package rikkei.academy.guitarplusclonejava.service.IMPL;

import rikkei.academy.guitarplusclonejava.config.ConnectionDB;
import rikkei.academy.guitarplusclonejava.model.Product;
import rikkei.academy.guitarplusclonejava.service.ICatalogService;
import rikkei.academy.guitarplusclonejava.service.IImageService;
import rikkei.academy.guitarplusclonejava.service.IProductService;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceIMPL implements IProductService {
    ICatalogService catalogService = new CatalogServiceIMPL();
    IImageService imageService = new ImageServiceIMPL();
    private static final String QUERY_GET_LAST_PROD_ID = "{CALL getLastProductId()}";
    private static final String QUERY_FIND_ALL = "SELECT * FROM Product";
    private static final String QUERY_FIND_BY_ID = "{CALL findProductById(?)}";
    private static final String QUERY_CREATE_PRODUCT = "{CALL createProduct(?,?,?,?,?)}";
    private static final String QUERY_DELETE_PRODUCT = "{CALL deleteProductById(?)}";
    private static final String QUERY_UPDATE_PRODUCT = "{CALL updateProduct(?,?,?,?,?,?,?)}";
    private static final String QUERY_GET_HOT_PRODUCTS = "{CALL getHotProducts()}";
    private static final String QUERY_GET_OUTSTANDING_PRODUCTS = "{CALL getOutStandingProducts()}";
    private static final String QUERY_FIND_PRODUCT_BY_PAGE = "{CALL pagingAdminProduct(?,?)}";
    @Override
    public List<Product> findAll() {
        Connection conn = null;
        List<Product> productList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(QUERY_FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setDescription(resultSet.getString("Description"));
                int catalogId = resultSet.getInt("CatalogID");
                product.setCatalog(catalogService.findById(catalogId));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setStatus(resultSet.getBoolean("ProductStatus"));
                product.setListImgs(imageService.findImagesByProductId(product.getProductId()));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return productList;
    }

    @Override
    public void save(Product product) {
        if(findById(product.getProductId()) == null) {
            // create
            System.out.println("in create");
            create(product);
        } else {
            // update
            System.out.println("in update");
            update(product);
        }
    }

    private void update(Product product) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_UPDATE_PRODUCT);
            callableStatement.setInt(1, product.getProductId());
            callableStatement.setString(2, product.getProductName());
            callableStatement.setInt(3, product.getCatalog().getCatalogId());
            callableStatement.setString(4, product.getDescription());
            callableStatement.setInt(5, product.getQuantity());
            callableStatement.setFloat(6, product.getPrice());
            callableStatement.setBoolean(7, product.isStatus());
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    private void create(Product product) {
        Connection conn = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_CREATE_PRODUCT);
            callableStatement.setString(1, product.getProductName());
            callableStatement.setInt(2, product.getCatalog().getCatalogId());
            callableStatement.setString(3, product.getDescription());
            callableStatement.setInt(4, product.getQuantity());
            callableStatement.setFloat(5, product.getPrice());
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
        try{
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_DELETE_PRODUCT);
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
    }

    @Override
    public Product findById(int id) {
        Connection conn = null;
        Product product = null;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_BY_ID);
            callableStatement.setInt(1,id);
            ResultSet resultSet = callableStatement.executeQuery();;
            while(resultSet.next()) {
                product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                int catId = resultSet.getInt("CatalogID");
                product.setCatalog(catalogService.findById(catId));
                product.setDescription(resultSet.getString("Description"));
                product.setListImgs(imageService.findImagesByProductId(id));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setStatus(resultSet.getBoolean("ProductStatus"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return product;
    }

    @Override
    public int getLastProductId() {
        Connection conn = null;
        int id = 0;
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_GET_LAST_PROD_ID);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return id;
    }

    @Override
    public List<Product> getHotProducts() {
        Connection conn = null;
        List<Product> list = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_GET_HOT_PRODUCTS);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setDescription(resultSet.getString("Description"));
                int catalogId = resultSet.getInt("CatalogID");
                product.setCatalog(catalogService.findById(catalogId));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setStatus(resultSet.getBoolean("ProductStatus"));
                product.setListImgs(imageService.findImagesByProductId(product.getProductId()));
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return list;
    }

    @Override
    public List<Product> getOutStandingProducts() {
        Connection conn = null;
        List<Product> list = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_GET_OUTSTANDING_PRODUCTS);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setDescription(resultSet.getString("Description"));
                int catalogId = resultSet.getInt("CatalogID");
                product.setCatalog(catalogService.findById(catalogId));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setStatus(resultSet.getBoolean("ProductStatus"));
                product.setListImgs(imageService.findImagesByProductId(product.getProductId()));
                list.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return list;
    }

    @Override
    public List<Product> findProductByPageNum(int page, int countPage) {
        Connection conn = null;
        List<Product> productList = new ArrayList<>();
        try {
            conn = ConnectionDB.getConnection();
            CallableStatement callableStatement = conn.prepareCall(QUERY_FIND_PRODUCT_BY_PAGE);
            callableStatement.setInt(1,page);
            callableStatement.setInt(2,countPage);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product();
                product.setProductId(resultSet.getInt("ProductID"));
                product.setProductName(resultSet.getString("ProductName"));
                product.setDescription(resultSet.getString("Description"));
                int catalogId = resultSet.getInt("CatalogID");
                product.setCatalog(catalogService.findById(catalogId));
                product.setQuantity(resultSet.getInt("Quantity"));
                product.setPrice(resultSet.getFloat("Price"));
                product.setStatus(resultSet.getBoolean("ProductStatus"));
                product.setListImgs(imageService.findImagesByProductId(product.getProductId()));
                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionDB.closeConnection(conn);
        }
        return productList;
    }
}

package rikkei.academy.guitarplusclonejava.model;

import java.util.List;

public class Product {
    private int productId;
    private String productName;
    private Catalog catalog;
    private String description;
    private int quantity;
    private float price;
    private List<Image> listImgs;
    private boolean status;

    public Product() {
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Image> getListImgs() {
        return listImgs;
    }

    public void setListImgs(List<Image> listImgs) {
        this.listImgs = listImgs;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Product(int productId, String productName, Catalog catalog, String description, int quantity, float price, List<Image> listImgs, boolean status) {
        this.productId = productId;
        this.productName = productName;
        this.catalog = catalog;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.listImgs = listImgs;
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", catalog=" + catalog +
                ", description='" + description + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                ", listImgs=" + listImgs +
                ", status=" + status +
                '}';
    }
}

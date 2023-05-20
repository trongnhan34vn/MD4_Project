package rikkei.academy.guitarplusclonejava.model;

public class OrderDetail {
    private int odId;
    private Order order;
    private Product product;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int odId, Order order, Product product, int quantity) {
        this.odId = odId;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
    }

    public int getOdId() {
        return odId;
    }

    public void setOdId(int odId) {
        this.odId = odId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

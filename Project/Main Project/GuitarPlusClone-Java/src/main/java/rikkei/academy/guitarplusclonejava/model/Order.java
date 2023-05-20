package rikkei.academy.guitarplusclonejava.model;

public class Order {
    private int orderId;
    private User user;
    private String phone;
    private String address;
    private String orderDate;
    private boolean orderStatus;

    public Order(int orderId, User user, String phone, String address, String orderDate, boolean orderStatus) {
        this.orderId = orderId;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(boolean orderStatus) {
        this.orderStatus = orderStatus;
    }
}

package rikkei.academy.guitarplusclonejava.model;

import java.util.List;

public class Order {
    private int orderId;
    private User user;
    private String phone;
    private String address;
    private String orderDate;
    private List<OrderDetail> orderDetailList;
    private int orderStatus;
    private float total;
    private Feedback feedback;

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Order(int orderId, User user, String phone, String address, String orderDate, List<OrderDetail> orderDetailList, int orderStatus, float total, Feedback feedback) {
        this.orderId = orderId;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.orderDetailList = orderDetailList;
        this.orderStatus = orderStatus;
        this.total = total;
        this.feedback = feedback;
    }

    public Order(int orderId, User user, String phone, String address, String orderDate, List<OrderDetail> orderDetailList, int orderStatus, float total) {
        this.orderId = orderId;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.orderDetailList = orderDetailList;
        this.orderStatus = orderStatus;
        this.total = total;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }

    public Order(int orderId, User user, String phone, String address, String orderDate, int orderStatus, float total) {
        this.orderId = orderId;
        this.user = user;
        this.phone = phone;
        this.address = address;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.total = total;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
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

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", user=" + user +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}

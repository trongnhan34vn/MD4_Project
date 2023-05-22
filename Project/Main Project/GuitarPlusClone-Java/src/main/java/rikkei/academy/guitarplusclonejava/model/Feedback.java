package rikkei.academy.guitarplusclonejava.model;

public class Feedback {
    private int feedBackId;
    private String feedBackMessage;
    private Order order;

    public Feedback(int feedBackId, String feedBackMessage, Order order) {
        this.feedBackId = feedBackId;
        this.feedBackMessage = feedBackMessage;
        this.order = order;
    }

    public Feedback() {
    }

    public int getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(int feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getFeedBackMessage() {
        return feedBackMessage;
    }

    public void setFeedBackMessage(String feedBackMessage) {
        this.feedBackMessage = feedBackMessage;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedBackId=" + feedBackId +
                ", feedBackMessage='" + feedBackMessage + '\'' +
                ", order=" + order +
                '}';
    }
}

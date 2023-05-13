package rikkei.academy.guitarplusclonejava.model;

public class Image {
    private int id;
    private int productId;
    private String Url;

    public Image() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", productId=" + productId +
                ", Url='" + Url + '\'' +
                '}';
    }
}

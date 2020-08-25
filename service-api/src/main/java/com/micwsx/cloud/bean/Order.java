package com.micwsx.cloud.bean;

/**
 * @author Michael
 * @create 8/18/2020 11:18 AM
 */
public class Order {
    private String orderId;
    private String product;
    private Double price;

    public Order(String orderId, String product, Double price) {
        this.orderId = orderId;
        this.product = product;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", product='" + product + '\'' +
                ", price=" + price +
                '}';
    }
}

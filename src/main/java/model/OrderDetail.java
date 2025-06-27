package model;

public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int variationId;
    private int quantity;
    private long price;

    public OrderDetail() {
    }
    public OrderDetail(int orderDetailId, int orderId, int variationId, int quantity, long price) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.variationId = variationId;
        this.quantity = quantity;
        this.price = price;
    }
    public int getOrderDetailId() {
        return orderDetailId;
    }
    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }
    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
    public int getVariationId() {
        return variationId;
    }
    public void setVariationId(int variationId) {
        this.variationId = variationId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public long getPrice() {
        return price;
    }
    public void setPrice(long price) {
        this.price = price;
    }

}


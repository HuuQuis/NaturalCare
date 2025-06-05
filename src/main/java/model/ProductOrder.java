package model;

import java.sql.Timestamp;

public class ProductOrder {
    private int orderId;
    private int userId;
    private String note;
    private int statusId;
    private Timestamp createdAt;
    private Integer shipperId;
    private int addressId;
    private Integer couponId;

    public ProductOrder() {
    }

    public ProductOrder(int orderId, int userId, String note, int statusId, Timestamp createdAt, Integer shipperId, int addressId, Integer couponId) {
        this.orderId = orderId;
        this.userId = userId;
        this.note = note;
        this.statusId = statusId;
        this.createdAt = createdAt;
        this.shipperId = shipperId;
        this.addressId = addressId;
        this.couponId = couponId;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getShipperId() {
        return shipperId;
    }

    public void setShipperId(Integer shipperId) {
        this.shipperId = shipperId;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }
}

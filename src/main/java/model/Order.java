package model;

import java.sql.Timestamp;

public class Order {
    private int orderId;
    private int userId;
    private int addressId;
    private int statusId;
    private String note;
    private Timestamp createAt;

    private Integer shipperId;
    private Integer couponId;

    public Order() {
    }

    public Order(int orderId, int userId, int addressId, int statusId, String note, Timestamp createAt, Integer shipperId, Integer couponId) {
        this.orderId = orderId;
        this.userId = userId;
        this.addressId = addressId;
        this.statusId = statusId;
        this.note = note;
        this.createAt = createAt;
        this.shipperId = shipperId;
        this.couponId = couponId;
    }


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
    public int getAddressId() {
        return addressId;
    }
    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getStatusId() {
        return statusId;
    }
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    public Timestamp getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }
    public int getCouponId() {
        return couponId;
    }
    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public int getShipperId() {
        return shipperId;
    }
    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }
}

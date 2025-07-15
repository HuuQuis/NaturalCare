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

    // Các trường mới để hỗ trợ thanh toán online
    private String paymentMethod;                   // vnpay, momo, zalopay
    private String paymentGatewayTxnRef;            // mã gửi sang cổng
    private String paymentGatewayTransactionNo;     // mã trả về từ cổng
    private Timestamp paymentTime;                  // thời gian thanh toán

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
    
    private String customerName;
    private String shipperName;
    private String addressDisplay;
    private String couponCode;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentGatewayTxnRef() {
        return paymentGatewayTxnRef;
    }

    public void setPaymentGatewayTxnRef(String paymentGatewayTxnRef) {
        this.paymentGatewayTxnRef = paymentGatewayTxnRef;
    }

    public String getPaymentGatewayTransactionNo() {
        return paymentGatewayTransactionNo;
    }

    public void setPaymentGatewayTransactionNo(String paymentGatewayTransactionNo) {
        this.paymentGatewayTransactionNo = paymentGatewayTransactionNo;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getShipperName() {
        return shipperName;
    }

    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }

    public String getAddressDisplay() {
        return addressDisplay;
    }

    public void setAddressDisplay(String addressDisplay) {
        this.addressDisplay = addressDisplay;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
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

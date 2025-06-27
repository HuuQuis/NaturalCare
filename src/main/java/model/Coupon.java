/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

public class Coupon {
    private int couponId;
    private String code;
    private int discountPercent;
    private int minOrderAmount;
    private Date validFrom;
    private Date validTo;
    private boolean isActive;
    private int usageLimit;
    private int timeUsed;
    private boolean isUserSpecific;

    public Coupon() {
    }

    public Coupon(int couponId, String code, int discountPercent, int minOrderAmount, Date validFrom, Date validTo, boolean isActive, int usageLimit, int timeUsed, boolean isUserSpecific) {
        this.couponId = couponId;
        this.code = code;
        this.discountPercent = discountPercent;
        this.minOrderAmount = minOrderAmount;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.isActive = isActive;
        this.usageLimit = usageLimit;
        this.timeUsed = timeUsed;
        this.isUserSpecific = isUserSpecific;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(int minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }

    public int getTimeUsed() {
        return timeUsed;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public boolean isIsUserSpecific() {
        return isUserSpecific;
    }

    public void setIsUserSpecific(boolean isUserSpecific) {
        this.isUserSpecific = isUserSpecific;
    }
    
    
}

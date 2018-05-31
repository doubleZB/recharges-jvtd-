package com.jtd.recharge.dao.po;

/**
 * Created by lenovo on 2017/1/6.
 */
public class UserMobileRecharge {

    private int userId;
    private int businessType;
    private int provinceId;
    private int operator;
    private String discountPrice;
    private String positionCode;
    private Integer amount;
    private String packagesSize;
    private Integer appType;
    private Integer videoType;



    public int getBusinessType() {
        return businessType;
    }

    public int getOperator() {
        return operator;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setBusinessType(int businessType) {
        this.businessType = businessType;
    }

    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getPackagesSize() {
        return packagesSize;
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public void setPackagesSize(String packagesSize) {
        this.packagesSize = packagesSize;
    }

    public Integer getAppType() {
        return appType;
    }

    public void setAppType(Integer appType) {
        this.appType = appType;
    }

    public Integer getVideoType() {
        return videoType;
    }

    public void setVideoType(Integer videoType) {
        this.videoType = videoType;
    }
}


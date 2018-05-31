package com.jtd.recharge.dao.po;

import java.util.Date;

public class CacheOrderForQuery {
    private String orderNum;

    private Integer userId;

    private String userName;

    private String customid;

    private String mobile;

    private Integer businessType;

    private Integer operator;

    private Integer province;

    private String productName;

    private Long payCount;

    private String positionCode;

    private String supplyName;

    private String supplier;

    private Long originalPrice;

    private Long newDiscount;

    private String newSupplier;

    private Long actualPayment;

    private Date cacheTime;

    private Integer status;

    private String callbackUrl;

    public String getBusinessTypename() {
        return businessTypename;
    }

    public void setBusinessTypename(String businessTypename) {
        this.businessTypename = businessTypename;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCacheTimeS() {
        return cacheTimeS;
    }

    public void setCacheTimeS(String cacheTimeS) {
        this.cacheTimeS = cacheTimeS;
    }

    private String businessTypename;
    private String operatorName;
    private String provinceName;
    private String cacheTimeS;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getCustomid() {
        return customid;
    }

    public void setCustomid(String customid) {
        this.customid = customid == null ? null : customid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public Long getPayCount() {
        return payCount;
    }

    public void setPayCount(Long payCount) {
        this.payCount = payCount;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode == null ? null : positionCode.trim();
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName == null ? null : supplyName.trim();
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public Long getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Long originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getNewDiscount() {
        return newDiscount;
    }

    public void setNewDiscount(Long newDiscount) {
        this.newDiscount = newDiscount;
    }

    public String getNewSupplier() {
        return newSupplier;
    }

    public void setNewSupplier(String newSupplier) {
        this.newSupplier = newSupplier == null ? null : newSupplier.trim();
    }

    public Long getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(Long actualPayment) {
        this.actualPayment = actualPayment;
    }

    public Date getCacheTime() {
        return cacheTime;
    }

    public void setCacheTime(Date cacheTime) {
        this.cacheTime = cacheTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }
}
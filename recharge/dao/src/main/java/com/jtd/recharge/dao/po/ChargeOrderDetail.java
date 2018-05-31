package com.jtd.recharge.dao.po;

import java.util.Date;

public class ChargeOrderDetail {
    private Integer id;

    private Integer businessType;

    private String channelNum;

    private String supplyChannelNum;

    private String orderNum;

    private String mobile;

    private Integer supplyId;

    private Integer status;

    private Date submitTime;

    private Date returnTime;

    private String submitRspcode;

    private String returnRspcode;

    private String tableName;

    /**
     * 订单状态
     */
    public static class OrderDetailStatus {
        public static int SUCCEED_OK = 1;//提交成功
        public static int NO_DEFEATED = 2;//提交失败
        public static int BACK_SUCCEED_OK = 3;//回执成功
        public static int RECEIPT_DEFEATED = 4;//回执失败
    }



    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getSupplyChannelNum() {
        return supplyChannelNum;
    }

    public void setSupplyChannelNum(String supplyChannelNum) {
        this.supplyChannelNum = supplyChannelNum;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getSubmitRspcode() {
        return submitRspcode;
    }

    public void setSubmitRspcode(String submitRspcode) {
        this.submitRspcode = submitRspcode == null ? null : submitRspcode.trim();
    }

    public String getReturnRspcode() {
        return returnRspcode;
    }

    public void setReturnRspcode(String returnRspcode) {
        this.returnRspcode = returnRspcode == null ? null : returnRspcode.trim();
    }
}
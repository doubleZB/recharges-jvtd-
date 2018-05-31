package com.jtd.recharge.bean;

import java.math.BigDecimal;

/**
 * @autor jipengkun
 */
public class ChargeRequest {

    /**
     * 供应商id
     */
    private int supplyId;


    /**
     * 供应商名称
     */
    private String supplyName;

    /**
     * 手机号
     */
    private String mobile;


    /**
     * 发送权重
     */
    private int weight;

    /**
     * 供应商的档位编码
     */
    private String positionCode;

    /**
     * 流量包大小
     */
    private String packageSize;

    /**
     * 成本折扣
     */
    private BigDecimal costDiscount;
    /**
     * 标准价
     */
    private BigDecimal amount;
    /**
     * 渠道流水
     */
    private String channelNum;

    /**
     * 运营商
     */
    private Integer operator;

    /**
     * 订单号（湖北流量池采用）
     */
    private String orderNum;

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public String getChannelNum() {
        return channelNum;
    }

    public void setChannelNum(String channelNum) {
        this.channelNum = channelNum;
    }

    public String getSupplyName() {
        return supplyName;
    }

    public void setSupplyName(String supplyName) {
        this.supplyName = supplyName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public int getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(int supplyId) {
        this.supplyId = supplyId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPackageSize() {
        return packageSize;
    }

    public void setPackageSize(String packageSize) {
        this.packageSize = packageSize;
    }

    public BigDecimal getCostDiscount() {
        return costDiscount;
    }

    public void setCostDiscount(BigDecimal costDiscount) {
        this.costDiscount = costDiscount;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
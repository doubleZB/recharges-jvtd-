package com.jtd.recharge.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.util.Date;
import java.util.List;

public class ChargeChannel {
    private Integer id;

    private String channelName;

    private Integer channelScope;

    private Integer supplyId;

    private Integer provinceId;

    private String positionCode;

    private BigDecimal costDiscount;

    private Integer status;

    private Integer businessType;

    private Integer operatorId;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    private List<Integer> privanceIds;//省份多选
    private List<String> parValue;//面值多选
    private Integer weight;

    private String updateName;
    private String updateReason;
    private Integer updateStatus;

    private String dateStr;

    private int deletes;

    private int amount;

    public void setParValue(List<String> parValue) {
        this.parValue = parValue;
    }

    public List<String> getParValue() {
        return parValue;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }

    public Integer getChannelScope() {
        return channelScope;
    }

    public void setChannelScope(Integer channelScope) {
        this.channelScope = channelScope;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public BigDecimal getCostDiscount() {
        return costDiscount;
    }

    public void setCostDiscount(BigDecimal costDiscount) {
        this.costDiscount = costDiscount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getOperatorId() {return operatorId;}

    public void setOperatorId(Integer operatorId) {this.operatorId = operatorId;}

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getUpdateReason() {
        return updateReason;
    }

    public void setUpdateReason(String updateReason) {
        this.updateReason = updateReason;
    }

    public Integer getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Integer updateStatus) {
        this.updateStatus = updateStatus;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDeletes(int deletes) {
        this.deletes = deletes;
    }

    public int getDeletes() {
        return deletes;
    }

    public List<Integer> getPrivanceIds() {
        return privanceIds;
    }

    public void setPrivanceIds(List<Integer> privanceIds) {
        this.privanceIds = privanceIds;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
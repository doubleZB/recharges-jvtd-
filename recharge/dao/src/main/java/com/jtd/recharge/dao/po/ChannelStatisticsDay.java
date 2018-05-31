package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyp on 2017/5/24.
 */
public class ChannelStatisticsDay {

    private Integer id;
    private Integer supplyId;
    private Integer businessType;
    private Integer tatalOrds;
    private Integer successNum;
    private BigDecimal successMoney;
    private String successRate;
    private String updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getTatalOrds() {
        return tatalOrds;
    }

    public void setTatalOrds(Integer tatalOrds) {
        this.tatalOrds = tatalOrds;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public BigDecimal getSuccessMoney() {
        return successMoney;
    }

    public void setSuccessMoney(BigDecimal successMoney) {
        this.successMoney = successMoney;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}

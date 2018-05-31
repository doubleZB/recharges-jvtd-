package com.jtd.recharge.dao.po;

import javax.swing.text.html.parser.DTD;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lyp on 2017/3/7.
 */
public class UserStatisticsDay {

    private Integer id;
    private Integer userId;
    private Integer businessType;
    private Integer successOrderNum;
    private Integer sumOrderNum;
    private BigDecimal amount;
    private String updateTime;
    private String successRate;
    private String selectOutTime;

    public String getSelectOutTime() {
        return selectOutTime;
    }
    public void setSelectOutTime(String selectOutTime) {
        this.selectOutTime = selectOutTime;
    }
    public String getSuccessRate() {
        return successRate;
    }
    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getSuccessOrderNum() {
        return successOrderNum;
    }

    public void setSuccessOrderNum(Integer successOrderNum) {
        this.successOrderNum = successOrderNum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSumOrderNum() {
        return sumOrderNum;
    }

    public void setSumOrderNum(Integer sumOrderNum) {
        this.sumOrderNum = sumOrderNum;
    }
}

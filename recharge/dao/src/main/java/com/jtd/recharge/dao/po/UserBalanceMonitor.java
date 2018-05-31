package com.jtd.recharge.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Lee on 2016/12/27.
 */
public class UserBalanceMonitor {
    private Integer id;

    private Integer userId;
    private String userName;
    private Integer status;
    private Integer isCredit;

    private BigDecimal monitorBalance;
    private BigDecimal userBalance;

    private String monitorMobile;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getMonitorBalance() {
        return monitorBalance;
    }

    public void setMonitorBalance(BigDecimal monitorBalance) {
        this.monitorBalance = monitorBalance;
    }

    public String getMonitorMobile() {
        return monitorMobile;
    }

    public void setMonitorMobile(String monitorMobile) {
        this.monitorMobile = monitorMobile;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

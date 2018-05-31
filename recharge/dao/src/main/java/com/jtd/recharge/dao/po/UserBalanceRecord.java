package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class UserBalanceRecord {
    private Integer id;

    private Integer userId;

    private Integer auditorId;

    private Integer proposerId;

    private Integer addType;

    private Integer receiveUser;

    private String receiveVoucher;

    private Long amount;

    private String remark;

    private Integer status;

    private Date updateTime;


    /**
     * 余额变动金额
     */
    private BigDecimal balanceChange;
    /**
     * 当前余额
     */
    private BigDecimal balanceNow;
    /**
     * 借款变动金额
     */
    private BigDecimal borrowChange;
    /**
     * 当前借款金额
     */
    private BigDecimal borrowNow;


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

    public Integer getAddType() {
        return addType;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public Integer getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(Integer receiveUser) {
        this.receiveUser = receiveUser;
    }

    public String getReceiveVoucher() {
        return receiveVoucher;
    }

    public void setReceiveVoucher(String receiveVoucher) {
        this.receiveVoucher = receiveVoucher == null ? null : receiveVoucher.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public BigDecimal getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigDecimal balanceChange) {
        this.balanceChange = balanceChange;
    }

    public BigDecimal getBalanceNow() {
        return balanceNow;
    }

    public void setBalanceNow(BigDecimal balanceNow) {
        this.balanceNow = balanceNow;
    }

    public BigDecimal getBorrowChange() {
        return borrowChange;
    }

    public void setBorrowChange(BigDecimal borrowChange) {
        this.borrowChange = borrowChange;
    }

    public BigDecimal getBorrowNow() {
        return borrowNow;
    }

    public void setBorrowNow(BigDecimal borrowNow) {
        this.borrowNow = borrowNow;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }
}
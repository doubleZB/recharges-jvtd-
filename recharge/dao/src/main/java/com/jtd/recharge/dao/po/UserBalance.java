package com.jtd.recharge.dao.po;

import java.math.BigDecimal;

public class UserBalance {
    private Integer id;

    private Integer userId;

    private BigDecimal userBalance;

    private BigDecimal creditBalance;

    private BigDecimal borrowBalance;

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

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public BigDecimal getBorrowBalance() {
        return borrowBalance;
    }

    public void setBorrowBalance(BigDecimal borrowBalance) {
        this.borrowBalance = borrowBalance;
    }
}
package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;

public class UserPayOrder {
    private Integer id;
    private Integer userId;
    private Integer payType;

    private String bankName;
    private String backAccountName;

    private BigDecimal amount;

    private Date payTime;

    private String bankCertificate;

    private Integer auditState;
    private String userName;
    private String checkCause;
    public static class auditState{
        public static int ONE = 1;
        public static int TWO = 2;
        public static int THREE = 3;
    }
    public String getCheckCause() {
        return checkCause;
    }

    public void setCheckCause(String checkCause) {
        this.checkCause = checkCause;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBackAccountName() {
        return backAccountName;
    }

    public void setBackAccountName(String backAccountName) {
        this.backAccountName = backAccountName;
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
    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getBankCertificate() {
        return bankCertificate;
    }

    public void setBankCertificate(String bankCertificate) {
        this.bankCertificate = bankCertificate == null ? null : bankCertificate.trim();
    }

    public Integer getAuditState() {
        return auditState;
    }

    public void setAuditState(Integer auditState) {
        this.auditState = auditState;
    }
}
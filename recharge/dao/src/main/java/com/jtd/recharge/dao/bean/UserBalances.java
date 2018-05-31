package com.jtd.recharge.dao.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商户余额公用实体类
 * Created by Administrator on 2016/12/12.
 */
public class UserBalances {
        private Integer id;
        private Integer pId;

        private Integer userId;

        private BigDecimal userBalance;

        private BigDecimal creditBalance;

        private BigDecimal borrowBalance;

        private String userSid;

        private String mobile;

        private String userName;

        private String password;

        private Integer flowIsEnable;

        private Integer chargeIsEnable;

        private Integer rechargeType;

        private String contacts;

        private String contactsMobile;

        private String sells;

        private Integer status;

        private Integer isCredit;

        private String userAllName;

        private String userCnName;

        private String token;

        private Integer orderCredit;

        private Integer orderBorrow;

        private Integer orderBalance;

        private Date registerTime;

        private Integer adminId;

        private String adminName;

    private String registerTimeFormat;

    public String getRegisterTimeFormat() {
        return registerTimeFormat;
    }

    public void setRegisterTimeFormat(String registerTimeFormat) {
        this.registerTimeFormat = registerTimeFormat;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
            return adminName;
        }

        public void setAdminName(String adminName) {
            this.adminName = adminName;
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

        public String getUserSid() {
            return userSid;
        }

        public void setUserSid(String userSid) {
            this.userSid = userSid;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public Integer getFlowIsEnable() {
            return flowIsEnable;
        }

        public void setFlowIsEnable(Integer flowIsEnable) {
            this.flowIsEnable = flowIsEnable;
        }

        public Integer getChargeIsEnable() {
            return chargeIsEnable;
        }

        public void setChargeIsEnable(Integer chargeIsEnable) {
            this.chargeIsEnable = chargeIsEnable;
        }

        public Integer getRechargeType() {
            return rechargeType;
        }

        public void setRechargeType(Integer rechargeType) {
            this.rechargeType = rechargeType;
        }

        public String getContacts() {
            return contacts;
        }

        public void setContacts(String contacts) {
            this.contacts = contacts;
        }

        public String getContactsMobile() {
            return contactsMobile;
        }

        public void setContactsMobile(String contactsMobile) {
            this.contactsMobile = contactsMobile;
        }

        public String getSells() {
            return sells;
        }

        public void setSells(String sells) {
            this.sells = sells;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Integer getIsCredit() {
            return isCredit;
        }

        public void setIsCredit(Integer isCredit) {
            this.isCredit = isCredit;
        }

        public String getUserAllName() {
            return userAllName;
        }

        public void setUserAllName(String userAllName) {
            this.userAllName = userAllName;
        }

        public String getUserCnName() {
            return userCnName;
        }

        public void setUserCnName(String userCnName) {
            this.userCnName = userCnName;
        }


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public Date getRegisterTime() {
            return registerTime;
        }

        public void setRegisterTime(Date registerTime) {
            this.registerTime = registerTime;
        }

        public Integer getOrderCredit() {
            return orderCredit;
        }

        public void setOrderCredit(Integer orderCredit) {
            this.orderCredit = orderCredit;
        }

        public Integer getOrderBorrow() {
            return orderBorrow;
        }

        public void setOrderBorrow(Integer orderBorrow) {
            this.orderBorrow = orderBorrow;
        }

        public Integer getOrderBalance() {
            return orderBalance;
        }

        public void setOrderBalance(Integer orderBalance) {
            this.orderBalance = orderBalance;
        }
}

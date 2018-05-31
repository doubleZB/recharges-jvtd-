package com.jtd.recharge.dao.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by lihuimin on 2016/11/14.
 * 用户货架组公用实体类
 */
public class UserGroup {
    private Integer id;
    private Integer ids;
    private Integer pushSum;

    private String userSid;

    private String mobile;

    private String userName;

    private String password;

    private Integer flowIsEnable;

    private Integer chargeIsEnable;

    private Integer groupId;
    private String Name;
    private Integer rechargeType;

    private String contacts;

    private String contactsMobile;

    private String sells;

    private Integer status;

    private Integer isCredit;

    private String userAllName;

    private String userCnName;

    private String userZhName;

    private String token;

    private Date registerTime;
    private Date udpateTime;

    private Integer userId;

    private BigDecimal userBalance;

    private BigDecimal creditBalance;

    private BigDecimal borrowBalance;

    public Integer getPushSum() {
        return pushSum;
    }

    public void setPushSum(Integer pushSum) {
        this.pushSum = pushSum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
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

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
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

    public String getUserZhName() {
        return userZhName;
    }

    public void setUserZhName(String userZhName) {
        this.userZhName = userZhName;
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

    public Date getUdpateTime() {
        return udpateTime;
    }

    public void setUdpateTime(Date udpateTime) {
        this.udpateTime = udpateTime;
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

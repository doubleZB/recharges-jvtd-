package com.jtd.recharge.dao.po;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@SuppressWarnings("serial")
public class User implements Serializable{
    private Integer id;
    private Integer ids;
    private Integer pId;

    private String userSid;

    private String mobile;

    private String userName;

    private String password;

    private Integer flowIsEnable;

    private Integer chargeIsEnable;

    private Integer groupId;

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
    private String registerTimeFormat;

    private String ipAddress;

    private String payPassword;

    private Integer pushSum;

    public static class pushSumStatus{
        public static int STATUS_ONE = 1;
        public static int STATUS_TWO = 2;
        public static int STATUS_THREE = 3;
    }
    public Integer getPushSum() {
        return pushSum;
    }

    public void setPushSum(Integer pushSum) {
        this.pushSum = pushSum;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getRegisterTimeFormat() {
        return registerTimeFormat;
    }

    public void setRegisterTimeFormat(String registerTimeFormat) {
        this.registerTimeFormat = registerTimeFormat;
    }

    public Integer getIds() {
        return ids;
    }

    public void setIds(Integer ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserSid() {
        return userSid;
    }

    public void setUserSid(String userSid) {
        this.userSid = userSid == null ? null : userSid.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
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
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile == null ? null : contactsMobile.trim();
    }

    public String getSells() {
        return sells;
    }

    public void setSells(String sells) {
        this.sells = sells == null ? null : sells.trim();
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
        this.userAllName = userAllName == null ? null : userAllName.trim();
    }

    public String getUserCnName() {
        return userCnName;
    }

    public void setUserCnName(String userCnName) {
        this.userCnName = userCnName == null ? null : userCnName.trim();
    }

    public String getUserZhName() {
        return userZhName;
    }

    public void setUserZhName(String userZhName) {
        this.userZhName = userZhName == null ? null : userZhName.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }



}
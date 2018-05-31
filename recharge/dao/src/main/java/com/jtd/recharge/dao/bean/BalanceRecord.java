package com.jtd.recharge.dao.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 余额，详情，加款,用户
 * Created by 商户加款 lhm on 2016/11/17.
 */
public class BalanceRecord {

    /**
     * 审核人
     */
    private Integer auditorId;
    private String auditorName;
    /**
     * 审请人
     */
    private Integer proposerId;
    private String proposerName;

    private String mobile;
    private String contacts;
    private String contactsMobile;
    private Date registerTime;
    private String registerTimeFormat;
    private String sells;


    private Integer id;
    /**
     * 用户余额
     */
    private BigDecimal userBalance;

    /**
     * 信用额度
     */
    private BigDecimal creditBalance;

    /**
     * 借款额度
     */
    private BigDecimal borrowBalance;
    private BigDecimal amountOne;

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

    private String userName;

    private String userAllName;

    private String userCnName;

    private Integer userId;

    private Integer addType;

    private Integer receiveUser;

    private String receiveVoucher;

    private String amount;

    private String remark;

    private Integer status;

    private String updateTime;
    private String timeStart;
    private String timeEnd;

    private Integer adminId;

    public String getSells() {
        return sells;
    }

    public void setSells(String sells) {
        this.sells = sells;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRegisterTimeFormat() {
        return registerTimeFormat;
    }

    public void setRegisterTimeFormat(String registerTimeFormat) {
        this.registerTimeFormat = registerTimeFormat;
    }

    public static class statusType {
        public static int  UNAUDITED = 1;//未审核
        public static int  CHECKED= 2;//已审核
    }
    public static class statusAddType {
        public static int  GATHERING= 1;//实收款
        public static int  BORROW_MONEY=2;//借款
        public static int  REDUCE_MONEY= 3;//还款
    }
    public BigDecimal getAmountOne() {
        return amountOne;
    }

    public void setAmountOne(BigDecimal amountOne) {
        this.amountOne = amountOne;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getAuditorId() {
        return auditorId;
    }

    public String getAuditorName() {
        return auditorName;
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public String getProposerName() {
        return proposerName;
    }

    public Integer getId() {
        return id;
    }

    public BigDecimal getUserBalance() {
        return userBalance;
    }

    public BigDecimal getCreditBalance() {
        return creditBalance;
    }

    public BigDecimal getBorrowBalance() {
        return borrowBalance;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAllName() {
        return userAllName;
    }

    public String getUserCnName() {
        return userCnName;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getAddType() {
        return addType;
    }

    public Integer getReceiveUser() {
        return receiveUser;
    }

    public String getReceiveVoucher() {
        return receiveVoucher;
    }


    public String getRemark() {
        return remark;
    }

    public Integer getStatus() {
        return status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setAuditorId(Integer auditorId) {
        this.auditorId = auditorId;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }

    public void setProposerName(String proposerName) {
        this.proposerName = proposerName;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserBalance(BigDecimal userBalance) {
        this.userBalance = userBalance;
    }

    public void setCreditBalance(BigDecimal creditBalance) {
        this.creditBalance = creditBalance;
    }

    public void setBorrowBalance(BigDecimal borrowBalance) {
        this.borrowBalance = borrowBalance;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAllName(String userAllName) {
        this.userAllName = userAllName;
    }

    public void setUserCnName(String userCnName) {
        this.userCnName = userCnName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setAddType(Integer addType) {
        this.addType = addType;
    }

    public void setReceiveUser(Integer receiveUser) {
        this.receiveUser = receiveUser;
    }

    public void setReceiveVoucher(String receiveVoucher) {
        this.receiveVoucher = receiveVoucher;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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
}

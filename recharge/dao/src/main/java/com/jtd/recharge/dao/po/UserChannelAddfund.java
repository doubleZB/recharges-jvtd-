package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserChannelAddfund {
    private Integer id;

    private BigDecimal amount;

    private Integer applyUserid;

    private Integer auditUserid;

    private String remark;

    private Integer state;

    private String certificate;

    private Date applyTime;

    private Date applyEndTime;

    private Date auditTime;

    private Date auditEndTime;

    private Integer supplyId;

    private List<Integer> supplyIdList;

    private String auditTimestr;

    private String applyTimestr;

    public void setApplyTimestr(String applyTimestr) {
        this.applyTimestr = applyTimestr;
    }

    public void setAuditTimestr(String auditTimestr) {
        this.auditTimestr = auditTimestr;
    }

    public String getApplyTimestr() {
        return applyTimestr;
    }

    public String getAuditTimestr() {
        return auditTimestr;
    }

    /**
     * 申请状态
     */
    public static class ADDFUNDStatus {
        public static int INITALIZE_VALUE = 0;//初始值
        public static int CREATE_ADDFUND = 1;//申请加款
        public static int SUCCESS_ADDFUND = 2;//加款成功
        public static int FAILE_ADDFUND = 3;//加款失败

    }

    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public Date getApplyEndTime() {
        return applyEndTime;
    }

    public void setAuditEndTime(Date auditEndTime) {
        this.auditEndTime = auditEndTime;
    }

    public Date getAuditEndTime() {
        return auditEndTime;
    }

    public void setSupplyIdList(List<Integer> supplyIdList) {
        this.supplyIdList = supplyIdList;
    }

    public List<Integer> getSupplyIdList() {
        return supplyIdList;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getApplyUserid() {
        return applyUserid;
    }

    public void setApplyUserid(Integer applyUserid) {
        this.applyUserid = applyUserid;
    }

    public Integer getAuditUserid() {
        return auditUserid;
    }

    public void setAuditUserid(Integer auditUserid) {
        this.auditUserid = auditUserid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getCertificate() {
        return certificate;
    }

    public void setCertificate(String certificate) {
        this.certificate = certificate == null ? null : certificate.trim();
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
}
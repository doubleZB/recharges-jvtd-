package com.jtd.recharge.dao.po;

import java.util.Date;

public class CacheRole {
    private Long id;

    private Integer businessType;

    private Integer cacheType;

    private Integer userId;

    private String supplier;

    private String objectName;

    private String operator;

    private String province;

    private String positionCode;

    public String getPositionCodeStr() {
        return positionCodeStr;
    }

    public void setPositionCodeStr(String positionCodeStr) {
        this.positionCodeStr = positionCodeStr;
    }

    private String positionCodeStr;

    private Integer status;

    private Date createTime;

    private String createUser;

    public static class statuss {
        public static int OPEN = 1;//开启

        public static int CLOSE = 2;//关闭
    }

    public static class cacheTypes {
        public static int USERCACHE = 1;//商户缓存
        public static int SUPPLIERCACHE = 2;//供应商缓存
    }

    public static class operators {
        public static int WHOLE = 0;//全部运营商
        public static int MOBILE = 1;//移动
        public static int UNICOM = 2;//联通
        public static int TELECOM = 3;//电信
    }

    public static class provinces {
        public static int WHOLE = 0;//全部省份
    }

    public static class positionCodes {
        public static int WHOLE_MOBILE_FLOW = 111;//全部移动流量
        public static int WHOLE_UNICOM_FLOW = 222;//全部联通流量
        public static int WHOLE_TELECOM_FLOW = 333;//全部电信流量
        public static int WHOLE_MOBILE_BILL = 444;//全部移动话费
        public static int WHOLE_UNICOM_BILL = 555;//全部联通话费
        public static int WHOLE_TELECOM_BILL = 666;//全部电信话费
        public static int WHOLE = 777;//全部运营商流量话费
    }

    private String provinceStr; //读取省的名称，用都好分割

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getCacheType() {
        return cacheType;
    }

    public void setCacheType(Integer cacheType) {
        this.cacheType = cacheType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier == null ? null : supplier.trim();
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName == null ? null : objectName.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode == null ? null : positionCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getProvinceStr() {
        return provinceStr;
    }

    public void setProvinceStr(String provinceStr) {
        this.provinceStr = provinceStr;
    }
}
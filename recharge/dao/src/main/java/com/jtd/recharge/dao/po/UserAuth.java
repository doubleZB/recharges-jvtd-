package com.jtd.recharge.dao.po;

import java.util.Date;

public class UserAuth {
    private Integer id;

    private Integer userId;

    private Integer userType;


    /**
     * 商户类型
     */
    public static class MERCHANTS_TYPE {
        public static int  FIRM = 1;//企业
        public static int   PERSONAGE= 2;//个人
    }
    private String businessLicenseNum;


    /**
     * 用户名 全称
     */
    private String userName;
    private String userAllName;

    private String remark;
    private String businessLicenseImage;

    private String name;

    private String identityCardNum;

    private String identityCardFront;

    private String identityCardBack;

    private Integer authState;

    /**
     * 认证状态
     */
    public static class ATTESTATION_STATUS {
        public static int  ATTESTATION_NO = 1;//未认证
        public static int  CHECK_PENDING= 2;//待审核
        public static int  ATTESTATION_YES= 3;//认证通过
        public static int  ATTESTATION_FAILED= 4;//认证未通过
    }
    private Date updateTime;

    public String getUserAllName() {
        return userAllName;
    }

    public void setUserAllName(String userAllName) {
        this.userAllName = userAllName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getBusinessLicenseNum() {
        return businessLicenseNum;
    }

    public void setBusinessLicenseNum(String businessLicenseNum) {
        this.businessLicenseNum = businessLicenseNum == null ? null : businessLicenseNum.trim();
    }


    public String getBusinessLicenseImage() {
        return businessLicenseImage;
    }

    public void setBusinessLicenseImage(String businessLicenseImage) {
        this.businessLicenseImage = businessLicenseImage == null ? null : businessLicenseImage.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentityCardNum() {
        return identityCardNum;
    }

    public void setIdentityCardNum(String identityCardNum) {
        this.identityCardNum = identityCardNum == null ? null : identityCardNum.trim();
    }

    public String getIdentityCardFront() {
        return identityCardFront;
    }

    public void setIdentityCardFront(String identityCardFront) {
        this.identityCardFront = identityCardFront == null ? null : identityCardFront.trim();
    }

    public String getIdentityCardBack() {
        return identityCardBack;
    }

    public void setIdentityCardBack(String identityCardBack) {
        this.identityCardBack = identityCardBack == null ? null : identityCardBack.trim();
    }

    public Integer getAuthState() {
        return authState;
    }

    public void setAuthState(Integer authState) {
        this.authState = authState;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
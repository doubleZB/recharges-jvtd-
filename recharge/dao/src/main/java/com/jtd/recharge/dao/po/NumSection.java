package com.jtd.recharge.dao.po;

public class NumSection {
    private Integer id;

    private String mobileNumber;

    private String mobileProvince;

    private Integer provinceId;

    private String mobileArea;

    private Integer mobileType;

    private String mobileTypeName;

    private String areaCode;

    private String postCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public String getMobileProvince() {
        return mobileProvince;
    }

    public void setMobileProvince(String mobileProvince) {
        this.mobileProvince = mobileProvince == null ? null : mobileProvince.trim();
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public String getMobileArea() {
        return mobileArea;
    }

    public void setMobileArea(String mobileArea) {
        this.mobileArea = mobileArea == null ? null : mobileArea.trim();
    }

    public Integer getMobileType() {
        return mobileType;
    }

    public void setMobileType(int mobileType) {
        this.mobileType = mobileType;
    }

    public String getMobileTypeName() {
        return mobileTypeName;
    }

    public void setMobileTypeName(String mobileTypeName) {
        this.mobileTypeName = mobileTypeName == null ? null : mobileTypeName.trim();
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }
}
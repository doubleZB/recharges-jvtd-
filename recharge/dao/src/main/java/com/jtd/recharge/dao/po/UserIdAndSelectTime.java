package com.jtd.recharge.dao.po;

/**
 * Created by Administrator on 2017/3/9.
 */
public class UserIdAndSelectTime {
    private Integer userId;
    private String updateTime;
    private Integer businessType;

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

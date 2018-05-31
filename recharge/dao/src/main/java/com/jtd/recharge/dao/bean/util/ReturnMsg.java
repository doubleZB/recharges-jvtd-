package com.jtd.recharge.dao.bean.util;

/**
 * Created by WXP on 2016-11-14.
 */
public class ReturnMsg {
    private Boolean isSuccess;
    private String message;
    private Object object;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

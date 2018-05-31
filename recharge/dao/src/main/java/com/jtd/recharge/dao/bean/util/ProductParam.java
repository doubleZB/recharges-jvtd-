package com.jtd.recharge.dao.bean.util;

import java.util.List;

/**
 * Created by WXP on 2016-11-18 16:22:06.
 */
public class ProductParam {
    private String business_type;//商品类型
    private String operator;//运营商
    private List<String> province_id;//省份id
    private List<String> position_code;//流量或者话费的档位ID
    private String scope;//生效范围

    public String getBusiness_type() {
        return business_type;
    }

    public void setBusiness_type(String business_type) {
        this.business_type = business_type;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public List<String> getProvince_id() {
        return province_id;
    }

    public void setProvince_id(List<String> province_id) {
        this.province_id = province_id;
    }

    public List<String> getPosition_code() {
        return position_code;
    }

    public void setPosition_code(List<String> position_code) {
        this.position_code = position_code;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

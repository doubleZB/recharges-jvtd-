package com.jtd.recharge.dao.po;

public class ChargeWhiteList {
    private Integer id;

    private String whiteMobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWhiteMobile() {
        return whiteMobile;
    }

    public void setWhiteMobile(String whiteMobile) {
        this.whiteMobile = whiteMobile == null ? null : whiteMobile.trim();
    }
}
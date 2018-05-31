package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ChargeProductGroup {
    private Integer id;

    private String name;

    private Date updatetime;

    private List<Integer> operators;

    private List<BigDecimal> discountPrices;

    private List<Integer> supplyid;

    public void setSupplyid(List<Integer> supplyid) {
        this.supplyid = supplyid;
    }

    public List<Integer> getSupplyid() {
        return supplyid;
    }

    public List<BigDecimal> getDiscountPrices() {
        return discountPrices;
    }

    public List<Integer> getOperators() {
        return operators;
    }

    public void setDiscountPrices(List<BigDecimal> discountPrices) {
        this.discountPrices = discountPrices;
    }

    public void setOperators(List<Integer> operators) {
        this.operators = operators;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
package com.jtd.recharge.dao.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ChargeProductStoreF {
    private Integer id;

    private String groupName;

    private String productTyoe;

    private String  yys;

    private String  privanceName;

    private String  mianzhi;

    private String  flowSize;

    private String  activeScope;

    private BigDecimal  zk;

    private BigDecimal  zk_to_update;

    private BigDecimal  zk_satrt;

    private BigDecimal  zk_end;

    private Integer  zk_type;

    private BigDecimal  zk_num;

    private Integer  supplierID;

    private String sendType;

    private String status;

    private String supply_name;

    private String status_to_update;

    private Date updatetime;

    private int amount;

    private List<Integer> privanceIds;

    private List<Integer> positionCodes;

    public List<Integer> getPositionCodes() {
        return positionCodes;
    }

    public void setPositionCodes(List<Integer> positionCodes) {
        this.positionCodes = positionCodes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProductTyoe() {
        return productTyoe;
    }

    public void setProductTyoe(String productTyoe) {
        this.productTyoe = productTyoe;
    }

    public String getYys() {
        return yys;
    }

    public void setYys(String yys) {
        this.yys = yys;
    }

    public String getPrivanceName() {
        return privanceName;
    }

    public void setPrivanceName(String privanceName) {
        this.privanceName = privanceName;
    }

    public String getMianzhi() {
        return mianzhi;
    }

    public void setMianzhi(String mianzhi) {
        this.mianzhi = mianzhi;
    }

    public String getFlowSize() {
        return flowSize;
    }

    public void setFlowSize(String flowSize) {
        this.flowSize = flowSize;
    }

    public String getActiveScope() {
        return activeScope;
    }

    public void setActiveScope(String activeScope) {
        this.activeScope = activeScope;
    }

    public BigDecimal getZk() {
        return zk;
    }

    public void setZk(BigDecimal zk) {
        this.zk = zk;
    }

    public BigDecimal getZk_satrt() {
        return zk_satrt;
    }

    public void setZk_satrt(BigDecimal zk_satrt) {
        this.zk_satrt = zk_satrt;
    }

    public BigDecimal getZk_end() {
        return zk_end;
    }

    public void setZk_end(BigDecimal zk_end) {
        this.zk_end = zk_end;
    }

    public Integer getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Integer supplierID) {
        this.supplierID = supplierID;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getPrivanceIds() {
        return privanceIds;
    }

    public void setPrivanceIds(List<Integer> privanceIds) {
        this.privanceIds = privanceIds;
    }

    public BigDecimal getZk_to_update() {
        return zk_to_update;
    }

    public void setZk_to_update(BigDecimal zk_to_update) {
        this.zk_to_update = zk_to_update;
    }

    public String getStatus_to_update() {
        return status_to_update;
    }

    public void setStatus_to_update(String status_to_update) {
        this.status_to_update = status_to_update;
    }

    public String getSupply_name() {
        return supply_name;
    }

    public void setSupply_name(String supply_name) {
        this.supply_name = supply_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Integer getZk_type() {
        return zk_type;
    }

    public void setZk_type(Integer zk_type) {
        this.zk_type = zk_type;
    }

    public BigDecimal getZk_num() {
        return zk_num;
    }

    public void setZk_num(BigDecimal zk_num) {
        this.zk_num = zk_num;
    }
}
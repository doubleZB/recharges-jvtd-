package com.jtd.recharge.bean;

import com.jtd.recharge.dao.po.ChargePosition;

import java.util.List;

/**
 * @autor jipengkun
 */
public class QueryPosition {

    private String status;

    private String statusMsg;

    /**
     * 档位列表
     */
    private List<ChargePosition> positions;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ChargePosition> getPositions() {
        return positions;
    }

    public void setPositions(List<ChargePosition> positions) {
        this.positions = positions;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }
}

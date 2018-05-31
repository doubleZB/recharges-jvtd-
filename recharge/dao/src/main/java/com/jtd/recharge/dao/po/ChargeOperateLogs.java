package com.jtd.recharge.dao.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ChargeOperateLogs {
    private Integer id;

    private String operater;

    private String menu;

    private String content;

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String operateTime;
    private String startOperateTime;
    private String endOperateTime;

    public String getEndOperateTime() {
        return endOperateTime;
    }

    public void setEndOperateTime(String endOperateTime) {
        this.endOperateTime = endOperateTime;
    }

    public String getStartOperateTime() {
        return startOperateTime;
    }

    public void setStartOperateTime(String startOperateTime) {
        this.startOperateTime = startOperateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperater() {
        return operater;
    }

    public void setOperater(String operater) {
        this.operater = operater == null ? null : operater.trim();
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu == null ? null : menu.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(String operateTime) {
        this.operateTime = operateTime;
    }
}
package com.jtd.recharge.dao.po;

public class IotConfig {
    private Long id;

    private String name;

    private String configData;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getConfigData() {
        return configData;
    }

    public void setConfigData(String configData) {
        this.configData = configData == null ? null : configData.trim();
    }
}
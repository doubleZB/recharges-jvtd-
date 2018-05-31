package com.jtd.recharge.service.admin;

import java.util.List;

/**
 * Created by anna on 2016-12-01.
 */
public class NavData {
    private List<Integer> rootIds;
    private Object data;

    public List<Integer> getRootIds() {
        return rootIds;
    }

    public void setRootIds(List<Integer> rootIds) {
        this.rootIds = rootIds;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

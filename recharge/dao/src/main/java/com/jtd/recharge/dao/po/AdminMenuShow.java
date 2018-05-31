package com.jtd.recharge.dao.po;

/**
 * Created by WXP 2016-11-25 11:45:18.
 */
public class AdminMenuShow {
    private Integer id;
    private Integer pId;
    private String name;
    private boolean open;
    private boolean checked;

    public AdminMenuShow(){
        this.checked=false;
        this.open=true;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "AdminMenuShow{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", open=" + open +
                ", checked=" + checked +
                '}';
    }
}

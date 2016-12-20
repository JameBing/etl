package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/12/20.
 */
public class Status {
    @SerializedName("order_mode")
    private int ordermode;
    @SerializedName("is_valid")
    private int isvalid;
    @SerializedName("is_open")
    private int isopen;

    public int getOrdermode() {
        return ordermode;
    }

    public void setOrdermode(int ordermode) {
        this.ordermode = ordermode;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public int getIsopen() {
        return isopen;
    }

    public void setIsopen(int isopen) {
        this.isopen = isopen;
    }
}

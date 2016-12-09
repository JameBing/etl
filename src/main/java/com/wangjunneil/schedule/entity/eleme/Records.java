package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by admin on 2016/12/8.
 */
public class Records {
    @SerializedName("deliveryman_info")
    private DeliverymanInfo deliverymaninfo;
    @SerializedName("status_code")
    private int statuscode;
    @SerializedName("updated_at")
    private Date updatedat;
    @SerializedName("sub_status_code")
    private int substatuscode;

    public DeliverymanInfo getDeliverymaninfo() {
        return deliverymaninfo;
    }

    public void setDeliverymaninfo(DeliverymanInfo deliverymaninfo) {
        this.deliverymaninfo = deliverymaninfo;
    }

    public int getStatuscode() {
        return statuscode;
    }

    public void setStatuscode(int statuscode) {
        this.statuscode = statuscode;
    }

    public Date getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Date updatedat) {
        this.updatedat = updatedat;
    }

    public int getSubstatuscode() {
        return substatuscode;
    }

    public void setSubstatuscode(int substatuscode) {
        this.substatuscode = substatuscode;
    }
}

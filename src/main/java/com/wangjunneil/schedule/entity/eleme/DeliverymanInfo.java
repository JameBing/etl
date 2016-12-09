package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/12/8.
 */
public class DeliverymanInfo {
    @SerializedName("deliveryman_name")
    private String deliverymanname;
    @SerializedName("deliveryman_phone")
    private String deliverymanphone;

    public String getDeliverymanname() {
        return deliverymanname;
    }

    public void setDeliverymanname(String deliverymanname) {
        this.deliverymanname = deliverymanname;
    }

    public String getDeliverymanphone() {
        return deliverymanphone;
    }

    public void setDeliverymanphone(String deliverymanphone) {
        this.deliverymanphone = deliverymanphone;
    }
}

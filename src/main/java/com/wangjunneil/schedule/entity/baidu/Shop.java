package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class Shop {
    @SerializedName("id")
    private String shopId;
    private String name;
    public String getShopId() {
        return shopId;
    }
    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}

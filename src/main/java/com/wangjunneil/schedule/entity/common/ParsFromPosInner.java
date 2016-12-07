package com.wangjunneil.schedule.entity.common;

import com.wangjunneil.schedule.utility.StringUtil;

/**
 * Created by yangwanbin on 2016-11-24.
 */
public class ParsFromPosInner {

    private String shopId;

    private String platformShopId;

    private String dishId;

    private String platformDishId;

    private String orderId;

    private String platformOrderId;

    public void setShopId(String shopId){
        this.shopId = shopId;
    }

    public String getShopId(){
        return this.shopId;
    }

    public void setPlatformShopId(String platformShopId){
        this.platformShopId = platformShopId;
    }

    public String getPlatformShopId(){
        return StringUtil.isEmpty(this.platformShopId)?"":this.platformShopId;
    }

    public  void setDishId(String dishId){
        this.dishId = dishId;
    }

    public String getDishId(){
        return StringUtil.isEmpty(this.dishId)?"":this.dishId;
    }

    public void setPlatformDishId(String platformDishId){
        this.platformDishId = platformDishId;
    }

    public String getPlatformDishId(){
        return StringUtil.isEmpty(this.platformDishId)?"":this.platformDishId;
    }

    public void setOrderId(String dishId){
        this.dishId = dishId;
    }

    public String getOrderId(){
        return StringUtil.isEmpty(this.orderId)?"":this.orderId;
    }

    public void setPlatformOrderId(String platformOrderId){
        this.platformOrderId = platformOrderId;
    }

    public String getPlatformOrderId(){
        return StringUtil.isEmpty(this.platformOrderId)?"":this.platformOrderId;
    }
}

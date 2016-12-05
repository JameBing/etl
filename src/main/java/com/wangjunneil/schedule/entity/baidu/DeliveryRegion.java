package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by yangwanbin on 2016-12-02.
 */
//配送区域信息
public class DeliveryRegion {

    //配送区域名称
    private String name;

    //配送时长;单位:分钟
    @SerializedName("delivery_time")
    private String deliveryTime;

    //配送费;单位:分
    @SerializedName("delivery_fee")
    private String deliveryFee;

    //满免配送费;单位:分;大于0;
    @SerializedName("min_buy_free")
    private String minBuyFree;

    //外卖起送价;单位:分
    @SerializedName("min_order_price")
    private  String minOrderPrice;

    //配送范围二维数组
    @SerializedName("region")
    private List<Region[]> regions;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return  this.name;
    }

    public void setDeliveryTime(String deliveryTime){
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryTime(){
        return this.deliveryTime;
    }

    public void setDeliveryFee(String deliveryFee){
        this.deliveryFee = deliveryFee;
    }

    public String getDeliveryFee(){
        return this.deliveryFee;
    }

    public void setMinBuyFree(String minBuyFree){
        this.minBuyFree = minBuyFree;
    }

    public String getMinBuyFree(){
        return this.minBuyFree;
    }

    public void setMinOrderPrice(String minOrderPrice){
        this.minOrderPrice = minOrderPrice;
    }

    public String getMinOrderPrice(){
        return this.minOrderPrice;
    }

    public void setRegions(List<Region[]> regions){
        this.regions = regions;
    }

    public List<Region[]> getRegions(){
        return this.regions;
    }
}

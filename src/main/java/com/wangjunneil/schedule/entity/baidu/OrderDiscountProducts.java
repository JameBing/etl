package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderDiscountProducts {

    //百度ID
    @SerializedName("baidu_product_id")
    private String baiduProductId;

    //原始价
    @SerializedName("orig_price")
    private  String origPrice;

    //优惠金额
    @SerializedName("save_price")
    private String savePrice;

    //优惠后金额
    @SerializedName("now_price")
    private String nowPrice;

    public void setBaiduProductId(String baiduProductId){
        this.baiduProductId = baiduProductId;
    }

    public String getBaiduProductId(){
        return this.baiduProductId;
    }

    public void setOrigPrice(String origPrice){
        this.origPrice = origPrice;
    }

    public String getOrigPrice(){
        return this.origPrice;
    }

    public void setSavePrice(String savePrice){
        this.savePrice = savePrice;
    }

    public String getSavePrice(){
        return this.savePrice;
    }

    public void setNowPrice(String nowPrice){
        this.nowPrice = nowPrice;
    }

    public String getNowPrice(){
        return this.nowPrice;
    }
}

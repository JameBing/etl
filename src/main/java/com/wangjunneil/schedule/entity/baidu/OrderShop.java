package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.io.PrintWriter;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderShop {

    //商户ID
    @SerializedName("id")
    private String shopId;

    //百度商户ID
    @SerializedName("baidu_shop_id")
    private  String baiduShopId;

    //百度商户名称
    private String name;

    public void setShopId(String shopId){
        this.shopId = shopId;
    }

    public String getShopId(){
        return this.shopId;
    }

    public void setBaiduShopId(String baiduShopId){
        this.baiduShopId = baiduShopId;
    }

    public String getBaiduShopId(){
        return this.baiduShopId;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}

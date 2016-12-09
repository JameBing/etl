package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderDiscount {

    //优惠类型
    private String type;

    //优惠金额 单位：分
    private String fee;

    //活动ID
    @SerializedName("activity_id")
    private String activityId;

    //百度承担金额
    @SerializedName("baidu_rate")
    private String baiduRate;

    //代理商承担金额
    @SerializedName("agent_rate")
    private String agentRate;

    //物流承担金额
    @SerializedName("logistics_rate")
    private String logisticsRate;

    //优惠描述
    private  String desc;

    //特价菜(单品)
    @SerializedName("products")
    private List<OrderDiscountProducts> products;

     public void setType(String type){
         this.type = type;
     }

    public String getType(){
        return this.type;
    }

    public void setFee(String fee){
        this.fee = fee;
    }

    public String getFee(){
        return this.fee;
    }

    public void setActivityId(String activityId){
        this.activityId = activityId;
    }

    public String getActivityId(){
        return this.activityId;
    }

    public void setBaiduRate(String baiduRate){
        this.baiduRate = baiduRate;
    }

    public String getBaiduRate(){
        return this.baiduRate;
    }

    public void setAgentRate(String agentRate){
        this.agentRate = agentRate;
    }

    public String getAgentRate(){
        return this.agentRate;
    }

    public void setLogisticsRate(String logisticsRate){
        this.logisticsRate = logisticsRate;
    }

    public String getLogisticsRate(){
        return this.logisticsRate;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setProducts(List<OrderDiscountProducts> products){
        this.products = products;
    }

    public List<OrderDiscountProducts> getProducts(){
        return this.products;
    }
}

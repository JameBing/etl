package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-17.
 */
public class Discount {

    //优惠类型，枚举值参见附录
    private String type;

    //优惠金额，单位：分
    private  int fee;

    //活动ID
    @SerializedName("activity_id")
    private  String activityId;

    //活动规则ID，一个活动可能对应多个规则
    @SerializedName("rule_id")
    private  String ruleId;

    //百度承担金额
    @SerializedName("baidu_rate")
    private String baiduRate;

    //商户承担金额
    @SerializedName("shop_rate")
    private String shopRate;

    //代理商承担金额
    @SerializedName("agent_rate")
    private String agentRate;

    //物流承担金额
    @SerializedName("logistics_rate")
    private String logisticsRate;

    //优惠描述
    private String desc;

    public  void setType(String type){
        this.type = type;
    }

    public String getType(){
        return  this.type;
    }

    public void setFee(int fee){
        this.fee = fee;
    }

    public int getFee(){
        return this.fee;
    }

    public void setActivityId(String activityId){
        this.activityId = activityId;
    }

    public String getActivityId(){
        return  this.activityId;
    }

    public void setRuleId(String ruleId){
        this.ruleId = ruleId;
    }

    public String getRuleId(){
        return this.ruleId;
    }

    public  void setBaiduRate(String baiduRate){
        this.baiduRate = baiduRate;
    }

    public  String getBaiduRate(){
        return  this.baiduRate;
    }

    public  void  setShopRate(String shopRate){
        this.shopRate = shopRate;
    }

    public  String getShopRate(){
        return  this.shopRate;
    }

    public  void setAgentRate(String agentRate){
        this.agentRate = agentRate;
    }

    public String getAgentRate(){
        return  this.agentRate;
    }

    public void setLogisticsRate(String logisticsRate){
        this.logisticsRate = logisticsRate;
    }

    public String getLogisticsRate(){
        return  this.logisticsRate;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return  this.desc;
    }
}

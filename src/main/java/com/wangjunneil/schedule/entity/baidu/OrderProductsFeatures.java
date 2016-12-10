package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsFeatures {

    //商品属性
    private  String option;

    //属性名称
    private  String name;

    //百度属性ID
    @SerializedName("baidu_feature_id")
    private String baiduFeatureId;

    public void setOption(String option){
        this.option = option;
    }

    public String getOption(){
        return this.option;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public  void setBaiduFeatureId(String baiduFeatureId){
        this.baiduFeatureId = baiduFeatureId;
    }

    public String getBaiduFeatureId(){
        return this.baiduFeatureId;
    }
}

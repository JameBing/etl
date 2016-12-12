package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsComboGroup {

    //组合名称
    @SerializedName("group_name")
    private String groupName;

    //百度组合ID
    @SerializedName("baidu_group_id")
    private String baiduGroupId;

    //组合内单品，菜品和单菜一致
    @SerializedName("product")
    private List<OrderProductsComboGroupProduct> products;

    public void setGroupName(String groupName){
        this.groupName = groupName;
    }

    public String getGroupName(){
        return this.groupName;
    }

    public void setBaiduGroupId(String baiduGroupId){
         this.baiduGroupId =baiduGroupId;
    }

    public String getBaiduGroupId(){
        return this.baiduGroupId;
    }

    public void setProducts(List<OrderProductsComboGroupProduct> products){
        this.products = products;
    }

    public List<OrderProductsComboGroupProduct> getProducts(){
        return this.products;
    }
}

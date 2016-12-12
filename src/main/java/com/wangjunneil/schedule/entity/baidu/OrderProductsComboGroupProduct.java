package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import com.wangjunneil.schedule.utility.StringUtil;

import java.util.Vector;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsComboGroupProduct {

    @SerializedName("baidu_product_id")
    private String baiduProductId;

    @SerializedName("product_id")
    private String productId;

    @SerializedName("product_name")
    private String productName;

    @SerializedName("product_type")
    private Integer productType;

    @SerializedName("product_amount")
    private Integer productAmount;

    @SerializedName("product_fee")
    private Integer productFee;

    @SerializedName("product_price")
    private Integer productPrice;

    public void setBaiduProductId(String baiduProductId){
        this.baiduProductId = baiduProductId;
    }

    public String getBaiduProductId(){
        return this.baiduProductId;
    }

    public void setProductId(String productId){
         this.productId = productId;
    }

    public String getProductId(){
        return this.productId;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductType(Integer productType){
        this.productType = productType;
    }

    public Integer getProductType(){
        return this.productType;
    }

    public void setProductAmount(Integer productAmount){
        this.productAmount = productAmount;
    }

    public Integer getProductAmount(){
        return this.productAmount;
    }

    public void setProductFee(Integer productFee){
        this.productFee = productFee;
    }

    public Integer getProductFee(){
        return this.productFee;
    }

    public void setProductPrice(Integer productPrice){
        this.productPrice = productPrice;
    }

    public Integer getProductPrice(){
        return this.productPrice;
    }
}

package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Products {

    //商品 ID
    @SerializedName("product_id")
    private String productId;

    //商品名称
    @SerializedName("product_name")
    private String productName;

    //份数
    @SerializedName("product_amount")
    private int productAmount;

    //商品价格，单位：分
    @SerializedName("product_price")
    private int productPrice;

    //商品总价，单位：分
    @SerializedName("product_fee")
    private int productFee;

    //餐盒单价，单位：分
    @SerializedName("package_price")
    private int packagePrice;

    //餐盒数量
    @SerializedName("package_amount")
    private int packageAmount;

    //餐盒总价，单位：分
    @SerializedName("package_fee")
    private int packageFee;

    //小结总价，单位：分
    @SerializedName("total_fee")
    private int totalFee;

    private String upc;

    public void setProductId(String productId){
        this.productId = productId;
    }

    public String getProductId(){
        return this.productId;
    }

    public  void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductName(){
        return this.productName;
    }

    public  void setProductAmount(int productAmount){
        this.productAmount = productAmount;
    }

    public int getProductAmount(){
        return  this.productAmount;
    }

    public void setProductPrice(int productPrice){
        this.productPrice = productPrice;
    }

    public int getProductPrice(){
        return this.productPrice;
    }

    public void setProductFee(int productFee){
        this.productFee = productFee;
    }

    public int getProductFee(){
        return this.productFee;
    }

    public void setPackagePrice(int packagePrice){
        this.packagePrice = packagePrice;
    }

    public int getPackagePrice(){
        return  this.packagePrice;
    }

    public void setPackageAmount(int packageAmount){
        this.packageAmount = packageAmount;
    }

    public int getPackageAmount(){
        return  this.packageAmount;
    }

    public void setPackageFee(int packageFee){
        this.packageFee = packageFee;
    }

    public int getPackageFee(){
        return this.packageFee;
    }

    public void setTotalFee(int totalFee){
        this.setTotalFee(totalFee);
    }

    public int getTotalFee(){
        return  this.totalFee;
    }

    public void setUpc(String upc){
        this.upc = upc;
    }

    public String getUpc(){
        return this.upc;
    }
}

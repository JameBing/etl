package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import com.jd.open.api.sdk.domain.stock.StoreService.StockInResult;

import java.util.List;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsCombo {

    //百度套餐ID
    @SerializedName("baidu_product_id")
    private String baiduProductId;

    //第三方套餐ID
    @SerializedName("product_id")
    private String productId;

    //菜品类型,1.单品 2 套餐 3 配料
    private String type;

    //套餐名称
    @SerializedName("product_name")
    private String productName;

    //套餐份数
    @SerializedName("product_amount")
    private String productAmount;

    //价格
    @SerializedName("product_price")
    private String productPrice;

    //套餐总价，单位：分
    @SerializedName("product_fee")
    private Integer productFee;

    //餐盒总价，单位：分
    @SerializedName("package_fee")
    private  Integer packageFee;

    //餐盒单价，单位：分
    @SerializedName("package_price")
    private Integer packagePrice;

    //餐盒数量，单位：分
    @SerializedName("packageAmount")
    private Integer packageAmount;

    //商品总价，单位：分
    @SerializedName("total_fee")
    private Integer totalFee;

    //是否固定价格，1.是，2.否
    @SerializedName("is_fixed_price")
    private Integer isFixedPrice;

    //套餐内组合
    @SerializedName("group")
    private List<OrderProductsComboGroup> groups;

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

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return  this.type;
    }

    public void setProductName(String productName){
        this.productName = productName;
    }

    public String getProductName(){
        return this.productName;
    }

    public void setProductAmount(String productAmount){
        this.productAmount = productAmount;
    }

    public String getProductAmount(){
        return this.productAmount;
    }

    public void setProductPrice(String productPrice){
        this.productPrice = productPrice;
    }

    public String getProductPrice(){
        return this.productPrice;
    }

    public void setProductFee(Integer productFee){
        this.productFee = productFee;
    }

    public Integer getProductFee(){
        return this.productFee;
    }

    public void setPackageFee(Integer packageFee){
        this.packageFee = packageFee;
    }

    public Integer getPackageFee(){
        return this.packageFee;
    }

    public void setPackagePrice(Integer packagePrice){
        this.packagePrice = packagePrice;
    }

    public Integer getPackagePrice(){
        return this.packagePrice;
    }

    public void setPackageAmount(Integer packageAmount){
        this.packageAmount = packageAmount;
    }

    public Integer getPackageAmount(){
        return this.packageAmount;
    }

    public void setTotalFee(Integer totalFee){
        this.totalFee = totalFee;
    }

    public Integer getTotalFee(){
        return this.totalFee;
    }

    public void setIsFixedPrice(Integer isFixedPrice){
        this.isFixedPrice = isFixedPrice;
    }

    public Integer getIsFixedPrice(){
        return this.isFixedPrice;
    }

    public void setGroups(List<OrderProductsComboGroup> groups){
        this.groups = groups;
    }

    public List<OrderProductsComboGroup> getGroups(){
        return this.groups;
    }
}

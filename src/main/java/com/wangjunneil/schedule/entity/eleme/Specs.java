package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/24.
 */
public class Specs {
    //规格id
    @SerializedName("spec_id")
    private String specid;
    //规格名字
    @SerializedName("name")
    private String name;
    //规格价格
    @SerializedName("price")
    private Double price;
    //库存
    @SerializedName("stock")
    private String stock;
    //最大库存
    @SerializedName("max_stock")
    private String maxstock;
    //包装
    @SerializedName("packing_fee")
    private int packingfee;
    //第三方食物id
    @SerializedName("tp_food_id")
    private String tpfoodid;
    //是否上架
    @SerializedName("on_shelf")
    private int onshelf;

    public String getSpecid() {
        return specid;
    }

    public void setSpecid(String specid) {
        this.specid = specid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getMaxstock() {
        return maxstock;
    }

    public void setMaxstock(String maxstock) {
        this.maxstock = maxstock;
    }

    public int getPackingfee() {
        return packingfee;
    }

    public void setPackingfee(int packingfee) {
        this.packingfee = packingfee;
    }

    public String getTpfoodid() {
        return tpfoodid;
    }

    public void setTpfoodid(String tpfoodid) {
        this.tpfoodid = tpfoodid;
    }

    public int getOnshelf() {
        return onshelf;
    }

    public void setOnshelf(int onshelf) {
        this.onshelf = onshelf;
    }
}

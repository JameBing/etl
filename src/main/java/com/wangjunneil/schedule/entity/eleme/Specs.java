package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/24.
 */
public class Specs {
    @SerializedName("spec_id")
    private String specid;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private Double price;
    @SerializedName("stock")
    private String stock;
    @SerializedName("max_stock")
    private String maxstock;
    @SerializedName("packing_fee")
    private int packingfee;
    @SerializedName("tp_food_id")
    private String tpfoodid;
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

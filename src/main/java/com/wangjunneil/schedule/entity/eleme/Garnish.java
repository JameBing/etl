package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/22.
 */
public class Garnish {
    //分类Id
    @SerializedName("category_id")
    private String categoryid;
    //名字
    @SerializedName("name")
    private String name;
    //价格
    @SerializedName("price")
    private Double price;
    //id
    @SerializedName("id")
    private String id;
    //数量
    @SerializedName("quantity")
    private String quantity;
    @SerializedName("tp_food_id")
    private String tpfoodid;

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTpfoodid() {
        return tpfoodid;
    }

    public void setTpfoodid(String tpfoodid) {
        this.tpfoodid = tpfoodid;
    }
}

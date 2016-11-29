package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/22.
 */
public class Extra {
    //描述
    @SerializedName("description")
    private String description;
    //价格
    @SerializedName("price")
    private Double price;
    //额外增收名字
    @SerializedName("name")
    private String name;
    //分类id
    @SerializedName("category_id")
    private String categoryid;
    //id
    @SerializedName("id")
    private String id;
    //数量
    @SerializedName("quantity")
    private String quantity;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
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
}

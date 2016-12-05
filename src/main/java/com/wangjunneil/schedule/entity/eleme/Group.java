package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/22.
 */
public class Group {
    //分类id
    @SerializedName("category_id")
    private String categoryid;
    //名字
    @SerializedName("name")
    private String name;
    //价格
    @SerializedName("price")
    private double price;
    @SerializedName("id")
    private String id;
    //搭配食品
    @SerializedName("garnish")
    private List<Garnish> garnish;
    //规格
    @SerializedName("specs")
    private List specs;
    //数量
    @SerializedName("quantity")
    private String quantity;
    //销售模式
    @SerializedName("sale_mode")
    private int salemode;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Garnish> getGarnish() {
        return garnish;
    }

    public void setGarnish(List<Garnish> garnish) {
        this.garnish = garnish;
    }

    public List getSpecs() {
        return specs;
    }

    public void setSpecs(List specs) {
        this.specs = specs;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public int getSalemode() {
        return salemode;
    }

    public void setSalemode(int salemode) {
        this.salemode = salemode;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/23.
 */
public class Foods {
    //描述
    @SerializedName("description")
    private String description;
    //食物Id
    @SerializedName("food_id")
    private int foodid;
    //食物名字
    @SerializedName("name")
    private String name;
    //是否有效
    @SerializedName("is_valid")
    private int isvalid;
    //最近热门程度
    @SerializedName("recent_popularity")
    private int recentpopularity;
    //餐厅id
    @SerializedName("restaurant_id")
    private int restaurantid;
    //食物分类id
    @SerializedName("food_category_id")
    private int foodcategoryid;
    //餐厅名
    @SerializedName("restaurant_name")
    private String restaurantname;
    //是否上架
    @SerializedName("on_shelf")
    private int onshelf;
    //食物图片
    @SerializedName("image_url")
    private String imageurl;
    //口味
    @SerializedName("labels")
    private Object labels;
    //规格
    @SerializedName("specs")
    private List<Specs> specs;

    @Override
    public String toString() {
        return "Foods{" +
            "description='" + description + '\'' +
            ", foodid=" + foodid +
            ", name='" + name + '\'' +
            ", isvalid=" + isvalid +
            ", recentpopularity=" + recentpopularity +
            ", restaurantid=" + restaurantid +
            ", foodcategoryid=" + foodcategoryid +
            ", restaurantname='" + restaurantname + '\'' +
            ", onshelf=" + onshelf +
            ", imageurl='" + imageurl + '\'' +
            ", labels=" + labels +
            ", specs=" + specs +
            '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public int getRecentpopularity() {
        return recentpopularity;
    }

    public void setRecentpopularity(int recentpopularity) {
        this.recentpopularity = recentpopularity;
    }

    public int getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(int restaurantid) {
        this.restaurantid = restaurantid;
    }

    public int getFoodcategoryid() {
        return foodcategoryid;
    }

    public void setFoodcategoryid(int foodcategoryid) {
        this.foodcategoryid = foodcategoryid;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public int getOnshelf() {
        return onshelf;
    }

    public void setOnshelf(int onshelf) {
        this.onshelf = onshelf;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Object getLabels() {
        return labels;
    }

    public void setLabels(Object labels) {
        this.labels = labels;
    }

    public List<Specs> getSpecs() {
        return specs;
    }

    public void setSpecs(List<Specs> specs) {
        this.specs = specs;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/24.
 */
public class Body {
    @SerializedName("food")
    private Foods food;//食物
    @SerializedName("group")
    private List<List<Group>> group;//食物列表
    @SerializedName("extra")
    private List<Extra> extra;//额外活动列表
    @SerializedName("abandoned_extra")
    private List<Object> abandonedextra;
    @SerializedName("restaurant")
    private Restaurant restaurant;//门店对象
    @SerializedName("food_ids")
    private Map<String,List<FoodIds>> foodids;//食物id列表
    @SerializedName("data")
    private List data;

    public Foods getFood() {
        return food;
    }

    public void setFood(Foods food) {
        this.food = food;
    }


    public List<List<Group>> getGroup() {
        return group;
    }

    public void setGroup(List<List<Group>> group) {
        this.group = group;
    }

    public List<Extra> getExtra() {
        return extra;
    }

    public void setExtra(List<Extra> extra) {
        this.extra = extra;
    }

    public List<Object> getAbandonedextra() {
        return abandonedextra;
    }

    public void setAbandonedextra(List<Object> abandonedextra) {
        this.abandonedextra = abandonedextra;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Map<String, List<FoodIds>> getFoodids() {
        return foodids;
    }

    public void setFoodids(Map<String, List<FoodIds>> foodids) {
        this.foodids = foodids;
    }

    public List getData() {
        return data;
    }

    public void setData(List data) {
        this.data = data;
    }
}

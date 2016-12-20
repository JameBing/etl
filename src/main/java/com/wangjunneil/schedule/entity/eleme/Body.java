package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2016/11/24.
 */
public class Body {
    /*****************食物详情start********************/
    @SerializedName("food")
    private Foods food;
    /*****************食物详情start********************/

    /*****************订单详情start********************/
    @SerializedName("group")
    private List<List<Group>> group;
    @SerializedName("extra")
    private List<Extra> extra;
    @SerializedName("abandoned_extra")
    private List<Object> abandonedextra;
    /*****************订单详情end********************/

    /*****************获取商户信息start********************/
    @SerializedName("restaurant")
    private Restaurant restaurant;
    /*****************获取商户信息end********************/
    @SerializedName("food_ids")
    private Map<String,List<FoodIds>> foodids;
    /***************************************************/
    @SerializedName("data")
    private List data;

    @SerializedName("batch_status")
    private BatchStatus batch_status;

    public BatchStatus getBatch_status() {
        return batch_status;
    }

    public void setBatch_status(BatchStatus batch_status) {
        this.batch_status = batch_status;
    }

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

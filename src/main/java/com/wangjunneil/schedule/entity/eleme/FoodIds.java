package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/12/2.
 */
public class FoodIds {
    //第三方餐厅id
    @SerializedName("tp_restaurant_id")
    private String tprestaurantid;
    //餐厅id
    @SerializedName("restaurant_id")
    private String restaurantid;
    //食物id
    @SerializedName("food_id")
    private String foodid;

    public String getTprestaurantid() {
        return tprestaurantid;
    }

    public void setTprestaurantid(String tprestaurantid) {
        this.tprestaurantid = tprestaurantid;
    }

    public String getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(String restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getFoodid() {
        return foodid;
    }

    public void setFoodid(String foodid) {
        this.foodid = foodid;
    }
}

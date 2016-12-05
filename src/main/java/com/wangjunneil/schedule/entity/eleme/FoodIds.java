package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/12/2.
 */
public class FoodIds {
    //第三方餐厅id
    private String tp_restaurant_id;
    //餐厅id
    private String restaurant_id;
    //食物id
    private String food_id;

    public String getTp_restaurant_id() {
        return tp_restaurant_id;
    }

    public void setTp_restaurant_id(String tp_restaurant_id) {
        this.tp_restaurant_id = tp_restaurant_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }
}

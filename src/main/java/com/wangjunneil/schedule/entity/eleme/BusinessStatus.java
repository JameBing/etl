package com.wangjunneil.schedule.entity.eleme;



/**
 * Created by admin on 2016/11/18.
 */
public class BusinessStatus {
    //开关店铺
    private String is_open;
    //商家id
    private String restaurant_id;

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}

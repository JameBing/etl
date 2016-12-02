package com.wangjunneil.schedule.entity.mt;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2016/12/1.
 */
public class FoodRequest {
    private String app_poi_code;
    private String app_food_code;
    private List<FoodData> food_data;

    public String getApp_poi_code() {
        return app_poi_code;
    }

    public void setApp_poi_code(String app_poi_code) {
        this.app_poi_code = app_poi_code;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }

    public String getFood_data() {
        Gson gson = new Gson();
        return gson.toJson(food_data);
    }

    public void setFood_data(List<FoodData> food_data) {
        this.food_data = food_data;
    }
}

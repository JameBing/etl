package com.wangjunneil.schedule.entity.mt;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2016/12/1.
 */
public class FoodData {
    private String app_food_code;
    private List<Skus> skus;

    public String getSkus() {
        Gson gson = new Gson();
        return gson.toJson(skus);
    }

    public void setSkus(List<Skus> skus) {
        this.skus = skus;
    }

    public String getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(String app_food_code) {
        this.app_food_code = app_food_code;
    }
}

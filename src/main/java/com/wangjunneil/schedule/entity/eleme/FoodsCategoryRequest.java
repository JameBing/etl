package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2016/11/28.
 */
public class FoodsCategoryRequest {
    /********************共有属性***************************/
    //食物名字
    private String name;
    //描述
    private String description;
    /*******************创建食物分类属性*******************/
    //商家id
    private String restaurant_id;
    private List<DisplayAttribute> display_attribute;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getDisplay_attribute() {
        Gson gson = new Gson();
        return gson.toJson(display_attribute);
    }

    public void setDisplay_attribute(List<DisplayAttribute> display_attribute) {
        this.display_attribute = display_attribute;
    }
}

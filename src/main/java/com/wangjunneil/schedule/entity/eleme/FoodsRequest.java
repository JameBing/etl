package com.wangjunneil.schedule.entity.eleme;


import com.google.gson.Gson;

import java.util.List;

/**
 * Created by admin on 2016/11/23.
 *
 */
public class FoodsRequest {
    /********************共有属性***************************/
    //食物名字
    private String name;
    //描述
    private String description;
    /********************添加食物属性**********************/
    //食物分类Id
    private String food_category_id;
    //食物价格
    private String price;
    //图片地址
    private String image_hash;
    //口味
    private Labels labels;
    //规格
    private List<Specs> specs;
    /*********通过第三方id查询平台id属性******************/
    private String tp_food_ids;

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

    public String getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(String food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }

    public String getLabels() {
        Gson gson = new Gson();
        return gson.toJson(labels);
    }

    public void setLabels(Labels labels) {
        this.labels = labels;
    }

    public String getSpecs() {
        Gson gson = new Gson();
        return gson.toJson(specs);
    }

    public void setSpecs(List<Specs> specs) {
        this.specs = specs;
    }

    public String getTp_food_ids() {
        return tp_food_ids;
    }

    public void setTp_food_ids(String tp_food_ids) {
        this.tp_food_ids = tp_food_ids;
    }
}

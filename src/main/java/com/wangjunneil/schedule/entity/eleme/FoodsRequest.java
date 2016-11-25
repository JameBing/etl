package com.wangjunneil.schedule.entity.eleme;


/**
 * Created by admin on 2016/11/23.
 */
public class FoodsRequest {
    //食物分类Id
    private String food_category_id;
    //食物名字
    private String name;
    //食物价格
    private String price;
    //描述
    private String description;
    //图片地址
    private String image_hash;
    //口味
    private String labels;
    //规格
    private String specs;

    public String getFood_category_id() {
        return food_category_id;
    }

    public void setFood_category_id(String food_category_id) {
        this.food_category_id = food_category_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getSpecs() {
        return specs;
    }

    public void setSpecs(String specs) {
        this.specs = specs;
    }
}

package com.wangjunneil.schedule.entity.meituan;

import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;

/**
 * @author liuxin
 * @since 2016.12.1
 */
public class FoodCreate {

    //app方门店ID
    private String app_poi_code;
    //菜品编号
    private String app_food_code;
    //菜品名称
    private String name;
    //菜品描述
    private String description;
    //菜品价格
    private float price;
    //最小购买数量
    private int min_order_count;
    //单位
    private String unit;
    //打包盒数量
    private float box_num;
    //餐盒价格
    private float box_price;
    //菜品分类名
    private String category_name;
    //菜品状态： 1：卖光，0：未卖光
    private int is_sold_out;
    //菜品图片ID
    private String picture;
    //当前分类下的排序序号
    private int sequence;


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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMin_order_count() {
        return min_order_count;
    }

    public void setMin_order_count(int min_order_count) {
        this.min_order_count = min_order_count;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getBox_num() {
        return box_num;
    }

    public void setBox_num(float box_num) {
        this.box_num = box_num;
    }

    public float getBox_price() {
        return box_price;
    }

    public void setBox_price(float box_price) {
        this.box_price = box_price;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getIs_sold_out() {
        return is_sold_out;
    }

    public void setIs_sold_out(int is_sold_out) {
        this.is_sold_out = is_sold_out;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

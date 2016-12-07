package com.wangjunneil.schedule.entity.meituan;

/**
 * @author liuxin
 * @since 2016-12-5.
 */
public class UpordownFrame {

    //门店编号
    private String app_poi_code;
    //菜品编号
    private String app_food_code;
    //菜品价格
    private String price;
    //所属分类名称
    private String category_name;
    //是否卖光 1：卖光，0：未卖光
    private int is_sold_out;
    //商品名称
    private String name;
    //份数
    private String unit;


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}

package com.wangjunneil.schedule.entity.meituan;

/**
 * @author liuxin
 * @since 2016/11/18.
 */
public class DetailInfo {

    private long app_food_code;

    private String food_name;

    private int quantity;

    private double price;

    private int box_num;

    private double box_price;

    private String unit;

    private double food_discount;


    public long getApp_food_code() {
        return app_food_code;
    }

    public void setApp_food_code(long app_food_code) {
        this.app_food_code = app_food_code;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBox_num() {
        return box_num;
    }

    public void setBox_num(int box_num) {
        this.box_num = box_num;
    }

    public double getBox_price() {
        return box_price;
    }

    public void setBox_price(double box_price) {
        this.box_price = box_price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getFood_discount() {
        return food_discount;
    }

    public void setFood_discount(double food_discount) {
        this.food_discount = food_discount;
    }
}

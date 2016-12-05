package com.wangjunneil.schedule.entity.mt;

import com.google.gson.Gson;

/**
 * Created by admin on 2016/12/1.
 */
public class Skus {
    private String sku_id;
    private String spec;
    private String upc;
    private Double price;
    private int stock;
    private Uptime available_times;
    private int location_code;
    private int box_num;
    private Double box_price;

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getAvailable_times() {
        Gson gson = new Gson();
        return gson.toJson(available_times);
    }

    public void setAvailable_times(Uptime available_times) {
        this.available_times = available_times;
    }

    public int getLocation_code() {
        return location_code;
    }

    public void setLocation_code(int location_code) {
        this.location_code = location_code;
    }

    public int getBox_num() {
        return box_num;
    }

    public void setBox_num(int box_num) {
        this.box_num = box_num;
    }

    public Double getBox_price() {
        return box_price;
    }

    public void setBox_price(Double box_price) {
        this.box_price = box_price;
    }
}

package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/28.
 */
public class OldFoodsRequest {
    private String food_category_id;
    private String name;
    private float price;
    private String description;
    private int max_stock;
    private int stock;
    private String tp_food_id;
    private String image_hash;
    private int is_new;
    private int is_featured;
    private int is_gum;
    private int is_spicy;
    private float packing_fee;
    private int sort_order;
    private String food_id;


    public String getFood_id() {
        return food_id;
    }

    public void setFood_id(String food_id) {
        this.food_id = food_id;
    }

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(int max_stock) {
        this.max_stock = max_stock;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getTp_food_id() {
        return tp_food_id;
    }

    public void setTp_food_id(String tp_food_id) {
        this.tp_food_id = tp_food_id;
    }

    public String getImage_hash() {
        return image_hash;
    }

    public void setImage_hash(String image_hash) {
        this.image_hash = image_hash;
    }

    public int getIs_new() {
        return is_new;
    }

    public void setIs_new(int is_new) {
        this.is_new = is_new;
    }

    public int getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(int is_featured) {
        this.is_featured = is_featured;
    }

    public int getIs_gum() {
        return is_gum;
    }

    public void setIs_gum(int is_gum) {
        this.is_gum = is_gum;
    }

    public int getIs_spicy() {
        return is_spicy;
    }

    public void setIs_spicy(int is_spicy) {
        this.is_spicy = is_spicy;
    }

    public float getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(float packing_fee) {
        this.packing_fee = packing_fee;
    }

    public int getSort_order() {
        return sort_order;
    }

    public void setSort_order(int sort_order) {
        this.sort_order = sort_order;
    }
}

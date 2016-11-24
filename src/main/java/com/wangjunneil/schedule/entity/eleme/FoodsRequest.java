package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/23.
 */
public class FoodsRequest {
    private String food_category_id;
    private String name;
    private String price;
    private String description;
    private String max_stock;
    private String stock;
    private String tp_food_id;
    private String image_hash;
    private String is_new;
    private String is_featured;
    private String is_gum;
    private String is_spicy;
    private String packing_fee;
    private String sort_order;

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

    public String getMax_stock() {
        return max_stock;
    }

    public void setMax_stock(String max_stock) {
        this.max_stock = max_stock;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
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

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getIs_featured() {
        return is_featured;
    }

    public void setIs_featured(String is_featured) {
        this.is_featured = is_featured;
    }

    public String getIs_gum() {
        return is_gum;
    }

    public void setIs_gum(String is_gum) {
        this.is_gum = is_gum;
    }

    public String getIs_spicy() {
        return is_spicy;
    }

    public void setIs_spicy(String is_spicy) {
        this.is_spicy = is_spicy;
    }

    public String getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(String packing_fee) {
        this.packing_fee = packing_fee;
    }

    public String getSort_order() {
        return sort_order;
    }

    public void setSort_order(String sort_order) {
        this.sort_order = sort_order;
    }
}

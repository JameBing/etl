package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by admin on 2016/11/23.
 */
public class Foods {
    @SerializedName("description")
    private String description;
    @SerializedName("food_id")
    private int foodid;
    @SerializedName("food_name")
    private String foodname;
    @SerializedName("has_activity")
    private int hasactivity;
    @SerializedName("is_featured")
    private int isfeatured;
    @SerializedName("is_gum")
    private int isgum;
    @SerializedName("is_new")
    private int isnew;
    @SerializedName("is_spicy")
    private int isspicy;
    @SerializedName("is_valid")
    private int isvalid;
    @SerializedName("numratings")
    private List numratings;
    @SerializedName("price")
    private double price;
    @SerializedName("recent_popularity")
    private int recentpopularity;
    @SerializedName("recent_rating")
    private Double recentrating;
    @SerializedName("restaurant_id")
    private int restaurantid;
    @SerializedName("restaurant_name")
    private String restaurantname;
    @SerializedName("stock")
    private Long stock;
    @SerializedName("image_url")
    private String imageurl;
    @SerializedName("packing_fee")
    private Double packingfee;
    @SerializedName("sortorder")
    private int sortorder;

    @Override
    public String toString() {
        return "Foods{" +
            "description='" + description + '\'' +
            ", foodid=" + foodid +
            ", foodname='" + foodname + '\'' +
            ", hasactivity=" + hasactivity +
            ", isfeatured=" + isfeatured +
            ", isgum=" + isgum +
            ", isnew=" + isnew +
            ", isspicy=" + isspicy +
            ", isvalid=" + isvalid +
            ", numratings=" + numratings +
            ", price=" + price +
            ", recentpopularity=" + recentpopularity +
            ", recentrating=" + recentrating +
            ", restaurantid=" + restaurantid +
            ", restaurantname='" + restaurantname + '\'' +
            ", stock=" + stock +
            ", imageurl='" + imageurl + '\'' +
            ", packingfee=" + packingfee +
            ", sortorder=" + sortorder +
            '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFoodid() {
        return foodid;
    }

    public void setFoodid(int foodid) {
        this.foodid = foodid;
    }

    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public int getHasactivity() {
        return hasactivity;
    }

    public void setHasactivity(int hasactivity) {
        this.hasactivity = hasactivity;
    }

    public int getIsfeatured() {
        return isfeatured;
    }

    public void setIsfeatured(int isfeatured) {
        this.isfeatured = isfeatured;
    }

    public int getIsgum() {
        return isgum;
    }

    public void setIsgum(int isgum) {
        this.isgum = isgum;
    }

    public int getIsnew() {
        return isnew;
    }

    public void setIsnew(int isnew) {
        this.isnew = isnew;
    }

    public int getIsspicy() {
        return isspicy;
    }

    public void setIsspicy(int isspicy) {
        this.isspicy = isspicy;
    }

    public int getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(int isvalid) {
        this.isvalid = isvalid;
    }

    public List getNumratings() {
        return numratings;
    }

    public void setNumratings(List numratings) {
        this.numratings = numratings;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getRecentpopularity() {
        return recentpopularity;
    }

    public void setRecentpopularity(int recentpopularity) {
        this.recentpopularity = recentpopularity;
    }

    public Double getRecentrating() {
        return recentrating;
    }

    public void setRecentrating(Double recentrating) {
        this.recentrating = recentrating;
    }

    public int getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(int restaurantid) {
        this.restaurantid = restaurantid;
    }

    public String getRestaurantname() {
        return restaurantname;
    }

    public void setRestaurantname(String restaurantname) {
        this.restaurantname = restaurantname;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Double getPackingfee() {
        return packingfee;
    }

    public void setPackingfee(Double packingfee) {
        this.packingfee = packingfee;
    }

    public int getSortorder() {
        return sortorder;
    }

    public void setSortorder(int sortorder) {
        this.sortorder = sortorder;
    }
}

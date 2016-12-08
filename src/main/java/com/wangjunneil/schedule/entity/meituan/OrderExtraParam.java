package com.wangjunneil.schedule.entity.meituan;

/**
 * @author liuxin
 * @since 2016/11/18.
 */
public class OrderExtraParam {

//    private String app_food_code;
//    private String food_name;
//    private Integer quantity;
//    private Float price;
//    private Float box_num;
//    private Float box_price;
//    private String unit;
//    private Float food_discount;
//    private String sku_id;
//    private String food_property;
//    private String spec;
//
//    public DetailInfo() {
//    }
//
//    public String getApp_food_code() {
//        return this.app_food_code;
//    }
//
//    public void setApp_food_code(String app_food_code) {
//        this.app_food_code = app_food_code;
//    }
//
//    public String getFood_name() {
//        return this.food_name;
//    }
//
//    public void setFood_name(String food_name) {
//        this.food_name = food_name;
//    }
//
//    public Integer getQuantity() {
//        return this.quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Float getPrice() {
//        return this.price;
//    }
//
//    public void setPrice(Float price) {
//        this.price = price;
//    }
//
//    public Float getBox_num() {
//        return this.box_num;
//    }
//
//    public void setBox_num(Float box_num) {
//        this.box_num = box_num;
//    }
//
//    public Float getBox_price() {
//        return this.box_price;
//    }
//
//    public void setBox_price(Float box_price) {
//        this.box_price = box_price;
//    }
//
//    public String getUnit() {
//        return this.unit;
//    }
//
//    public void setUnit(String unit) {
//        this.unit = unit;
//    }
//
//    public Float getFood_discount() {
//        return this.food_discount;
//    }
//
//    public void setFood_discount(Float food_discount) {
//        this.food_discount = food_discount;
//    }
//
//    public String getSku_id() {
//        return this.sku_id;
//    }
//
//    public void setSku_id(String sku_id) {
//        this.sku_id = sku_id;
//    }
//
//    public String getFood_property() {
//        return this.food_property;
//    }
//
//    public void setFood_property(String food_property) {
//        this.food_property = food_property;
//    }
//
//    public String getSpec() {
//        return this.spec;
//    }
//
//    public void setSpec(String spec) {
//        this.spec = spec;
//    }
//
//    public String toString() {
//        return "DetailInfo [app_food_code=\'" + this.app_food_code + '\'' + ", food_name=\'" + this.food_name + '\'' + ", quantity=" + this.quantity + ", price=" + this.price + ", box_num=" + this.box_num + ", box_price=" + this.box_price + ", unit=\'" + this.unit + '\'' + ", food_discount=" + this.food_discount + ", sku_id=\'" + this.sku_id + '\'' + ", food_property=\'" + this.food_property + '\'' + ", spec=\'" + this.spec + '\'' + ']';
//    }

    private Integer act_detail_id;
    private Float reduce_fee;
    private String remark;
    private Integer type;
    private String rider_fee;
    private Float mt_charge;
    private Float poi_charge;

    public OrderExtraParam() {
    }

    public Integer getAct_detail_id() {
        return this.act_detail_id;
    }

    public void setAct_detail_id(Integer act_detail_id) {
        this.act_detail_id = act_detail_id;
    }

    public Float getReduce_fee() {
        return this.reduce_fee;
    }

    public void setReduce_fee(Float reduce_fee) {
        this.reduce_fee = reduce_fee;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRider_fee() {
        return this.rider_fee;
    }

    public void setRider_fee(String rider_fee) {
        this.rider_fee = rider_fee;
    }

    public Float getMt_charge() {
        return this.mt_charge;
    }

    public void setMt_charge(Float mt_charge) {
        this.mt_charge = mt_charge;
    }

    public Float getPoi_charge() {
        return this.poi_charge;
    }

    public void setPoi_charge(Float poi_charge) {
        this.poi_charge = poi_charge;
    }

}

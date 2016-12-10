package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/22.
 */
public class OrderRequest {
    /********************共有属性*************************/
    //eleme订单id
    private String eleme_order_id;
    //状态
    private  String status;
    /*****************查询订单详情属性********************/
    //第三方订单id
    private String tp_id;
    /******************取消订单属性***********************/
    //原因
    private  String reason;
    /******************拉取新订单请求参数***********************/
    //商户可管理商家id
    private String restaurant_id;
    private String eleme_order_ids;


    public String getEleme_order_id() {
        return eleme_order_id;
    }

    public void setEleme_order_id(String eleme_order_id) {
        this.eleme_order_id = eleme_order_id;
    }

    public String getTp_id() {
        return tp_id;
    }

    public void setTp_id(String tp_id) {
        this.tp_id = tp_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getEleme_order_ids() {
        return eleme_order_ids;
    }

    public void setEleme_order_ids(String eleme_order_ids) {
        this.eleme_order_ids = eleme_order_ids;
    }
}

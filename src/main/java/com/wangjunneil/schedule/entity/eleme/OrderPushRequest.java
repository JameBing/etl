package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/28.
 */
public class OrderPushRequest {
    /*************** 拉取新订单请求参数*******************/
    //商户可管理商家id
    private String restaurant_id;
    //请求模式
    private String push_action;
    //订单id
    private String order_ids;

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getPush_action() {
        return push_action;
    }

    public void setPush_action(String push_action) {
        this.push_action = push_action;
    }

    public String getOrder_ids() {
        return order_ids;
    }

    public void setOrder_ids(String order_ids) {
        this.order_ids = order_ids;
    }
}

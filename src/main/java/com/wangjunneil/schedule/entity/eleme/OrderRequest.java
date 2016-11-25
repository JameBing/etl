package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/22.
 */
public class OrderRequest {
    //订单id
    private String eleme_order_id;
    //第三方订单id
    private String tp_id;

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
}

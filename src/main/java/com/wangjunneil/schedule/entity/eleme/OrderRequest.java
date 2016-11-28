package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/22.
 */
public class OrderRequest {
    /********************共有属性*************************/
    //eleme订单id
    private String eleme_order_id;
    /*****************查询订单详情属性********************/
    //第三方订单id
    private String tp_id;
    /******************取消订单属性***********************/
    //状态
    private  String status;
    //原因
    private  String reason;

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
}

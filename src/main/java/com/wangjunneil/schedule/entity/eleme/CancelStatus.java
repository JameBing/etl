package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/22.
 */
public class CancelStatus {
    //取消订单
    private  String eleme_order_id;
    private  int status;
    private  String reason;

    public String getEleme_order_id() {
        return eleme_order_id;
    }

    public void setEleme_order_id(String eleme_order_id) {
        this.eleme_order_id = eleme_order_id;
    }
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

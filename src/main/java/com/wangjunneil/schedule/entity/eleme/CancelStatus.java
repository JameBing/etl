package com.wangjunneil.schedule.entity.eleme;

/**
 * Created by admin on 2016/11/22.
 */
public class CancelStatus {
    //取消订单id
    private  String eleme_order_id;
    //状态
    private  int status;
    //原因
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

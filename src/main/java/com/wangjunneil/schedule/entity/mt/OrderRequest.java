package com.wangjunneil.schedule.entity.mt;

/**
 * Created by admin on 2016/12/1.
 */
public class OrderRequest {
    private String order_id;

    private String reason;
    private String reason_code;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason_code() {
        return reason_code;
    }

    public void setReason_code(String reason_code) {
        this.reason_code = reason_code;
    }
}

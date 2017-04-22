package com.wangjunneil.schedule.entity.meituan;

/**
 * @author yangyongbing
 * @since 2017-04-20
 */
public class Delivery {
    //订单号
    private Long order_id;
    //配送单状态
    private Integer logistics_status;
    //配送对应时间
    private  String time;
    //骑手姓名
    private String dispatcher_name;
    //骑手电话
    private String dispatcher_mobile;

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Integer getLogistics_status() {
        return logistics_status;
    }

    public void setLogistics_status(Integer logistics_status) {
        this.logistics_status = logistics_status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDispatcher_name() {
        return dispatcher_name;
    }

    public void setDispatcher_name(String dispatcher_name) {
        this.dispatcher_name = dispatcher_name;
    }

    public String getDispatcher_mobile() {
        return dispatcher_mobile;
    }

    public void setDispatcher_mobile(String dispatcher_mobile) {
        this.dispatcher_mobile = dispatcher_mobile;
    }
}

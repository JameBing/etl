package com.wangjunneil.schedule.entity.meituan;

/**
 * @author liuxin
 * @since 2016/11/18.
 */
public class ExtrasInfo {

    private String act_detail_id;

    //优惠金额
    private double reduce_fee;

    //优惠说明
    private String remark;

    //活动类型
    private int type;

    //餐厅平均送餐时间，单位为分钟
    private double avg_send_time;



    public String getAct_detail_id() {
        return act_detail_id;
    }

    public void setAct_detail_id(String act_detail_id) {
        this.act_detail_id = act_detail_id;
    }

    public double getReduce_fee() {
        return reduce_fee;
    }

    public void setReduce_fee(double reduce_fee) {
        this.reduce_fee = reduce_fee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getAvg_send_time() {
        return avg_send_time;
    }

    public void setAvg_send_time(double avg_send_time) {
        this.avg_send_time = avg_send_time;
    }
}

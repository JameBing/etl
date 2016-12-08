package com.wangjunneil.schedule.entity.meituan;

/**
 * @author liuxin
 * @since 2016/11/18.
 */
public class ExtrasInfo {

    private Integer act_detail_id;
    private Float reduce_fee;
    private String remark;
    private Integer type;
    private String rider_fee;
    private Float mt_charge;
    private Float poi_charge;

    public ExtrasInfo() {
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

    public String toString() {
        return "ExtrasInfo [act_detail_id=" + this.act_detail_id + ", reduce_fee=" + this.reduce_fee + ", remark=\'" + this.remark + '\'' + ", type=" + this.type + ", rider_fee=\'" + this.rider_fee + '\'' + ", mt_charge=" + this.mt_charge + ", poi_charge=" + this.poi_charge + ']';
    }
}

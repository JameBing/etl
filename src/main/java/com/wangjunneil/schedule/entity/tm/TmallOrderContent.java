package com.wangjunneil.schedule.entity.tm;

import java.util.Date;

/**
 * Created by wangjun on 8/25/16.
 */
public class TmallOrderContent {
    private String buyer_nick;
    private String payment;
    private String status;
    private long oid;
    private long tid;
    private String type;
    private String seller_nick;
    private long refund_id;
    private String refund_fee;
    private String refund_phase;
    private String bill_type;

    public long getRefund_id() {
        return refund_id;
    }

    public void setRefund_id(long refund_id) {
        this.refund_id = refund_id;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }

    public String getRefund_phase() {
        return refund_phase;
    }

    public void setRefund_phase(String refund_phase) {
        this.refund_phase = refund_phase;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    private Date modified;


    public String getBuyer_nick() {
        return buyer_nick;
    }

    public void setBuyer_nick(String buyer_nick) {
        this.buyer_nick = buyer_nick;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getOid() {
        return oid;
    }

    public void setOid(long oid) {
        this.oid = oid;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeller_nick() {
        return seller_nick;
    }

    public void setSeller_nick(String seller_nick) {
        this.seller_nick = seller_nick;
    }
}

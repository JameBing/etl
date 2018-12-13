package com.wangjunneil.schedule.entity.common;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author yangyongbing
 * @since 2017/12/18.
 */
@Document(collection = "sync.waimai.order.crm.zt")
public class OrderCrmZt {
    private String orderId;
    private Integer status ;//0推送 1未推送
    private Date createTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

package com.wangjunneil.schedule.entity.common;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @author yangyongbing
 * @since 2017/12/18.
 */
@Document(collection = "sync.waimai.push.record")
public class PushRecord {
    private String orderId;
    private String type;
    private Integer status ;//0推送 1成功接收
    private Integer pushTimes;//推送次数
    private Date createTime;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPushTimes() {
        return pushTimes;
    }

    public void setPushTimes(Integer pushTimes) {
        this.pushTimes = pushTimes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

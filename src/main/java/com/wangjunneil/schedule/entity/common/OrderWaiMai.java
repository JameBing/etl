package com.wangjunneil.schedule.entity.common;

import com.taobao.api.request.OcEserviceAppointSaveRequest;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.activemq.ActiveMQInputStream;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by yangwanbin on 2016-12-05.
 */
@Document(collection = "sync.waimai.order")
public class OrderWaiMai {

    private String platform;

    private String shopId;

    private String orderId;

    private String platformOrderId;

    private  Object order;

    private  String sellerShopId;

    private Date createTime;

    public   void setPlatform(String platform){
        this.platform = platform;
    }

    public String getPlatform(){
        return StringUtil.isEmpty(this.platform)?"":this.platform;
    }

    public void setShopId(String shopId){
        this.shopId = shopId;
    }

    public String getShopId(){
        return StringUtil.isEmpty(this.shopId)?"":this.shopId;
    }

    public void setOrderId(String orderId){
        this.orderId = orderId;
    }

    public String getOrderId(){
        return  StringUtil.isEmpty(this.orderId)?"":this.orderId;
    }

    public void setPlatformOrderId(String platformOrderId){
        this.platformOrderId = platformOrderId;
    }

    public String getPlatformOrderId(){
        return StringUtil.isEmpty(this.platformOrderId)?"":this.platformOrderId;
    }

    public void setOrder(Object order){
        this.order = order;
    }

    public Object getOrder(){
        return  this.order;
    }

    public String getSellerShopId() {
        return sellerShopId;
    }

    public void setSellerShopId(String sellerShopId) {
        this.sellerShopId = sellerShopId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}

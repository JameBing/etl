package com.wangjunneil.schedule.service.meituan;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


/**
 * @author liuxin
 * @since 2016-11-17.
 */
@Service
public class MeiTuanInnerService {

    private static Logger log = Logger.getLogger(MeiTuanInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //把获取到的订单信息插入到sync.meituan.order
    public void insertAllOrder(OrderInfo orders)
    {
        mongoTemplate.insert(orders);
    }

    //修改订单,把一条完整订单把数据库订单覆盖,不仅仅修改状态
    public void updateOrderDetail(OrderInfo order){
        Query query = new Query(Criteria.where("platformOrderId").is(order.getOrderid()).and("platform").is(Constants.PLATFORM_WAIMAI_MEITUAN));
        Update update = new Update().set("_class", order.getClass().getName())
            .set("orderid",order.getOrderid())
            .set("wmorderidview",order.getWmorderidview())
            .set("apppoicode",order.getApppoicode())
            .set("wmpoiname",order.getWmpoiname())
            .set("wmpoiaddress",order.getWmpoiaddress())
            .set("wmpoiphone",order.getWmpoiphone())
            .set("recipientaddress",order.getRecipientaddress())
            .set("recipientphone",order.getRecipientphone())
            .set("recipientname",order.getRecipientname())
            .set("shippingfee",order.getShippingfee())
            .set("total",order.getTotal())
            .set("originalprice",order.getOriginalprice())
            .set("caution",order.getCaution())
            .set("shipperphone",order.getShipperphone())
            .set("status",order.getStatus())
            .set("cityid",order.getCityid())
            .set("hasinvoiced",order.getHasinvoiced())
            .set("invoicetitle",order.getInvoicetitle())
            .set("ctime",order.getCtime())
            .set("utime",order.getUtime())
            .set("deliverytime",order.getDeliverytime())
            .set("isthirdshipping",order.getIsthirdshipping())
            .set("paytype",order.getPaytype())
            .set("latitude",order.getLatitude())
            .set("longitude",order.getLongitude())
            .set("detail",order.getDetail())
            .set("extras",order.getExtras());
        mongoTemplate.updateFirst(query,update,OrderWaiMai.class);
    }

    //修改订单状态
    public void updateStatus(String orderId,int status){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_MEITUAN).where("platformOrderId").is(orderId));
        Update update = new Update().set("order.status",status);
        mongoTemplate.upsert(query,update, OrderWaiMai.class);
    }

}

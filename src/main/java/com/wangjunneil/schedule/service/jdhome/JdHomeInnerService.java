package com.wangjunneil.schedule.service.jdhome;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Service
public class JdHomeInnerService {
    private static Logger log = Logger.getLogger(JdHomeInnerService.class.getName());

    @Autowired
    private MongoTemplate mongoTemplate;

    //批量插入订单
    public void addSyncOrders(List<OrderInfoDTO> orders) {
        mongoTemplate.insertAll(orders);
    }

    //批量插入/修改订单
    public void addOrUpdateSyncOrder(List<OrderInfoDTO> orders){
        for(OrderInfoDTO order : orders){
            Query query = new Query(Criteria.where("orderId").is(order.getOrderId()));
            Update update = new Update().set("_class",OrderInfoDTO.class.getName())
                .set("srcOrderId",order.getSrcOrderId())
                .set("srcInnerType",order.getSrcInnerType())
                .set("srcInnerOrderId",order.getSrcInnerOrderId())
                .set("orderType",order.getOrderType())
                .set("orderStatus",order.getOrderStatus())
                .set("orderStatusTime",order.getOrderStatusTime())
                .set("orderStartTime",order.getOrderStartTime())
                .set("orderPurchaseTime",order.getOrderPurchaseTime())
                .set("orderAgingType",order.getOrderAgingType())
                .set("orderPreStartDeliveryTime",order.getOrderPreStartDeliveryTime())
                .set("orderPreEndDeliveryTime",order.getOrderPreEndDeliveryTime())
                .set("orderCancelTime",order.getOrderCancelTime())
                .set("orderCancelRemark",order.getOrderCancelRemark())
                .set("orgCode",order.getOrgCode())
                .set("buyerFullName",order.getBuyerFullName())
                .set("buyerFullAddress",order.getBuyerFullAddress())
                .set("buyerTelephone",order.getBuyerTelephone())
                .set("buyerMobile",order.getBuyerMobile())
                .set("produceStationNo",order.getProduceStationNo())
                .set("produceStationName",order.getProduceStationName())
                .set("produceStationNoIsv",order.getProduceStationNoIsv())
                .set("deliveryStationNo",order.getDeliveryStationNo())
                .set("deliveryStationName",order.getDeliveryStationName())
                .set("deliveryCarrierNo",order.getDeliveryCarrierNo())
                .set("deliveryCarrierName",order.getDeliveryCarrierName())
                .set("deliveryBillNo",order.getDeliveryBillNo())
                .set("deliveryPackageWeight",order.getDeliveryPackageWeight())
                .set("deliveryConfirmTime",order.getDeliveryConfirmTime())
                .set("orderPayType",order.getOrderPayType())
                .set("orderTotalMoney",order.getOrderTotalMoney())
                .set("orderDiscountMoney",order.getOrderDiscountMoney())
                .set("orderFreightMoney",order.getOrderFreightMoney())
                .set("orderBuyerPayableMoney",order.getOrderBuyerPayableMoney())
                .set("packagingMoney",order.getPackagingMoney())
                .set("orderInvoiceOpenMark",order.getOrderInvoiceOpenMark())
                .set("adjustId",order.getAdjustId())
                .set("adjustIsExists",order.getAdjustIsExists())
                .set("ts",order.getTs())
                .set("orderExtend",order.getOrderExtend())
                .set("productList",order.getProductList())
                .set("discountList",order.getDiscountList());
            mongoTemplate.upsert(query,update,OrderInfoDTO.class);
        }
    }

    //修改订单状态
    public void updateStatus(OrderAcceptOperate operate,int code){
        if(operate.getOrderId()==null || operate.getIsAgreed()==null){
            return;
        }
        Query query = new Query(Criteria.where("orderId").is(operate.getOrderId()));
        Update update = new Update().set("orderStatus",code);
        mongoTemplate.upsert(query,update,OrderInfoDTO.class);
    }

    public void addAccessToken(JdAccessToken jdAccessToken) {
        // 计算token到期时间
        long time = Long.parseLong(jdAccessToken.getTime());
        int expire_in = jdAccessToken.getExpires_in();
        Date expireDate = DateTimeUtil.getExpireDate(time, expire_in);
        jdAccessToken.setExpire_Date(expireDate);

        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        Update update = new Update()
            .set("access_token", jdAccessToken.getAccess_token())
            .set("expires_in", jdAccessToken.getExpires_in())
            .set("refresh_token", jdAccessToken.getRefresh_token())
            .set("token_type", jdAccessToken.getToken_type())
            .set("time", jdAccessToken.getTime())
            .set("uid", jdAccessToken.getUid())
            .set("user_nick", jdAccessToken.getUser_nick())
            .set("expire_Date", jdAccessToken.getExpire_Date())
            .set("username", jdAccessToken.getUsername());
        mongoTemplate.upsert(query, update, JdAccessToken.class);
    }

    public Cfg getJdCfg() {
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        Cfg cfg = mongoTemplate.findOne(query, Cfg.class);
        return cfg;
    }
}

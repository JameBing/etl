package com.wangjunneil.schedule.service.jdhome;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.baidu.Order;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.jdhome.JdHomeAccessToken;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public void addOrUpdateSyncOrder(List<OrderInfoDTO> orders) throws ScheduleException{
        for(OrderInfoDTO order : orders){
            Query query = new Query(Criteria.where("platformOrderId").is(order.getOrderId()));
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
                .set("latestTime",new Date())
                .set("orderExtend",order.getOrderExtend())
                .set("productList",order.getProductList())
                .set("discountList",order.getDiscountList());
            mongoTemplate.updateMulti(query,update,OrderInfoDTO.class);
        }
    }

    //获取单个订单
    public OrderWaiMai getOrder(String  orderId){
        Query query = new Query(Criteria.where("platformOrderId").is(orderId).and("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        OrderWaiMai order = mongoTemplate.findOne(query, OrderWaiMai.class);
        return order;
    }

    //修改整个订单
    public void updateSysOrder(OrderInfoDTO order){
        String orderId = String.valueOf(order.getOrderId());
        Query query = new Query(Criteria.where("platformOrderId").is(orderId).and("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        Update update = new Update().set("order",order);
        mongoTemplate.updateFirst(query, update, OrderWaiMai.class);
    }

    //修改订单状态
    public void updateStatus(String orderId,int status)throws ScheduleException{
        Query query = new Query(Criteria.where("platformOrderId").is(orderId).and("platform").is(Constants.PLATFORM_WAIMAI_JDHOME));
        Update update = new Update().set("order.orderStatus",status)
            .set("order.latestTime", new Date());
        mongoTemplate.updateFirst(query, update, OrderWaiMai.class);
    }

    //添加/修改token
    public void addAccessToken(JdHomeAccessToken jdHomeAccessToken) throws ScheduleException{
        // 计算token到期时间
        long time = Long.parseLong(jdHomeAccessToken.getTime());
        int expire_in = jdHomeAccessToken.getExpires_in();
        Date expireDate = DateTimeUtil.getExpireDate(time, expire_in);
        jdHomeAccessToken.setExpire_Date(expireDate);

        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).and("companyId").is(jdHomeAccessToken.getCompanyId()));
        Update update = new Update()
            .set("token", jdHomeAccessToken.getToken())
            .set("expires_in", jdHomeAccessToken.getExpires_in())
            .set("token_type", jdHomeAccessToken.getToken_type())
            .set("time", jdHomeAccessToken.getTime())
            .set("uid", jdHomeAccessToken.getUid())
            .set("user_nick", jdHomeAccessToken.getUser_nick())
            .set("expire_Date", jdHomeAccessToken.getExpire_Date())
            .set("username", jdHomeAccessToken.getUsername())
            .set("companyId",jdHomeAccessToken.getCompanyId())
            .set("appKey",jdHomeAccessToken.getAppKey())
            .set("appSecret",jdHomeAccessToken.getAppSecret())
            .set("callback",jdHomeAccessToken.getCallback())
            .set("shopIds", jdHomeAccessToken.getShopIds());
        mongoTemplate.upsert(query, update, JdHomeAccessToken.class);
    }

    //根据门店Id获取token值
    public JdHomeAccessToken getAccessToken(String shopId){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).and("shopIds").elemMatch(Criteria.where("shopId").is(shopId)));
        JdHomeAccessToken jdHomeAccessToken = mongoTemplate.findOne(query, JdHomeAccessToken.class);
        if(jdHomeAccessToken == null){
            return  getAccessTokenByComId(shopId);
        }
        return jdHomeAccessToken;
    }

    //根据商家Id获取token值
    public JdHomeAccessToken getAccessTokenByComId(String companyId){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).and("companyId").is(companyId));
        JdHomeAccessToken jdHomeAccessToken = mongoTemplate.findOne(query, JdHomeAccessToken.class);
        return jdHomeAccessToken;
    }

    //添加/修改回调token
    public void addRefreshToken(JdHomeAccessToken jdHomeAccessToken)throws ScheduleException{
        // 计算token到期时间
        long time = Long.parseLong(jdHomeAccessToken.getTime());
        int expire_in = jdHomeAccessToken.getExpires_in();
        Date expireDate = DateTimeUtil.getExpireDate(time, expire_in);
        jdHomeAccessToken.setExpire_Date(expireDate);

        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).and("companyId").is(jdHomeAccessToken.getCompanyId()));
        Update update = new Update()
            .set("token", jdHomeAccessToken.getToken())
            .set("expires_in", jdHomeAccessToken.getExpires_in())
            .set("token_type", jdHomeAccessToken.getToken_type())
            .set("time", jdHomeAccessToken.getTime())
            .set("uid", jdHomeAccessToken.getUid())
            .set("user_nick", jdHomeAccessToken.getUser_nick())
            .set("expire_Date", jdHomeAccessToken.getExpire_Date())
            .set("username", jdHomeAccessToken.getUsername())
            .set("companyId",jdHomeAccessToken.getCompanyId());
        mongoTemplate.upsert(query, update, JdHomeAccessToken.class);
    }

    //添加回调Code
    public void addBackCode(JdHomeAccessToken jdHomeAccessToken)throws ScheduleException{
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).and("companyId").is(jdHomeAccessToken.getCompanyId()));
        Update update = new Update()
            .set("code", jdHomeAccessToken.getCode());
        mongoTemplate.upsert(query, update, JdHomeAccessToken.class);
    }

    //门店接单更新标识
    public void updateIsReceived(String orderId,int isRec){
        Query query = new Query(Criteria.where("platform").is(Constants.PLATFORM_WAIMAI_JDHOME).where("platformOrderId").is(orderId));
        Update update = new Update().set("isReceived",isRec);
        mongoTemplate.upsert(query,update, OrderWaiMai.class);
    }
}

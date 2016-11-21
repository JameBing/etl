package com.wangjunneil.schedule.service.jdhome;

import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

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
}

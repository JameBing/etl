package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-08.
 */
public class OrderEleSerializer implements JsonSerializer<OrderEle>{
    @Override
    public JsonElement serialize(OrderEle orderInfo, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("address",context.serialize(orderInfo.getAddress()));
        object.add("createdAt",context.serialize(orderInfo.getCreatedAt()));
        object.add("activeAt",context.serialize(orderInfo.getActiveAt()));
        object.add("deliverFee",context.serialize(orderInfo.getDeliverFee()));
        object.add("deliverTime",context.serialize(orderInfo.getDeliverTime()));
        object.add("description",context.serialize(orderInfo.getDescription()));
        object.add("groups",context.serialize(orderInfo.getGroups()));
        object.add("invoice",context.serialize(orderInfo.getInvoice()));
        object.add("book",context.serialize(orderInfo.getBook()));
        object.add("onlinePaid",context.serialize(orderInfo.getOnlinePaid()));
        object.add("id",context.serialize(orderInfo.getId()));
        object.add("phoneList",context.serialize(orderInfo.getPhoneList()));
        object.add("shopId",context.serialize(orderInfo.getShopId()));
        object.add("openId",context.serialize(orderInfo.getOpenId()));
        object.add("shopName",context.serialize(orderInfo.getShopName()));
        object.add("daySn",context.serialize(orderInfo.getDaySn()));
        object.add("status",context.serialize(orderInfo.getStatus()));
        object.add("refundStatus",context.serialize(orderInfo.getRefundStatus()));
        object.add("userId",context.serialize(orderInfo.getUserId()));
        object.add("totalPrice",context.serialize(orderInfo.getTotalPrice()));
        object.add("originalPrice",context.serialize(orderInfo.getOriginalPrice()));
        object.add("consignee",context.serialize(orderInfo.getConsignee()));
        object.add("deliveryGeo",context.serialize(orderInfo.getDeliveryGeo()));
        object.add("deliveryPoiAddress",context.serialize(orderInfo.getDeliveryPoiAddress()));
        object.add("invoiced",context.serialize(orderInfo.getInvoiced()));
        object.add("income",context.serialize(orderInfo.getIncome()));
        object.add("serviceRate",context.serialize(orderInfo.getServiceRate()));
        object.add("serviceFee",context.serialize(orderInfo.getServiceFee()));
        object.add("hongbao",context.serialize(orderInfo.getHongbao()));
        object.add("packageFee",context.serialize(orderInfo.getPackageFee()));
        object.add("activityTotal",context.serialize(orderInfo.getActivityTotal()));
        object.add("shopPart",context.serialize(orderInfo.getShopPart()));
        object.add("downgraded",context.serialize(orderInfo.getDowngraded()));
        object.add("secretPhoneExpireTime",context.serialize(orderInfo.getSecretPhoneExpireTime()));
        object.add("orderActivities",context.serialize(orderInfo.getOrderActivities()));
        object.add("invoiceType",context.serialize(orderInfo.getInvoiceType()));
        object.add("taxpayerId",context.serialize(orderInfo.getTaxpayerId()));
        return object;
    }
}

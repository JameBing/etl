package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class OrderSerializer implements JsonSerializer<Order> {

    @Override
    public JsonElement serialize(Order order,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("order_id",context.serialize(order.getOrderId()));
        object.add("send_immediately",context.serialize(order.getSendImmediately()));
        object.add("send_time",context.serialize(order.getSendTime()));
        object.add("send_fee",context.serialize(order.getSendFee()));
        object.add("package_fee",context.serialize(order.getPackageFee()));
        object.add("discount_fee",context.serialize(order.getDiscountFee()));
        object.add("shop_fee",context.serialize(order.getShopFee()));
        object.add("total_fee",context.serialize(order.getTotalFee()));
        object.add("user_fee",context.serialize(order.getUserFee()));
        object.add("pay_type",context.serialize(order.getPayType()));
        object.add("pay_status",context.serialize(order.getPayStatus()));
        object.add("need_invoice",context.serialize(order.getNeedInvoice()));
        object.add("invoice_title",context.serialize(order.getInvoiceTitle()));
        object.add("remark",context.serialize(order.getRemark()));
        object.add("delivery_party",context.serialize(order.getDeliveryParty()));
        object.add("create_time",context.serialize(order.getCreateTime()));
        object.add("type",context.serialize(order.getType()));
        object.add("status",context.serialize(order.getStatus()));
        object.add("reason",context.serialize(order.getReason()));

        return  object;
    }
}

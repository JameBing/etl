package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/21.
 */
public class OrderSerializer implements JsonSerializer<Order> {
    @Override
    public JsonElement serialize(Order order, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("original_price",jsonSerializationContext.serialize(order.getOriginalprice()));
        object.add("restaurant_name",jsonSerializationContext.serialize(order.getRestaurantname()));
        object.add("refund_code",jsonSerializationContext.serialize(order.getRefundcode()));
        object.add("status_code",jsonSerializationContext.serialize(order.getStatuscode()));
        object.add("restaurant_id",jsonSerializationContext.serialize(order.getRestaurantid()));
        object.add("service_fee",jsonSerializationContext.serialize(order.getServicefee()));
        object.add("consignee",jsonSerializationContext.serialize(order.getConsignee()));
        object.add("invoice",jsonSerializationContext.serialize(order.getInvoice()));
        object.add("description",jsonSerializationContext.serialize(order.getDescription()));
        object.add("user_id",jsonSerializationContext.serialize(order.getUserid()));
        object.add("delivery_geo",jsonSerializationContext.serialize(order.getDeliverygeo()));
        object.add("detail",jsonSerializationContext.serialize(order.getDetail()));
        object.add("active_at",jsonSerializationContext.serialize(order.getActiveat()));
        object.add("invoiced",jsonSerializationContext.serialize(order.getInvoiced()));
        object.add("user_name",jsonSerializationContext.serialize(order.getUsername()));
        object.add("deliver_fee",jsonSerializationContext.serialize(order.getDeliverfee()));
        object.add("is_book",jsonSerializationContext.serialize(order.getIsbook()));
        object.add("restaurant_part",jsonSerializationContext.serialize(order.getRestaurantpart()));
        object.add("deliver_time",jsonSerializationContext.serialize(order.getDelivertime()));
        object.add("order_id",jsonSerializationContext.serialize(order.getOrderid()));
        object.add("eleme_part",jsonSerializationContext.serialize(order.getElemepart()));
        object.add("income",jsonSerializationContext.serialize(order.getIncome()));
        object.add("restaurant_number",jsonSerializationContext.serialize(order.getRestaurantnumber()));
        object.add("address",jsonSerializationContext.serialize(order.getAddress()));
        object.add("hongbao",jsonSerializationContext.serialize(order.getHongbao()));
        object.add("is_online_paid",jsonSerializationContext.serialize(order.getIsonlinepaid()));
        object.add("delivery_poi_address",jsonSerializationContext.serialize(order.getDeliverypoiaddress()));
        object.add("total_price",jsonSerializationContext.serialize(order.getTotalprice()));
        object.add("created_at",jsonSerializationContext.serialize(order.getCreatedat()));
        object.add("service_rate",jsonSerializationContext.serialize(order.getServicerate()));
        object.add("package_fee",jsonSerializationContext.serialize(order.getPackagefee()));
        object.add("activity_total",jsonSerializationContext.serialize(order.getActivitytotal()));
        object.add("phone_list",jsonSerializationContext.serialize(order.getPhonelist()));
        object.add("distribution",jsonSerializationContext.serialize(order.getDistribution()));
        return object;
    }
}

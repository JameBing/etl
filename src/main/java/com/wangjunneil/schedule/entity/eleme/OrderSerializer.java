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
        object.add("address",jsonSerializationContext.serialize(order.getAddress()));
        object.add("consignee",jsonSerializationContext.serialize(order.getConsignee()));
        object.add("created_at",jsonSerializationContext.serialize(order.getCreatedat()));
        object.add("active_at",jsonSerializationContext.serialize(order.getActiveat()));
        object.add("deliver_fee",jsonSerializationContext.serialize(order.getDeliverfee()));
        object.add("deliver_time",jsonSerializationContext.serialize(order.getDelivertime()));
        object.add("description",jsonSerializationContext.serialize(order.getDescription()));
        object.add("detail",jsonSerializationContext.serialize(order.getDetail()));
        object.add("invoice",jsonSerializationContext.serialize(order.getInvoice()));
        object.add("is_book",jsonSerializationContext.serialize(order.getIsbook()));
        object.add("is_online_paid",jsonSerializationContext.serialize(order.getIsonlinepaid()));
        object.add("order_id",jsonSerializationContext.serialize(order.getOrderid()));
        object.add("phone_list",jsonSerializationContext.serialize(order.getPhonelist()));
        object.add("tp_restaurant_id",jsonSerializationContext.serialize(order.getTprestaurantid()));
        object.add("restaurant_id",jsonSerializationContext.serialize(order.getRestaurantid()));
        object.add("inner_id",jsonSerializationContext.serialize(order.getInnerid()));
        object.add("restaurant_name",jsonSerializationContext.serialize(order.getRestaurantname()));
        object.add("restaurant_number",jsonSerializationContext.serialize(order.getRestaurantnumber()));
        object.add("status_code",jsonSerializationContext.serialize(order.getStatuscode()));
        object.add("refund_code",jsonSerializationContext.serialize(order.getRefundcode()));
        object.add("total_price",jsonSerializationContext.serialize(order.getTotalprice()));
        object.add("original_price",jsonSerializationContext.serialize(order.getOriginalprice()));
        object.add("user_id",jsonSerializationContext.serialize(order.getUserid()));
        object.add("user_name",jsonSerializationContext.serialize(order.getUsername()));
        object.add("delivery_geo",jsonSerializationContext.serialize(order.getDeliverygeo()));
        return object;
    }
}

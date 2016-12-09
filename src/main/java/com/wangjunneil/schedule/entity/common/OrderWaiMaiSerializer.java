package com.wangjunneil.schedule.entity.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/6.
 */
public class OrderWaiMaiSerializer implements JsonSerializer<OrderWaiMai> {
    @Override
    public JsonElement serialize(OrderWaiMai orderWaiMai, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("platform",context.serialize(orderWaiMai.getPlatform()));
        object.add("orderId",context.serialize(orderWaiMai.getOrderId()));
        object.add("platformOrderId",context.serialize(orderWaiMai.getPlatformOrderId()));
        object.add("order",context.serialize(orderWaiMai.getOrder()));
        return object;
    }
}

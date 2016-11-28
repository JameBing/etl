package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class OrderPushRequestSerializer implements JsonSerializer<OrderPushRequest> {
    @Override
    public JsonElement serialize(OrderPushRequest orderPushRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.add("push_action",jsonSerializationContext.serialize(orderPushRequest.getPush_action()));
        object.add("restaurant_id",jsonSerializationContext.serialize(orderPushRequest.getRestaurant_id()));
        object.add("order_ids",jsonSerializationContext.serialize(orderPushRequest.getOrder_ids()));
        return object;
    }
}

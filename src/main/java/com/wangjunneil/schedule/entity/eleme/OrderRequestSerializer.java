package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class OrderRequestSerializer implements JsonSerializer<OrderRequest> {

    @Override
    public JsonElement serialize(OrderRequest orderRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("eleme_order_id",jsonSerializationContext.serialize(orderRequest.getEleme_order_id()));
        object.add("tp_id",jsonSerializationContext.serialize(orderRequest.getTp_id()));
        object.add("status",jsonSerializationContext.serialize(orderRequest.getStatus()));
        object.add("reason",jsonSerializationContext.serialize(orderRequest.getReason()));
        object.add("restaurant_id",jsonSerializationContext.serialize(orderRequest.getRestaurant_id()));
        object.add("eleme_order_ids",jsonSerializationContext.serialize(orderRequest.getEleme_order_ids()));
        return object;
    }
}

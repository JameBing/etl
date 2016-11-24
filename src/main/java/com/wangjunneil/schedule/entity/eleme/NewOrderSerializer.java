package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/18.
 */
public class NewOrderSerializer implements JsonSerializer<NewOrder> {
    @Override
    public JsonElement serialize(NewOrder newOrder, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("eleme_order_ids",jsonSerializationContext.serialize(newOrder.getEleme_order_ids()));
        object.add("push_action",jsonSerializationContext.serialize(newOrder.getPush_action()));
        object.add("restaurant_id",jsonSerializationContext.serialize(newOrder.getRestaurant_id()));
        object.add("order_ids",jsonSerializationContext.serialize(newOrder.getOrder_ids()));
        return object;
    }
}

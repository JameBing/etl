package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderDiscountSerializer implements JsonSerializer<OrderDiscount> {
    @Override
    public JsonElement serialize(OrderDiscount orderDiscount, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("type",context.serialize(orderDiscount.getType()));
        object.add("fee",context.serialize(orderDiscount.getFee()));
        object.add("activity_id",context.serialize(orderDiscount.getActivityId()));
        object.add("baidu_rate",context.serialize(orderDiscount.getBaiduRate()));
        object.add("agent_rate",context.serialize(orderDiscount.getAgentRate()));
        object.add("logistics_rate",context.serialize(orderDiscount.getLogisticsRate()));
        object.add("desc",context.serialize(orderDiscount.getDesc()));
        object.add("products",context.serialize(orderDiscount.getProducts()));
        return object;
    }
}

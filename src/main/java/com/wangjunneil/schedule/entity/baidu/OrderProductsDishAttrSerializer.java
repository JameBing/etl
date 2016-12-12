package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsDishAttrSerializer implements JsonSerializer<OrderProductsDishAttr> {
    @Override
    public JsonElement serialize(OrderProductsDishAttr orderProductsDishAttr, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("option",context.serialize(orderProductsDishAttr.getOption()));
        object.add("name",context.serialize(orderProductsDishAttr.getName()));
        object.add("baidu_attr_id",context.serialize(orderProductsDishAttr.getBaiduAttrId()));
        object.add("attr_id",context.serialize(orderProductsDishAttr.getAttrId()));
        return object;
    }
}

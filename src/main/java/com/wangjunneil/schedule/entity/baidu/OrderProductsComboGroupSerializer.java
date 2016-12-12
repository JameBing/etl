package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsComboGroupSerializer implements JsonSerializer<OrderProductsComboGroup> {
    @Override
    public JsonElement serialize(OrderProductsComboGroup orderProductsComboGroup, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("group_name",context.serialize(orderProductsComboGroup.getGroupName()));
        object.add("baidu_group_id",context.serialize(orderProductsComboGroup.getBaiduGroupId()));
        object.add("product",context.serialize(orderProductsComboGroup.getProducts()));
        return object;
    }
}

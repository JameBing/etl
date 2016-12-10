package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsFeaturesSerializer implements JsonSerializer<OrderProductsFeatures > {
    @Override
    public JsonElement serialize(OrderProductsFeatures orderProductsFeatures, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("option",context.serialize(orderProductsFeatures.getOption()));
        object.add("name",context.serialize(orderProductsFeatures.getName()));
        object.add("baidu_feature_id",context.serialize(orderProductsFeatures.getBaiduFeatureId()));
        return object;
    }
}

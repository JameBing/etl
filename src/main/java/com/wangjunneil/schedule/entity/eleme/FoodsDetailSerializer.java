package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/23.
 */
public class FoodsDetailSerializer implements JsonSerializer<FoodsDetail> {
    @Override
    public JsonElement serialize(FoodsDetail foodsDetail, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("foods",jsonSerializationContext.serialize(foodsDetail.getFoods()));
        object.add("get_failed",jsonSerializationContext.serialize(foodsDetail.getGetfailed()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class OldFoodsRequestSerializer implements JsonSerializer<OldFoodsRequest> {
    @Override
    public JsonElement serialize(OldFoodsRequest oldFoodsRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("food_id",jsonSerializationContext.serialize(oldFoodsRequest.getFood_id()));
        object.add("stock",jsonSerializationContext.serialize(oldFoodsRequest.getStock()));
        return object;
    }
}

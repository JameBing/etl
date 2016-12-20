package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class RestaurantRequestSerializer implements JsonSerializer<RestaurantRequest> {
    @Override
    public JsonElement serialize(RestaurantRequest restaurantRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元素
        return object;
    }
}

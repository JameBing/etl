package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/18.
 */
public class BusinessStatusSerializer implements JsonSerializer<BusinessStatus> {
    @Override
    public JsonElement serialize(BusinessStatus businessStatus, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元素
        object.add("is_open", jsonSerializationContext.serialize(businessStatus.getIs_open()));
        return object;
    }
}

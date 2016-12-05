package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-02.
 */
public class BusinessTimeSerializer implements JsonSerializer<BusinessTime> {
    @Override
    public JsonElement serialize(BusinessTime businessTime, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("start",context.serialize(businessTime.getStart()));
        object.add("end",context.serialize(businessTime.getEnd()));
        return object;
    }
}

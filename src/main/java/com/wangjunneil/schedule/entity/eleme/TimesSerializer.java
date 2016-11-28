package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class TimesSerializer implements JsonSerializer<Times> {
    @Override
    public JsonElement serialize(Times times, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("start_time",jsonSerializationContext.serialize(times.getStart_time()));
        object.add("end_time",jsonSerializationContext.serialize(times.getEnd_time()));
        return object;
    }
}

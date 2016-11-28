package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class DisplayAttributeSerializer implements JsonSerializer<DisplayAttribute> {
    @Override
    public JsonElement serialize(DisplayAttribute displayAttribute, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("times",jsonSerializationContext.serialize(displayAttribute.getTimes()));
        return object;
    }
}

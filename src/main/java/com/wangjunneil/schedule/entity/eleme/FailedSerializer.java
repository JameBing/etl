package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/23.
 */
public class FailedSerializer implements JsonSerializer<Failed> {
    @Override
    public JsonElement serialize(Failed failed, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("18755176",jsonSerializationContext.serialize(failed.getFoodsid()));
        return object;
    }
}

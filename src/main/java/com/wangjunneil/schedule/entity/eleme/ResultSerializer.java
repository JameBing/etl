package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/18.
 */
public class ResultSerializer implements JsonSerializer<Result>{
    @Override
    public JsonElement serialize(Result result, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("code", jsonSerializationContext.serialize(result.getCode()));
        object.add("data", jsonSerializationContext.serialize(result.getData()));
        object.add("message", jsonSerializationContext.serialize(result.getMessage()));
        object.add("request_id", jsonSerializationContext.serialize(result.getRequest_id()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.mt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/1.
 */
public class ResultSerializer implements JsonSerializer<Result> {
    @Override
    public JsonElement serialize(Result result, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元素
        object.add("data", jsonSerializationContext.serialize(result.getData()));
        object.add("error", jsonSerializationContext.serialize(result.getError()));
        return object;
    }
}

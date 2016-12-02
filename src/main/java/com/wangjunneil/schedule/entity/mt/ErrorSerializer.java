package com.wangjunneil.schedule.entity.mt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/1.
 */
public class ErrorSerializer implements JsonSerializer<Error> {
    @Override
    public JsonElement serialize(Error error, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元素
        object.add("code", jsonSerializationContext.serialize(error.getCode()));
        object.add("msg", jsonSerializationContext.serialize(error.getMsg()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class ResultSerializer implements JsonSerializer<Result> {

    @Override
    public JsonElement serialize(Result result, Type type, JsonSerializationContext context){

        JsonObject object = new JsonObject();
        object.add("errno",context.serialize(result.getErrno()));
        object.add("error",context.serialize(result.getError()));
        object.add("data",context.serialize(result.GetData()));
        return object;
    };
}

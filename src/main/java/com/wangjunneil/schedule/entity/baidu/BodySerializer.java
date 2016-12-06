package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class BodySerializer implements JsonSerializer<Body> {

    @Override
    public JsonElement serialize(Body body, Type type, JsonSerializationContext context){

        JsonObject object = new JsonObject();
        object.add("errno",context.serialize(Integer.valueOf(body.getErrno())));
        object.add("error",context.serialize(body.getError()));
        object.add("data",context.serialize(body.getData()));
        return object;
    };
}

package com.wangjunneil.schedule.entity.jdhome;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-21.
 */
public class ResultSerializer implements JsonSerializer<Result>{

    @Override
    public JsonElement serialize(Result result,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("code",context.serialize(result.getCode()));
        object.add("msg",context.serialize(result.getMsg()));
        object.add("data",context.serialize(result.getData()));
        object.add("success",context.serialize(result.getSuccess()));
        return object;
    }

}

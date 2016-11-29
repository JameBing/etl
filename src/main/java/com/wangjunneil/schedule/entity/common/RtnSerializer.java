package com.wangjunneil.schedule.entity.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-21.
 */
public class RtnSerializer implements JsonSerializer<Rtn>{

    @Override
    public JsonElement serialize(Rtn result,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("logId",context.serialize(result.getLogId()));
        object.add("code",context.serialize(result.getCode()));
        object.add("desc",context.serialize(result.getDesc()));
        object.add("remark",context.serialize(result.getRemark()));
        object.add("dynamic",context.serialize(result.getDynamic()));
        return object;
    }

}

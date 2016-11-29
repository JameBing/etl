package com.wangjunneil.schedule.entity.eleme;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-15.
*/
public class SysParamsSerializer implements JsonSerializer<SysParams> {
    @Override
    public JsonElement serialize(SysParams sysParams, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("consumer_key", context.serialize(sysParams.getConsumer_key()));
        object.add("timestamp", context.serialize(sysParams.getTimestamp()));
        object.add("sig", context.serialize(sysParams.getSig()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.mt;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/1.
 */
public class SysparamsSerializer implements JsonSerializer<SysParams> {
    @Override
    public JsonElement serialize(SysParams sysParams, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元素
        object.add("timestamp", jsonSerializationContext.serialize(sysParams.getTimestamp()));
        object.add("app_id", jsonSerializationContext.serialize(sysParams.getApp_id()));
        object.add("sig", jsonSerializationContext.serialize(sysParams.getSig()));
        return object;
    }
}

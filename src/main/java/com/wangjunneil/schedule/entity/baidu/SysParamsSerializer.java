package com.wangjunneil.schedule.entity.baidu;
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
        //注意：此处必须按照字母顺序依次加入元素
        object.add("body", context.serialize(sysParams.getBody()));
        object.add("cmd", context.serialize(sysParams.getCmd()));

        object.add("sign", context.serialize(sysParams.getSign()));
        object.add("source", context.serialize(sysParams.getSource()));
        object.add("ticket", context.serialize(sysParams.getTicket()));
        object.add("timestamp", context.serialize(sysParams.getTimestamp()));
        object.add("version", context.serialize(sysParams.getVersion()));
        return object;
    }
}

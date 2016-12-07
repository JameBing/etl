package com.wangjunneil.schedule.entity.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-06.
 */
public class ParsFormPos2Serializer  implements JsonSerializer<ParsFormPos2> {
    @Override
    public JsonElement serialize(ParsFormPos2 parsFormPos2, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("baidu",context.serialize(parsFormPos2.getBaidu()));
        object.add("jdhome",context.serialize(parsFormPos2.getJdhome()));
        object.add("eleme",context.serialize(parsFormPos2.getEleme()));
        object.add("meituan",context.serialize(parsFormPos2.getMeituan()));
        return object;
    }
}

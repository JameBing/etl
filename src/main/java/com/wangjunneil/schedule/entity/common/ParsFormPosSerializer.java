package com.wangjunneil.schedule.entity.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-24.
 */
public class ParsFormPosSerializer implements JsonSerializer<ParsFromPos> {


    @Override
    public JsonElement serialize(ParsFromPos parsFromPos, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("baidu",context.serialize(parsFromPos.getBaidu()));
        object.add("jdhome",context.serialize(parsFromPos.getJdhome()));
        object.add("eleme",context.serialize(parsFromPos.getEleme()));
        object.add("meituan",context.serialize(parsFromPos.getMeituan()));
        return object;
    }
}

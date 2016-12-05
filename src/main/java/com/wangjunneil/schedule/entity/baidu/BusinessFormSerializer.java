package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class BusinessFormSerializer implements JsonSerializer<businessForm> {

    @Override
    public JsonElement serialize(businessForm businessForm, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("id",context.serialize(businessForm.getId()));
        object.add("name",context.serialize(businessForm.getName()));
        return object;
    }
}

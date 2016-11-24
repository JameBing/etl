package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class DetailSerializer implements JsonSerializer<Detail> {

    @Override
    public JsonElement serialize(Detail detail, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("group",jsonSerializationContext.serialize(detail.getGroup()));
        object.add("extra",jsonSerializationContext.serialize(detail.getExtra()));
        object.add("abandoned_extra",jsonSerializationContext.serialize(detail.getAbandonedextra()));
        return object;
    }
}

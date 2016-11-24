package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/24.
 */
public class BodySerializer implements JsonSerializer<Body> {
    @Override
    public JsonElement serialize(Body body, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("food", jsonSerializationContext.serialize(body.getFood()));
        object.add("group", jsonSerializationContext.serialize(body.getGroup()));
        object.add("extra", jsonSerializationContext.serialize(body.getExtra()));
        object.add("abandoned_extra", jsonSerializationContext.serialize(body.getAbandonedextra()));
        return object;
    }
}

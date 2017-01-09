package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/20.
 */
public class StatusSerializer implements JsonSerializer<Status> {

    @Override
    public JsonElement serialize(Status status, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("order_mode",jsonSerializationContext.serialize(status.getOrdermode()));
        object.add("is_valid",jsonSerializationContext.serialize(status.getIsvalid()));
        object.add("is_open",jsonSerializationContext.serialize(status.getIsopen()));
        return object;
    }
}

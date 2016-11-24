package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class CancelStatusSerializer implements JsonSerializer<CancelStatus> {
    @Override
    public JsonElement serialize(CancelStatus cancelStatus, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("eleme_order_id",jsonSerializationContext.serialize(cancelStatus.getEleme_order_id()));
        object.add("status",jsonSerializationContext.serialize(cancelStatus.getStatus()));
        object.add("reason",jsonSerializationContext.serialize(cancelStatus.getReason()));
        return object;
    }
}

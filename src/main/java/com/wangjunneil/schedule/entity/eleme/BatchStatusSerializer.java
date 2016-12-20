package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/20.
 */
public class BatchStatusSerializer implements JsonSerializer<BatchStatus> {
    @Override
    public JsonElement serialize(BatchStatus batchStatus, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("batchstatus",jsonSerializationContext.serialize(batchStatus.getBatchstatus()));
        object.add("failed",jsonSerializationContext.serialize(batchStatus.getFailed()));
        return object;
    }
}

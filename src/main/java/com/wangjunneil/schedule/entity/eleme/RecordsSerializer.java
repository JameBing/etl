package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/8.
 */
public class RecordsSerializer implements JsonSerializer<Records> {
    @Override
    public JsonElement serialize(Records records, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("deliveryman_info", jsonSerializationContext.serialize(records.getDeliverymaninfo()));
        object.add("status_code", jsonSerializationContext.serialize(records.getStatuscode()));
        object.add("updated_at", jsonSerializationContext.serialize(records.getUpdatedat()));
        object.add("sub_status_code", jsonSerializationContext.serialize(records.getSubstatuscode()));
        return object;
    }
}

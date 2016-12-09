package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/8.
 */
public class DistributionSerializer implements JsonSerializer<Distribution> {
    @Override
    public JsonElement serialize(Distribution distribution, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("eleme_order_id", jsonSerializationContext.serialize(distribution.getElemeorderid()));
        object.add("records", jsonSerializationContext.serialize(distribution.getRecords()));
        object.add("eleme_order_ids_not_existed", jsonSerializationContext.serialize(distribution.getElemeorderidsnotexisted()));
        object.add("order_delivery_info_not_existed", jsonSerializationContext.serialize(distribution.getOrderdeliveryinfonotexisted()));
        return object;
    }
}

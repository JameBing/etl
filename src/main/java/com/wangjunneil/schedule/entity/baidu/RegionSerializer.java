package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-02.
 */
public class RegionSerializer implements JsonSerializer<Region> {
    @Override
    public JsonElement serialize(Region region, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("longitude",context.serialize(region.getLongitude()));
        object.add("latitude",context.serialize(region.getLatitude()));
        return object;
    }
}

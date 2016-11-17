package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class CoordAmapSerializer implements JsonSerializer<CoordAmap> {

    @Override
    public JsonElement serialize(CoordAmap coordAmap,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("longitude",context.serialize(coordAmap.getLongitude()));
        object.add("latitude",context.serialize(coordAmap.getLatitude()));
        return object;
    }
}

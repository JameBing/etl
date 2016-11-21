package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class CoordSerializer implements JsonSerializer<Coord> {

    @Override
    public JsonElement serialize(Coord coord,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("longitude",context.serialize(coord.getLongitude()));
        object.add("latitude",context.serialize(coord.getLatitude()));
        return  object;
    }
}

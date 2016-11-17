package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class AvailableTimeSerializer implements JsonSerializer<AvailableTime> {

    @Override
    public  JsonElement serialize(AvailableTime availableTime, Type type, JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("start_time",context.serialize(availableTime.getStartTime()));
        object.add("end_time",context.serialize(availableTime.getEndTime()));
        return object;
    };
}

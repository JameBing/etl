package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class AttrSerializer implements JsonSerializer<Attr> {

    @Override
    public JsonElement serialize(Attr attr,Type type,JsonSerializationContext context){

        JsonObject object = new JsonObject();
        object.add("name",context.serialize(attr.getName()));
        object.add("value",context.serialize(attr.getValue()));
        return object;
    }
}

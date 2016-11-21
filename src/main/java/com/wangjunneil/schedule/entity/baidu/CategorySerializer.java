package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class CategorySerializer implements JsonSerializer<Category>{

    @Override
    public JsonElement serialize(Category category,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("name",context.serialize(category.getName()));
        object.add("rank",context.serialize(category.getRank()));
        return object;
    }

}

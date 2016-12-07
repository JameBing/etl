package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class CategorysSerializer implements JsonSerializer<Categorys> {
    @Override
    public JsonElement serialize(Categorys categorys, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("category1",context.serialize(categorys.getCategory1()));
        object.add("category1_id",context.serialize(categorys.getCategoryId1()));
        object.add("category2",context.serialize(categorys.getCategory2()));
        object.add("category2_id",context.serialize(categorys.getCategoryId2()));
        object.add("category3",context.serialize(categorys.getCategory3()));
        object.add("category3_id",context.serialize(categorys.getCategoryId3()));
        return object;
    }
}

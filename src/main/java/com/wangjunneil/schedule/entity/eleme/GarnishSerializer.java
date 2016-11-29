package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class GarnishSerializer implements JsonSerializer<Garnish>{
    @Override
    public JsonElement serialize(Garnish garnish, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("category_id",jsonSerializationContext.serialize(garnish.getCategoryid()));
        object.add("name",jsonSerializationContext.serialize(garnish.getName()));
        object.add("price",jsonSerializationContext.serialize(garnish.getPrice()));
        object.add("id",jsonSerializationContext.serialize(garnish.getId()));
        object.add("quantity",jsonSerializationContext.serialize(garnish.getQuantity()));
        object.add("tp_food_id",jsonSerializationContext.serialize(garnish.getTpfoodid()));
        return object;
    }
}

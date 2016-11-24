package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class ExtraSerializer implements JsonSerializer<Extra> {
    @Override
    public JsonElement serialize(Extra extra, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("description",jsonSerializationContext.serialize(extra.getDescription()));
        object.add("price",jsonSerializationContext.serialize(extra.getPrice()));
        object.add("name",jsonSerializationContext.serialize(extra.getName()));
        object.add("category_id",jsonSerializationContext.serialize(extra.getCategoryid()));
        object.add("id",jsonSerializationContext.serialize(extra.getId()));
        object.add("quantity",jsonSerializationContext.serialize(extra.getQuantity()));
        return object;
    }
}

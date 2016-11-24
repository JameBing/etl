package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/24.
 */
public class LabelsSerializer implements JsonSerializer<Labels> {
    @Override
    public JsonElement serialize(Labels labels, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("is_featured",jsonSerializationContext.serialize(labels.getIsfeatured()));
        object.add("is_gum",jsonSerializationContext.serialize(labels.getIsgum()));
        object.add("is_new",jsonSerializationContext.serialize(labels.getIsnew()));
        object.add("is_spicy",jsonSerializationContext.serialize(labels.getIsspicy()));
        return object;
    }
}

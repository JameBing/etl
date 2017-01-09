package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class FoodsCategoryRequestSerializer implements JsonSerializer<FoodsCategoryRequest> {
    @Override
    public JsonElement serialize(FoodsCategoryRequest foodsCategoryRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("name",jsonSerializationContext.serialize(foodsCategoryRequest.getName()));
        object.add("description",jsonSerializationContext.serialize(foodsCategoryRequest.getDescription()));
        object.add("name",jsonSerializationContext.serialize(foodsCategoryRequest.getName()));
        object.add("display_attribute",jsonSerializationContext.serialize(foodsCategoryRequest.getDisplay_attribute()));
        object.add("weight",jsonSerializationContext.serialize(foodsCategoryRequest.getWeight()));
        return object;
    }
}

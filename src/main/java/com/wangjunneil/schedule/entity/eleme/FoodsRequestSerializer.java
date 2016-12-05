package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/23.
 */
public class FoodsRequestSerializer implements JsonSerializer<FoodsRequest> {
    @Override
    public JsonElement serialize(FoodsRequest foodsRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("food_category_id",jsonSerializationContext.serialize(foodsRequest.getFood_category_id()));
        object.add("name",jsonSerializationContext.serialize(foodsRequest.getName()));
        object.add("price",jsonSerializationContext.serialize(foodsRequest.getPrice()));
        object.add("description",jsonSerializationContext.serialize(foodsRequest.getDescription()));
        object.add("image_hash",jsonSerializationContext.serialize(foodsRequest.getImage_hash()));
        object.add("labels",jsonSerializationContext.serialize(foodsRequest.getLabels()));
        object.add("specs",jsonSerializationContext.serialize(foodsRequest.getSpecs()));
        object.add("tp_food_ids",jsonSerializationContext.serialize(foodsRequest.getTp_food_ids()));
        return object;
    }
}

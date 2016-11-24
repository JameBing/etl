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
        object.add("max_stock",jsonSerializationContext.serialize(foodsRequest.getMax_stock()));
        object.add("stock",jsonSerializationContext.serialize(foodsRequest.getStock()));
        object.add("tp_food_id",jsonSerializationContext.serialize(foodsRequest.getTp_food_id()));
        object.add("image_hash",jsonSerializationContext.serialize(foodsRequest.getImage_hash()));
        object.add("is_new",jsonSerializationContext.serialize(foodsRequest.getIs_new()));
        object.add("is_featured",jsonSerializationContext.serialize(foodsRequest.getIs_featured()));
        object.add("is_gum",jsonSerializationContext.serialize(foodsRequest.getIs_gum()));
        object.add("is_spicy",jsonSerializationContext.serialize(foodsRequest.getIs_spicy()));
        object.add("packing_fee",jsonSerializationContext.serialize(foodsRequest.getPacking_fee()));
        object.add("sort_order",jsonSerializationContext.serialize(foodsRequest.getSort_order()));
        return object;
    }
}

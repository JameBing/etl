package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class OldFoodsRequestSerializer implements JsonSerializer<OldFoodsRequest> {
    @Override
    public JsonElement serialize(OldFoodsRequest oldFoodsRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("food_category_id",jsonSerializationContext.serialize(oldFoodsRequest.getFood_category_id()));
        object.add("name",jsonSerializationContext.serialize(oldFoodsRequest.getName()));
        object.add("price",jsonSerializationContext.serialize(oldFoodsRequest.getPrice()));
        object.add("description",jsonSerializationContext.serialize(oldFoodsRequest.getDescription()));
        object.add("max_stock",jsonSerializationContext.serialize(oldFoodsRequest.getMax_stock()));
        object.add("stock",jsonSerializationContext.serialize(oldFoodsRequest.getStock()));
        object.add("tp_food_id",jsonSerializationContext.serialize(oldFoodsRequest.getTp_food_id()));
        object.add("image_hash",jsonSerializationContext.serialize(oldFoodsRequest.getImage_hash()));
        object.add("is_new",jsonSerializationContext.serialize(oldFoodsRequest.getIs_new()));
        object.add("is_featured",jsonSerializationContext.serialize(oldFoodsRequest.getIs_featured()));
        object.add("is_gum",jsonSerializationContext.serialize(oldFoodsRequest.getIs_gum()));
        object.add("is_spicy",jsonSerializationContext.serialize(oldFoodsRequest.getIs_spicy()));
        object.add("packing_fee",jsonSerializationContext.serialize(oldFoodsRequest.getPacking_fee()));
        object.add("sort_order",jsonSerializationContext.serialize(oldFoodsRequest.getSort_order()));
        object.add("food_id",jsonSerializationContext.serialize(oldFoodsRequest.getFood_id()));
        object.add("tp_food_ids",jsonSerializationContext.serialize(oldFoodsRequest.getTp_food_ids()));
        object.add("food_ids",jsonSerializationContext.serialize(oldFoodsRequest.getFood_ids()));
        object.add("foods_info",jsonSerializationContext.serialize(oldFoodsRequest.getFoods_info()));
        return object;
    }
}

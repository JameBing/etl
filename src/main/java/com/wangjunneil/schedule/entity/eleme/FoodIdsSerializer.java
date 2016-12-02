package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/2.
 */
public class FoodIdsSerializer implements JsonSerializer<FoodIds>{
    @Override
    public JsonElement serialize(FoodIds foodIds, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("food_id",jsonSerializationContext.serialize(foodIds.getFood_id()));
        object.add("tp_restaurant_id",jsonSerializationContext.serialize(foodIds.getTp_restaurant_id()));
        object.add("restaurant_id",jsonSerializationContext.serialize(foodIds.getRestaurant_id()));
        return object;
    }
}

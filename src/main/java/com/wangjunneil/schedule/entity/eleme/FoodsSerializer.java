package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/23.
 */
public class FoodsSerializer implements JsonSerializer<Foods> {


    @Override
    public JsonElement serialize(Foods foods, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("description",jsonSerializationContext.serialize(foods.getDescription()));
        object.add("food_id",jsonSerializationContext.serialize(foods.getFoodid()));
        object.add("food_name",jsonSerializationContext.serialize(foods.getFoodname()));
        object.add("has_activity",jsonSerializationContext.serialize(foods.getHasactivity()));
        object.add("is_featured",jsonSerializationContext.serialize(foods.getIsfeatured()));
        object.add("is_gum",jsonSerializationContext.serialize(foods.getIsgum()));
        object.add("is_new",jsonSerializationContext.serialize(foods.getIsnew()));
        object.add("is_spicy",jsonSerializationContext.serialize(foods.getIsspicy()));
        object.add("is_valid",jsonSerializationContext.serialize(foods.getIsvalid()));
        object.add("numratings",jsonSerializationContext.serialize(foods.getNumratings()));
        object.add("price",jsonSerializationContext.serialize(foods.getPrice()));
        object.add("recent_popularity",jsonSerializationContext.serialize(foods.getRecentpopularity()));
        object.add("recent_rating",jsonSerializationContext.serialize(foods.getRecentrating()));
        object.add("restaurant_id",jsonSerializationContext.serialize(foods.getRestaurantid()));
        object.add("restaurant_name",jsonSerializationContext.serialize(foods.getRestaurantname()));
        object.add("stock",jsonSerializationContext.serialize(foods.getStock()));
        object.add("image_url",jsonSerializationContext.serialize(foods.getImageurl()));
        object.add("packing_fee",jsonSerializationContext.serialize(foods.getPackingfee()));
        object.add("sortorder",jsonSerializationContext.serialize(foods.getSortorder()));
        return object;
    }
}

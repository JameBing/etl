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
        object.add("name",jsonSerializationContext.serialize(foods.getName()));
        object.add("is_valid",jsonSerializationContext.serialize(foods.getIsvalid()));
        object.add("recent_popularity", jsonSerializationContext.serialize(foods.getRecentpopularity()));
        object.add("restaurant_id",jsonSerializationContext.serialize(foods.getRestaurantid()));
        object.add("food_category_id",jsonSerializationContext.serialize(foods.getFoodcategoryid()));
        object.add("restaurant_name",jsonSerializationContext.serialize(foods.getRestaurantname()));
        object.add("on_shelf",jsonSerializationContext.serialize(foods.getOnshelf()));
        object.add("image_url",jsonSerializationContext.serialize(foods.getImageurl()));
        object.add("labels",jsonSerializationContext.serialize(foods.getLabels()));
        object.add("specs",jsonSerializationContext.serialize(foods.getSpecs()));
        return object;
    }
}

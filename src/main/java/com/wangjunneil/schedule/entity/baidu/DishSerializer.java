package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class DishSerializer implements JsonSerializer<Dish> {

    @Override
    public JsonElement serialize(Dish dish,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("shop_id",context.serialize(dish.getShopId()));
        object.add("dish_id",context.serialize(dish.getDishId()));
        object.add("name",context.serialize(dish.getName()));
        object.add("upc",context.serialize(dish.getUpc()));
        object.add("price",context.serialize(dish.getPrice()));
        object.add("pic",context.serialize(dish.getPic()));
        object.add("min_order_num",context.serialize(dish.getMinOrderNum()));
        object.add("package_box_num",context.serialize(dish.getPackageBoxNum()));
        object.add("description",context.serialize(dish.getDescription()));
        object.add("available_times",context.serialize(dish.getAvailableTime()));
        object.add("stock",context.serialize(dish.getStock()));
        object.add("category",context.serialize(dish.getCategories()));
        object.add("norms",context.serialize(dish.getNorms()));
        object.add("attr",context.serialize(dish.getAttrs()));
        object.add("wid",context.serialize(dish.getWid()));
        object.add("left_num",context.serialize(dish.getLeftNum()));
        object.add("status",context.serialize(dish.getStatus()));
        object.add("baidu_dish_id",context.serialize(dish.getBaiduDishId()));
        object.add("page_size",context.serialize(dish.getPageSize()));
        return object;
    }
}

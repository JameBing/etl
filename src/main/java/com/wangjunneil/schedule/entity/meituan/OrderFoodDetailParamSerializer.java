package com.wangjunneil.schedule.entity.meituan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-08.
 */
public class OrderFoodDetailParamSerializer implements JsonSerializer<OrderFoodDetailParam> {
    @Override
    public JsonElement serialize(OrderFoodDetailParam orderFoodDetailParam, Type type, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("app_food_code",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("food_name",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("quantity",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("price",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("box_num",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("box_price",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("unit",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("food_discount",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("sku_id",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("food_property",context.serialize(orderFoodDetailParam.getApp_food_code()));
        jsonObject.add("spec",context.serialize(orderFoodDetailParam.getApp_food_code()));
        return jsonObject;
    }

}

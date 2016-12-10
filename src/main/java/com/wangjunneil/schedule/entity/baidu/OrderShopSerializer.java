package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderShopSerializer implements JsonSerializer<OrderShop> {
    @Override
    public JsonElement serialize(OrderShop orderShop, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("id",context.serialize(orderShop.getShopId()));
        object.add("baidu_shop_id",context.serialize(orderShop.getBaiduShopId()));
        object.add("name",context.serialize(orderShop.getName()));
        return object;
    }
}

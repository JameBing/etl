package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class ShopSerializer implements JsonSerializer<Shop>{


        @Override
        public JsonElement serialize(Shop shop, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("name", context.serialize(shop.getName()));
            object.add("id", context.serialize(shop.getShopId()));
            return object;
        }


}

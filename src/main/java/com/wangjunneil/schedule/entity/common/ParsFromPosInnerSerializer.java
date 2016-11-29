package com.wangjunneil.schedule.entity.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwnabin on 2016-11-24.
 */
public class ParsFromPosInnerSerializer implements JsonSerializer<ParsFromPosInner>{


    @Override
    public JsonElement serialize(ParsFromPosInner parsFromPosInner, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("shopId",context.serialize(parsFromPosInner.getShopId()));
        object.add("platformShopId",context.serialize(parsFromPosInner.getPlatformShopId()));
        object.add("dishId",context.serialize(parsFromPosInner.getDishId()));
        object.add("platformDishId",context.serialize(parsFromPosInner.getPlatformDishId()));
        object.add("orderId",context.serialize(parsFromPosInner.getOrderId()));
        object.add("platformOrderId",context.serialize(parsFromPosInner.getPlatformOrderId()));
        return object;
    }
}

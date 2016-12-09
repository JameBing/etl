package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/12/8.
 */
public class DeliverymanInfoSerializer implements JsonSerializer<DeliverymanInfo> {

    @Override
    public JsonElement serialize(DeliverymanInfo deliverymanInfo, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("deliveryman_name", jsonSerializationContext.serialize(deliverymanInfo.getDeliverymanname()));
        object.add("deliveryman_phone", jsonSerializationContext.serialize(deliverymanInfo.getDeliverymanphone()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-02.
 */
public class DeliveryRegionSerializer  implements JsonSerializer<DeliveryRegion>{
    @Override
    public JsonElement serialize(DeliveryRegion deliveryRegion, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("name",context.serialize(deliveryRegion.getName()));
        object.add("delivery_time",context.serialize(deliveryRegion.getDeliveryTime()));
        object.add("delivery_fee",context.serialize(deliveryRegion.getDeliveryFee()));
        object.add("min_buy_free",context.serialize(deliveryRegion.getMinBuyFree()));
        object.add("min_order_price",context.serialize(deliveryRegion.getMinOrderPrice()));
        object.add("region",context.serialize(deliveryRegion.getRegions()));
        return object;
    }
}

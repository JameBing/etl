package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderDiscountProductsSerializer implements JsonSerializer<OrderDiscountProducts> {
    @Override
    public JsonElement serialize(OrderDiscountProducts orderDiscountProducts, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("baidu_product_id",context.serialize(orderDiscountProducts.getBaiduProductId()));
        object.add("orig_price",context.serialize(orderDiscountProducts.getOrigPrice()));
        object.add("save_price",context.serialize(orderDiscountProducts.getSavePrice()));
        object.add("now_price",context.serialize(orderDiscountProducts.getNowPrice()));
        return object;
    }
}

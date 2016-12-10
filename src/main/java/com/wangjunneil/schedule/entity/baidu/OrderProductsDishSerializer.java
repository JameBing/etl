package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsDishSerializer implements JsonSerializer<OrderProductsDish> {


    @Override
    public JsonElement serialize(OrderProductsDish orderProductsDish, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("baidu_product_id",context.serialize(orderProductsDish.getBaiduProductId()));
        object.add("other_dish_id",context.serialize(orderProductsDish.getOtherDishId()));
        object.add("upc",context.serialize(orderProductsDish.getUpc()));
        object.add("type",context.serialize(orderProductsDish.getType()));
        object.add("product_name",context.serialize(orderProductsDish.getProductName()));
        object.add("product_amount",context.serialize(orderProductsDish.getProductAmount()));
        object.add("product_price",context.serialize(orderProductsDish.getProductPrice()));
        object.add("product_attr",context.serialize(orderProductsDish.getProductAttr()));
        object.add("product_features",context.serialize(orderProductsDish.getProductFeatures()));
        object.add("product_fee",context.serialize(orderProductsDish.getProductFee()));
        object.add("package_fee",context.serialize(orderProductsDish.getPackageFee()));
        object.add("package_price",context.serialize(orderProductsDish.getPackagePrice()));
        object.add("package_amount",context.serialize(orderProductsDish.getPackageAmount()));
        object.add("total_fee",context.serialize(orderProductsDish.getTotalFee()));
        return object;
    }
}

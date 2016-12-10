package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsComboGroupProductSerializer implements JsonSerializer<OrderProductsComboGroupProduct> {
    @Override
    public JsonElement serialize(OrderProductsComboGroupProduct orderProductsComboGroupProduct, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("product_id",context.serialize(orderProductsComboGroupProduct.getProductId()));
        object.add("baidu_product_id",context.serialize(orderProductsComboGroupProduct.getBaiduProductId()));
        object.add("product_name",context.serialize(orderProductsComboGroupProduct.getProductName()));
        object.add("product_type",context.serialize(orderProductsComboGroupProduct.getProductType()));
        object.add("product_amount",context.serialize(orderProductsComboGroupProduct.getProductAmount()));
        object.add("product_fee",context.serialize(orderProductsComboGroupProduct.getProductFee()));
        object.add("product_price",context.serialize(orderProductsComboGroupProduct.getProductPrice()));
        return object;
    }
}

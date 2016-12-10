package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsComboSerializer implements JsonSerializer<OrderProductsCombo> {
    @Override
    public JsonElement serialize(OrderProductsCombo orderProductsCombo, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("baidu_product_id",context.serialize(orderProductsCombo.getBaiduProductId()));
        object.add("product_id",context.serialize(orderProductsCombo.getProductId()));
        object.add("type",context.serialize(orderProductsCombo.getType()));
        object.add("product_name",context.serialize(orderProductsCombo.getProductName()));
        object.add("product_amount",context.serialize(orderProductsCombo.getProductAmount()));
        object.add("product_price",context.serialize(orderProductsCombo.getProductPrice()));
        object.add("product_fee",context.serialize(orderProductsCombo.getProductFee()));
        object.add("package_fee",context.serialize(orderProductsCombo.getPackageFee()));
        object.add("package_price",context.serialize(orderProductsCombo.getPackagePrice()));
        object.add("package_amount",context.serialize(orderProductsCombo.getProductAmount()));
        object.add("total_fee",context.serialize(orderProductsCombo.getTotalFee()));
        object.add("is_fixed_price",context.serialize(orderProductsCombo.getIsFixedPrice()));
        object.add("group",context.serialize(orderProductsCombo.getGroups()));
        return object;
    }
}

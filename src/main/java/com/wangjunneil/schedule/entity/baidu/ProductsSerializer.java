package com.wangjunneil.schedule.entity.baidu;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class ProductsSerializer implements JsonSerializer<Products> {

    @Override
    public JsonElement serialize(Products products,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("product_id",context.serialize(products.getProductId()));
        object.add("product_name",context.serialize(products.getProductName()));
        object.add("product_amount",context.serialize(products.getProductAmount()));
        object.add("product_price",context.serialize(products.getProductPrice()));
        object.add("product_fee",context.serialize(products.getProductFee()));
        object.add("package_price",context.serialize(products.getPackagePrice()));
        object.add("package_amount",context.serialize(products.getPackageAmount()));
        object.add("package_fee",context.serialize(products.getPackageFee()));
        object.add("total_fee",context.serialize(products.getTotalFee()));
        object.add("upc",context.serialize(products.getUpc()));
        return object;
    }
}

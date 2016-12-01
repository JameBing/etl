package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class DataSerializer implements JsonSerializer<Data> {

    @Override
    public JsonElement serialize(Data data, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("source",context.serialize(data.getSource()));
        object.add("shop",context.serialize(data.getShop()));
        object.add("order",context.serialize(data.getOrder()));
        object.add("user",context.serialize(data.getUser()));
        object.add("supplier",context.serialize(data.getSupplier()));
        object.add("products",context.serialize(data.getProducts()));
        object.add("discount",context.serialize(data.getDiscount()));

        return object;
    }
}

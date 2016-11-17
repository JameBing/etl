package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-17.
 */
public class BodySerializer implements JsonSerializer<Body> {

    @Override
    public JsonElement serialize(Body body,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("shop",context.serialize(body.getShop()));
        object.add("order",context.serialize(body.getOrder()));
        object.add("user",context.serialize(body.getUser()));
        object.add("products",context.serialize(body.getProducts()));
        object.add("discount",context.serialize(body.getDiscount()));
        return object;
    }
}

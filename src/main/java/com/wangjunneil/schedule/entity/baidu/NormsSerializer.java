package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class NormsSerializer implements JsonSerializer<Norms> {

    @Override
    public JsonElement serialize(Norms norms,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("dish_norms_id",context.serialize(norms.getDishNormsId()));
        object.add("dish_id",context.serialize(norms.getDishId()));
        object.add("wid",context.serialize(norms.getWid()));
        object.add("name",context.serialize(norms.getName()));
        object.add("name_value",context.serialize(norms.getNameValue()));
        object.add("current_price",context.serialize(norms.getCurrentPrice()));
        object.add("discount",context.serialize(norms.getDisCount()));
        object.add("store_num",context.serialize(norms.getStoreNum()));
        object.add("left_num",context.serialize(norms.getLeftNum()));
        object.add("status",context.serialize(norms.getStatus()));
        object.add("create_time",context.serialize(norms.getCreateTime()));
        object.add("supplier_dish_norms_id",context.serialize(norms.getSupplierDishNormsId()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/24.
 */
public class SpecsSerializer implements JsonSerializer<Specs> {
    @Override
    public JsonElement serialize(Specs specs, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("specid",jsonSerializationContext.serialize(specs.getSpecid()));
        object.add("name",jsonSerializationContext.serialize(specs.getName()));
        object.add("price",jsonSerializationContext.serialize(specs.getPrice()));
        object.add("stock",jsonSerializationContext.serialize(specs.getStock()));
        object.add("maxstock", jsonSerializationContext.serialize(specs.getMaxstock()));
        object.add("packingfee",jsonSerializationContext.serialize(specs.getPackingfee()));
        object.add("tpfoodid",jsonSerializationContext.serialize(specs.getTpfoodid()));
        object.add("onshelf",jsonSerializationContext.serialize(specs.getOnshelf()));
        return object;
    }
}

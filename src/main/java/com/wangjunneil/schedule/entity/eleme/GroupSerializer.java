package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/22.
 */
public class GroupSerializer  implements JsonSerializer<Group> {
    @Override
    public JsonElement serialize(Group group, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("category_id",jsonSerializationContext.serialize(group.getCategoryid()));
        object.add("name",jsonSerializationContext.serialize(group.getName()));
        object.add("price",jsonSerializationContext.serialize(group.getPrice()));
        object.add("garnish",jsonSerializationContext.serialize(group.getGarnish()));
        object.add("id",jsonSerializationContext.serialize(group.getId()));
        object.add("quantity",jsonSerializationContext.serialize(group.getQuantity()));
        object.add("tp_food_id",jsonSerializationContext.serialize(group.getTpfoodid()));
        object.add("specs",jsonSerializationContext.serialize(group.getSpecs()));
        return object;
    }
}

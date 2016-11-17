package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class UserSerializer implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("name",context.serialize(user.getName()));
        object.add("phone",context.serialize(user.getPhone()));
        object.add("gender",context.serialize(user.getGender()));
        object.add("address",context.serialize(user.getAddress()));
        object.add("coord",context.serialize(user.getCoord()));
        object.add("coord_amap",context.serialize(user.getCoordAmap()));

        return object;
    }
}

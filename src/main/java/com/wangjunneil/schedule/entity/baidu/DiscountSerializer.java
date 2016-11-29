package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-17.
 */
public class DiscountSerializer implements JsonSerializer<Discount> {

    @Override
    public JsonElement serialize(Discount discount,Type type,JsonSerializationContext context){
        JsonObject object = new JsonObject();
        object.add("type",context.serialize(discount.getType()));
        object.add("fee",context.serialize(discount.getFee()));
        object.add("activity_id",context.serialize(discount.getActivityId()));
        object.add("rule_id",context.serialize(discount.getRuleId()));
        object.add("baidu_rate",context.serialize(discount.getBaiduRate()));
        object.add("shop_rate",context.serialize(discount.getShopRate()));
        object.add("agent_rate",context.serialize(discount.getAgentRate()));
        object.add("logistics_rate",context.serialize(discount.getLogisticsRate()));
        object.add("desc",context.serialize(discount.getDesc()));
        return  object;
    }
}

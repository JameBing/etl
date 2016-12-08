package com.wangjunneil.schedule.entity.meituan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-08.
 */
public class OrderExtraParamSerializer  implements JsonSerializer<OrderExtraParam>{
    @Override
    public JsonElement serialize(OrderExtraParam orderExtraParam, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("act_detail_id",context.serialize(orderExtraParam.getAct_detail_id()));
        object.add("reduce_fee",context.serialize(orderExtraParam.getReduce_fee()));
        object.add("remark",context.serialize(orderExtraParam.getRemark()));
        object.add("type",context.serialize(orderExtraParam.getType()));
        object.add("rider_fee",context.serialize(orderExtraParam.getRider_fee()));
        object.add("mt_charge",context.serialize(orderExtraParam.getMt_charge()));
        object.add("poi_charge",context.serialize(orderExtraParam.getPoi_charge()));
        return object;
    }
}

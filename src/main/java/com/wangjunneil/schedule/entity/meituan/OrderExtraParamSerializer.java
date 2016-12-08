package com.wangjunneil.schedule.entity.meituan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.sankuai.meituan.waimai.opensdk.vo.OrderExtraParam;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-08.
 */
public class OrderExtraParamSerializer  implements JsonSerializer<OrderExtraParam>{
    @Override
    public JsonElement serialize(OrderExtraParam orderExtraParam, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("act_detail_id",context.serialize(orderExtraParam.getAct_detail_id()));
     
        return object;
    }
}

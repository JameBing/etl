package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/28.
 */
public class RestaurantRequestSerializer implements JsonSerializer<RestaurantRequest> {
    @Override
    public JsonElement serialize(RestaurantRequest restaurantRequest, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        object.add("restaurant_id",jsonSerializationContext.serialize(restaurantRequest.getRestaurant_id()));
        object.add("is_open",jsonSerializationContext.serialize(restaurantRequest.getIs_open()));
        object.add("address_text",jsonSerializationContext.serialize(restaurantRequest.getAddress_text()));
        object.add("geo",jsonSerializationContext.serialize(restaurantRequest.getGeo()));
        object.add("agent_fee",jsonSerializationContext.serialize(restaurantRequest.getAgent_fee()));
        object.add("close_description",jsonSerializationContext.serialize(restaurantRequest.getClose_description()));
        object.add("deliver_description",jsonSerializationContext.serialize(restaurantRequest.getDeliver_description()));
        object.add("description",jsonSerializationContext.serialize(restaurantRequest.getDescription()));
        object.add("name",jsonSerializationContext.serialize(restaurantRequest.getName()));
        object.add("is_bookable",jsonSerializationContext.serialize(restaurantRequest.getIs_bookable()));
        object.add("open_time",jsonSerializationContext.serialize(restaurantRequest.getOpen_time()));
        object.add("phone",jsonSerializationContext.serialize(restaurantRequest.getPhone()));
        object.add("promotion_info",jsonSerializationContext.serialize(restaurantRequest.getPromotion_info()));
        object.add("logo_image_hash",jsonSerializationContext.serialize(restaurantRequest.getLogo_image_hash()));
        object.add("invoice",jsonSerializationContext.serialize(restaurantRequest.getInvoice()));
        object.add("invoice_min_amount",jsonSerializationContext.serialize(restaurantRequest.getInvoice_min_amount()));
        object.add("no_agent_fee_total",jsonSerializationContext.serialize(restaurantRequest.getNo_agent_fee_total()));
        object.add("is_valid",jsonSerializationContext.serialize(restaurantRequest.getIs_valid()));
        object.add("packing_fee",jsonSerializationContext.serialize(restaurantRequest.getPacking_fee()));
        object.add("tp_id",jsonSerializationContext.serialize(restaurantRequest.getTp_id()));
        object.add("tp_restaurant_id",jsonSerializationContext.serialize(restaurantRequest.getTp_restaurant_id()));
        object.add("restaurant_ids",jsonSerializationContext.serialize(restaurantRequest.getRestaurant_ids()));
        return object;
    }
}

package com.wangjunneil.schedule.entity.eleme;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016/11/25.
 */
public class RestaurantSerializer implements JsonSerializer<Restaurant> {
    @Override
    public JsonElement serialize(Restaurant restaurant, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();
        //注意：此处必须按照字母顺序依次加入元
        object.add("restaurant_name", jsonSerializationContext.serialize(restaurant.getRestaurantname()));
        object.add("city_id", jsonSerializationContext.serialize(restaurant.getCityid()));
        object.add("restaurant_id", jsonSerializationContext.serialize(restaurant.getRestaurantid()));
        object.add("packing_fee", jsonSerializationContext.serialize(restaurant.getPackingfee()));
        object.add("address_text", jsonSerializationContext.serialize(restaurant.getAddresstext()));
        object.add("is_time_ensure", jsonSerializationContext.serialize(restaurant.getIstimeensure()));

        object.add("is_premium", jsonSerializationContext.serialize(restaurant.getIspremium()));
        object.add("is_open", jsonSerializationContext.serialize(restaurant.getIsopen()));
        object.add("invoice", jsonSerializationContext.serialize(restaurant.getInvoice()));
        object.add("flavors", jsonSerializationContext.serialize(restaurant.getFlavors()));
        object.add("is_phone_hidden", jsonSerializationContext.serialize(restaurant.getIsphonehidden()));

        object.add("payment_method", jsonSerializationContext.serialize(restaurant.getPaymentmethod()));
        object.add("is_valid", jsonSerializationContext.serialize(restaurant.getIsvalid()));
        object.add("close_description", jsonSerializationContext.serialize(restaurant.getClosedescription()));
        object.add("no_agent_fee_total", jsonSerializationContext.serialize(restaurant.getNoagentfeetotal()));
        object.add("latitude", jsonSerializationContext.serialize(restaurant.getLatitude()));
        object.add("serving_time", jsonSerializationContext.serialize(restaurant.getServingtime()));

        object.add("city_code", jsonSerializationContext.serialize(restaurant.getCitycode()));
        object.add("online_payment", jsonSerializationContext.serialize(restaurant.getOnlinepayment()));
        object.add("description", jsonSerializationContext.serialize(restaurant.getDescription()));
        object.add("open_time_bitmap", jsonSerializationContext.serialize(restaurant.getOpentimebitmap()));
        object.add("inner_id", jsonSerializationContext.serialize(restaurant.getInnerid()));
        object.add("promotion_info", jsonSerializationContext.serialize(restaurant.getPromotioninfo()));

        object.add("invoice_min_amount", jsonSerializationContext.serialize(restaurant.getInvoiceminamount()));
        object.add("recent_food_popularity", jsonSerializationContext.serialize(restaurant.getRecentfoodpopularity()));
        object.add("time_ensure_full_description", jsonSerializationContext.serialize(restaurant.getTimeensurefulldescription()));
        object.add("deliver_spent", jsonSerializationContext.serialize(restaurant.getDeliverspent()));
        object.add("num_ratings", jsonSerializationContext.serialize(restaurant.getNumratings()));

        object.add("deliver_times", jsonSerializationContext.serialize(restaurant.getDelivertimes()));
        object.add("mobile", jsonSerializationContext.serialize(restaurant.getMobile()));
        object.add("order_mode", jsonSerializationContext.serialize(restaurant.getOrdermode()));
        object.add("restaurant_url", jsonSerializationContext.serialize(restaurant.getRestauranturl()));
        object.add("longitude", jsonSerializationContext.serialize(restaurant.getLongitude()));
        object.add("book_time_bitmap", jsonSerializationContext.serialize(restaurant.getBooktimebitmap()));
        object.add("image_url", jsonSerializationContext.serialize(restaurant.getImageurl()));
        object.add("support_online", jsonSerializationContext.serialize(restaurant.getSupportonline()));

        object.add("is_bookable", jsonSerializationContext.serialize(restaurant.getIsbookable()));
        object.add("phone_list", jsonSerializationContext.serialize(restaurant.getPhonelist()));
        object.add("busy_level", jsonSerializationContext.serialize(restaurant.getBusylevel()));
        object.add("agent_fee", jsonSerializationContext.serialize(restaurant.getAgentfee()));
        return object;
    }
}

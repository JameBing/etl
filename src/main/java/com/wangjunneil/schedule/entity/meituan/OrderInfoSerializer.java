package com.wangjunneil.schedule.entity.meituan;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by admin on 2016-12-08.
 */
public class OrderInfoSerializer  implements JsonSerializer<OrderInfo>{
    @Override
    public JsonElement serialize(OrderInfo orderInfo, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("order_id",context.serialize(orderInfo.getOrderid()));
        object.add("wm_order_id_view",context.serialize(orderInfo.getWmorderidview()));
        object.add("app_poi_code",context.serialize(orderInfo.getApppoicode()));
        object.add("wm_poi_name",context.serialize(orderInfo.getWmpoiname()));
        object.add("wm_poi_address",context.serialize(orderInfo.getWmpoiaddress()));
        object.add("wm_poi_phone",context.serialize(orderInfo.getWmpoiphone()));
        object.add("recipient_address",context.serialize(orderInfo.getRecipientaddress()));
        object.add("recipient_name",context.serialize(orderInfo.getRecipientname()));
        object.add("recipient_phone",context.serialize(orderInfo.getRecipientphone()));
        object.add("shipping_fee",context.serialize(orderInfo.getShippingfee()));
        object.add("total",context.serialize(orderInfo.getTotal()));
        object.add("original_price",context.serialize(orderInfo.getOriginalprice()));
        object.add("caution",context.serialize(orderInfo.getCaution()));
        object.add("shipper_phone",context.serialize(orderInfo.getShipperphone()));
        object.add("status",context.serialize(orderInfo.getStatus()));
        object.add("city_id",context.serialize(orderInfo.getCityid()));
        object.add("has_invoiced",context.serialize(orderInfo.getHasinvoiced()));
        object.add("invoice_title",context.serialize(orderInfo.getInvoicetitle()));
        object.add("ctime",context.serialize(orderInfo.getCtime()));
        object.add("utime",context.serialize(orderInfo.getUtime()));
        object.add("delivery_time",context.serialize(orderInfo.getDeliverytime()));
        object.add("is_third_shipping",context.serialize(orderInfo.getIsthirdshipping()));
        object.add("pay_type",context.serialize(orderInfo.getPaytype()));
        object.add("latitude",context.serialize(orderInfo.getLatitude()));
        object.add("longitude",context.serialize(orderInfo.getLongitude()));
        object.add("day_seq",context.serialize(orderInfo.getDayseq()));
        object.add("is_favorites",context.serialize(orderInfo.getIsfavorites()));
        object.add("is_poi_first_order",context.serialize(orderInfo.getIspoifirstorder()));
        object.add("dinners_number",context.serialize(orderInfo.getDinnersnumber()));
        object.add("logistics_code",context.serialize(orderInfo.getLogisticscode()));
        object.add("poi_receive_detail",context.serialize(orderInfo.getPoireceivedetail()));
        object.add("detail",context.serialize(orderInfo.getDetail()));
        object.add("extras",context.serialize(orderInfo.getExtras()));

        return object;
    }
}

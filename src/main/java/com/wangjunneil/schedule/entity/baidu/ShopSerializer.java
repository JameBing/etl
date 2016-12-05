package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class ShopSerializer implements JsonSerializer<Shop>{


        @Override
        public JsonElement serialize(Shop shop, Type type, JsonSerializationContext context) {
            JsonObject object = new JsonObject();
            object.add("name", context.serialize(shop.getName()));
            object.add("shop_id", context.serialize(shop.getShopId()));
            object.add("baidu_shop_id",context.serialize(shop.getBaiduShopId()));
            object.add("supplier_id",context.serialize(shop.getSupplierId()));
            object.add("shop_logo",context.serialize(shop.getShopLogo()));
            object.add("province",context.serialize(shop.getProvince()));
            object.add("city",context.serialize(shop.getCity()));
            object.add("county",context.serialize(shop.getCounty()));
            object.add("address",context.serialize(shop.getAddress()));
            object.add("categorys",context.serialize(shop.getCategorys()));
            object.add("phone",context.serialize(shop.getPhone()));
            object.add("service_phone",context.serialize(shop.getServicePhone()));
            object.add("longitude",context.serialize(shop.getLongitude()));
            object.add("latitude",context.serialize(shop.getLatitude()));
            object.add("coord_type",context.serialize(shop.getCoordType()));
            object.add("delivery_region",context.serialize(shop.getDeliveryRegions()));
            object.add("business_time",context.serialize(shop.getBusinessTimes()));
            object.add("book_ahead_time",context.serialize(shop.getBookAheadTime()));
            object.add("invoice_support",context.serialize(shop.getInvoiceSupport()));
            object.add("package_box_price",context.serialize(shop.getPackageBoxPrice()));
            object.add("shop_code",context.serialize(shop.getShopCode()));
            object.add("business_form_id",context.serialize(shop.getBusinessFormId()));
            return object;
        }


}


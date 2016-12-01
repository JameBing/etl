package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class SupplierSerializer  implements JsonSerializer<Supplier>{
    @Override
    public JsonElement serialize(Supplier supplier, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("supplier_id",context.serialize(supplier.getSupplierId()));
        object.add("name",context.serialize(supplier.getName()));
        object.add("brand",context.serialize(supplier.getBrand()));
        object.add("status",context.serialize(supplier.getStatus()));
        object.add("category_name",context.serialize(supplier.getCategoryName()));
        object.add("extend_flag",context.serialize(supplier.getExtendFlag()));
        object.add("business_form",context.serialize(supplier.getBusinessForm()));
        return null;
    }
}

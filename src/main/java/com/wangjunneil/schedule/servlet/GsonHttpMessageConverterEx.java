package com.wangjunneil.schedule.servlet;

import com.google.gson.*;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.lang.reflect.Type;

/**
 * Created by yangwanbin on 2016-12-03.
 * Spring MVC Json converter tools
 */
public class GsonHttpMessageConverterEx  extends GsonHttpMessageConverter{

        public GsonHttpMessageConverterEx(){
              //change json converter
            super.setGson(
                new GsonBuilder().registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                        if (aDouble == aDouble.longValue())
                            return new JsonPrimitive(aDouble.longValue());
                            return new JsonPrimitive(aDouble);
                    }
                })
                    .serializeNulls() //序列化null值
                    .setDateFormat("yyyy-MM-dd HH:mm:ss") //设置日期转换
                    .create()
            );
        }
}

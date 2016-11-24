package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.ElemaException;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeFacadeService {
    @Autowired
    private EleMeApiService eleMeApiService;
    @Autowired
    private EleMeInnerService eleMeInnerService;

    /**
     * 获取订单详情
     * @param params
     * @return
     * @throws ElemaException
     */
    public String orderDetailPost(OrderRequest params) throws ElemaException {
        String requeststr = eleMeApiService.getOrderDetail(params);
        requeststr = "{\n" +
         "   \"code\":200,\n" +
         "   \"data\": {\n"+
         "       \"address\":\"Smart Garden 5A\",\n" +
         "       \"consignee\":\"张三\",\n" +
         "       \"created_at\":\"2013-09-24 12:31:24\",\n" +
         "       \"active_at\":\"2013-09-24 12:40:21\",\n" +
         "       \"deliver_fee\":20,\n" +
         "       \"deliver_time\":\"1970-01-01 08:00:00\",\n" +
         "       \"description\":\"\",\n" +
         "       \"detail\":{\n" +
         "       \"group\":[\n" +
         "          [\n" +
         "              {\n" +
         "                  \"category_id\":1,\n" +
         "                  \"name\":\"狗不理\",\n" +
         "                  \"price\":100,\n" +
         "                  \"garnish\":[],\n" +
         "                  \"id\":1541311,\n" +
         "                  \"quantity\":1,\n" +
         "                  \"tp_food_id\":\"1312312\",\n" +
         "                  \"specs\":[\"辣\",\"大份\"]\n" +
         "              },\n" +
         "              {\n" +
         "                  \"category_id\":1,\n" +
         "                  \"name\":\"牛肉盖浇饭\",\n" +
         "                  \"price\":100,\n" +
         "                  \"garnish\":[\n" +
         "                        {\n" +
         "                            \"category_id\":1,\n" +
         "                            \"name\":\"荷包蛋\",\n" +
         "                            \"price\":2,\n" +
         "                            \"id\":1541313,\n" +
         "                            \"quantity\":1,\n" +
         "                            \"tp_food_id\":\"1312313\"\n" +
         "                         }\n" +
         "                  ],\n" +
         "                  \"id\":1541312,\n" +
         "                  \"quantity\":1,\n"+
         "                  \"tp_food_id\":\"1312314\",\n" +
         "                  \"specs\":[]\n" +
         "              }\n" +
         "          ]\n" +
         "       ],\n" +
         "       \"extra\": [\n" +
         "          {\n" +
         "              \"description\":\"\",\n" +
         "              \"price\":20,\n" +
         "              \"name\":\"配送费\",\n" +
         "              \"category_id\":2,\n" +
         "              \"id\":-10,\n" +
         "              \"quantity\":1\n" +
         "          }\n" +
         "       ],\n" +
         "       \"abandoned_extra\":null\n" +
         "   },\n" +
         "   \"invoice\":\"\",\n" +
         "   \"is_book\":0,\n" +
         "   \"is_online_paid\":0,\n" +
         "   \"order_id\":\"12637645858619059\",\n" +
         "   \"phone_list\":[\n" +
         "      \"15216709049\"\n" +
         "   ],\n" +
         "   \"tp_restaurant_id\":\"1231231\",\n" +
         "   \"restaurant_id\":72823931,\n" +
         "   \"inner_id\":37932,\n" +
         "   \"restaurant_name\":\"饿了么体验店\",\n"+
         "   \"restaurant_number\":3,\n" +
         "   \"status_code\":2,\n" +
         "   \"refund_code\":6,\n" +
         "   \"total_price\":120,\n" +
         "   \"original_price\":120,\n" +
         "   \"user_id\":481769,\n" +
         "   \"user_name\":\"tester\",\n" +
         "   \"delivery_geo\":\"31.2538,121.4185\"\n" +
         "   },\n" +
         "   \"message\":\"ok\",\n" +
         "   \"request_id\":\"115bc4a55e3c4e9eaf3f1a111a3e7271\"\n" +
         "}";
        requeststr = requeststr.replace("/","\\/");
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Result.class, new ResultSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(Detail.class, new DetailSerializer())
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
            Result result = gson.fromJson(requeststr, Result.class);
            Order order = gson.fromJson(gson.toJson(result.getData()).toString(), Order.class);
        }catch (Exception ex) {
            throw new ElemaException("",ex);
        }
        return "";
    }

    /**
     * 获取食物详情
     * @param params
     * @return
     * @throws ElemaException
     */
    private String foodsDetailPost(String params) throws ElemaException {
        String requeststr = "{\n" +
             "   \"code\": 200,\n" +
             "   \"data\": {\n" +
             "   \"foods\": [\n" +
             "   {\n" +
             "       \"description\": \"\",\n" +
             "       \"food_id\": 1000,\n" +
             "       \"food_name\": \"猪肉饭\",\n" +
             "       \"has_activity\": 0,\n" +
             "       \"is_featured\": 0,\n" +
             "       \"is_gum\": 0,\n" +
             "       \"is_new\": 0,\n" +
             "       \"is_spicy\": 0,\n" +
             "       \"is_valid\": 1,\n" +
             "       \"num_ratings\": [\n" +
             "       0,\n" +
             "           0,\n" +
             "           0,\n" +
             "           0,\n" +
             "           2\n" +
             "       ],\n" +
             "       \"price\": 19,\n" +
             "       \"recent_popularity\": 2,\n" +
             "       \"recent_rating\": 5,\n" +
             "       \"restaurant_id\": 11,\n" +
             "       \"restaurant_name\": \"麦当劳\",\n" +
             "       \"stock\": 99999,\n" +
             "       \"image_url\": \"http://www.ele.me/demo.jpg\",\n" +
             "       \"packing_fee\": 1.5,\n" +
             "       \"sort_order\": 100\n" +
             "   },\n" +
             "   {\n" +
             "       \"description\": \" \",\n" +
             "       \"food_id\": 2000,\n" +
             "       \"food_name\": \"猪肉饭\",\n" +
             "       \"has_activity\": 1,\n" +
             "       \"is_featured\": 0,\n" +
             "       \"is_gum\": 0,\n" +
             "       \"is_new\": 1,\n" +
             "       \"is_spicy\": 0,\n" +
             "       \"is_valid\": 1,\n" +
             "       \"num_ratings\": [\n" +
             "       0,\n" +
             "           1,\n" +
             "           0,\n" +
             "           0,\n" +
             "           2\n" +
             "       ],\n" +
             "       \"price\": 20,\n" +
             "       \"recent_popularity\": 3,\n" +
             "       \"recent_rating\": 6,\n" +
             "       \"restaurant_id\": 12,\n" +
             "       \"restaurant_name\": \"肯德基\",\n" +
             "       \"stock\": 99999,\n" +
             "       \"image_url\": \"http://www.ele.me/aaa.jpg\",\n" +
             "       \"packing_fee\": 2,\n" +
             "       \"sort_order\": 200\n" +
             "   }\n" +
             "   ],\n" +
             "   \"get_failed\": {\n" +
             "       \"18755176\": \"invalid food\"\n" +
             "   }\n" +
             "       },\n" +
             "           \"message\": \"ok\",\n" +
             "           \"request_id\": \"335bc4a55e3c4e9eaf3f1a111a3e7561\"\n" +
             "       }";
        requeststr.replace("/","\\/");
        try {
            Gson gson = new GsonBuilder().registerTypeAdapter(Result.class, new ResultSerializer())
                .registerTypeAdapter(FoodsDetail.class, new FoodsDetailSerializer())
                .registerTypeAdapter(Foods.class, new FoodsSerializer())
                .registerTypeAdapter(Failed.class, new FailedSerializer())
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
            Result result = gson.fromJson(requeststr, Result.class);
            FoodsDetail foodsDetail = gson.fromJson(gson.toJson(result.getData()).toString(), FoodsDetail.class);
        }catch (Exception ex) {
            throw new ElemaException("",ex);
        }
        return "";
    }

//    public static void main(String[] arg) throws ElemaException {
//        foodsDetailPost("");
//    }
}
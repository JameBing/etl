package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.wangjunneil.schedule.common.BaiDuException;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import com.wangjunneil.schedule.service.baidu.BaiDuInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class BaiDuFacadeService {

    @Autowired
    private BaiDuApiService baiDuApiService;

        @Autowired
        private BaiDuInnerService baiDuInnerService;

        //接收百度外卖推送过来的订单
    public String orderPost(String params) throws BaiDuException{
        String requestStr = "{\n" +
            "    \"body\": {\n" +
            "        \"shop\": {\n" +
            "            \"id\": \"2520\",\n" +
            "            \"name\": \"百度外卖（测试店）\"\n" +
            "        },\n" +
            "        \"order\": {\n" +
            "            \"order_id\": \"14347012309352\",\n" +
            "            \"send_immediately\": 1,\n" +
            "            \"send_time\": \"1\",\n" +
            "            \"send_fee\": 500,\n" +
            "            \"package_fee\": 200,\n" +
            "            \"discount_fee\": 0,\n" +
            "            \"total_fee\": 3700,\n" +
            "            \"shop_fee\": 3200,\n" +
            "            \"user_fee\": 3700,\n" +
            "            \"pay_type\": 1,\n" +
            "            \"pay_status\": 1,\n" +
            "            \"need_invoice\": 2,\n" +
            "            \"invoice_title\": \"\",\n" +
            "            \"remark\": \"请提供餐具\",\n" +
            "            \"delivery_party\": 1,\n" +
            "            \"create_time\": \"1434701230\"\n" +
            "        },\n" +
            "        \"user\": {\n" +
            "            \"name\": \"测试订单请勿操作\",\n" +
            "            \"phone\": \"18912345678\",\n" +
            "            \"gender\": 1,\n" +
            "            \"address\": \"北京海淀区奎科科技大厦 测试\",\n" +
            "            \"coord\": {\n" +
            "                \"longitude\": 116.143145,\n" +
            "                \"latitude\": 39.741426\n" +
            "            }\n" +
            "        },\n" +
            "        \"products\": [\n" +
            "            {\n" +
            "                \"product_id\": \"12277320\",\n" +
            "                \"product_name\": \"酱肉包（/份）\",\n" +
            "                \"product_price\": 1200,\n" +
            "                \"product_amount\": 1,\n" +
            "                \"product_fee\": 1200,\n" +
            "                \"package_price\": 100,\n" +
            "                \"package_amount\": 1,\n" +
            "                \"package_fee\": 100,\n" +
            "                \"total_fee\": 1300,\n" +
            "                \"upc\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"discount\": []\n" +
            "    },\n" +
            "    \"cmd\": \"order.create\",\n" +
            "    \"sign\": \"E362B8AACE4F7A77047A885C0C0D230D\",\n" +
            "    \"source\": \"65400\",\n" +
            "    \"ticket\": \"909C3B92-8CDD-AF2C-C887-5B660233E2B2\",\n" +
            "    \"timestamp\": 1434701234,\n" +
            "    \"version\": \"2.0\"\n" +
            "}";
        String orderStr = "{\n" +
            "            \"order_id\": \"14347012309352\",\n" +
            "            \"send_immediately\": 1,\n" +
            "            \"send_time\": \"1\",\n" +
            "            \"send_fee\": 500,\n" +
            "            \"package_fee\": 200,\n" +
            "            \"discount_fee\": 0,\n" +
            "            \"total_fee\": 3700,\n" +
            "            \"shop_fee\": 3200,\n" +
            "            \"user_fee\": 3700,\n" +
            "            \"pay_type\": 1,\n" +
            "            \"pay_status\": 1,\n" +
            "            \"need_invoice\": 2,\n" +
            "            \"invoice_title\": \"\",\n" +
            "            \"remark\": \"请提供餐具\",\n" +
            "            \"delivery_party\": 1,\n" +
            "            \"create_time\": \"1434701230\"\n" +
            "        }";
        String userStr =" {\n" +
            "            \"name\": \"测试订单请勿操作\",\n" +
            "            \"phone\": \"18912345678\",\n" +
            "            \"gender\": 1,\n" +
            "            \"address\": \"北京海淀区奎科科技大厦 测试\",\n" +
            "            \"coord\": {\n" +
            "                \"longitude\": 116.143145,\n" +
            "                \"latitude\": 39.741426\n" +
            "            }\n" +
            "        }";
        requestStr.replace("/", "\\/");
        try {

            //requestStr = SysParams.chinaToUnicode(requestStr);
            Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                .registerTypeAdapter(Body.class, new BodySerializer())
                .registerTypeAdapter(Shop.class, new ShopSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(User.class, new UserSerializer())
                .registerTypeAdapter(Coord.class, new CoordSerializer())
                .registerTypeAdapter(CoordAmap.class, new CoordAmapSerializer())
                .registerTypeAdapter(Products.class, new ProductsSerializer())
                .registerTypeAdapter(Discount.class, new DiscountSerializer())
                .serializeNulls()
                .disableHtmlEscaping()
                .create();

            SysParams sysParams = gson.fromJson(requestStr, SysParams.class);
            Body body = gson.fromJson(gson.toJson(sysParams.getBody()).toString(), Body.class);
            //将订单信息插入Mongodb
        }catch (Exception ex){
            throw new BaiDuException("",ex);  //异常这块待增强，考虑结构化的异常信息，引入日志ID号，用于返回给Pos方
        }
        return  null;
    }

}

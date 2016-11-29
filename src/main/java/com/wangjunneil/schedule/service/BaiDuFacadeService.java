package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import com.wangjunneil.schedule.service.baidu.BaiDuInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class BaiDuFacadeService {

    @Autowired
    private BaiDuApiService baiDuApiService;

    @Autowired
    private BaiDuInnerService baiDuInnerService;

    private Gson gson;

    private Gson getGson(){

        if (gson == null){
            gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                                    .registerTypeAdapter(Body.class, new BodySerializer())
                                    .registerTypeAdapter(Shop.class, new ShopSerializer())
                                    .registerTypeAdapter(Order.class, new OrderSerializer())
                                    .registerTypeAdapter(User.class, new UserSerializer())
                                    .registerTypeAdapter(Coord.class, new CoordSerializer())
                                    .registerTypeAdapter(CoordAmap.class, new CoordAmapSerializer())
                                    .registerTypeAdapter(Products.class, new ProductsSerializer())
                                    .registerTypeAdapter(Discount.class, new DiscountSerializer())
                                    .registerTypeAdapter(Result.class,new ResultSerializer())
                                    .serializeNulls()
                                    .disableHtmlEscaping()
                .create();
        }
        return gson;
    }

    //门店开业
    public String startBusiness(String baiduShopId,String shopId){
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            shop.setBaiduShopId(baiduShopId);
            result =  baiDuApiService.startBusiness(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(shopId);
        }catch (Exception ex) {
            rtn.setDynamic(shopId);
            rtn.setCode(-999);
            rtn.setDesc("发生异常");
            rtn.setLogId("");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //门店歇业
    public String endBusiness(String baiduShopId,String shopId){
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            shop.setBaiduShopId(baiduShopId);
            result =  baiDuApiService.endBusiness(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(shopId);
        }catch (Exception ex) {
            rtn.setDynamic(shopId);
            rtn.setCode(-999);
            rtn.setDesc("发生异常");
            rtn.setLogId("");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //菜品上架
    public String online(String baiduShopId,String shopId,String baiduDishId,String dishId){
        String result = null;
            Rtn rtn = new Rtn();
            rtn.setDynamic(StringUtil.isEmpty(dishId)?baiduDishId:dishId);
            Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
            try {
                Dish dish = new Dish();
                dish.setShopId(shopId);
                dish.setBaiduShopId(baiduShopId);
                dish.setDishId(dishId);
                dish.setBaiduDishId(baiduDishId);
                result = baiDuApiService.online(dish);
                rtn = gson1.fromJson(result,Rtn.class);
        }catch (Exception ex){
                rtn.setCode(-999);
                rtn.setDesc("发生异常");
                rtn.setLogId("");
        }
        result = gson1.toJson(rtn);
        return result;
    }

    //菜品下架
    public String offline(){

        return  null;
    }




//    //接收百度外卖推送过来的订单(2.0)
//    public String orderPost(String params) throws BaiDuException{
//        String requestStr = "{\n" +
//            "    \"body\": {\n" +
//            "        \"shop\": {\n" +
//            "            \"id\": \"2520\",\n" +
//            "            \"name\": \"百度外卖（测试店5555）\"\n" +
//            "        },\n" +
//            "        \"order\": {\n" +
//            "            \"order_id\": \"14347012309352\",\n" +
//            "            \"send_immediately\": 1,\n" +
//            "            \"send_time\": \"1\",\n" +
//            "            \"send_fee\": 500,\n" +
//            "            \"package_fee\": 200,\n" +
//            "            \"discount_fee\": 0,\n" +
//            "            \"total_fee\": 3700,\n" +
//            "            \"shop_fee\": 3200,\n" +
//            "            \"user_fee\": 3700,\n" +
//            "            \"pay_type\": 1,\n" +
//            "            \"pay_status\": 1,\n" +
//            "            \"need_invoice\": 2,\n" +
//            "            \"invoice_title\": \"\",\n" +
//            "            \"remark\": \"请提供餐具\",\n" +
//            "            \"delivery_party\": 1,\n" +
//            "            \"create_time\": \"1434701230\"\n" +
//            "        },\n" +
//            "        \"user\": {\n" +
//            "            \"name\": \"测试订单请勿操作\",\n" +
//            "            \"phone\": \"18912345678\",\n" +
//            "            \"gender\": 1,\n" +
//            "            \"address\": \"北京海淀区奎科科技大厦 测试\",\n" +
//            "            \"coord\": {\n" +
//            "                \"longitude\": 116.143145,\n" +
//            "                \"latitude\": 39.741426\n" +
//            "            }\n" +
//            "        },\n" +
//            "        \"products\": [\n" +
//            "            {\n" +
//            "                \"product_id\": \"12277320\",\n" +
//            "                \"product_name\": \"酱肉包（/份）\",\n" +
//            "                \"product_price\": 1200,\n" +
//            "                \"product_amount\": 1,\n" +
//            "                \"product_fee\": 1200,\n" +
//            "                \"package_price\": 100,\n" +
//            "                \"package_amount\": 1,\n" +
//            "                \"package_fee\": 100,\n" +
//            "                \"total_fee\": 1300,\n" +
//            "                \"upc\": \"\"\n" +
//            "            }\n" +
//            "        ],\n" +
//            "        \"discount\": []\n" +
//            "    },\n" +
//            "    \"cmd\": \"order.create\",\n" +
//            "    \"sign\": \"E362B8AACE4F7A77047A885C0C0D230D\",\n" +
//            "    \"source\": \"65400\",\n" +
//            "    \"ticket\": \"909C3B92-8CDD-AF2C-C887-5B660233E2B2\",\n" +
//            "    \"timestamp\": 1434701234,\n" +
//            "    \"version\": \"2.0\"\n" +
//            "}";
//        String orderStr = "{\n" +
//            "            \"order_id\": \"14347012309352\",\n" +
//            "            \"send_immediately\": 1,\n" +
//            "            \"send_time\": \"1\",\n" +
//            "            \"send_fee\": 500,\n" +
//            "            \"package_fee\": 200,\n" +
//            "            \"discount_fee\": 0,\n" +
//            "            \"total_fee\": 3700,\n" +
//            "            \"shop_fee\": 3200,\n" +
//            "            \"user_fee\": 3700,\n" +
//            "            \"pay_type\": 1,\n" +
//            "            \"pay_status\": 1,\n" +
//            "            \"need_invoice\": 2,\n" +
//            "            \"invoice_title\": \"\",\n" +
//            "            \"remark\": \"请提供餐具\",\n" +
//            "            \"delivery_party\": 1,\n" +
//            "            \"create_time\": \"1434701230\"\n" +
//            "        }";
//        String userStr =" {\n" +
//            "            \"name\": \"测试订单请勿操作\",\n" +
//            "            \"phone\": \"18912345678\",\n" +
//            "            \"gender\": 1,\n" +
//            "            \"address\": \"北京海淀区奎科科技大厦 测试\",\n" +
//            "            \"coord\": {\n" +
//            "                \"longitude\": 116.143145,\n" +
//            "                \"latitude\": 39.741426\n" +
//            "            }\n" +
//            "        }";
//        requestStr.replace("/", "\\/");
//        SysParams sysParams = new SysParams();
//        try {
//            sysParams = getGson().fromJson(requestStr, SysParams.class);
//            Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()).toString(), Body.class);
//            //将订单信息插入Mongodb
//            baiDuInnerService.updSyncBaiDuOrder(body);
//        }catch (Exception ex){
//            throw new BaiDuException("",sysParams,ex);  //异常这块待增强，考虑结构化的异常信息，引入日志ID号，用于返回给Pos方
//        }
//        return  null;
//    }

    //接收百度外卖推送过来的订单
    public String orderPost(SysParams sysParams){
        Result result = new Result();
        try{
            Order order = getGson().fromJson(getGson().toJson(sysParams.getBody()).toString(),Order.class);
            SysParams sysParams1 = baiDuApiService.orderGet(order);
            String bodyStr = getGson().toJson(sysParams1.getBody());
            Body body = getGson().fromJson(getGson().toJson(bodyStr).toString(),Body.class);
            result = getGson().fromJson(bodyStr,Result.class);
            if (result.getErrno().equals("0")){
                //生成商家订单Id
                String sourceOrderId = "";
                baiDuInnerService.updSyncBaiDuOrder(body);
                result.setErrno("0");
                result.setError("error");
                result.setData(MessageFormat.format("{0}",MessageFormat.format("source_order_id:{0}",sourceOrderId)));
            }
            else {
              result.setErrno("1");
              result.setError("error");
            }
        }
        catch (Exception ex){
            result.setErrno("1");
            result.setError("error");
            //记录日志
        }
        sysParams.setBody(result);
        return baiDuApiService.GetRequestPars(sysParams);
    }

    //根据订单号拉取订单详情况
    public SysParams orderGet(String params){
        Result result = new Result();
        SysParams sysParams = getGson().fromJson(params,SysParams.class);
        try {
            Order order = getGson().fromJson(getGson().toJson(sysParams.getBody()).toString(),Order.class);
            return   baiDuApiService.orderGet(order);
        }catch (Exception ex){
            Rtn rtn = new Rtn();
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setDesc("");
            rtn.setDynamic(params);
            sysParams.setBody(rtn);
        }
        return sysParams;
    }

    //接收百度外卖推送过来的订单状态[order.status.push]
    public String orderStatus(SysParams sysParams){
        Result result = new Result();
        try{
        Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()).toString(),Body.class);
        Order order = getGson().fromJson(getGson().toJson(body),Order.class);
       //？是否多个订单存在多个订单号存在
       int intR = baiDuInnerService.updSyncBaiDuOrderStastus(body.getOrder().getOrderId(), Integer.valueOf(Enum.GetEnumDesc(Enum.OrderTypeBaiDu.R5,order.getStatus()).get("code").toString()));
        if (intR > 0){
            result.setErrno("0");
            result.setError("success");
            sysParams.setBody(result);
        }else{
            result.setErrno("1");
            result.setError("error");
            //result.setData("订单不存在");
            sysParams.setBody(result);
        }
       }catch (Exception ex){
            result.setErrno("1");
            result.setError("error");
           // result.setData("发生异常");
            //日志记录,异常分析
        }
        sysParams.setBody(result);
        return baiDuApiService.GetRequestPars(sysParams);
    }

    //确认订单
    public String orderConfirm(String params){
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            Order order = new Order();
            order.setOrderId(params);
            result = baiDuApiService.orderConfirm(order);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(params);
            result = gson1.toJson(rtn);
        }catch (Exception ex){
            rtn.setDynamic(params);
            rtn.setCode(-999);
            rtn.setDesc("发生异常");
            rtn.setLogId("");
            result = gson1.toJson(rtn);
            //日志记录,异常分析

        }
        return  result;
    }

   //取消订单
    public String orderCancel(String params){
            String result = null;
            Rtn rtn = new Rtn();
            Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
            try {
                Order order = new Order();
                order.setOrderId(params);
                result = baiDuApiService.orderCancel(order);
                rtn = gson1.fromJson(result,Rtn.class);
                rtn.setDynamic(params);
                result = gson1.toJson(rtn);
            }catch (Exception ex){
                rtn.setDynamic(params);
                rtn.setCode(-999);
                rtn.setDesc("发生异常");
                rtn.setLogId("");
            result = gson1.toJson(rtn);
            //日志记录,异常分析

        }
        return  result;
    }
}

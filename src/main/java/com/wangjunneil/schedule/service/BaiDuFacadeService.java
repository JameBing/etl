package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.wangjunneil.schedule.activemq.Topic.TopicMessageProducer;
import com.wangjunneil.schedule.common.BaiDuException;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import com.wangjunneil.schedule.service.baidu.BaiDuInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class BaiDuFacadeService {

    private static Logger log = Logger.getLogger(BaiDuFacadeService.class.getName());

    @Autowired
    private BaiDuApiService baiDuApiService;

    @Autowired
    private BaiDuInnerService baiDuInnerService;

    @Autowired
    private SysFacadeService sysFacadeService;

    private Gson gson;

    private Gson getGson(){

        if (gson == null){
            gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                                    .registerTypeAdapter(Body.class, new BodySerializer())
                                    .registerTypeAdapter(Data.class,new DataSerializer())
                                    .registerTypeAdapter(Shop.class, new ShopSerializer())
                                    .registerTypeAdapter(Order.class, new OrderSerializer())
                                    .registerTypeAdapter(User.class, new UserSerializer())
                                    .registerTypeAdapter(Supplier.class,new SupplierSerializer())
                                    .registerTypeAdapter(Coord.class, new CoordSerializer())
                                    .registerTypeAdapter(CoordAmap.class, new CoordAmapSerializer())
                                    .registerTypeAdapter(Products.class, new ProductsSerializer())
                                    .registerTypeAdapter(Discount.class, new DiscountSerializer())
                                    .registerTypeAdapter(businessForm.class,new BusinessFormSerializer())
                                    .registerTypeAdapter(Categorys.class,new CategorysSerializer())
                                    .registerTypeAdapter(DeliveryRegion.class,new DeliveryRegionSerializer())
                                    .registerTypeAdapter(Region.class, new RegionSerializer())
                                    .registerTypeAdapter(BusinessTime.class, new BusinessTimeSerializer())
                                   .registerTypeAdapter(AvailableTime.class, new AvailableTimeSerializer())
                                    .registerTypeAdapter(Rtn.class, new RtnSerializer())
                                    .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                        @Override
                                        public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                                            if (aDouble == aDouble.longValue())
                                                return new JsonPrimitive(aDouble.longValue());
                                            return new JsonPrimitive(aDouble);
                                        }
                                    })
                                  //  .serializeNulls()
                                    .disableHtmlEscaping()
                .create();
        }
        return gson;
    }


    //平台推送消息 没有接收到任何参数时 response
    public String responseNoPars(String cmd){
        Body body = new Body();
        body.setErrno("1");
        body.setError("没有接收到任何参数");
        body.setData("");
        SysParams sysParams = new SysParams();
        sysParams.setBody(body);
        sysParams.setCmd(cmd);
        return baiDuApiService.getRequestPars(sysParams);
    }


    //获取供应商
    public String getSupplier(){
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer())
                                      .registerTypeAdapter(Supplier.class,new SupplierSerializer())
                                      .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                          @Override
                                          public JsonElement serialize(Double d, Type typeOfd, JsonSerializationContext context) {
                                              if(d == d.longValue())
                                                  return new JsonPrimitive(d.longValue());
                                              return new JsonPrimitive(d);
                                          }
                                      }).disableHtmlEscaping().create();
        try {
            rtn.setRemark(getGson().toJson(baiDuApiService.getSupplierList()));
            rtn.setCode(0);
            rtn.setDesc("success");
        }
        //ApiService & InnerService Exception   -999
        catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }finally {
            if (log != null)
                rtn.setDesc("发生异常");
                rtn.setRemark(MessageFormat.format("获取供应商{0}失败！", ""));
        }
        result = gson1.toJson(rtn);
        return result;
    }

    //创建门店
    public String shopCreate(JsonObject jsonBody) {
            String result = null;
            Log log = null;
            Rtn rtn = new Rtn();
                try {
                    rtn  = getGson().fromJson( baiDuApiService.shopCreate(jsonBody),Rtn.class);
                }
                catch (BaiDuException ex){
                    rtn.setCode(-997);
                    log = sysFacadeService.functionRtn.apply(ex);
                 }
                catch (ScheduleException ex){
                    rtn.setCode(-999);
                    log = sysFacadeService.functionRtn.apply(ex);
                    log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                }
                catch (Exception ex){
                    log = sysFacadeService.functionRtn.apply(ex);
                    log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                    rtn.setCode(-998);
                }finally {
                    if (log != null)
                        rtn.setDesc("发生异常");
                        rtn.setRemark(MessageFormat.format("创建门店{0}失败！", ""));
                    }
        return getGson().toJson(rtn);
    }
    //门店开业
    public String shopOpen(String baiduShopId,String shopId){
        String result = "";
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            shop.setBaiduShopId(baiduShopId);
            result =  baiDuApiService.shopOpen(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(shopId);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
             log = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
             log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex) {
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }
        finally {
            //有异常产生
            if (log !=null){
                log.setLogId(shopId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("门店{0}开业失败", shopId));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"baidu_shop_id\":{1}", shopId, baiduShopId)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(shopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}开业失败！",shopId));
            }
            result = gson1.toJson(rtn);
            return  result;
        }
    }

    //门店歇业
    public String shopClose(String baiduShopId,String shopId){
        String result = null;
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        //判断入参数非空
        if(StringUtil.isEmpty(shopId)){
            return gson1.toJson(rtn);
        }
        try {
            Shop shop = new Shop();
            shop.setShopId(shopId);
            shop.setBaiduShopId(baiduShopId);
            result =  baiDuApiService.shopClose(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(shopId);
        }catch (BaiDuException ex) {
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        } finally {
            //有异常产生
            if (log != null) {
                log.setLogId(shopId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("门店{0}歇业失败", shopId));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"baidu_shop_id\":{1}", shopId, baiduShopId)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(shopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}歇业失败！", shopId));
            }
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //新增菜品分类
    public String dishCategoryCreate(JsonObject jsonDishCategory){
       Rtn rtn = new Rtn();
        Log log = null;
        try{
            rtn = getGson().fromJson(baiDuApiService.dishCategoryCreate(jsonDishCategory),Rtn.class);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log != null)
                rtn.setDesc("发生异常");
            rtn.setRemark(MessageFormat.format("添加菜品分类{0}失败！", ""));
        }
        return getGson().toJson(rtn);
    }

    //新增菜品
    public String dishCreate(JsonObject jsonDish){
        Rtn rtn = new Rtn();
        Log log = null;
        try {
            rtn = getGson().fromJson(baiDuApiService.dishCreate(jsonDish),Rtn.class);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log != null)
                rtn.setDesc("发生异常");
            rtn.setRemark(MessageFormat.format("新增菜品{0}失败！", ""));
        }
        return  getGson().toJson(rtn);
    }

    //菜品查看
    public String dishGet(String baiduShopId,String shopId,String baiduDishId,String dishId){
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        try {
//            String bodyStr = (StringUtil.isEmpty(baiduShopId)?"":MessageFormat.format("baidu_shop_id:{0},",baiduShopId)).concat( (StringUtil.isEmpty(shopId)?"":MessageFormat.format("shop_id:{0},",shopId)))
//                                                .concat( (StringUtil.isEmpty(baiduDishId)?"":MessageFormat.format("baidu_dish_id:{0},",baiduDishId))).concat((StringUtil.isEmpty(dishId) ? "" : MessageFormat.format("dish_id:{0}", dishId)));
            Dish dish = new Dish();
            dish.setBaiduShopId(StringUtil.isEmpty(baiduShopId)?"":baiduShopId);
            dish.setShopId(StringUtil.isEmpty(shopId)?"":shopId);
            dish.setBaiduDishId(StringUtil.isEmpty(baiduDishId)?"":baiduDishId);
            dish.setDishId(StringUtil.isEmpty(dishId)?"":dishId);
             result = baiDuApiService.dishGet(dish);

            SysParams sysParams = getGson().fromJson(result,SysParams.class);
            Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
            rtn.setRemark(result);
            rtn.setCode(Integer.valueOf(body.getErrno()));
            rtn.setDesc(body.getError());
            rtn.setRemark(getGson().toJson(body.getData()));
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log != null) {
                log.setLogId(dishId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("查看{0}菜品失败", dishId));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"dish_id\":{0},\"baidu_shop_id\":{1}", dishId, baiduShopId)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(dishId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("查看{0}菜品失败！", dishId));
            }
        }

        return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
    }

    //菜品上架 & 下架
    public String dishOpt(String baiduShopId,String shopId,String baiduDishId,String dishId,String cmd){
        String result = "";
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(shopId) || StringUtil.isEmpty(dishId)){
            return gson1.toJson(rtn);
        }
        try {
            Dish dish = new Dish();
            dish.setShopId(shopId);
            dish.setDishId(dishId);
            switch (cmd){
                case "dish.online":
                    result = baiDuApiService.dishOnline(dish);
                    break;
                case "dish.offline":
                    result = baiDuApiService.dishOffline(dish);
                    break;
                default:break;
            }
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(StringUtil.isEmpty(dishId)?baiduDishId:dishId);
        }catch (BaiDuException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex){
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            }
            catch (Exception ex){
                log =  sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                rtn.setCode(-998);
            }finally {
                if (log !=null){
                    log.setLogId(dishId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("商品{0}上下架失败！",dishId));
                    if (StringUtil.isEmpty(log.getRequest()))
                        log.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", dishId)).concat("}"));
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic("");
                    rtn.setDesc("发生异常");
                    rtn.setRemark(MessageFormat.format("商品{0}上下架失败！", dishId));
                }
            }
        result = gson1.toJson(rtn);
        return result;
    }

    //批量查看门店商品上下架状态 shopId 商家门店Id
    public String getDishStatus(String shopId){
        String result = "";
        Rtn rtn = new Rtn();
        Log log = null;
        String rtnStr = "";
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(shopId)){
            return gson1.toJson(rtn);
        }
        try {
            Dish dish = new Dish();
            dish.setShopId(shopId);
            result = baiDuApiService.getDishStatus(dish);
            if(result.equals("20253")){
                rtn.setCode(-1);
                rtn.setDesc("success");
                rtn.setRemark("无此门店");
                rtn.setDynamic(shopId);
                return  gson1.toJson(rtn);
            }
            if(StringUtil.isEmpty(result)){
                rtn.setCode(-1);
                rtn.setDesc("success");
                rtn.setRemark("此门店无商品");
                rtn.setDynamic(shopId);
                return  gson1.toJson(rtn);
            }
            JSONObject  jsonObject = JSON.parseObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("data");
            for(int i=0;i<jsonArray.size();i++){
                JSONObject json = jsonArray.getJSONObject(i);
                if(json.getInteger("is_online")==1){
                    rtn.setCode(0);
                    rtn.setDynamic(json.getString("dish_id"));
                    rtnStr =rtnStr+gson1.toJson(rtn)+",";
                }
            }

        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log !=null){
                log.setLogId(shopId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("查看门店{0}商品上下架状态失败！",shopId));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shopId\":{0}", shopId)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic("");
                rtn.setDesc("发生异常");
                rtn.setRemark(MessageFormat.format("查看商品{0}上下架状态失败！", shopId));
            }
            if(!StringUtil.isEmpty(rtnStr)){
                return  rtnStr.substring(0,rtnStr.length()-1);
            }
            return gson1.toJson(rtn);
        }
    }

    //接收百度外卖推送过来的订单
    public String orderPost(SysParams sysParams){
        String result = "";
        Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
        Data data1 = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
        try{
            Order order = getGson().fromJson(sysParams.getBody().toString(),Order.class);
            SysParams sysParams1 =getGson().fromJson(baiDuApiService.orderGet(order,data1.getShop().getShopId()),SysParams.class);
            String bodyStr = getGson().toJson(sysParams1.getBody());
            body = getGson().fromJson(bodyStr,Body.class);
            if (body.getErrno().trim().equals("0")){

               Data data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
                OrderWaiMai orderWaiMai = new OrderWaiMai();
                orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                //平台门店ID
                String shopId = data.getShop().getBaiduShopId();
                String sellerId = data.getShop().getShopId();
                //百度订单ID
                String platformOrderId = data.getOrder().getOrderId();
                orderWaiMai.setPlatformOrderId(platformOrderId);
                //商家订单ID
                String orderId = sysFacadeService.getOrderNum(shopId);
                orderWaiMai.setOrderId(orderId);
                orderWaiMai.setOrder(data);
                orderWaiMai.setShopId(shopId);
                orderWaiMai.setSellerShopId(sellerId);
                orderWaiMai.setCreateTime(new Date());
                sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
                body.setErrno("0");
                body.setError("success");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("source_order_id",orderId);
                body.setData(jsonObject);
            }else {
              body.setErrno("1");
              body.setError("error");
              body.setData("");
            }
        }catch (Exception ex){
            body.setErrno("1");
            body.setError("error");
            body.setData("Exception");
            //日志记录,异常分析
        }
        sysParams.setCmd(Constants.BAIDU_CMD_RESP.concat(".").concat(Constants.BAIDU_CMD_ORDER_CREATE));
        sysParams.setBody(body);
        sysParams.setTimestamp("");
        sysParams.setTicket("");
        sysParams.setSign("");
        return baiDuApiService.responseStr(sysParams);
    }

    //根据订单号拉取订单详情况
    public Data orderGet(String params,String shopId){
        Body body = new Body();
        Data data = new Data();
        Rtn rtn = new Rtn();
        Log log = null;
        try {
            Order order = new Order();
            order.setOrderId(params);
            String result = baiDuApiService.orderGet(order,shopId);
            SysParams sysParams =getGson().fromJson(result,SysParams.class);
            body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
            data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log !=null){
                log.setLogId(params.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("订单详情{0}获取失败！",params));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"order_id\":{0}", params)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic("");
                rtn.setDesc("发生异常");
                rtn.setRemark(MessageFormat.format("订单详情{0}获取失败！", params));
            }
        }
        return data;
    }

    //接收百度外卖推送过来的订单状态[order.status.push] flag 是否推送整个订单 true 推送  false不推送
    public String orderStatus(SysParams sysParams,Boolean flag){
        Body result = new Body();
        try{
        Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
        Data data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
        Integer status = Integer.valueOf(data.getOrder().getStatus());
       //？是否多个订单号存在
        int intR = 0;
        //推送整个订单
        if(flag){
            Order order = getGson().fromJson(sysParams.getBody().toString(),Order.class);
            SysParams sysParams1 =getGson().fromJson(baiDuApiService.orderGet(order,data.getShop().getShopId()),SysParams.class);
            String bodyStr = getGson().toJson(sysParams1.getBody());
            body = getGson().fromJson(bodyStr,Body.class);
            if (body.getErrno().equals("0")){
                String orderId = data.getOrder().getOrderId();
                Data dataOrder = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
                OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_BAIDU,orderId);
                sysFacadeService.updateWaiMaiOrder(orderId,orderWaiMai);
                intR =1;
            }else {
                body.setErrno("1");
                body.setError("error");
                body.setData("");
            }
        }else {
            intR = baiDuInnerService.updSyncBaiDuOrderStastus(data.getOrder().getOrderId(), Integer.valueOf(Enum.getEnumDesc(Enum.OrderTypeBaiDu.R5, status).get("code").getAsString()));
            OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_BAIDU,data.getOrder().getOrderId());
            List<String> listIds = new ArrayList<String>();
            Collections.addAll(listIds,data.getOrder().getOrderId().split(","));
            listIds.forEach((id)->{
                sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_BAIDU,status,id,null,orderWaiMai.getSellerShopId());
            });
        }
        if (intR > 0){
            result.setErrno("0");
            result.setError("success");
            result.setData("");
            sysParams.setBody(result);
        }else{
            result.setErrno("1");
            result.setError("error");
            result.setData("order not exist");
            sysParams.setBody(result);
        }
       }catch (Exception ex){
            result.setErrno("1");
            result.setError("error");
            result.setData("Exception");
            //日志记录,异常分析
        }
        sysParams.setBody(result);
        sysParams.setTicket("");
        sysParams.setTimestamp("");
        return baiDuApiService.responseStr(sysParams);
    }

    //确认订单
    public String orderConfirm(String params,String shopId){
        Rtn rtn = new Rtn();
        Log log = null;
        String result = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(params) || StringUtil.isEmpty(shopId)){
            return gson1.toJson(rtn);
        }
        try {
            //是否是接单状态
            Data data = orderGet(params,shopId);
            if(data==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(params));
                return gson.toJson(rtn);
            }
            Order searchOrder = getGson().fromJson(getGson().toJson(data.getOrder()),Order.class);

            if(searchOrder.getStatus()==null || searchOrder.getStatus()!=Constants.BD_SUSPENDING){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                jsonObject.put("desc","error");
                jsonObject.put("remark","订单已确实过，请更新状态");
                jsonObject.put("orderId",params);
                jsonObject.put("orderStatus",searchOrder.getStatus()==null?0:new SysFacadeService().tranBdOrderStatus(searchOrder.getStatus()));
                return jsonObject.toJSONString();
            }
            rtn.setDynamic(params);
            Order order = new Order();
            order.setOrderId(params);
            result = baiDuApiService.orderConfirm(order,shopId);
            rtn = gson1.fromJson(result,Rtn.class);
            //统一状态码给POS
            if(!StringUtil.isEmpty(rtn.getCode()) && rtn.getCode()==20216) {
                rtn.setCode(Constants.RETURN_ORDER_CODE);
            }
            //更新门店是否接单标识字段
            baiDuInnerService.updateIsReceived(params,1);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);

        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }
        if (log !=null){
            log.setLogId(shopId.concat(log.getLogId()));
            log.setTitle(MessageFormat.format("门店{0}确认订单{1}失败", shopId,params));
            if (StringUtil.isEmpty(log.getRequest()))
                log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"baidu_order_id\":{1}", shopId, params)).concat("}"));
            sysFacadeService.updSynLog(log);
            rtn.setDynamic(shopId);
            rtn.setDesc("发生异常");
            rtn.setLogId(log.getLogId());
            rtn.setRemark(MessageFormat.format("门店{0}确认订单{1}失败！",shopId,params));
        }
        result = gson1.toJson(rtn);
        return  result;
    }

   //取消订单
    public String orderCancel(String params,String reason,String reason_code,String shopId){
        Rtn rtn = new Rtn();
        Log log = null;
        String result = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(params) || StringUtil.isEmpty(shopId)){
            return gson1.toJson(rtn);
        }
        try {
            //是否是接单状态
            Data data = orderGet(params,shopId);
            if(data==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(params));
                return gson.toJson(rtn);
            }
            Order searchOrder = getGson().fromJson(getGson().toJson(data.getOrder()),Order.class);
            if(searchOrder.getStatus()==Constants.BD_SUSPENDING){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                jsonObject.put("desc","error");
                jsonObject.put("remark","订单已确实过，请更新状态");
                jsonObject.put("orderId",params);
                jsonObject.put("orderStatus",new SysFacadeService().tranBdOrderStatus(searchOrder.getStatus()));
                return jsonObject.toJSONString();
            }
            rtn.setDynamic(params);
            Order order = new Order();
            order.setOrderId(params);
            order.setType(reason_code);
            order.setReason(reason);
            result = baiDuApiService.orderCancel(order,shopId);
            rtn = gson1.fromJson(result,Rtn.class);
            //统一状态码给POS
            if(!StringUtil.isEmpty(rtn.getCode()) && rtn.getCode()==20016){
                rtn.setCode(Constants.RETURN_ORDER_CODE);
            }
            //更新门店是否接单标识字段
            baiDuInnerService.updateIsReceived(params,2);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);

        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }
        if (log !=null){
            log.setLogId(shopId.concat(log.getLogId()));
            log.setTitle(MessageFormat.format("门店{0}取消订单{1}失败", shopId,params));
            if (StringUtil.isEmpty(log.getRequest()))
                log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"baidu_order_id\":{1}", shopId, params)).concat("}"));
            sysFacadeService.updSynLog(log);
            rtn.setDynamic(shopId);
            rtn.setDesc("发生异常");
            rtn.setLogId(log.getLogId());
            rtn.setRemark(MessageFormat.format("门店{0}取消订单{1}失败！",shopId,params));
        }
        return  result;
    }
    //修改商户信息
    public String shopUpdate(JsonObject jsonObject){
        Rtn rtn = new Rtn();
        Log log = null;
        try {
            rtn = getGson().fromJson(baiDuApiService.shopUpdate(jsonObject),Rtn.class);
        }catch (BaiDuException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        }
        catch (Exception ex){
            log =  sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
            rtn.setCode(-998);
        }finally {
            if (log !=null){
                rtn.setDesc("发生异常");
                rtn.setRemark(MessageFormat.format("修改商户{0}信息失败！", ""));
            }
        }
        return  getGson().toJson(rtn);
    }

    //查看商户状态
    public String shopStatus(String params) {
        Rtn rtn = new Rtn();
        String result = null;
        Body body = new Body();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class, new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(params)){
            return gson1.toJson(rtn);
        }
        try {
            Shop shop = new Shop();
            shop.setShopId(params);
            result = baiDuApiService.shopStatus(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            if(rtn.getCode()!=0){
                return gson1.toJson(rtn);
            }
            Shop shop1 = getGson().fromJson(result, Shop.class);
           switch (shop1.getBusinessStauts()){
               case 1:
                   rtn.setCode(0);
                   rtn.setDynamic(params);
                   rtn.setDesc("success");
                   rtn.setRemark(MessageFormat.format("商店{0}正常营业！", params));
                   break;
               case  3:
                   rtn.setCode(1);
                   rtn.setDynamic(params);
                   rtn.setDesc("success");
                   rtn.setRemark(MessageFormat.format("商店{0}休息中！", params));
                   break;
               case 9:
                   rtn.setCode(2);
                   rtn.setDynamic(params);
                   rtn.setDesc("success");
                   rtn.setRemark(MessageFormat.format("商店{0}停止营业！", params));
                   break;
           }
        } catch (BaiDuException ex) {
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        } catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        } catch (Exception ex) {
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        } finally {
            //有异常产生
            if (log != null) {
                log.setLogId(params.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("查看{0}商户状态失败", params));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},", params)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(params);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("查看{0}商户状态失败！", params));
            }
        }
        result = gson1.toJson(rtn);
        return result;
    }

    //查看订单状态
    public String OrderStatus(String params,String shopId) {
        Rtn rtn = new Rtn();
        String result = null;
        Body body = new Body();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class, new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(params)){
            return gson1.toJson(rtn);
        }
        try {
            Order order = new Order();
            order.setOrderId(params);
            String rtnOrder = baiDuApiService.orderGet(order,shopId);
            SysParams sysParams =getGson().fromJson(rtnOrder,SysParams.class);
            body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
            if(body.getErrno().equals("20212")){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(params));
                return gson.toJson(rtn);
            }
            Data data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
            Order searchOrder = getGson().fromJson(getGson().toJson(data.getOrder()),Order.class);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("desc","success");
            jsonObject.put("platformOrderId",params);
            jsonObject.put("orderStatus",new SysFacadeService().tranBdOrderStatus(searchOrder.getStatus()));
            return jsonObject.toJSONString();
        } catch (BaiDuException ex) {
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        } catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        } catch (Exception ex) {
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
        } finally {
            //有异常产生
            if (log != null) {
                log.setLogId(params.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("查看{0}订单状态失败", params));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},", params)).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(params);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("查看{0}订单状态失败！", params));
            }
        }
        result = gson1.toJson(rtn);
        return result;
    }
}

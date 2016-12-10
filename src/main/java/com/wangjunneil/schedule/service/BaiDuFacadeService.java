package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.*;
import com.google.gson.annotations.Expose;
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
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.DoubleSummaryStatistics;
import java.util.function.Function;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class BaiDuFacadeService {

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
        }
        catch (ScheduleException ex){
            rtn.setCode(-999);
            rtn.setDesc("error");
            rtn.setRemark("发生异常");
            rtn.setLogId("");
            //异常日志
        }
        //FacadeService Exception -998
        catch (Exception ex){
          rtn.setCode(-998);
        }
        result = gson1.toJson(rtn);
        return result;
    }

    //创建门店
    public String shopCreate(JsonObject jsonBody) {
            String result = null;
            Rtn rtn = new Rtn();
                try {
                    rtn  = getGson().fromJson( baiDuApiService.shopCreate(jsonBody),Rtn.class);
                }
                catch (Exception ex){
                    rtn.setDynamic("");
                    rtn.setCode(-999);
                    rtn.setDesc("发生异常");
                    rtn.setLogId("");
                    //异常日志
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
        }
        catch (Exception ex) {
            log =  sysFacadeService.functionRtn.apply(ex);
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
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {

            Shop shop = new Shop();
            shop.setShopId(shopId);
            shop.setBaiduShopId(baiduShopId);
            result =  baiDuApiService.shopClose(shop);
            rtn = gson1.fromJson(result,Rtn.class);
            rtn.setDynamic(shopId);
        }catch (Exception ex) {
            rtn.setDynamic(shopId);
            rtn.setCode(-999);
            rtn.setDesc("发生异常");
            rtn.setLogId("");
            //异常日志
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //新增菜品分类
    public String dishCategoryCreate(JsonObject jsonDishCategory){
       Rtn rtn = new Rtn();
        try{
            rtn = getGson().fromJson(baiDuApiService.dishCategoryCreate(jsonDishCategory),Rtn.class);
        }
        catch (Exception ex){
            rtn.setDynamic("");
            rtn.setCode(-999);
            rtn.setDesc("error");
            rtn.setRemark("发生异常");
            rtn .setLogId("");
            //异常日志
        }
        return getGson().toJson(rtn);
    }

    //新增菜品
    public String dishCreate(JsonObject jsonDish){
        Rtn rtn = new Rtn();
        try {
            rtn = getGson().fromJson(baiDuApiService.dishCreate(jsonDish),Rtn.class);
        }
        catch (Exception ex){
          rtn.setDynamic("");
           rtn.setCode(-999);
            rtn.setDesc("error");
            rtn.setRemark("发生异常");
            rtn .setLogId("");
            //异常日志
        }
        return  getGson().toJson(rtn);
    }

    //菜品查看
    public String dishGet(String baiduShopId,String shopId,String baiduDishId,String dishId){
        String result = null;
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
        }catch (Exception ex){
           rtn.setCode(-999);
            rtn.setDesc("error");
            rtn.setRemark("发生异常");
            //异常记录
            rtn.setLogId("");
        }

        return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
    }

    //菜品上架 & 下架
    public String dishOpt(String baiduShopId,String shopId,String baiduDishId,String dishId,String cmd){
           String result = "";
            Rtn rtn = new Rtn();
            rtn.setDynamic(StringUtil.isEmpty(dishId)?baiduDishId:dishId);
            Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
            try {
                Dish dish = new Dish();
                dish.setShopId(shopId);
                dish.setBaiduShopId(baiduShopId);
                dish.setDishId(dishId);
                dish.setBaiduDishId(baiduDishId);
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
        }catch (Exception ex){
                rtn.setCode(-999);
                rtn.setDesc("发生异常");
                rtn.setLogId("");
                //异常日志
        }
        result = gson1.toJson(rtn);
        return result;
    }

    //接收百度外卖推送过来的订单
    public String orderPost(SysParams sysParams){
        Body body = new Body();
        try{
            Order order = getGson().fromJson(sysParams.getBody().toString(),Order.class);
            SysParams sysParams1 =getGson().fromJson(baiDuApiService.orderGet(order),SysParams.class);
            String bodyStr = getGson().toJson(sysParams1.getBody());
            body = getGson().fromJson(bodyStr,Body.class);
            if (body.getErrno().equals("0")){
                Gson gsonDataOrder = new GsonBuilder().registerTypeAdapter(Data.class, new DataSerializer())
                                                                                                    .registerTypeAdapter(OrderShop.class,new OrderShopSerializer())
                                                                                                    .registerTypeAdapter(Order.class,new OrderSerializer())
                                                                                                    .registerTypeAdapter(User.class,new UserSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsDish.class, new OrderProductsDishSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsDishAttr.class,new OrderProductsDishAttrSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsFeatures.class,new OrderProductsFeaturesSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsCombo.class,new OrderProductsComboSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsComboGroup.class,new OrderProductsComboGroupSerializer())
                                                                                                    .registerTypeAdapter(OrderProductsComboGroupProduct.class,new OrderProductsComboGroupProductSerializer())
                                                                                                    .registerTypeAdapter(Supplier.class,new SupplierSerializer())
                                                                                                    .registerTypeAdapter(OrderDiscount.class,new OrderDiscountSerializer())
                                                                                                    .registerTypeAdapter(OrderDiscountProducts.class, new OrderDiscountProductsSerializer())
                    .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                        @Override
                        public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                            if (aDouble == aDouble.longValue())
                                return new JsonPrimitive(aDouble.longValue());
                            return new JsonPrimitive(aDouble);
                        }
                    })
                                                                                                    .disableHtmlEscaping().create();
               Data data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
               // JsonObject jsonData = new JsonParser().parse(getGson().toJson(body.getData())).getAsJsonObject();
                OrderWaiMai orderWaiMai = new OrderWaiMai();
                orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                //商家门店ID
                String shopId = data.getShop().getShopId();
                //百度订单ID
                String platformOrderId = data.getOrder().getOrderId();
                orderWaiMai.setPlatformOrderId(platformOrderId);
                //商家订单ID
                String orderId = sysFacadeService.getOrderNum(shopId);
                orderWaiMai.setOrderId(orderId);
                orderWaiMai.setOrder(data);
                orderWaiMai.setShopId(shopId);
                sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
                body.setErrno("0");
                body.setError("success");
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("source_order_id",orderId);
                body.setData(jsonObject);

            }
            else {
              body.setErrno("1");
              body.setError("error");
              body.setData("");
            }
        }
        catch (Exception ex){
            body.setErrno("1");
            body.setError("error");
            body.setData("");
            //记录日志
        }
        sysParams.setCmd(Constants.BAIDU_CMD_RESP.concat(".").concat(Constants.BAIDU_CMD_ORDER_CREATE));
        sysParams.setBody(body);
       sysParams.setTimestamp("");
        sysParams.setTicket("");
        sysParams.setSign("");
        return baiDuApiService.responseStr(sysParams);
    }

    //根据订单号拉取订单详情况
    public SysParams orderGet(String params){
        Body body = new Body();
        SysParams sysParams = getGson().fromJson(params,SysParams.class);
        try {
            Order order = getGson().fromJson(getGson().toJson(sysParams.getBody()).toString(),Order.class);
            return getGson().fromJson(baiDuApiService.orderGet(order),SysParams.class);
        }catch (Exception ex){
            Rtn rtn = new Rtn();
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setDesc("");
            rtn.setDynamic(params);
            sysParams.setBody(rtn);
            //记录异常
        }
        return sysParams;
    }

    //接收百度外卖推送过来的订单状态[order.status.push]
    public String orderStatus(SysParams sysParams){
        Body result = new Body();
        try{
        Body body = getGson().fromJson(getGson().toJson(sysParams.getBody()),Body.class);
        Data data = getGson().fromJson(getGson().toJson(body.getData()),Data.class);
       //？是否多个订单号存在
       int intR = baiDuInnerService.updSyncBaiDuOrderStastus(data.getOrder().getOrderId(), Integer.valueOf(Enum.getEnumDesc(Enum.OrderTypeBaiDu.R5, data.getOrder().getStatus()).get("code").getAsString()));
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
    //修改商户信息
    public String shopUpdate(JsonObject jsonObject){
        Rtn rtn = new Rtn();
        try {
            rtn = getGson().fromJson(baiDuApiService.shopUpdate(jsonObject),Rtn.class);
        }
        catch (Exception ex){
            rtn.setDynamic("");
            rtn.setCode(-999);
            rtn.setDesc("error");
            rtn.setRemark("发生异常");
            rtn .setLogId("");
            //异常日志
        }
        return  getGson().toJson(rtn);
    }
}

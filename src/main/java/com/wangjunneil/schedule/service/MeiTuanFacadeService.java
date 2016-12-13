package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.MeiTuanException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.entity.meituan.*;
import com.wangjunneil.schedule.service.meituan.MeiTuanApiService;
import com.wangjunneil.schedule.service.meituan.MeiTuanInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.text.MessageFormat;


/**
 * <code>MtFacadeService</code>类美团接口API的调用
 * @author liuxin
 * @since 2016-11-10.
 */
@Service
public class MeiTuanFacadeService {

    @Autowired
    private MeiTuanApiService mtApiService ;

    @Autowired
    private MeiTuanInnerService mtInnerService;

    @Autowired
    private SysFacadeService sysFacadeService;

    //日志配置
    private static Logger log = Logger.getLogger(JdHomeFacadeService.class.getName());


    //region 门店开业,歇业

    /**
     * 门店开业
     * @params app_poi_code  APP方门店id
     */
    public String openShop(String code)
    {
        String json  = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(code);
        Log log1 = null;
        try {
            json = mtApiService.openShop(code);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
           rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(code.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("门店{0}开业失败", code));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", code, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(code);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}开业失败！",code));
            }
            return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }

    /**
     * 门店歇业
     * @params app_poi_code  APP方门店id
     */
    public String closeShop(String code)
    {
        String json = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(code);
        Log log1 = null;
        try {
            json = mtApiService.closeShop(code);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(code.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("门店{0}关店失败", code));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", code, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(code);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}关店失败！",code));
            }
            return new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }

    //endregion


    //region 商品类接口

    /**
     * 创建菜品
     * @params foodParam 菜品实体对象
     */
    public String createFood(FoodParam foodParam) {
        String json = "";
        try {
            json = mtApiService.foodCreate(foodParam);
            return json;
        }
        catch (MeiTuanException ex){}
        catch (ScheduleException ex){}
        catch (ApiOpException ex){}
        catch (Exception ex){}
        finally {
            return json;
        }
    }

    /**
     * 查询所有菜品
     * @params appPoiCode 门店id
     * @params foodCode 菜品id
     */
    public FoodParam foodList(String appPoiCode, String foodCode) {
        FoodParam foodParam = null;
        try {
            foodParam = mtApiService.foodList(appPoiCode, foodCode);
        }
        catch (MeiTuanException ex){}
        catch (ScheduleException ex){}
        catch (ApiOpException ex){}
        catch (Exception ex){}
        finally {
            return  foodParam;
        }
    }

    /**
     * 商品上架
     * @parama
     */
    public String upFrame(String appPoiCode,String foodCode) {
        String json = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(appPoiCode);
        rtn.setDynamic(foodCode);
        Log log1 = null;
        try {
            json = mtApiService.upFrame(appPoiCode, foodCode);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(appPoiCode.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("商品{0}上架失败", appPoiCode));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", appPoiCode, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(appPoiCode);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("商品{0}上架失败！",appPoiCode));
            }
            return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }


    /**
     * 商品下架
     * @parama
     */
    public String downFrame(String appPoiCode,String foodCode) {
        String json = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(appPoiCode);
        rtn.setDynamic(foodCode);
        Log log1 = null;
        try {
            json = mtApiService.downFrame(appPoiCode, foodCode);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException e) {
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(appPoiCode.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("商品{0}下架失败", appPoiCode));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", appPoiCode, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(appPoiCode);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("商品{0}下架失败！",appPoiCode));
            }
            return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }


    //endregion


    //region 订单相关

    /**
     * 商家确认订单
     * @param orderId - 订单id
     */
    public String getConfirmOrder(long orderId) {
        String json = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(String.valueOf(orderId));
        Log log1 = null;
        try {
            json = mtApiService.getConfirmOrder(orderId);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException ex){
            rtn.setCode(ex.getCode());
            rtn.setDesc("error");
            rtn.setRemark(ex.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(String.valueOf(orderId).concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("商家确认{0}订单失败", String.valueOf(orderId)));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", String.valueOf(orderId), "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(String.valueOf(orderId));
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("商家确认{0}订单失败！",String.valueOf(orderId)));
            }
            return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }


    /**
     * 商家取消订单
     * @param orderId - 订单id
     */
    public String getCancelOrder(long orderId,String reason,String reason_code) {
        String json = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(String.valueOf(orderId));
        Log log1 = null;
        try {
            json = mtApiService.getCancelOrder(orderId,reason,reason_code);
            if(json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ApiOpException ex)
        {
            rtn.setCode(ex.getCode());
            rtn.setDesc("error");
            rtn.setRemark(ex.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(String.valueOf(orderId).concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("商家取消{0}订单失败", String.valueOf(orderId)));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", String.valueOf(orderId), "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(String.valueOf(orderId));
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("商家取消{0}订单失败！",String.valueOf(orderId)));
            }
            return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
    }

    /**
     * 推送订单订单状态（已支付新订单）
     * @return
    */
    public String newOrder(JsonObject jsonObject) {
        String result = "";
        Gson gson = new GsonBuilder().registerTypeAdapter(OrderInfo.class,new OrderInfoSerializer())
            .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderExtraParam.class, new OrderExtraParamSerializer())
            .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderFoodDetailParam.class, new OrderFoodDetailParamSerializer()) .disableHtmlEscaping().create();
        try {
                String json =gson.toJson(jsonObject);
                json = java.net.URLDecoder.decode(json,"utf-8");
                OrderInfo order = gson.fromJson(json, OrderInfo.class);
            //商家门店ID
            String shopId = order.getApppoicode();
            //美团订单ID
            String platformOrderId = String.valueOf(order.getOrderid());
            OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_MEITUAN,platformOrderId);
            //如果订单已经存在则商家订单ID不重新获取
            if (orderWaiMai !=null&&orderWaiMai.getPlatform().equals(Constants.PLATFORM_WAIMAI_MEITUAN)&&orderWaiMai.getPlatformOrderId().equals(platformOrderId)){
                orderWaiMai.setOrderId(orderWaiMai.getOrderId());
            }else {
                orderWaiMai = new OrderWaiMai();
                //商家订单ID
                String orderId = sysFacadeService.getOrderNum(shopId);
                orderWaiMai.setOrderId(orderId);
            }
            orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
            orderWaiMai.setPlatformOrderId(platformOrderId);
            orderWaiMai.setOrder(order);
            orderWaiMai.setShopId(shopId);
            sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
            result = "{\"data\" : \"ok\"}" ;
        }
        catch (Exception ex){
           result = "{\"code\":700,\"msg\":\"系统异常\"}";
        }
        finally {
            return result;
        }
    }

    /**
     * 推送订单订单状态（已确认、已完成）
     * @return
     */
    public String getChangeOrderStatus(JsonObject jsonObject) {
        String result = "";
        Gson gson = new GsonBuilder().registerTypeAdapter(OrderInfo.class,new OrderInfoSerializer())
            .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderExtraParam.class, new OrderExtraParamSerializer())
            .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderFoodDetailParam.class, new OrderFoodDetailParamSerializer()) .disableHtmlEscaping().create();
        try {
            String json =gson.toJson(jsonObject);
            json = java.net.URLDecoder.decode(json,"utf-8");
            OrderInfo order = gson.fromJson(json, OrderInfo.class);
            //商家门店ID
            String shopId = order.getApppoicode();
            //美团订单ID
            String platformOrderId = String.valueOf(order.getOrderid());
            OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_MEITUAN,platformOrderId);
            //如果订单已经存在则商家订单ID不重新获取
            if (orderWaiMai !=null&&orderWaiMai.getPlatform().equals(Constants.PLATFORM_WAIMAI_MEITUAN)&&orderWaiMai.getPlatformOrderId().equals(platformOrderId)){
                orderWaiMai.setOrderId(orderWaiMai.getOrderId());
            }else {
                orderWaiMai = new OrderWaiMai();
                //商家订单ID
                String orderId = sysFacadeService.getOrderNum(shopId);
                orderWaiMai.setOrderId(orderId);
            }
            orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
            orderWaiMai.setPlatformOrderId(platformOrderId);
            orderWaiMai.setOrder(order);
            orderWaiMai.setShopId(shopId);
            sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
            result = "{\"data\" : \"ok\"}" ;
        }
        catch (Exception ex){
            result = "{\"code\":700,\"msg\":\"系统异常\"}";
        }
        finally {
            return result;
        }
    }

//endregion



}

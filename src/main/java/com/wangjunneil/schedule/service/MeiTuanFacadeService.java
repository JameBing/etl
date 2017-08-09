package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderParam;
import com.sankuai.meituan.waimai.opensdk.vo.PoiParam;
import com.wangjunneil.schedule.activemq.StaticObj;
import com.wangjunneil.schedule.activemq.Topic.TopicMessageProducer;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import java.net.URLDecoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


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
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(code)){
            return gson.toJson(rtn);
        }
        try {
            json = mtApiService.openShop(code);
            System.out.println(json);
            rtn.setDynamic(code);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(Integer.valueOf(0));
                rtn.setDesc("success");
                rtn.setRemark("成功");
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
           rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
            return gson.toJson(rtn);
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
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(code)){
            return gson.toJson(rtn);
        }
        try {
            rtn.setDynamic(code);
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
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
            return gson.toJson(rtn);
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
     * 批量获取门店详细信息  open_level - 0.未上线  1.正常营业  3.休息状态
     * @params appPoiCode  APP方门店id
     */
    public String findShopStatus(String appPoiCode){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        List<PoiParam> poiParam = new ArrayList<>();
        //判断请求参数非空
        if(StringUtil.isEmpty(appPoiCode)){
            return gson.toJson(rtn);
        }
        try {
            rtn.setDynamic(appPoiCode);
            poiParam = mtApiService.poiMget(appPoiCode);
            if(poiParam!=null && poiParam.size()>0){
                PoiParam poi = poiParam.get(0);
                int level =poi.getOpen_level();
                if(level==0){
                    rtn.setCode(2);
                    rtn.setDesc("error");
                    rtn.setRemark("此门店未上线");
                    return gson.toJson(rtn);
                }else if(level==1){
                    rtn.setCode(0);
                    rtn.setDesc("success");
                    rtn.setRemark("正常营业");
                    return gson.toJson(rtn);
                }else if(level==3){
                    rtn.setCode(1);
                    rtn.setDesc("success");
                    rtn.setRemark("门店休息");
                    return gson.toJson(rtn);
                }else {
                    rtn.setCode(-1);
                    rtn.setDesc("error");
                    rtn.setRemark("无此门店");
                    return gson.toJson(rtn);
                }
            }else {
                rtn.setCode(-1);
                rtn.setDesc("error");
                rtn.setRemark("无此门店");
                return gson.toJson(rtn);
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (ApiSysException e){
            rtn.setCode(-999999);
            rtn.setDesc("error");
            rtn.setRemark("美团系统异常");
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(appPoiCode.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("门店{0}信息获取失败", appPoiCode));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", appPoiCode, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(appPoiCode);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}信息获取失败！",appPoiCode));
            }
            return gson.toJson(rtn);
        }
    }


    /**
     * 获取订单状态
     * @params orderId
     */
    public String findOrderStatus(String orderId,String shopId){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        List<PoiParam> poiParam = new ArrayList<>();
        //判断请求参数非空
        if(StringUtil.isEmpty(orderId)){
            return gson.toJson(rtn);
        }
        try {
            rtn.setDynamic(orderId);
            OrderDetailParam param = getOrderDetail(Long.parseLong(orderId));
            if(param==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(orderId));
                return gson.toJson(rtn);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("desc","success");
            jsonObject.put("platformOrderId",orderId);
            jsonObject.put("orderStatus",(new SysFacadeService()).tranMTOrderStatus(param.getStatus()));
            if(param.getStatus()==Constants.POS_ORDER_DISPATCH_GET){
                jsonObject.put("dispatcherMobile",param.getLogistics_dispatcher_mobile());
                jsonObject.put("dispatcherName",param.getLogistics_dispatcher_name());
            }
            return jsonObject.toJSONString();
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(orderId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("订单{0}信息获取失败", orderId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", orderId, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(orderId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("订单{0}信息获取失败！",orderId));
            }
        }
        return gson.toJson(rtn);
    }


    /**
     * 商品上架
     * @parama
     */
    public String upFrame(String appPoiCode,String foodCode) {
        String json = "";
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class, new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(foodCode)){
            return gson.toJson(rtn);
        }
        rtn.setDynamic(foodCode);
        try {
            json = mtApiService.upFrame(appPoiCode, foodCode);
            if(StringUtil.isEmpty(json)){
                rtn.setCode(0);
                rtn.setDesc("error");
                rtn.setRemark("上架失败,无此商品!");
                return gson.toJson(rtn);
            }
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
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e){
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
            return gson.toJson(rtn);
        }
    }


    /**
     * 商品下架
     * @parama
     */
    public String downFrame(String appPoiCode,String foodCode) {
        String json = "";
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(foodCode)){
            return gson.toJson(rtn);
        }
        try {
            rtn.setDynamic(foodCode);
            json = mtApiService.downFrame(appPoiCode, foodCode);
            if(StringUtil.isEmpty(json)){
                rtn.setCode(0);
                rtn.setDesc("error");
                rtn.setRemark("下架失败,无此商品!");
                return gson.toJson(rtn);
            }
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
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e) {
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
            return gson.toJson(rtn);
        }
    }

    /**
     * 查询门店商品
     * @param appPoiCode 门店编号
     * @return
     */
    public String queryDishStatus(String appPoiCode){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String rtnStr = "";
        if(StringUtil.isEmpty(appPoiCode)){
            return gson.toJson(rtn);
        }
        try {
            List<FoodParam> foodList = mtApiService.getFoodList(appPoiCode);
            if(foodList ==null || foodList.size()==0){
                rtn.setCode(-1);
                rtn.setDesc("success");
                rtn.setRemark("此门店无商品");
                rtn.setDynamic(appPoiCode);
                return  gson.toJson(rtn);
            }
            for(int i=0;i<foodList.size();i++){
                FoodParam food = foodList.get(i);
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setStatus(food.getIs_sold_out());
                rtn.setName(food.getName());
                rtn.setDynamic(food.getApp_food_code());
                rtnStr =rtnStr+gson.toJson(rtn)+",";
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException e) {
            rtn.setCode(e.getCode());
            rtn.setDesc("error");
            rtn.setRemark(e.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(appPoiCode.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("查询门店{0}商品失败！",appPoiCode));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", appPoiCode, "")).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(appPoiCode);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("查询门店{0}商品失败！",appPoiCode));
            }
            if(!StringUtil.isEmpty(rtnStr)){
                return  rtnStr.substring(0,rtnStr.length()-1);
            }
            return gson.toJson(rtn);
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
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(orderId)){
            return new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
        }
        try {
            //确认时判断是否接单
            OrderDetailParam param = getOrderDetail(orderId);
            if(param==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(orderId));
                return gson.toJson(rtn);
            }
            if(param.getStatus()!=Constants.MT_STATUS_CODE_UNPROCESSED){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                jsonObject.put("desc","error");
                jsonObject.put("remark","订单已确实过，请更新状态");
                jsonObject.put("orderId",orderId);
                jsonObject.put("orderStatus",(new SysFacadeService()).tranMTOrderStatus(param.getStatus()));
                return jsonObject.toJSONString();
            }
            rtn.setDynamic(String.valueOf(orderId));
            json = mtApiService.getConfirmOrder(orderId);
            if (json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
                //更新门店是否接单标识字段
                mtInnerService.updateIsReceived(String.valueOf(orderId),1);
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException ex){
            rtn.setCode(ex.getCode());
            rtn.setDesc("error");
            rtn.setRemark(ex.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
        }
        return  gson.toJson(rtn);
    }


    /**
     * 商家取消订单
     * @param orderId - 订单id
     */
    public String getCancelOrder(long orderId,String reason,String reason_code) {
        String json = "";
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(orderId)){
            return gson.toJson(rtn);
        }
        try {
            //确认时判断是否接单
            OrderDetailParam param = getOrderDetail(orderId);
            if(param==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(orderId));
                return gson.toJson(rtn);
            }
            if(param.getStatus()!=Constants.MT_STATUS_CODE_UNPROCESSED){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                jsonObject.put("desc","error");
                jsonObject.put("remark","订单已确实过，请更新状态");
                jsonObject.put("orderId",orderId);
                jsonObject.put("orderStatus",param.getStatus());
                return jsonObject.toJSONString();
            }
            rtn.setDynamic(String.valueOf(orderId));
            json = mtApiService.getCancelOrder(orderId,reason,reason_code);
            if(json.equals("ok") || json.equals("200")){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
                //更新门店是否接单标识字段
                mtInnerService.updateIsReceived(String.valueOf(orderId),2);
            }
        }catch (MeiTuanException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
        }catch (ApiOpException ex){
            rtn.setCode(ex.getCode());
            rtn.setDesc("error");
            rtn.setRemark(ex.getMsg());
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
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
        }
        return  gson.toJson(rtn);
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
            if (orderWaiMai !=null&&orderWaiMai.getPlatform().equals(Constants.PLATFORM_WAIMAI_MEITUAN)&& orderWaiMai.getPlatformOrderId().equals(platformOrderId)){
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
            orderWaiMai.setSellerShopId(shopId);
            orderWaiMai.setCreateTime(new Date());
            sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
            result = "{\"data\" : \"ok\"}" ;
        }catch (Exception ex){
           result = "{\"code\":700,\"msg\":\"系统异常\"}";
        }
        finally {
            if(StringUtil.isEmpty(result)){
                result = "{\"data\" : \"ok\"}";
            }
            return result;
        }
    }

    /**
     * 推送订单订单状态（已确认、已完成）
     * @return
     */
    public String getChangeOrderStatus(JsonObject jsonObject,Boolean flag) {
        Rtn rtn = new Rtn();
        Log log1 = null;
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
            if(StringUtil.isEmpty(shopId)){
                return  "{\"code\":800,\"msg\":\"未绑定商家门店ID\"}";
            }
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
            if(!flag){
               OrderDetailParam param = getOrderDetail(order.getOrderid());
                if(StringUtil.isEmpty(param)){
                   return  "{\"code\":700,\"msg\":\"订单状态推送异常\"}";
                }
                mtInnerService.updateStatus(String.valueOf(order.getOrderid()),param.getStatus());
                sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_MEITUAN,Integer.valueOf(param.getStatus()),platformOrderId,null,shopId,null);
            }else {
                sysFacadeService.updateWaiMaiOrder(String.valueOf(order.getOrderid()), orderWaiMai);
                sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_MEITUAN, order.getStatus(),order.getOrderid().toString(),orderWaiMai.getOrderId(),shopId,null);
            }
            result = "{\"data\" : \"ok\"}" ;
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
            result = "{\"code\":700,\"msg\":\"系统异常\"}";
        }
        finally {
            if (log1 != null) {
                log1.setLogId(log1.getLogId());
                log1.setTitle("推送订单失败");
                sysFacadeService.updSynLog(log1);
            }
            if(StringUtil.isEmpty(result)){
                result = "{\"data\" : \"ok\"}";
            }
            return result;
        }
    }

    /**
     * 配送订单状态推送
     * @param delivery
     * @param flag
     * @return
     */
    public String getDeliveryOrderStatus(Delivery delivery,Boolean flag){
        Rtn rtn = new Rtn();
        Log log1 = null;
        String result = "";
        if(delivery==null || StringUtil.isEmpty(delivery.getOrder_id())){
            return  "{\"code\":801,\"msg\":\"配送订单状态推送失败\"}";
        }
        try{
            OrderDetailParam param = getOrderDetail(delivery.getOrder_id());
            if(StringUtil.isEmpty(param)){
                return  "{\"code\":802,\"msg\":\"配送订单不存在\"}";
            }
            mtInnerService.updateStatus(String.valueOf(delivery.getOrder_id()),delivery.getLogistics_status());
            if(delivery.getLogistics_status()==Constants.MT_STATUS_CODE_RECEIVED){
                sysFacadeService.topicMessageOrderDelivery(Constants.PLATFORM_WAIMAI_MEITUAN, delivery.getLogistics_status(), delivery.getOrder_id().toString(),
                    delivery.getDispatcher_mobile(), delivery.getDispatcher_name(), param.getApp_poi_code());
            }
            return "{\"data\" : \"ok\"}";
        }catch (Exception ex){
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
            result = "{\"code\":700,\"msg\":\"系统异常\"}";
        }
        finally {
            if (log1 != null) {
                log1.setLogId(log1.getLogId());
                log1.setTitle("推送订单失败");
                sysFacadeService.updSynLog(log1);
            }
        }
        if(StringUtil.isEmpty(result)){
            return "{\"data\" : \"ok\"}";
        }
        return result;
    }


    /**
     * 通过订单id,获取订单明细.
     * @return
     */
    public OrderDetailParam getOrderDetail(long orderId) {
        Rtn rtn = new Rtn();
        Log log1 = null;
        OrderDetailParam orderDetailParam = null;
        if(StringUtil.isEmpty(orderId)){
            return orderDetailParam;
        }
        try {
            rtn.setDynamic(String.valueOf(orderId));
            orderDetailParam = mtApiService.getOrderDetail(orderId);
        } catch (MeiTuanException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        } catch (ScheduleException ex) {
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
        } catch (ApiOpException ex) {
            rtn.setCode(ex.getCode());
            rtn.setDesc("error");
            rtn.setRemark(ex.getMsg());
        } catch (Exception ex) {
            rtn.setCode(-998);
            log1 = sysFacadeService.functionRtn.apply(ex);
        } finally {
            //有异常产生
            if (log1 != null) {
                log1.setLogId(String.valueOf(orderId).concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("获取{0}订单明细失败", String.valueOf(orderId)));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"meituan_shop_id\":{1}", String.valueOf(orderId), "")).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
            return orderDetailParam;
        }

    }

//endregion



}

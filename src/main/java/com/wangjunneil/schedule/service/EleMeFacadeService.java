package com.wangjunneil.schedule.service;

import com.google.gson.*;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ElemeException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeFacadeService {
    @Autowired
    private EleMeApiService eleMeApiService;
    @Autowired
    private EleMeInnerService eleMeInnerService;

    @Autowired
    private SysFacadeService sysFacadeService;

    private Gson gson;

    private Gson getGson(){
        if (gson == null){
            gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                .registerTypeAdapter(Body.class, new BodySerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(Result.class,new ResultSerializer())
                .registerTypeAdapter(OldFoodsRequest.class,new OldFoodsRequestSerializer())
                .registerTypeAdapter(Distribution.class,new DistributionSerializer())
                .registerTypeAdapter(Restaurant.class,new RestaurantSerializer())
                .registerTypeAdapter(OldFoodsRequest.class,new OldFoodsRequestSerializer())
                .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                    @Override
                    public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                        if (aDouble == aDouble.longValue())
                            return new JsonPrimitive(aDouble.longValue());
                        return new JsonPrimitive(aDouble);
                    }
                })
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        }
        return gson;
    }

    /**
     * 开关店
     * @param merchantId 店铺id
     * @param status 1:开店/0:关店
     * @return
     */
    public String setRestaurantStatus(String merchantId,String status){
        String result = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(merchantId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantId)) {
            rtn.setCode(-1);
            rtn.setDesc("门店ID为空");
            rtn.setRemark("门店ID为空");
        }else {
            try {
                RestaurantRequest restaurantRequest = new RestaurantRequest();
                restaurantRequest.setRestaurant_id(merchantId);
                restaurantRequest.setIs_open(status);
                result = eleMeApiService.setRestaurantStatus(restaurantRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if("ok".equals(obj.getMessage())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("门店{0}不存在或无权限管理", merchantId));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log!=null){
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("门店{0}{1}失败", merchantId,status=="1"?"开业":"歇业"));
                    if (StringUtil.isEmpty(log.getRequest()))
                        log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"eleme_shop_id\":{1}", merchantId, "")).concat("}"));
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("门店{0}{1}失败！",merchantId,status=="1"?"开业":"歇业"));
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 主动拉取新订单
     * @param merchantId 商户Id
     * 获取前30分钟的最新订单，参数可以空值。
     * @return
     */
    public String pullNewOrder(String merchantId){
        Log log = null;
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setRestaurant_id(merchantId);
                String result = eleMeApiService.pullNewOrder(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
            } finally {
                if (log != null) {
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("拉取门店{0}新订单失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_shop_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }
    /**
     * 查询新订单
     * @param merchantId 商户Id
     * 获取前30分钟的最新订单，参数可以空值。
     * @return
     */
    public String findNewOrder(String merchantId){
        Log log = null;
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setRestaurant_id(merchantId);
                String result = eleMeApiService.findNewOrder(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
            } finally {
                if (log != null) {
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("拉取门店{0}新订单失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_shop_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }

    /**
     * 订单状态修改
     * @param elemeOrderId 订单ID
     * @param status 查看Enum.java的<订单状态--饿了么>
     * @param reason 取消订单原因,不是取消订单请填写""或者null
     * @return
     */
    public String upOrderStatus(String elemeOrderId, String status, String reason){
        String result = null;
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderId)) {
            rtn.setCode(-1);
            rtn.setDesc("订单ID为空");
            rtn.setRemark("订单ID为空");
        }else {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setEleme_order_id(elemeOrderId);
                orderRequest.setStatus(status);
                if(!StringUtil.isEmpty(reason)) {
                    orderRequest.setReason(reason);
                }
                result = eleMeApiService.upOrderStatus(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("订单{0}不存在或无权限管理", elemeOrderId));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("修改订单{0}状态失败", elemeOrderId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_id\":{0},\"status\":{1},\"reason\":{2}", elemeOrderId, status, reason)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("修改订单{0}状态失败!",elemeOrderId));
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 添加食物
     * @param json json字符串(保证key与实体属性对应)
     * @return
     */
    public String addFoods(String json){
        if (json == null || "".equals(json)) return "食物数据为空!";
        String result = null;
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(json)) {
            rtn.setCode(-1);
            rtn.setDesc("食物数据为空");
            rtn.setRemark("食物数据为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
                result = eleMeApiService.addFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("添加食物失败,{0}必填", obj.getMessage().toString()));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle("添加食物失败!");
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark("添加食物失败");
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     *  查询餐厅菜单
     * 此接口不推荐使用
     * @param merchantId 商户ID
     * @return
     */
    public String restaurantMenu(String merchantId){
        Log log = null;
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                RestaurantRequest restaurantRequest = new RestaurantRequest();
                restaurantRequest.setRestaurant_id(merchantId);
                String result = eleMeApiService.restaurantMenu(restaurantRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("查询门店{0}菜单失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }

    /**
     * 新订单接收
     * @param elemeOrderIds eleme平台订单id
     * @return
     */
    public String getNewOrder(String elemeOrderIds){
        List<String> listIds = new ArrayList<String>();
        Log[] log = {null};
        String [] rtnStr = {null};
        if (StringUtil.isEmpty(elemeOrderIds)) {
            log[0].setLogId(elemeOrderIds.concat(log[0].getLogId()));
            log[0].setTitle(MessageFormat.format("订单{0}为空", elemeOrderIds));
        }else {
            Collections.addAll(listIds, elemeOrderIds.split(","));
            listIds.forEach((id)->{
                try {
                    OrderRequest orderRequest = new OrderRequest();
                    orderRequest.setEleme_order_id(id);
                    String result = eleMeApiService.orderDetail(orderRequest);
                    Result obj = getGson().fromJson(result, Result.class);
                    Order order = getGson().fromJson(getGson().toJson(obj.getData()), Order.class);
                    orderRequest.setEleme_order_ids(id);
                    String result1 = eleMeApiService.getDeliveryInfo(orderRequest);
                    Body obj1 = getGson().fromJson(result1, Body.class);
                    Distribution distribution = null;
                    for (int i = 0;i < obj1.getData().size(); i++) {
                        distribution = getGson().fromJson(getGson().toJson(obj1.getData().get(i)), Distribution.class);
                    }
                    order.setDistribution(distribution);
                    OrderWaiMai orderWaiMai = new OrderWaiMai();
                    orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
                    orderWaiMai.setPlatformOrderId(order.getOrderid().toString());
                    String orderId = sysFacadeService.getOrderNum(order.getRestaurantid());
                    orderWaiMai.setOrder(order);
                    orderWaiMai.setOrderId(orderId);
                    orderWaiMai.setShopId(order.getRestaurantid());
                    rtnStr[0] = "{\"message\": \"ok\"}";
                    sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
                }catch (ElemeException ex){
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }
                catch (ScheduleException ex) {
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }catch (Exception ex){
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }finally {
                    if (log[0] != null) {
                        log[0].setLogId(elemeOrderIds.concat(log[0].getLogId()));
                        log[0].setTitle(MessageFormat.format("接收新订单{0}or获取新订单{1}配送信息失败", elemeOrderIds,elemeOrderIds));
                        if (StringUtil.isEmpty(log[0].getRequest())) {
                            log[0].setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
                        }
                        sysFacadeService.updSynLog(log[0]);
                    }
                }
            });
        }
        return rtnStr[0];
    }


    /**
     * 消息订单修改状态
     * @param elemeOrderIds
     * @param newStatus
     * @return
     */
    public String orderChange(String elemeOrderIds,String newStatus){
        //温馨提醒：单个参数传输都使用String类型，因为Request对象的getparamter方法返回的均是String类型，外层做类型转换的话，如果发生异常则无法捕获，异常处理均在这一层处理，所以放在这里做类型转换更合适
        Log log =null;
        try {
            eleMeInnerService.updSyncElemeOrderStastus(elemeOrderIds, Integer.parseInt(newStatus));
        }catch (ScheduleException e){
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log = sysFacadeService.functionRtn.apply(e);
            log .setLogId(elemeOrderIds.concat(log.getLogId()));
            log.setTitle(MessageFormat.format("饿了么订单:{0}修改失败", elemeOrderIds));
            if (StringUtil.isEmpty(log.getRequest())) {
                log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
            }
            sysFacadeService.updSynLog(log);
            return "{\"message\": \"error\"}";
        }
        List<String> listIds = new ArrayList<String>();
        Collections.addAll(listIds, elemeOrderIds.split(","));
        listIds.forEach((id)->{
            sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_ELEME,Integer.valueOf(newStatus),id,null,null);
        });
        return  "{\"message\": \"ok\"}";
    }

    //退单状态接收  refund_status:退单订单状态
    public String chargeBack(String elemeOrderIds,String refundStatus) {
        Log log = null;
        try {
            eleMeInnerService.updSyncElemeOrderStastus(elemeOrderIds,Integer.parseInt(refundStatus));
        }catch (ScheduleException e){
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log = sysFacadeService.functionRtn.apply(e);
            log .setLogId(elemeOrderIds.concat(log.getLogId()));
            log.setTitle(MessageFormat.format("饿了么订单:{0}退单失败", elemeOrderIds));
            if (StringUtil.isEmpty(log.getRequest())) {
                log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
            }
            sysFacadeService.updSynLog(log);
            return "{\"message\": \"error\"}";
        }
        return  "{\"message\": \"ok\"}";
    }

    //订单配送状态接收
    public String distributionStatus(String elemeOrderIds,String statusCode,int sub_status_code){
        Log log =null;
        try {
            eleMeInnerService.updSyncElemeOrderStastus(elemeOrderIds,Integer.parseInt(statusCode));
        }catch (ScheduleException e){
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log = sysFacadeService.functionRtn.apply(e);
            log .setLogId(elemeOrderIds.concat(log.getLogId()));
            log.setTitle(MessageFormat.format("饿了么订单:{0}获取配送失败", elemeOrderIds));
            if (StringUtil.isEmpty(log.getRequest())) {
                log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
            }
            sysFacadeService.updSynLog(log);
            return "{\"message\": \"error\"}";
        }
        return  "{\"message\": \"ok\"}";
    }

    /**
     * 通过第三方ID获取平台食物ID
     * @param parms 多个第三方ID可用逗号隔开 例如：1425223342,1425223343
     * @return
     */
    public String getFoodId(String parms) {
        Log log = null;
        if (!StringUtil.isEmpty(parms)) {
            try {
                OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                oldFoodsRequest.setTp_food_ids(parms);
                String result = eleMeApiService.getFoodId(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(parms.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("食物{0}获取平台食物ID失败", parms));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"parms\":{0}", parms)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }

    /**
     * 商品上下架
     * @param dishList
     * @param status 小于0->下架||大于0->上架
     * @return
     */
    public String upBatchFrame(List<ParsFromPosInner> dishList,String status) {
        Rtn rtn = new Rtn();
        String reponse = "";
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (dishList == null || dishList.size() < 1) {
            rtn.setCode(-1);
            rtn.setDesc("食物ID列表为空");
            rtn.setRemark("食物ID列表为空");
            return gson1.toJson(rtn);
        }else {
            for (int i = 0; i < dishList.size(); i++) {
                String result = getFoodId(dishList.get(i).getDishId());
                if ("failure".equals(result)) {
                    rtn.setCode(-1);
                    rtn.setDesc(MessageFormat.format("获取食物{0}出错!",dishList.get(i).getDishId()));
                    rtn.setRemark(MessageFormat.format("获取食物{0}出错!",dishList.get(i).getDishId()));
                    rtn.setDynamic(dishList.get(i).getDishId());
                    reponse += gson1.toJson(rtn);
                }else {
                    Body body = getGson().fromJson(result, Body.class);
                    reponse += StringUtil.isEmpty(reponse)?"":",";
                    if (!StringUtil.isEmpty(body.getFoodids().get(dishList.get(i).getDishId()))) {
                        for (int j = 0; j < body.getFoodids().get(dishList.get(i).getDishId()).size(); j++) {
                            OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                            oldFoodsRequest.setStock(status);
                            oldFoodsRequest.setFood_id(body.getFoodids().get(dishList.get(i).getDishId()).get(j).getFood_id());
                            reponse += uporDownFrame(dishList.get(i).getDishId(),getGson().toJson(oldFoodsRequest));
                        }
                    }else {
                        rtn.setCode(-1);
                        rtn.setDesc(MessageFormat.format("食物{0}不存在!",dishList.get(i).getDishId()));
                        rtn.setRemark(MessageFormat.format("食物{0}不存在!",dishList.get(i).getDishId()));
                        rtn.setDynamic(dishList.get(i).getDishId());
                        reponse += gson1.toJson(rtn);
                    }
                }
            }
        }
        return reponse;
    }

    /**
     * 更新食物
     * @param json json字符串(保证key与实体属性对应)
     * @return
     */
    public String uporDownFrame(String foodid, String json){
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        rtn.setDynamic(foodid);
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(json)) {
            rtn.setCode(-1);
            rtn.setDesc("食物数据为空");
            rtn.setRemark("食物数据为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
                result = eleMeApiService.upFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("更新食物{0}失败", foodid));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("更新食物{0}失败", foodid));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"json\":{0}", json)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("更新食物{0}失败", foodid));
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 获取餐厅信息
     * @param merchantId
     * @return
     */
    public String getRestaurantInfo(String merchantId) {
        String result = null;
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                RestaurantRequest restaurantRequest= new RestaurantRequest();
                restaurantRequest.setRestaurant_id(merchantId);
                result = eleMeApiService.getRestaurantInfo(restaurantRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取餐厅{0}信息失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }

    /**
     * 获取门店开关店状态
     * @param merchantId
     * @return
     */
    public String getStatus(String merchantId) {
        String result = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(merchantId);
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantId)) {
            rtn.setCode(-1);
            rtn.setDesc("餐厅ID为空");
            rtn.setRemark("餐厅ID为空");
        }else {
            String info = getRestaurantInfo(merchantId);
            if ("failure".equals(info)) {
                rtn.setCode(-1);
                rtn.setDesc("failure");
                rtn.setRemark(MessageFormat.format("获取餐厅{0}信息失败或餐厅不存在", merchantId));
            }else {
                Body body = getGson().fromJson(info, Body.class);
                if (body.getRestaurant().getIsopen() == 1) {
                    rtn.setCode(0);
                    rtn.setDesc("success");
                    rtn.setRemark(MessageFormat.format("餐厅{0}开店", merchantId));
                }else {
                    rtn.setCode(1);
                    rtn.setDesc("success");
                    rtn.setRemark(MessageFormat.format("获取餐厅{0}关店", merchantId));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 获取餐厅状态
     * @param merchantIds 多个餐厅以逗号隔开
     * @return
     */
    public String getRestaurantStatus(String merchantIds) {
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantIds)) {
            rtn.setCode(-1);
            rtn.setDesc("餐厅ID列表为空");
            rtn.setRemark("餐厅ID列表为空");
        }else {
            try {
                RestaurantRequest restaurantRequest= new RestaurantRequest();
                restaurantRequest.setRestaurant_ids(merchantIds);
                result = eleMeApiService.getRestaurantStatus(restaurantRequest);
                Result obj = getGson().fromJson(result, Result.class);
                Body body = getGson().fromJson(getGson().toJson(obj.getData()), Body.class);
                String[] split = merchantIds.split(",");
           /* for (int i = 0; i < split.length; i++) {
                if (!StringUtil.isEmpty(body.getBatch_status().getBatchstatus())) {
                    if ("1".equals(body.getBatch_status().getBatchstatus().get(split[i]).getIsvalid())&&"1".equals(body.getBatch_status().getBatchstatus().get(split[i]).getIsopen())){
                        rtn.setCode(0);
                        rtn.setRemark(MessageFormat.format("餐厅{0}开店成功", split[i]));
                    }
                    if ("1".equals(body.getBatch_status().getBatchstatus().get(split[i]).getIsvalid())&&"0".equals(body.getBatch_status().getBatchstatus().get(split[i]).getIsopen())){
                        rtn.setCode(1);
                        rtn.setRemark(MessageFormat.format("餐厅{0}关店成功", split[i]));
                    }
                    rtn.setDesc("success");
                    rtn.setDynamic(split[i]);
                }
                if ()
            }*/

            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取餐厅{0}信息失败", merchantIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantIds)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("获取餐厅{0}信息失败", merchantIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 同意退单
     * @param elemeOrderIds
     * @return
     */
    public String agreeRefund(String elemeOrderIds) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderIds);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderIds)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                String result = eleMeApiService.agreeRefund(elemeOrderIds);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(0);
                rtn.setDesc(obj.getMessage().toString());
            }catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            } catch (ElemeException ex) {
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex) {
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderIds.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("同意订单{0}退单失败", elemeOrderIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(elemeOrderIds);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("同意订单{0}退单失败!",elemeOrderIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 不同意退单
     * @param elemeOrderIds
     * @param reason
     * @return
     */
    public String disAgreeRefund(String elemeOrderIds, String reason) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderIds);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderIds)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setEleme_order_id(elemeOrderIds);
                orderRequest.setReason(reason);
                String result = eleMeApiService.disAgreeRefund(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(0);
                rtn.setDesc(obj.getMessage().toString());
            }catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            } catch (ElemeException ex) {
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex) {
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderIds.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("不同意订单{0}退单失败", elemeOrderIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0},\"reason\":{1}", elemeOrderIds, reason)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(elemeOrderIds);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("不同意订单{0}退单失败!",elemeOrderIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 通过商户ID获取餐厅ID
     * @param tpRestaurantId
     * @return
     */
    public String getRestaurantId(String tpRestaurantId) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(tpRestaurantId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setTp_restaurant_id(tpRestaurantId);
            return eleMeApiService.getRestaurantId(restaurantRequest);
        }catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
        } catch (ElemeException ex) {
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }catch (Exception ex) {
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
        }finally {
            if (log != null) {
                log.setLogId(tpRestaurantId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("获取餐厅{}失败", tpRestaurantId));
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"tpRestaurantId\":{0}", tpRestaurantId)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(tpRestaurantId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("获取餐厅{}失败!",tpRestaurantId));
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 批量删除食物
     * @param dishList
     * @return
     */
    public String deleteFoods(List<ParsFromPosInner> dishList) {
        if (dishList == null || dishList.size() < 1) return "食物ID为空!";
        Rtn rtn = new Rtn();
        Log log = null;
        StringBuilder sb = new StringBuilder();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (dishList == null || dishList.size() < 1) {
            rtn.setCode(1);
            rtn.setDesc("POS食物ID列表为空");
            rtn.setRemark("POS食物ID列表为空");
        }else {
            try {
                for (int i = 0; i < dishList.size(); i++) {
                    String result = getFoodId(dishList.get(i).getDishId());
                    Result obj = getGson().fromJson(result, Result.class);
                    Body body = getGson().fromJson(getGson().toJson(obj.getData()), Body.class);
                    if (body.getFoodids().get(dishList.get(i).getDishId()).size() > 0 ) {
                        if (i == 0) {
                            sb.append(body.getFoodids().get(dishList.get(i).getDishId()).get(0).getFood_id());
                        }else {
                            sb.append(","+body.getFoodids().get(dishList.get(i).getDishId()).get(0).getFood_id());
                        }
                    }
                }
                OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                oldFoodsRequest.setFood_ids(sb.toString());
                String result = eleMeApiService.delectAllFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(0);
                rtn.setDesc(obj.getMessage().toString());
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex){
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (Exception ex) {
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
            }finally {
                if (log!=null){
                    for (int i = 0; i < dishList.size(); i++) {
                        log.setLogId(dishList.get(i).getDishId().concat(log.getLogId()));
                        log.setTitle(MessageFormat.format("删除食物{0}失败", dishList.get(i).getDishId()));
                        if (StringUtil.isEmpty(log.getRequest()))
                            log.setRequest("{".concat(MessageFormat.format("\"dishList\":{0}", dishList.get(i).getDishId())).concat("}"));
                        sysFacadeService.updSynLog(log);
                        rtn.setDesc("发生异常");
                        rtn.setLogId(log.getLogId());
                        rtn.setRemark(MessageFormat.format("删除食物{0}失败！",dishList.get(i).getDishId()));
                    }
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 获取历史订单
     * @return
     */
    public String getHistoryOrder() {
        List<Order> orders = eleMeInnerService.findAll();
        return getGson().toJson(orders);
    }
}

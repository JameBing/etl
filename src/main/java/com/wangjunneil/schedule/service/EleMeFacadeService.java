package com.wangjunneil.schedule.service;


import com.google.gson.*;
import com.wangjunneil.schedule.common.ElemeException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.taglibs.standard.lang.jstl.ELEvaluator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
     * @param elemeShopId 店铺id
     * @param status 1:开店/0:关店
     * @return
     */
    //门店开关店
    public String setRestaurantStatus(String elemeShopId,String status){
        String result = "";
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeShopId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeShopId)) {
            rtn.setCode(1);
            rtn.setDesc("门店ID为空");
            rtn.setRemark("门店ID为空");
        }else {
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(elemeShopId);
            restaurantRequest.setIs_open(status);
            result = eleMeApiService.setRestaurantStatus(restaurantRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
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
                log.setLogId(elemeShopId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("门店{0}{1}失败", elemeShopId,status=="1"?"开业":"歇业"));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"eleme_shop_id\":{1}", elemeShopId, "")).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDynamic(elemeShopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}{1}失败！",elemeShopId,status=="1"?"开业":"歇业"));
            }
          }
        }

        return  gson1.toJson(rtn);
    }

    /**
     * 拉取新订单
     * @param elemeShopId 店铺ID
     * @return
     */
    public String pullNewOrder(String elemeShopId){
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeShopId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeShopId)) {
            rtn.setCode(1);
            rtn.setDesc("门店ID为空");
            rtn.setRemark("门店ID为空");
        }else {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setRestaurant_id(elemeShopId);
                return eleMeApiService.pullNewOrder(orderRequest);
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
            } finally {
                if (log != null) {
                    log.setLogId(elemeShopId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("拉取门店{0}新订单失败", elemeShopId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_shop_id\":{0}", elemeShopId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(elemeShopId);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("拉取门店{0}新订单失败！",elemeShopId));
                }
            }
        }
        return gson1.toJson(rtn);
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
            rtn.setCode(1);
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
                rtn.setCode(obj.getCode());
                rtn.setDesc(obj.getMessage());
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
                    rtn.setDynamic(elemeOrderId);
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
            rtn.setCode(1);
            rtn.setDesc("食物数据为空");
            rtn.setRemark("食物数据为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
                result = eleMeApiService.addFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setDesc(obj.getMessage());
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
     * @param restaurantId 店铺ID
     * @return
     */
    public String restaurantMenu(String restaurantId){
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        rtn.setDynamic(restaurantId);
        Log log = null;
        if (StringUtil.isEmpty(restaurantId)) {
            rtn.setCode(1);
            rtn.setDesc("餐厅ID为空");
            rtn.setRemark("餐厅ID为空");
        }else {
            try {
                RestaurantRequest restaurantRequest = new RestaurantRequest();
                restaurantRequest.setRestaurant_id(restaurantId);
                return eleMeApiService.restaurantMenu(restaurantRequest);
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
                    log.setLogId(restaurantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("查询门店{0}菜单失败", restaurantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", restaurantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(restaurantId);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("查询门店{0}菜单失败!",restaurantId));
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 更新食物
     * @param json json字符串(保证key与实体属性对应)
     * @return
     */
    public String uporDownFrame(String json){
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(json)) {
            rtn.setCode(1);
            rtn.setDesc("食物数据为空");
            rtn.setRemark("食物数据为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
                result = eleMeApiService.upFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setDesc(obj.getMessage());
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
                    log.setTitle("更新食物失败!");
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark("更新食物失败");
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 新订单接收
     * @param eleme_order_ids eleme平台订单id
     * @return
     */
    public String getNewOrder(String eleme_order_ids){
        List<String> listIds = new ArrayList<String>();
        Rtn rtn = new Rtn();
        Log[] log = {null};
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(eleme_order_ids)) {
            rtn.setCode(1);
            rtn.setDesc("订单ID为空");
            rtn.setRemark("订单ID为空");
        }else {
            Collections.addAll(listIds, eleme_order_ids.split(","));
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
                    eleMeInnerService.addSyncOrder(order);
                    rtn.setCode(obj.getCode());
                    rtn.setDesc(obj.getMessage());
                }catch (ElemeException ex){
                    rtn.setCode(-997);
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }
                catch (ScheduleException ex) {
                    rtn.setCode(-999);
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }catch (Exception ex){
                    rtn.setCode(-998);
                    log[0] = sysFacadeService.functionRtn.apply(ex);
                }finally {
                    if (log != null) {
                        log[0].setLogId(eleme_order_ids.concat(log[0].getLogId()));
                        log[0].setTitle(MessageFormat.format("接收新订单{0}or获取新订单{1}配送信息失败", eleme_order_ids,eleme_order_ids));
                        if (StringUtil.isEmpty(log[0].getRequest())) {
                            log[0].setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", eleme_order_ids)).concat("}"));
                        }
                        sysFacadeService.updSynLog(log[0]);
                        rtn.setDynamic(eleme_order_ids);
                        rtn.setDesc("发生异常");
                        rtn.setLogId(log[0].getLogId());
                        rtn.setRemark(MessageFormat.format("接收新订单{0}or获取新订单{1}配送信息失败!",eleme_order_ids, eleme_order_ids));
                    }
                }
            });
        }
        return gson1.toJson(rtn);
    }

    //订单状态变更接收   new_status：订单状态
    public String orderChange(String eleme_order_ids,String new_status){
        //温馨提醒：单个参数传输都使用String类型，因为Request对象的getparamter方法返回的均是String类型，外层做类型转换的话，如果发生异常则无法捕获，异常处理均在这一层处理，所以放在这里做类型转换更合适
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids, Integer.parseInt(new_status));
        return null;
    }
    //退单状态接收  refund_status:退单订单状态
    public String chargeBack(String eleme_order_ids,String refund_status){
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids,Integer.parseInt(refund_status));
        return  null;
    }
    //订单配送状态接收
    public String distributionStatus(String eleme_order_ids,String status_code,int sub_status_code){
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids,Integer.parseInt(status_code));
        return  null;
    }

    /**
     * 通过第三方ID获取平台食物ID
     * @param parms 多个第三方ID可用逗号隔开 例如：1425223342,1425223343
     * @return
     */
    public String getFoodId(String parms) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(parms);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(parms)) {
            rtn.setCode(1);
            rtn.setDesc("POS食物ID为空");
            rtn.setRemark("POS食物ID为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                oldFoodsRequest.setTp_food_ids(parms);
                return eleMeApiService.getFoodId(oldFoodsRequest);
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
                    log.setLogId(parms.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("通过POS食物{0}获取平台ID失败", parms));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"parms\":{0}", parms)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(parms);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("通过POS食物{0}获取平台ID失败!",parms));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 商品上下架
     * @param dishList
     * @param status 小于0->下架||大于0->上架
     * @return
     */
    public String upBatchFrame(List<ParsFromPosInner> dishList,String status) {
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (dishList == null || dishList.size() < 1) {
            rtn.setCode(1);
            rtn.setDesc("POS食物ID列表为空");
            rtn.setRemark("POS食物ID列表为空");
        }else {
            try {
                HashMap<String, OldFoodsRequest> map = new HashMap<String, OldFoodsRequest>();
                for (int i = 0; i < dishList.size(); i++) {
                    String result = getFoodId(dishList.get(i).getDishId());
                    Result obj = getGson().fromJson(result, Result.class);
                    Body body = getGson().fromJson(getGson().toJson(obj.getData()), Body.class);
                    if (body.getFoodids().get(dishList.get(i).getDishId()).size() > 0 ) {
                        OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                        oldFoodsRequest.setStock(status);
                        map.put(body.getFoodids().get(dishList.get(i).getDishId()).get(0).getFood_id(), oldFoodsRequest);
                    }
                }
                OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                oldFoodsRequest.setFoods_info(map);
                String result = eleMeApiService.upBatchFrame(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setRemark(obj.getMessage());
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
                        log.setTitle(MessageFormat.format("食物{0}{1}失败", dishList.get(i).getDishId(),status=="0"?"下架":"上架"));
                        if (StringUtil.isEmpty(log.getRequest()))
                            log.setRequest("{".concat(MessageFormat.format("\"dishList\":{0}", dishList.get(i).getDishId())).concat("}"));
                        sysFacadeService.updSynLog(log);
                        rtn.setDesc("发生异常");
                        rtn.setLogId(log.getLogId());
                        rtn.setRemark(MessageFormat.format("食物{0}{1}失败！",dishList.get(i).getDishId(),status=="0"?"下架":"上架"));
                    }
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 删除食物
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
                rtn.setCode(obj.getCode());
                rtn.setRemark(obj.getMessage());
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

    /**
     * 同意退单
     * @param eleme_order_ids
     * @return
     */
    public String agreeRefund(String eleme_order_ids) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(eleme_order_ids);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(eleme_order_ids)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                String result = eleMeApiService.agreeRefund(eleme_order_ids);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setRemark(obj.getMessage());
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
                    log.setLogId(eleme_order_ids.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("同意订单{0}退单失败", eleme_order_ids));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", eleme_order_ids)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(eleme_order_ids);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("同意订单{0}退单失败!",eleme_order_ids));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 不同意退单
     * @param eleme_order_ids
     * @param reason
     * @return
     */
    public String disAgreeRefund(String eleme_order_ids, String reason) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(eleme_order_ids);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(eleme_order_ids)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setEleme_order_id(eleme_order_ids);
                orderRequest.setReason(reason);
                String result = eleMeApiService.disAgreeRefund(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setRemark(obj.getMessage());
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
                    log.setLogId(eleme_order_ids.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("不同意订单{0}退单失败", eleme_order_ids));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0},\"reason\":{1}", eleme_order_ids, reason)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(eleme_order_ids);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("不同意订单{0}退单失败!",eleme_order_ids));
                }
            }
        }
        return gson1.toJson(rtn);
    }

}

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
        if ("".equals(elemeShopId) || elemeShopId == null) {
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
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     * 拉取新订单
     * @param elemeShopId 店铺ID
     * @return
     */
    public String pullNewOrder(String elemeShopId){
        if ("".equals(elemeShopId) || elemeShopId == null) return "餐厅ID为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setRestaurant_id(elemeShopId);
            return eleMeApiService.pullNewOrder(orderRequest);
        }catch (ElemeException ex){}
        catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex){}
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
        if (elemeOrderId == null || "".equals(elemeOrderId)) return "订单id为空!";
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setEleme_order_id(elemeOrderId);
            orderRequest.setStatus(status);
            if(!"".equals(reason) || reason != null) {
                orderRequest.setReason(reason);
            }
            result = eleMeApiService.upOrderStatus(orderRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
            rtn.setDynamic(status);
        }catch (ElemeException ex){}
        catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex){}
        result = gson1.toJson(rtn);
        return  result;
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
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
            result = eleMeApiService.addFoods(oldFoodsRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
        }catch (ElemeException ex){}
        catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex){}
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     *  查询餐厅菜单
     * @param restaurantId 店铺ID
     * @return
     */
    public String restaurantMenu(String restaurantId){
        if (restaurantId == null || "".equals(restaurantId)) return "餐厅ID为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(restaurantId);
            return eleMeApiService.restaurantMenu(restaurantRequest);
        }catch (ElemeException ex){}
        catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex){}
        return  gson1.toJson(rtn);
    }

    /**
     * 更新食物
     * @param json json字符串(保证key与实体属性对应)
     * @return
     */
    public String uporDownFrame(String json){
        if (json == null || "".equals(json)) return "食物数据为空!";
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
            result = eleMeApiService.upFoods(oldFoodsRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
        }catch (ElemeException ex){}
        catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        catch (Exception ex){}
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     * 新订单接收
     * @param eleme_order_ids eleme平台订单id
     * @return
     */
    public String getNewOrder(String eleme_order_ids){
        if (eleme_order_ids == null && "".equals(eleme_order_ids)) return "订单id列表为空!";
        List<String> listIds = new ArrayList<String>();
        Collections.addAll(listIds, eleme_order_ids.split(","));
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
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
                rtn.setLogId("");
                rtn.setDesc(obj.getMessage());
            }catch (ElemeException ex){}
            catch (ScheduleException ex) {
                rtn.setLogId("");
                rtn.setCode(-999);
                rtn.setRemark("发生异常");
                rtn.setDesc("error");
            }catch (Exception ex){}
        });
        return gson1.toJson(rtn);
    }

    //订单状态变更接收   new_status：订单状态
    public String orderChange(String eleme_order_ids,String new_status){
        if (eleme_order_ids == null || "".equals(eleme_order_ids)) return "订单id列表为空!";
        //温馨提醒：单个参数传输都使用String类型，因为Request对象的getparamter方法返回的均是String类型，外层做类型转换的话，如果发生异常则无法捕获，异常处理均在这一层处理，所以放在这里做类型转换更合适
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids, Integer.parseInt(new_status));
        return null;
    }
    //退单状态接收  refund_status:退单订单状态
    public String chargeBack(String eleme_order_ids,String refund_status){
        if (eleme_order_ids == null || "".equals(eleme_order_ids)) return "订单id列表为空!";
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids,Integer.parseInt(refund_status));
        return  null;
    }
    //订单配送状态接收
    public String distributionStatus(String eleme_order_ids,String status_code,int sub_status_code){
        if (eleme_order_ids == null || "".equals(eleme_order_ids)) return "订单id列表为空!";
        eleMeInnerService.updSyncElemeOrderStastus(eleme_order_ids,Integer.parseInt(status_code));
        return  null;
    }

    /**
     * 通过第三方ID获取平台食物ID
     * @param parms 多个第三方ID可用逗号隔开 例如：1425223342,1425223343
     * @return
     */
    public String getFoodId(String parms) {
        if (parms == null || "".equals(parms)) return "食物id列表为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
            oldFoodsRequest.setTp_food_ids(parms);
            return eleMeApiService.getFoodId(oldFoodsRequest);
        }catch (Exception ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
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
        if (dishList == null || dishList.size() < 1) return "食物ID为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
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
            rtn.setLogId("");
            rtn.setCode(obj.getCode());
            rtn.setRemark(obj.getMessage());
        }catch (ElemeException ex){}
        catch (ScheduleException ex){}
        catch (Exception ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
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
        StringBuilder sb = new StringBuilder();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
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
            rtn.setLogId("");
            rtn.setCode(obj.getCode());
            rtn.setRemark(obj.getMessage());
        }catch (ElemeException ex){}
        catch (ScheduleException ex){}
        catch (Exception ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        return gson1.toJson(rtn);
    }

    public String getHistoryOrder() {
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            List<Order> orders = eleMeInnerService.findAll();
            return getGson().toJson(orders);
        }
        catch (ScheduleException ex){}
        catch (Exception ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        return gson1.toJson(rtn);
    }

    /**
     * 同意退单
     * @param eleme_order_ids
     * @return
     */
    public String agreeRefund(String eleme_order_ids) {
        if (eleme_order_ids == null && "".equals(eleme_order_ids)) return "订单id列表为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            String result = eleMeApiService.agreeRefund(eleme_order_ids);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setLogId("");
            rtn.setCode(obj.getCode());
            rtn.setRemark(obj.getMessage());
        }catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex) {

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
        if (eleme_order_ids == null && "".equals(eleme_order_ids)) return "订单id列表为空!";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setEleme_order_id(eleme_order_ids);
            orderRequest.setReason(reason);
            String result = eleMeApiService.disAgreeRefund(orderRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setLogId("");
            rtn.setCode(obj.getCode());
            rtn.setRemark(obj.getMessage());
        }catch (ScheduleException ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }catch (Exception ex) {

        }
        return gson1.toJson(rtn);
    }

}

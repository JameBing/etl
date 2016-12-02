package com.wangjunneil.schedule.service;


import com.google.gson.*;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
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

    private Gson gson;

    private Gson getGson(){
        if (gson == null){
            gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                .registerTypeAdapter(Body.class, new BodySerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(Result.class,new ResultSerializer())
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
     * @param elemeShopId 店铺id
     * @param status 1:开店/0:关店
     * @return
     */
    public String setRestaurantStatus(String elemeShopId,String status){
        String result = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(elemeShopId);
            restaurantRequest.setIs_open(status);
            result = eleMeApiService.setRestaurantStatus(restaurantRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
            rtn.setDynamic(status);
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
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
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setRestaurant_id(elemeShopId);
            return eleMeApiService.pullNewOrder(orderRequest);
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
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
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     * 商品上下架（通过修改库存）
     * @param elemeFoodId 食物Id
     * @param stock
     * @return
     */
    public String uporDownFrame(String elemeFoodId ,String stock){
        String result = null;
        Result obj = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class, new RtnSerializer())
                                   .disableHtmlEscaping().create();
        try {
            result = getFoodId(elemeFoodId);
            obj = getGson().fromJson(result, Result.class);
            Body body = getGson().fromJson(getGson().toJson(obj.getData()), Body.class);
            if (body.getFoodids().get(elemeFoodId).size() > 0 ) {
                OldFoodsRequest orderRequest = new OldFoodsRequest();
                orderRequest.setFood_id(body.getFoodids().get(elemeFoodId).get(0).getFood_id());
                orderRequest.setStock(stock);
                result = eleMeApiService.uporDownFrame(orderRequest);
                obj = getGson().fromJson(result, Result.class);
                rtn.setCode(obj.getCode());
                rtn.setLogId("");
                rtn.setDesc(obj.getMessage());
                rtn.setDynamic(stock);
            }else {
                rtn.setLogId("");
                rtn.setDesc("食物不存在");
                rtn.setDynamic(stock);
            }
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     * 添加食物
     * @param json json字符串(保证key与实体属性对应)
     * @return
     */
    public String addFoods(String json){
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
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     *  查询餐厅菜单
     * @param restaurantId 店铺ID
     * @return
     */
    public String restaurantMenu(String restaurantId){
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            return eleMeApiService.restaurantMenu(restaurantId);
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
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
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
            result = eleMeApiService.uporDownFrame(oldFoodsRequest);
            Result obj = getGson().fromJson(result, Result.class);
            rtn.setCode(obj.getCode());
            rtn.setLogId("");
            rtn.setDesc(obj.getMessage());
        } catch (ScheduleException e) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    /**
     * 新订单接收
     * @param eleme_order_ids eleme平台食物id
     * @return
     */
    public String getNewOrder(String eleme_order_ids){
        List<String> listIds = new ArrayList<String>();
        Collections.addAll(listIds, eleme_order_ids.split(","));
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        listIds.forEach((id)->{
            try {
                String result = eleMeApiService.orderDetail(id);
                Result obj = getGson().fromJson(result, Result.class);
                eleMeInnerService.updSyncElemeOrder((Order)obj.getData());
                rtn.setCode(obj.getCode());
                rtn.setLogId("");
                rtn.setDesc(obj.getMessage());
            } catch (ScheduleException e) {
                rtn.setLogId("");
                rtn.setCode(-999);
                rtn.setRemark("发生异常");
                rtn.setDesc("error");
            }
        });
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
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        try {
            FoodsRequest foodsRequest = new FoodsRequest();
            foodsRequest.setTp_food_ids(parms);
            return eleMeApiService.getFoodId(foodsRequest);
        }catch (Exception ex) {
            rtn.setLogId("");
            rtn.setCode(-999);
            rtn.setRemark("发生异常");
            rtn.setDesc("error");
        }
        return gson1.toJson(rtn);
    }

}

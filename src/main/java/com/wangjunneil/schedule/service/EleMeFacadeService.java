package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .serializeNulls()
                .disableHtmlEscaping()
                .create();
        }
        return gson;
    }

    //门店开关店
    public String setRestaurantStatus(String elemeShopId,int status){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(elemeShopId);
            restaurantRequest.setIs_open(status);
            result = eleMeApiService.setRestaurantStatus(restaurantRequest);
            rtn = gson1.fromJson(result, Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //拉取新订单
    public String pullNewOrder(String elemeShopId){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setRestaurant_id(elemeShopId);
            result = eleMeApiService.pullNewOrder(orderRequest);
            rtn = gson1.fromJson(result, Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //订单状态修改
    public String upOrderStatus(String elemeOrderId, int status){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setEleme_order_id(elemeOrderId);
            orderRequest.setStatus(status);
            result = eleMeApiService.upOrderStatus(orderRequest);
            rtn = gson1.fromJson(result, Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //商品上下架
    public String uporDownFrame(String elemeFoodId ,int status){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest orderRequest = new OldFoodsRequest();
            orderRequest.setFood_id(elemeFoodId);
            orderRequest.setStock(status);
            result = eleMeApiService.uporDownFrame(orderRequest);
            rtn = gson1.fromJson(result, Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //添加食品
    public String addFoods(OldFoodsRequest obj){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            OldFoodsRequest orderRequest = new OldFoodsRequest();
//            orderRequest.setFood_id(obj.getFood_category_id());
//            orderRequest.setName(obj.getName());
//            orderRequest.setPrice(obj.getPrice());
//            orderRequest.setDescription(obj.getDescription());
//            orderRequest.setMax_stock(obj.getMax_stock());
//            orderRequest.setStock(obj.getStock());
            result = eleMeApiService.addFoods(obj);
            rtn = gson1.fromJson(result,Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //查询餐厅菜单
    public String restaurantMenu(){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            result = eleMeApiService.restaurantMenu();
            rtn = gson1.fromJson(result, Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //更新食物
    public String uporDownFrame(OldFoodsRequest obj){
        String result = null;
        Result rtn = new Result();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new ResultSerializer()).disableHtmlEscaping().create();
        try {
            obj.getFood_id();
            obj.getPrice();
            result = eleMeApiService.uporDownFrame(obj);
            rtn = gson1.fromJson(result,Result.class);
        } catch (ScheduleException e) {
            rtn.setCode(-999);
            rtn.setMessage("发生异常");
        }
        result = gson1.toJson(rtn);
        return  result;
    }

    //新订单接收
    public String getNewOrder(String eleme_order_ids){

        return null;
    }
    //订单状态变更接收   new_status：订单状态
    public String orderChange(String eleme_order_ids,int new_status){

        return null;
    }
    //退单状态接收  refund_status:退单订单状态
    public String chargeBack(String eleme_order_ids,int refund_status){

        return  null;
    }
    //订单配送状态接收
    public String distributionStatus(String eleme_order_ids,int status_code,int sub_status_code){

        return  null;
    }

}

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
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(elemeShopId);
            restaurantRequest.setIs_open(status);
            result = eleMeApiService.setRestaurantStatus(restaurantRequest);
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //拉取新订单
    public String pullNewOrder(String elemeShopId){
        String result = null;
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setRestaurant_id(elemeShopId);
            result = eleMeApiService.pullNewOrder(orderRequest);
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //订单状态修改
    public String upOrderStatus(String elemeOrderId, int status){
        String result = null;
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setEleme_order_id(elemeOrderId);
            orderRequest.setStatus(status);
            result = eleMeApiService.upOrderStatus(orderRequest);
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //商品上下架
    public String Upordownframe(String elemeFoodId ,int status){
        String result = null;
        try {
            OldFoodsRequest orderRequest = new OldFoodsRequest();
            orderRequest.setFood_id(elemeFoodId);
            orderRequest.setStock(status);
            result = eleMeApiService.Upordownframe(orderRequest);
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }

    //添加食品
    public String addFoods(OldFoodsRequest obj){
        String result = null;
        try {
            OldFoodsRequest orderRequest = new OldFoodsRequest();
            orderRequest.setFood_id(obj.getFood_category_id());
            orderRequest.setName(obj.getName());
            orderRequest.setPrice(obj.getPrice());
            orderRequest.setDescription(obj.getDescription());
            orderRequest.setMax_stock(obj.getMax_stock());
            orderRequest.setStock(obj.getStock());
            result = eleMeApiService.addFoods(orderRequest);
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }
    //查询餐厅菜单
    public String restaurantmenu(){
        String result = null;
        try {
            result = eleMeApiService.restaurantmenu();
        } catch (ScheduleException e) {
            e.printStackTrace();
        }
        return  result;
    }


}

package com.wangjunneil.schedule.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.wangjunneil.schedule.common.MeiTuanException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.meituan.*;
import com.wangjunneil.schedule.service.meituan.MeiTuanApiService;
import com.wangjunneil.schedule.service.meituan.MeiTuanInnerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


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

    //日志配置
    private static Logger log = Logger.getLogger(JdHomeFacadeService.class.getName());



    //region 门店开业,歇业

    /**
     * 门店开业
     * @parama app_poi_code - APP方门店id
     */
    public String openShop(String code)throws ScheduleException
    {
        try {
            String json = mtApiService.openShop(code);
            return json;
        }catch (Exception ex){
            throw new ScheduleException("meituan",ex.getClass().getName(),"",code,new Throwable().getStackTrace());
        }
    }


    /**
     * 门店歇业
     * @params app_poi_code - APP方门店id
     */
    public String closeShop(String code) throws ScheduleException
    {
        try {
            String json = mtApiService.poiClose(code);
            return json;
        }catch (Exception ex){
            throw new ScheduleException("meituan",ex.getClass().getName(),"",code,new Throwable().getStackTrace());
        }
    }

    //endregion


    //region 菜品类接口

    /**
     * 商品上架
     * @parama
     */
    public String upFrame(String appPoiCode,String foodCode)throws ScheduleException
    {
        try {
            String json = mtApiService.upFrame(appPoiCode,foodCode);
            return json;
        }catch (Exception ex){
            throw new ScheduleException("meituan",ex.getClass().getName(),"",foodCode,new Throwable().getStackTrace());
        }
    }


    /**
     * 商品下架
     * @parama
     */
    public String downFrame(String appPoiCode,String foodCode)throws ScheduleException
    {
        try {
            String json = mtApiService.downFrame(appPoiCode, foodCode);
            return json;
        }catch (Exception ex){
            throw new ScheduleException("meituan",ex.getClass().getName(),"",foodCode,new Throwable().getStackTrace());
        }
    }


    /**
     * 创建菜品
     * @parama app_poi_code - APP方门店id
     */
    public String createFood(FoodParam foodParam)throws ScheduleException
    {
        try {
            String json = mtApiService.foodCreate(foodParam);
            return json;
        }catch (Exception ex){
            throw new ScheduleException("meituan",ex.getClass().getName(),"",foodParam.getApp_food_code(),new Throwable().getStackTrace());
        }
    }

    /**
     * 查询所有菜品
     *
     */
    public FoodParam foodList(String appPoiCode, String foodCode) throws Exception {
        FoodParam foodParam = mtApiService.foodList(appPoiCode,foodCode);
        return foodParam;
    }

    //endregion


    //region 订单相关

    /**
     * 商家确认订单
     * @param orderid - 订单id
     */
    public String getConfirmOrder(long orderid)throws ScheduleException {
        try {
            String json = mtApiService.getConfirmOrder(orderid);
            return json;
        }catch (Exception e){
            throw new ScheduleException("meituan",e.getClass().getName(),"","",new Throwable().getStackTrace());
        }
    }


    /**
     * 商家取消订单
     * @param orderid - 订单id
     */
    public String getCancelOrder(long orderid,String reason,String reason_code)throws ScheduleException
    {
        try {
            String json = mtApiService.getCancelOrder(orderid,reason,reason_code);
            return json;
        }catch (Exception e)
        {
            throw new ScheduleException("meituan",e.getClass().getName(),"","",new Throwable().getStackTrace());
        }
    }


    /**
     * 新增订单推送（提供给美团方调用）
     */
    public OrderDetailParam newOrder(long orderId)throws ScheduleException
    {
       try {
            OrderDetailParam json = mtApiService.getOrderDetail(orderId);
            return json;
        }catch (Exception e)
        {
            throw new ScheduleException("meituan",e.getClass().getName(),"","",new Throwable().getStackTrace());
        }

    }




//endregion



}

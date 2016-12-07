package com.wangjunneil.schedule.service.meituan;

import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.Test;
import com.sankuai.meituan.waimai.opensdk.api.PoiAPI;
import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.factory.URLFactory;
import com.sankuai.meituan.waimai.opensdk.util.ConvertUtil;
import com.sankuai.meituan.waimai.opensdk.util.SignGenerator;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import com.wangjunneil.schedule.entity.meituan.FoodCreate;
import com.wangjunneil.schedule.entity.meituan.UpordownFrame;
import com.google.gson.Gson;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.mt.FoodRequest;
import com.wangjunneil.schedule.entity.mt.OrderRequest;
import com.wangjunneil.schedule.entity.mt.ShopRequest;
import com.wangjunneil.schedule.entity.mt.SysParams;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.MD5Util;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.*;
import java.util.Date;
import java.util.Map;

/**
 * @author liuxin
 * @since 2016-11-14.
 */
@Service
public class MeiTuanApiService {

    private static Logger log = Logger.getLogger(MeiTuanApiService.class.getName());

    //系统参数.（app_id 和 secret）
    private static final SystemParam sysPram = new SystemParam("459", "5ca2cf48c1d6dc4253f9d491b2246091");


    //region 门店的开业及歇业

    //门店开业
    public String openShop(String code) throws Exception {
        return APIFactory.getPoiAPI().poiOpen(sysPram, code);
    }

    //门店歇业
    public String closeShop(String code) throws Exception {
        return APIFactory.getPoiAPI().poiClose(sysPram, code);
    }

    //endregion


    //region商品的新增及上下架

    //新增商品 foodCreate
    public String foodCreate(FoodParam foodParam) throws Exception {
        return APIFactory.getFoodAPI().foodSave(sysPram, foodParam);
    }

    //查询所有商品
    public FoodParam foodList(String appPoiCode,String foodCode) throws Exception {
        List<FoodParam> foodList = APIFactory.getFoodAPI().foodList(sysPram, appPoiCode);
        if(foodList !=null && foodList.size()>0){
            for(FoodParam food :foodList){
                if(food.getApp_food_code().equals(foodCode)){
                    return food;
                }
            }
        }
        return null;
    }

    //批量上架商品 upFrame   (0：未卖光 )
    public String upFrame(String appPoiCode,String foodCode) throws Exception {
        FoodParam food = foodList(appPoiCode,foodCode);
        FoodParam foodParam = new FoodParam();
        foodParam.setApp_poi_code(appPoiCode);
        foodParam.setApp_food_code(foodCode);
        foodParam.setPrice(food.getPrice());
        foodParam.setCategory_name(food.getCategory_name());
        foodParam.setIs_sold_out(0);
        foodParam.setName(food.getName());
        return APIFactory.getFoodAPI().foodInitData(sysPram,foodParam);
    }

    //批量下架商品 downFrame (1：卖光)
    public String downFrame(String appPoiCode,String foodCode) throws Exception {
        FoodParam food = foodList(appPoiCode,foodCode);
        FoodParam foodParam = new FoodParam();
        foodParam.setApp_poi_code(appPoiCode);
        foodParam.setApp_food_code(foodCode);
        foodParam.setPrice(food.getPrice());
        foodParam.setCategory_name(food.getCategory_name());
        foodParam.setIs_sold_out(1);
        foodParam.setName(food.getName());
        return APIFactory.getFoodAPI().foodInitData(sysPram,foodParam);
    }

    //endregion


    //region 订单类接口（商家确认订单及商家取消订单）

    //商家确认订单
    public String getConfirmOrder(long orderId) throws Exception {
        return APIFactory.getOrderAPI().orderConfirm(sysPram,orderId);
    }

    //商家取消订单
    public String getCancelOrder(long orderId,String reason,String reasonCode) throws Exception {
        return APIFactory.getOrderAPI().orderCancel(sysPram,orderId,reason,reasonCode);
    }

    //通过订单id获取订单明细信息
    public OrderDetailParam getOrderDetail(long orderId) throws Exception {
        return APIFactory.getOrderAPI().orderGetOrderDetail(sysPram,orderId,0L);
    }

    //endregion

}

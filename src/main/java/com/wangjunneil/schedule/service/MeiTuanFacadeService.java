package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.wangjunneil.schedule.common.MeiTuanException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.meituan.*;
import com.wangjunneil.schedule.entity.mt.*;
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
    public String openShop(String code)
    {
        String json  = "";
        try {
            ShopRequest shopRequest = new ShopRequest();
            shopRequest.setApp_poi_code(code);
             json = mtApiService.openShop(code);
        }catch (MeiTuanException ex){

        }catch (ScheduleException ex){

        }catch (Exception ex){

        }finally {
            return  json;
        }
    }

    /**
     * 门店歇业
     * @params app_poi_code - APP方门店id
     */
    public String closeShop(String code)
    {
        String json = "";
        try {
             json = mtApiService.closeShop(code);
        }catch (MeiTuanException ex){

        }catch (ScheduleException ex){

        }catch (Exception ex){

        }finally {
            return  json;
        }
    }

    //endregion


    //region 菜品类接口

    /**
     * 创建菜品
     * @parama app_poi_code - APP方门店id
     */
    public String createFood(FoodParam foodParam) {
        String json = "";
        try {
            json = mtApiService.foodCreate(foodParam);
            return json;
        }catch (MeiTuanException ex){

        }
        catch (ScheduleException ex){}
        catch (Exception ex){}
        finally {
            return json;
        }
    }

    /**
     * 查询所有菜品
     *
     */
    public FoodParam foodList(String appPoiCode, String foodCode) {
        FoodParam foodParam = null;
        try {
            foodParam = mtApiService.foodList(appPoiCode,foodCode);
        }catch (MeiTuanException ex){}
        catch (ScheduleException ex){}
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
        try {
            json = mtApiService.upFrame(appPoiCode, foodCode);
        }catch (MeiTuanException ex){

        }
        catch (ScheduleException ex){}
        catch (Exception ex){

        }finally {
            return json; }
    }


    /**
     * 商品下架
     * @parama
     */
    public String downFrame(String appPoiCode,String foodCode) {
        String json = "";
        try {
            json = mtApiService.downFrame(appPoiCode, foodCode);
        }
        catch (MeiTuanException ex){}
        catch(ScheduleException ex){}
        catch (Exception ex){

        }finally {
            return json;
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
        try {
             json = mtApiService.getConfirmOrder(orderId);
            return json;
        }catch (MeiTuanException ex){

        }catch (ScheduleException ex){}
        catch (Exception ex){}
        finally {
            return  json;
        }
    }


    /**
     * 商家取消订单
     * @param orderId - 订单id
     */
    public String getCancelOrder(long orderId,String reason,String reason_code) {
        String json = "";
        try {
             json = mtApiService.getCancelOrder(orderId,reason,reason_code);
        }catch (MeiTuanException ex)
        {

        }catch (ScheduleException ex){}
        catch (Exception ex){}
        finally {
            return  json;
        }
    }


    /**
     * 通过订单id获取订单明细信息
     * @return
     * @param orderId 订单Id
     */
    public OrderDetailParam newOrder(long orderId) {
        OrderDetailParam json = null;
        try {
           json  = mtApiService.getOrderDetail(orderId);
            return json;
        } catch (MeiTuanException ex) {

        }
        catch (ScheduleException ex){

        }
        catch (Exception ex){

        }
        finally {
            return  json;
        }
    }


    //endregion



}

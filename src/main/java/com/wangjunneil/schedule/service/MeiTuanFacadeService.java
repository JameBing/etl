package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.sankuai.meituan.waimai.opensdk.vo.FoodParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderDetailParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderExtraParam;
import com.sankuai.meituan.waimai.opensdk.vo.OrderFoodDetailParam;
import com.wangjunneil.schedule.common.MeiTuanException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.meituan.*;
import com.wangjunneil.schedule.entity.mt.*;
import com.wangjunneil.schedule.service.meituan.MeiTuanApiService;
import com.wangjunneil.schedule.service.meituan.MeiTuanInnerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;


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
     * 通过订单id获取订单明细信息（已支付）
     * @return
            */
            public OrderDetailParam newOrder(JsonObject jsonObject) {
                OrderDetailParam detailParam = null;
                Gson gson = new GsonBuilder().registerTypeAdapter(OrderInfo.class,new OrderInfoSerializer())
                    .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderExtraParam.class, new OrderExtraParamSerializer())
                    .registerTypeAdapter(com.wangjunneil.schedule.entity.meituan.OrderFoodDetailParam.class, new OrderFoodDetailParamSerializer()) .disableHtmlEscaping().create();
                try {
                        String json =gson.toJson(jsonObject);
                        json = java.net.URLDecoder.decode(json,"utf-8");
                        OrderInfo order = gson.fromJson(json, OrderInfo.class);
                        detailParam = mtApiService.getOrderDetail(order.getOrderid());
//            OrderInfo order = new OrderInfo();
//            order.setApp_order_code(detailParam.getApp_order_code());
//            order.setApp_poi_code(detailParam.getApp_poi_code());
//            order.setAvg_send_time(detailParam.getAvg_send_time());
//            order.setCaution(detailParam.getCaution());
//            order.setCity_id(detailParam.getCity_id());
//            order.setCtime(detailParam.getCtime());
//            order.setDay_seq(detailParam.getDay_seq());
//            order.setDelivery_time(detailParam.getDelivery_time());
//            order.setDetail(detailParam.getDetail());
//            order.setDinners_number(detailParam.getDinners_number());
//            order.setExpect_deliver_time(detailParam.getExpect_deliver_time());
//            order.setExtras(detailParam.getExtras());
//            order.setHas_invoiced(detailParam.getHas_invoiced());
//            order.setInvoice_title(detailParam.getInvoice_title());
//            order.setIs_favorites(detailParam.getIs_favorites());
//            order.setIs_poi_first_order(detailParam.getIs_poi_first_order());
//            order.setIs_pre(detailParam.getIs_pre());
//            order.setIs_third_shipping(detailParam.getIs_third_shipping());
//            order.setLatitude(detailParam.getLatitude());
//            order.setLogistics_cancel_time(detailParam.getLogistics_cancel_time());
//            order.setLogistics_code(detailParam.getLogistics_code());
//            order.setLogistics_completed_time(detailParam.getLogistics_completed_time());
//            order.setLogistics_confirm_time(detailParam.getLogistics_confirm_time());
//            order.setLogistics_dispatcher_mobile(detailParam.getLogistics_dispatcher_mobile());
//            order.setLogistics_dispatcher_name(detailParam.getLogistics_dispatcher_name());
//            order.setLogistics_fetch_time(detailParam.getLogistics_fetch_time());
//            order.setLogistics_id(detailParam.getLogistics_id());
//            order.setLogistics_name(detailParam.getLogistics_name());
//            order.setLogistics_send_time(detailParam.getLogistics_send_time());
//            order.setLongitude(detailParam.getLongitude());
//            order.setOrder_cancel_time(detailParam.getOrder_cancel_time());
//            order.setOrder_completed_time(detailParam.getOrder_completed_time());
//            order.setOrder_confirm_time(detailParam.getOrder_confirm_time());
//            order.setOrder_id(detailParam.getOrder_id());
//            order.setOrder_receive_time(detailParam.getOrder_receive_time());
//            order.setOrder_send_time(detailParam.getOrder_send_time());
//            order.setOriginal_price(detailParam.getOriginal_price());
//            order.setPay_done_time(detailParam.getPay_done_time());
//            order.setPay_status(detailParam.getPay_status());
//            order.setPay_type(detailParam.getPay_type());
//            order.setPaying_time(detailParam.getPaying_time());
//            order.setPoi_receive_detail(detailParam.getPoi_receive_detail());
//            order.setRecipient_address(detailParam.getRecipient_address());
//            order.setRecipient_name(detailParam.getRecipient_name());
//            order.setRecipient_phone(detailParam.getRecipient_phone());
//            order.setRefund_apply_time(detailParam.getRefund_apply_time());
//            order.setRefund_complete_time(detailParam.getRefund_complete_time());
//            order.setRefund_confirm_time(detailParam.getRefund_confirm_time());
//            order.setRefund_reject_time(detailParam.getRefund_reject_time());
//            order.setRemark(detailParam.getRemark());
//            order.setResult(detailParam.getResult());
//            order.setShipper_phone(detailParam.getShipper_phone());
//            order.setShipping_fee(detailParam.getShipping_fee());
//            order.setShipping_type(detailParam.getShipping_type());
//            order.setSource_id(detailParam.getSource_id());
//            order.setStatus(detailParam.getStatus());
//            order.setTotal(detailParam.getTotal());
//            order.setUnpaid_time(detailParam.getUnpaid_time());
//            order.setUtime(detailParam.getUtime());
//            order.setWm_order_id_view(detailParam.getWm_order_id_view());
//            order.setWm_poi_address(detailParam.getWm_poi_address());
//            order.setWm_poi_id(detailParam.getWm_poi_id());
//            order.setWm_poi_name(detailParam.getWm_poi_name());
//            order.setWm_poi_phone(detailParam.getWm_poi_phone());
            mtInnerService.insertAllOrder(order);
            return detailParam;
        } catch (MeiTuanException ex) {

        }
        catch (ScheduleException ex){

        }
        catch (Exception ex){

        }
        finally {
            return detailParam;
        }
    }

    /**
     * 推送订单订单状态（已确认、已完成）
     * @return
     * @param orderId 订单Id
     */
    public String getConfirmOrder(String orderId,String status) {
            if (orderId ==null || "".equals(orderId)){
                return "订单id列表为空";
            }
            mtInnerService.updateStatus(orderId,Integer.parseInt(status));
        return  null;
    }
//endregion



}

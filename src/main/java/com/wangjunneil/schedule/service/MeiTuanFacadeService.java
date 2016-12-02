package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
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
<<<<<<< HEAD
    public String openShop(String code)throws ScheduleException
=======
    public String openShop(String code)
>>>>>>> 079bb2ae9ceb6ad6bdcb817b1221acafae7c33f3
    {
        try {
            ShopRequest shopRequest = new ShopRequest();
            shopRequest.setApp_poi_code(code);
            String json = mtApiService.openShop(shopRequest);
            return json;
        }catch (Exception ex){
            throw  new ScheduleException(Constants.PLATFORM_WAIMAI_MEITUAN,ex.getClass().getName(),"",code,new Throwable().getStackTrace());
        }
    }

    /**
     * 门店歇业
     * @params app_poi_code - APP方门店id
     */
    public String closeShop(String code)
    {
        try {
            ShopRequest shopRequest = new ShopRequest();
            shopRequest.setApp_poi_code(code);
            String json = mtApiService.closeShop(shopRequest);
            return json;
        }catch (Exception ex){
<<<<<<< HEAD
            throw  new ScheduleException(Constants.PLATFORM_WAIMAI_MEITUAN,ex.getClass().getName(), "",code,new Throwable().getStackTrace());
=======
            return null;
>>>>>>> 079bb2ae9ceb6ad6bdcb817b1221acafae7c33f3
        }
    }

    //endregion



    //region 订单相关


    /**
     * 商家确认订单
     * @param orderid - 订单id
     */
    public String getConfirmOrder(String orderid) throws ScheduleException {
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setOrder_id(orderid);
            String json = mtApiService.getConfirmOrder(orderRequest);
            return json;
        }catch (Exception e){
            return "";
        }
    }


    /**
     * 商家取消订单
     * @param orderId - 订单id
     */
    public String getCancelOrder(String orderId,String reason,String reasonCode) throws ScheduleException
    {
        try {
            OrderRequest orderRequest = new OrderRequest();
            orderRequest.setOrder_id(orderId);
            orderRequest.setReason(reason);
            orderRequest.setReason_code(reasonCode);
            String json = mtApiService.getCancelOrder(orderRequest);
            return json;
        }catch (Exception e) {
            return "";
        }
    }

    /**
     * 上下架
     * @return
     */
    public String setFrame(String app_poi_code,List<FoodData> data) {
        try {
            FoodRequest foodRequest = new FoodRequest();
            foodRequest.setApp_poi_code(app_poi_code);
            foodRequest.setFood_data(data);
            String json = mtApiService.setFrame(foodRequest);
            return json;
        } catch (ScheduleException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 修改订单状态
     * @return
     */
    public String upStatus(String status) {
        return "";
    }


//    /**
//     * 新增订单推送（提供给美团方调用）
//     */
//    public String newOrder(String orderId,String status)
//    {
//        //String json = mtApiService.newOrder(orderId,status);
//
//        String results = "{\n" +
//            "    \"data\": {\n" +
//            "        \"order_id\": \"25217\",\n" +
//            "        \"wm_order_id_view\": \"55555\",\n" +
//            "        \"app_poi_code\": \"55555\",\n" +
//            "        \"wm_poi_name\": \"美团商家名称\",\n" +
//            "        \"wm_poi_address\": \"美团商家地址\",\n" +
//            "        \"wm_poi_phone\": \"美团商家电话\",\n" +
//            "        \"recipient_address\": \"收件人地址\",\n" +
//            "        \"recipient_phone\": \"收件人电话\",\n" +
//            "        \"recipient_name\": \"收件人姓名\",\n" +
//            "        \"shipping_fee\": \"555.4f\",\n" +
//            "        \"total\": \"555221.00\",\n" +
//            "        \"original_price\": \"55543.00\",\n" +
//            "        \"caution\": \"无\",\n" +
//            "        \"shipper_phone\": \"110\",\n" +
//            "        \"status\": \"0\",\n" +
//            "        \"city_id\": \"1001\",\n" +
//            "        \"has_invoiced\": \"0\",\n" +
//            "        \"invoice_title\": \"1122\",\n" +
//            "        \"ctime\": \"1111111\",\n" +
//            "        \"utime\": \"2222222\",\n" +
//            "        \"delivery_time\": \"0\",\n" +
//            "        \"is_third_shipping\": \"0\",\n" +
//            "        \"latitude\": \"10.00\",\n" +
//            "        \"longitude\": \"50.00\",\n" +
//            "        \"detail\": [\n" +
//            "        {\n" +
//            "            \"app_food_code\" : \"100001036354906\",\n" +
//            "            \"food_name\" : \"狗不理\",\n" +
//            "            \"quantity\" : \"6\",\n" +
//            "            \"price\" : \"100\",\n" +
//            "            \"box_num\" : \"2\",\n" +
//            "            \"box_price\" : \"1\",\n" +
//            "            \"unit\" : \"份\",\n" +
//            "            \"food_discount\" : \"0.8\"\n" +
//            "        }\n" +
//            "        ],\n" +
//            "        \"extras\": [\n" +
//            "        {\n" +
//            "            \"act_detail_id\" : \"10\",\n" +
//            "            \"reduce_fee\" : \"2.5\",\n" +
//            "            \"remark\" : \"满10元减2.5元\",\n" +
//            "            \"type\" : \"1\",\n" +
//            "            \"avg_send_time\" : \"5.5\"\n" +
//            "        },{\n" +
//            "            \"reduce_fee\" : \"5\",\n" +
//            "            \"remark\" : \"新用户立减5元\",\n" +
//            "            \"act_detail_id\" : \"10\",\n" +
//            "            \"type\" : \"1\",\n" +
//            "            \"avg_send_time\" : \"1.0\"\n" +
//            "        }\n" +
//            "        ],\n" +
//            "        \"order_send_time\": \"143254562350\",\n" +
//            "        \"order_receive_time\": \"143254561110\",\n" +
//            "        \"order_confirm_time\": \"143254561110\",\n" +
//            "        \"order_cancel_time\": \"143254562340\",\n" +
//            "        \"order_completed_time\": \"143254562340\",\n" +
//            "        \"logistics_status\": \"20\",\n" +
//            "        \"logistics_id\": \"7\",\n" +
//            "        \"logistics_name\": \"斑马快送\",\n" +
//            "        \"logistics_send_time\": \"143254562350\",\n" +
//            "        \"logistics_confirm_time\": \"143254561110\",\n" +
//            "        \"logistics_cancel_time\": \"143254562340\",\n" +
//            "        \"logistics_fetch_time\": \"143254562340\",\n" +
//            "        \"logistics_completed_time\": \"143254562340\",\n" +
//            "        \"logistics_dispatcher_name\": \"143254562340\",\n" +
//            "        \"logistics_dispatcher_mobile\": \"143254562340\"\n" +
//            "    }\n" +
//            "}";
//        //把result字符串转换成JSON对象
//        JSONObject jsonObject = JSONObject.parseObject(results);
//        //解析JSON
//        JSONObject jsonData = jsonObject.getJSONObject("data");
//        JSONArray arrayDetail = jsonData.getJSONArray("detail");
//        JSONArray arrayExtras = jsonData.getJSONArray("extras");
//        OrderInfo orderInfo = new OrderInfo();
//        //订单基础信息
//        orderInfo.setOrder_id(jsonData.getLong("order_id"));
//        orderInfo.setWm_order_id_view(jsonData.getLong("wm_order_id_view"));
//        orderInfo.setApp_poi_code(jsonData.getString("app_poi_code"));
//        orderInfo.setWm_poi_name(jsonData.getString("wm_poi_name"));
//        orderInfo.setWm_poi_address(jsonData.getString("wm_poi_address"));
//        orderInfo.setWm_poi_phone(jsonData.getString("wm_poi_phone"));
//        orderInfo.setRecipient_address(jsonData.getString("recipient_address"));
//        orderInfo.setRecipient_phone(jsonData.getString("recipient_phone"));
//        orderInfo.setRecipient_name(jsonData.getString("recipient_name"));
//        orderInfo.setShipping_fee(jsonData.getFloat("shipping_fee"));
//        orderInfo.setTotal(jsonData.getDouble("total"));
//        orderInfo.setOriginal_price(jsonData.getDouble("original_price"));
//        orderInfo.setCaution(jsonData.getString("caution"));
//        orderInfo.setShipper_phone(jsonData.getString("shipper_phone"));
//        orderInfo.setStatus(jsonData.getInteger("status"));
//        orderInfo.setCity_id(jsonData.getLong("city_id"));
//        orderInfo.setHas_invoiced(jsonData.getInteger("has_invoiced"));
//        orderInfo.setInvoiced_title(jsonData.getString("invoiced_title"));
//        orderInfo.setCtime(jsonData.getLong("ctime"));
//        orderInfo.setUtime(jsonData.getLong("utime"));
//        orderInfo.setDelivery_time(jsonData.getLong("delivery_time"));
//        orderInfo.setIs_third_shipping(jsonData.getInteger("is_third_shipping"));
//        orderInfo.setLatitude(jsonData.getDouble("latitude"));
//        orderInfo.setLongitude(jsonData.getDouble("longitude"));
//        //商户订单信息
//        orderInfo.setOrder_send_time(jsonData.getLong("order_send_time"));
//        orderInfo.setOrder_receive_time(jsonData.getLong("order_receive_time"));
//        orderInfo.setOrder_confirm_time(jsonData.getLong("order_confirm_time"));
//        orderInfo.setOrder_cancel_time(jsonData.getLong("order_cancel_time"));
//        orderInfo.setOrder_completed_time(jsonData.getLong("order_completed_time"));
//        //配送信息
//        orderInfo.setLogistics_status(jsonData.getInteger("logistics_status"));
//        orderInfo.setLogistics_id(jsonData.getLong("logistics_id"));
//        orderInfo.setLogistics_name(jsonData.getString("logistics_name"));
//        orderInfo.setLogistics_send_time(jsonData.getLong("logistics_send_time"));
//        orderInfo.setLogistics_confirm_time(jsonData.getLong("logistics_confirm_time"));
//        orderInfo.setLogistics_cancel_time(jsonData.getLong("logistics_cancel_time"));
//        orderInfo.setLogistics_fetch_time(jsonData.getLong("logistics_fetch_time"));
//        orderInfo.setLogistics_completed_time(jsonData.getLong("logistics_completed_time"));
//        //骑手信息
//        orderInfo.setLogistics_dispatcher_name(jsonData.getString("logistics_dispatcher_name"));
//        orderInfo.setLogistics_dispatcher_mobile(jsonData.getString("logistics_dispatcher_mobile"));
//        List<DetailInfo> detailList = new ArrayList<>();
//        List<ExtrasInfo> extrasList = new ArrayList<>();
//        //解析数组,进行遍历,拿到detail里面的数据.
//        if(arrayDetail!=null && arrayDetail.size()>0){
//            for(int i =0;i<arrayDetail.size();i++){
//                DetailInfo info = new DetailInfo();
//                JSONObject jsDetail = arrayDetail.getJSONObject(i);
//                info.setApp_food_code(jsDetail.getLong("app_food_code"));
//                info.setFood_name(jsDetail.getString("food_name"));
//                info.setQuantity(jsDetail.getInteger("quantity"));
//                info.setPrice(jsDetail.getDouble("price"));
//                info.setBox_num(jsDetail.getInteger("box_num"));
//                info.setBox_price(jsDetail.getDouble("box_price"));
//                info.setUnit(jsDetail.getString("unit"));
//                info.setFood_discount(jsDetail.getDouble("food_discount"));
//                detailList.add(info);
//            }
//            orderInfo.setDetail(detailList);
//        }
//        //解析数组,进行遍历,拿到extras里面的数据.
//        if (arrayExtras!=null && arrayExtras.size()>0)
//        {
//            for(int i=0;i<arrayExtras.size();i++)
//            {
//                JSONObject jsExtras = arrayExtras.getJSONObject(i);
//                ExtrasInfo extrasInfo = new ExtrasInfo();
//                extrasInfo.setAct_detail_id(jsExtras.getString("act_detail_id"));
//                extrasInfo.setReduce_fee(jsExtras.getDouble("reduce_fee"));
//                extrasInfo.setRemark(jsExtras.getString("remark"));
//                extrasInfo.setType(jsExtras.getInteger("type"));
//                extrasInfo.setAvg_send_time(jsExtras.getDouble("avg_send_time"));
//                extrasList.add(extrasInfo);
//            }
//            orderInfo.setExtras(extrasList);
//        }
//        mtInnerService.insertAllOrder(orderInfo);
//        return null;
//    }


//endregion



}

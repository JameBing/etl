package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ElemeException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.service.eleme.EleMeApiService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import eleme.openapi.sdk.api.entity.order.*;
import eleme.openapi.sdk.api.entity.product.OCategory;
import eleme.openapi.sdk.api.entity.product.OItem;
import eleme.openapi.sdk.api.entity.product.OItemIdWithSpecIds;
import eleme.openapi.sdk.api.entity.product.OSpec;
import eleme.openapi.sdk.api.entity.shop.OShop;
import eleme.openapi.sdk.api.enumeration.order.OOrderDetailGroupType;
import eleme.openapi.sdk.api.enumeration.order.OOrderRefundStatus;
import eleme.openapi.sdk.api.enumeration.order.OOrderStatus;
import eleme.openapi.sdk.oauth.response.Token;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeFacadeService {

    private static Logger logInfo = Logger.getLogger(EleMeFacadeService.class.getName());
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
                .registerTypeAdapter(DeliverymanInfo.class, new DeliverymanInfoSerializer())
                .registerTypeAdapter(Distribution.class, new DistributionSerializer())
                .registerTypeAdapter(Extra.class, new ExtraSerializer())
                .registerTypeAdapter(FoodIds.class, new FoodIdsSerializer())
                .registerTypeAdapter(Foods.class, new FoodsSerializer())
                .registerTypeAdapter(FoodsCategoryRequest.class, new FoodsCategoryRequestSerializer())
                .registerTypeAdapter(FoodsRequest.class, new FoodsRequestSerializer())
                .registerTypeAdapter(Garnish.class, new GarnishSerializer())
                .registerTypeAdapter(Group.class, new GroupSerializer())
                .registerTypeAdapter(Labels.class, new LabelsSerializer())
                .registerTypeAdapter(OldFoodsRequest.class, new OldFoodsRequestSerializer())
                .registerTypeAdapter(Order.class, new OrderSerializer())
                .registerTypeAdapter(OrderRequest.class, new OrderRequestSerializer())
                .registerTypeAdapter(Records.class, new RecordsSerializer())
                .registerTypeAdapter(Restaurant.class, new RestaurantSerializer())
                .registerTypeAdapter(RestaurantOrderEvaluationRequest.class, new RestaurantOrderEvaluationRequestSerializer())
                .registerTypeAdapter(RestaurantRequest.class, new RestaurantRequestSerializer())
                .registerTypeAdapter(Result.class, new ResultSerializer())
                .registerTypeAdapter(Specs.class, new SpecsSerializer())
                .registerTypeAdapter(Status.class, new StatusSerializer())
                .registerTypeAdapter(Times.class, new TimesSerializer())
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
     * @param merchantId 店铺id
     * @param status 1:开店/0:关店
     * @return
     */
    public String setRestaurantStatus(String merchantId,String status){
        String result = "";
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantId)) {
            return gson1.toJson(rtn);
        }
        try {
            rtn.setDynamic(merchantId);
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(merchantId);
            restaurantRequest.setIs_open(status);
            OShop shop = eleMeApiService.setRestaurantStatus(restaurantRequest);
            if (shop!=null) {
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("操作成功");
                rtn.setDynamic(merchantId);
            }else {
                rtn.setCode(-1);
                rtn.setDesc("error");
                rtn.setRemark("餐厅不存在");
                rtn.setDynamic(merchantId);
            }
        }catch (ElemeException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }
        catch (Exception ex){
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log!=null){
                log.setLogId(merchantId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("门店{0}{1}失败", merchantId,status=="1"?"开业":"歇业"));
                if (StringUtil.isEmpty(log.getRequest()))
                    log.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"eleme_shop_id\":{1}", merchantId, "")).concat("}"));
                sysFacadeService.updSynLog(log);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}{1}失败！",merchantId,status=="1"?"开业":"歇业"));
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 主动拉取新订单
     * @param merchantId 商户Id
     * 获取前30分钟的最新订单，参数可以空值。
     * @return
     */
    public String pullNewOrder(String merchantId){
        Log log = null;
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setRestaurant_id(merchantId);
                String result = eleMeApiService.pullNewOrder(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            } finally {
                if (log != null) {
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("拉取门店{0}新订单失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_shop_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }
    /**
     * 查询新订单
     * @param merchantId 商户Id
     * 获取前30分钟的最新订单，参数可以空值。
     * @return
     */
    public String findNewOrder(String merchantId){
        Log log = null;
        if (!StringUtil.isEmpty(merchantId)) {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setRestaurant_id(merchantId);
                String result = eleMeApiService.findNewOrder(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    return getGson().toJson(obj.getData());
                }
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            } finally {
                if (log != null) {
                    log.setLogId(merchantId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("拉取门店{0}新订单失败", merchantId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_shop_id\":{0}", merchantId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return "failure";
    }

    /**
     * 订单状态修改
     * @param elemeOrderId 订单ID
     * @param status 查看Enum.java的<订单状态--饿了么>
     * @param reason 取消订单原因,不是取消订单请填写""或者null
     * @return
     */
    public String upOrderStatus(String elemeOrderId, String status, String shopId,String reason){
        String result = null;
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderId);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderId)) {
            return gson1.toJson(rtn);
        }else {
            try {
                //是否是接单状态
                OrderRequest request = new OrderRequest();
                request.setEleme_order_id(elemeOrderId);
                request.setTp_id("1");
                OOrder order = eleMeApiService.orderDetail(elemeOrderId,shopId);
                if(order==null){
                    rtn.setCode(-1);
                    rtn.setRemark("订单不存在");
                    rtn.setDesc("error");
                    rtn.setDynamic(String.valueOf(elemeOrderId));
                    return gson.toJson(rtn);
                }
                if(order.getStatus().toString().equals(OOrderStatus.unprocessed)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                    jsonObject.put("desc","error");
                    jsonObject.put("remark","订单已确实过，请更新状态");
                    jsonObject.put("orderId",elemeOrderId);
                    jsonObject.put("orderStatus",new SysFacadeService().tranELOrderStatus(order.getStatus()));
                    return jsonObject.toJSONString();
                }
                //确认订单
                if("2".equals(status)){
                    eleMeApiService.configOrderStatus(elemeOrderId,shopId);
                }
                //取消订单
                if("-1".equals(status)){
                    eleMeApiService.cancelOrderStatus(elemeOrderId,shopId);
                }
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("操作成功");
                rtn.setDynamic(elemeOrderId);
                //更新门店是否接单标识字段
                if("2".equals(status)){
                    eleMeInnerService.updateIsReceived(elemeOrderId,1);
                }else {
                    eleMeInnerService.updateIsReceived(elemeOrderId,2);
                }
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderId.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("修改订单{0}状态失败", elemeOrderId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_id\":{0},\"status\":{1},\"reason\":{2}", elemeOrderId, status, reason)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
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
            rtn.setCode(-1);
            rtn.setDesc("食物数据为空");
            rtn.setRemark("食物数据为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = getGson().fromJson(json, OldFoodsRequest.class);
                result = eleMeApiService.addFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("添加食物失败,{0}必填", obj.getMessage().toString()));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
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
     *  获取门店商品状态
     * @param merchantId 商户ID
     * @return
     */
    public String restaurantMenu(String merchantId){
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String rtnStr = "";
        if(StringUtil.isEmpty(merchantId)){
            return gson.toJson(rtn);
        }
        try {
            RestaurantRequest restaurantRequest = new RestaurantRequest();
            restaurantRequest.setRestaurant_id(merchantId);
            //获取门店所有商品分类
            List<OCategory> categoryList = getCategoryId(merchantId);
            if(categoryList==null || categoryList.size()==0){
                rtn.setCode(-1);
                rtn.setDesc("success");
                rtn.setRemark("此门店无商品");
                rtn.setDynamic(merchantId);
            }
            for(int i=0;i<categoryList.size();i++){
                //获取分类下商品
                Map<Long,OItem> items = getCategProducts(categoryList.get(i).getId(),merchantId);
                Set<Long> keys = items.keySet();
                for(Long key :keys){
                    OItem item = items.get(key);
                    List<OSpec> specsList = item.getSpecs();
                    if(specsList==null || specsList.size()==0){
                        continue;
                    }
                    rtn.setCode(0);
                    rtn.setDesc("success");
                    rtn.setStatus(specsList.get(0).getOnShelf()==1?0:1);
                    rtn.setName(item.getName());
                    rtn.setDynamic(String.valueOf(item.getId()));
                    rtnStr = rtnStr +gson.toJson(rtn)+",";
                }
            }
        }catch (Exception ex){
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(merchantId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("查询门店{0}菜单失败", merchantId));
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantId)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
            }
        }
        if(!StringUtil.isEmpty(rtnStr)){
            return rtnStr.substring(0,rtnStr.length()-1);
        }
        return gson.toJson(rtn);
    }

    /**
     * 新订单接收
     * @param  jsonObject
     * @return
     */
    public String getNewOrder(JSONObject jsonObject){
        String rtnStr = "";
        Log log = null;
        if(StringUtil.isEmpty(jsonObject)){
            return  "{\"message\": \"error\"}";
        }
        try {
            //转换对象
            OrderEle order = new OrderEle();
            formatEle(order,jsonObject);
            String orderId = jsonObject.getString("orderId");
            OrderWaiMai orderWaiMai = new OrderWaiMai();
            orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            orderWaiMai.setPlatformOrderId(jsonObject.getString("orderId"));
            String wmOrderId = sysFacadeService.getOrderNum(String.valueOf(jsonObject.getString("shopId")));
            orderWaiMai.setOrder(order);
            orderWaiMai.setOrderId(wmOrderId);
            orderWaiMai.setPlatformOrderId(orderId);
            //获取中台门店Id
            ShopEle shopEle = eleMeInnerService.getShop(jsonObject.getString("shopId"));
            orderWaiMai.setSellerShopId(shopEle==null?jsonObject.getString("shopId"):shopEle.getSellerId());
            orderWaiMai.setShopId(shopEle==null?jsonObject.getString("shopId"):shopEle.getSellerId());
            orderWaiMai.setCreateTime(new Date());
            rtnStr = "{\"message\": \"ok\"}";
            sysFacadeService.updSynWaiMaiOrder(orderWaiMai);
        }catch (ElemeException ex){
            log = sysFacadeService.functionRtn.apply(ex);
        }catch (Exception ex){
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(log.getLogId());
                log.setTitle("接收新订单或者获取新订单配送信息失败");
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"order\":{0}", jsonObject.toJSONString())).concat("}"));
                }
                sysFacadeService.updSynLog(log);
                rtnStr = "{\"message\": \"error\"}";
            }
        }
        return rtnStr;
    }


    /**
     * 消息订单修改状态
     * @param jsonObject
     * @return
     */
    public String orderChange(JSONObject jsonObject,Boolean flag){
        //温馨提醒：单个参数传输都使用String类型，因为Request对象的getparamter方法返回的均是String类型，外层做类型转换的话，如果发生异常则无法捕获，异常处理均在这一层处理，所以放在这里做类型转换更合适
        Log log =null;
        try {
            String orderId = jsonObject.getString("orderId");
            String newStatus = jsonObject.getString("state");
            OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_ELEME,orderId);
            if(StringUtil.isEmpty(orderWaiMai)){
                return "{\"message\": \"不存在此订单\"}";
            }
            if(!flag){
                eleMeInnerService.updSyncElemeOrderStastus(orderId, newStatus);
            }else {
                sysFacadeService.updateWaiMaiOrder(orderId, orderWaiMai);
            }
            sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_ELEME,null,orderId,null,orderWaiMai.getSellerShopId(), OOrderStatus.valueOf(newStatus));
        }catch (ScheduleException e){
            log = sysFacadeService.functionRtn.apply(e);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }catch (Exception e){
            log = sysFacadeService.functionRtn.apply(e);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if(log!=null){
                log .setLogId(log.getLogId());
                log.setTitle("饿了么订单修改失败");
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"order\":{0}", jsonObject)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
                return "{\"message\": \"error\"}";
            }
        }
        return  "{\"message\": \"ok\"}";
    }

    //退单状态接收  refund_status:退单订单状态
    public String chargeBack(JSONObject jsonObject,Boolean flag) {
        Log log = null;
        try {
            String orderId = jsonObject.getString("orderId");
            String refundStatus = jsonObject.getString("refundStatus");
            OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_ELEME,orderId);
            if(StringUtil.isEmpty(orderWaiMai)){
                return "{\"message\": \"不存在此订单\"}";
            }
            if(!flag){
                eleMeInnerService.updSyncElemeOrderStastus(orderId,refundStatus);
            }else {
                sysFacadeService.updateWaiMaiOrder(orderId, orderWaiMai);
            }
            sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_ELEME,null,orderId,null,orderWaiMai.getSellerShopId(), OOrderStatus.valueOf(refundStatus));
        }catch (ScheduleException e){
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log = sysFacadeService.functionRtn.apply(e);
        }catch (Exception e){
            log = sysFacadeService.functionRtn.apply(e);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if(log!=null){
                log .setLogId(log.getLogId());
                log.setTitle("饿了么订单退单失败");
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"order\":{0}", jsonObject)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
                return "{\"message\": \"error\"}";
            }
        }

        return  "{\"message\": \"ok\"}";
    }

    //订单配送状态接收
    public String distributionStatus(JSONObject jsonObject){
        Log log =null;
        try {
            if(StringUtil.isEmpty(jsonObject)){
                return "{\"message\": \"error\"}";
            }
            eleMeInnerService.updSyncElemeOrderStastus(jsonObject.getString("orderId"),jsonObject.getString("state"));
            Integer status = sysFacadeService.tranELDeliveryStatus(jsonObject.getString("state"));
            sysFacadeService.topicMessageOrderDelivery(Constants.PLATFORM_WAIMAI_ELEME,status, jsonObject.getString("orderId"),
                jsonObject.getString("phone"), jsonObject.getString("name"), jsonObject.getString("shopId"));
        }catch (ScheduleException e){
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log = sysFacadeService.functionRtn.apply(e);
            log .setLogId(log.getLogId());
            log.setTitle("饿了么订单:获取配送失败");
            if (StringUtil.isEmpty(log.getRequest())) {
                log.setRequest("{".concat(MessageFormat.format("\"deliveryOrder\":{0}", jsonObject.toJSONString())).concat("}"));
            }
            sysFacadeService.updSynLog(log);
            return "{\"message\": \"error\"}";
        }
        return  "{\"message\": \"ok\"}";
    }

    /**
     * 通过门店ID获取商品分类ID
     * @param parms
     * @return
     */
    public List<OCategory> getCategoryId(String parms) {
        Log log = null;
        if (!StringUtil.isEmpty(parms)) {
            try {
                List<OCategory> cateList = eleMeApiService.getCategoryId(parms);
                return cateList;
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(parms.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("获取门店{0}食物分类ID失败", parms));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"parms\":{0}", parms)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return  null;
    }

    /**
     * 通过商品分类ID获取商品
     * @param parms
     * @return
     */
    public Map<Long, OItem> getCategProducts(Long parms,String shopId) {
        Log log = null;
        if (!StringUtil.isEmpty(parms)) {
            try {
                Map<Long, OItem> prds = eleMeApiService.getCategProducts(parms,shopId);
                return prds;
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取分类{0}食物详情ID失败", parms));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"parms\":{0}", parms)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return  null;
    }

    /**
     * 通过第三方商品Id获取商品
     * @param shopId  dishId
     * @return
     */
    public OItem getProductByExtendCode(Long shopId,String dishId) {
        Log log = null;
        if (!StringUtil.isEmpty(shopId) && !StringUtil.isEmpty(dishId)) {
            try {
                OItem item = eleMeApiService.getProductByExtendCode(shopId,dishId);
                return item;
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取门店{0}食物详情失败", shopId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"shopId\":{0}", shopId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return  null;
    }

    /**
     * 通过第三方商品Id获取商品
     * @param   dishId
     * @return
     */
    public OItem getProductById(String dishId,String shopId) {
        Log log = null;
        if (!StringUtil.isEmpty(dishId)) {
            try {
                OItem item = eleMeApiService.getProductById(dishId,shopId);
                return item;
            }catch (ElemeException ex){
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }catch (Exception ex){
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取门店{0}食物详情失败", dishId));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", dishId)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                }
            }
        }
        return  null;
    }

    /**
     * 商品上架
     * @param dishList
     * @param status
     * @return
     */
    public String upBatchFrame(List<ParsFromPosInner> dishList,String status) {
        Log log = null;
        Rtn rtn = new Rtn();
        String reponse = "";
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (dishList == null || dishList.size() == 0 || StringUtil.isEmpty(dishList.get(0).getShopId())) {
            return gson1.toJson(rtn);
        }
        //判断批量请求参数是否有空字段
        for(int i=0;i<dishList.size();i++){
            if(StringUtil.isEmpty(dishList.get(i).getShopId()) || StringUtil.isEmpty(dishList.get(i).getDishId())){
                rtn.setCode(-1);
                rtn.setDesc("error");
                rtn.setRemark("门店Id或者商品Id为空");
                rtn.setDynamic(dishList.get(i).getShopId()+dishList.get(i).getDishId());
                return  gson.toJson(rtn);
            }
        }
        //上架请求参数
        List<OItemIdWithSpecIds> specIds = new ArrayList<OItemIdWithSpecIds>();
        for(int j=0; j<dishList.size();j++){
            OItem item = getProductById(dishList.get(j).getDishId(),dishList.get(j).getShopId());
            Long proId = item.getId();
            List<OSpec> specsList = item.getSpecs();
            OItemIdWithSpecIds oItemIdWithSpecIds = new OItemIdWithSpecIds();
            oItemIdWithSpecIds.setItemId(proId);
            List<Long> itemSpecIds = new ArrayList<Long>();
            for(int k=0;k<specsList.size();k++){
                itemSpecIds.add(specsList.get(k).getSpecId());
            }
            oItemIdWithSpecIds.setItemSpecIds(itemSpecIds);
            specIds.add(oItemIdWithSpecIds);
        }
        //请求上架接口
        try {
            eleMeApiService.upBatchFrame(specIds,dishList.get(0).getShopId());
            for(int m=0;m<dishList.size();m++){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
                rtn.setDynamic(dishList.get(m).getDishId());
                reponse+=gson1.toJson(rtn)+",";
            }
        }catch (ElemeException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }
        catch (Exception ex){
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(log.getLogId());
                log.setTitle("批量上架失败");
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest(specIds.toString());
                }
                sysFacadeService.updSynLog(log);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark("批量上架食物{0}失败");
            }
            if(!StringUtil.isEmpty(reponse)){
                return  reponse.substring(0,reponse.length()-1);
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 下架商品
     * @param
     * @return
     */
    public String downBatchFrame(List<ParsFromPosInner> dishList,String status){
        Log log = null;
        Rtn rtn = new Rtn();
        String reponse = "";
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (dishList == null || dishList.size() == 0 || StringUtil.isEmpty(dishList.get(0).getShopId())) {
            return gson1.toJson(rtn);
        }
        //判断批量请求参数是否有空字段
        for(int i=0;i<dishList.size();i++){
            if(StringUtil.isEmpty(dishList.get(i).getShopId()) || StringUtil.isEmpty(dishList.get(i).getDishId())){
                rtn.setCode(-1);
                rtn.setDesc("error");
                rtn.setRemark("门店Id或者商品Id为空");
                rtn.setDynamic(dishList.get(i).getShopId()+dishList.get(i).getDishId());
                return  gson.toJson(rtn);
            }
        }
        //下架请求参数
        List<OItemIdWithSpecIds> specIds = new ArrayList<OItemIdWithSpecIds>();
        for(int j=0; j<dishList.size();j++){
            OItem item = getProductById(dishList.get(j).getDishId(),dishList.get(j).getShopId());
            Long proId = item.getId();
            List<OSpec> specsList = item.getSpecs();
            OItemIdWithSpecIds oItemIdWithSpecIds = new OItemIdWithSpecIds();
            oItemIdWithSpecIds.setItemId(proId);
            List<Long> itemSpecIds = new ArrayList<Long>();
            for(int k=0;k<specsList.size();k++){
                itemSpecIds.add(specsList.get(k).getSpecId());
            }
            oItemIdWithSpecIds.setItemSpecIds(itemSpecIds);
            specIds.add(oItemIdWithSpecIds);
        }
        //请求下架接口
        try {
            eleMeApiService.downBatchFrame(specIds,dishList.get(0).getShopId());
            for(int m=0;m<dishList.size();m++){
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark("成功");
                rtn.setDynamic(dishList.get(m).getDishId());
                reponse+=gson1.toJson(rtn)+",";
            }
        }catch (ElemeException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }
        catch (Exception ex){
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(log.getLogId());
                log.setTitle("批量上架失败");
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest(specIds.toString());
                }
                sysFacadeService.updSynLog(log);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark("批量上架食物{0}失败");
            }
            if(!StringUtil.isEmpty(reponse)){
                return  reponse.substring(0,reponse.length()-1);
            }
            return gson.toJson(rtn);
        }
    }

    //修改商品信息
    public String dishUpdate(ParsFromPosInner parsFromPosInner){
        return null;
    }

    /**
     * 获取餐厅信息
     * @param merchantId
     * @return
     */
    public OShop getRestaurantInfo(String merchantId) {
        Log log = null;
        try {
            RestaurantRequest restaurantRequest= new RestaurantRequest();
            restaurantRequest.setRestaurant_id(merchantId);
            OShop shop = eleMeApiService.getRestaurantInfo(restaurantRequest);
            return shop;
        }catch (ElemeException ex){
            log = sysFacadeService.functionRtn.apply(ex);
        }
        catch (ScheduleException ex) {
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }
        catch (Exception ex){
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(log.getLogId());
                log.setTitle(MessageFormat.format("获取餐厅{0}信息失败", merchantId));
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantId)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
            }
        }
        return null;
    }

    /**
     * 获取门店开关店状态
     * @param merchantId
     * @return
     */
    public String getStatus(String merchantId) {
        String result = "";
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantId)) {
            return gson1.toJson(rtn);
        }
        OShop shop = getRestaurantInfo(merchantId);
        if (shop==null) {
            rtn.setCode(-1);
            rtn.setDesc("success");
            rtn.setRemark(MessageFormat.format("餐厅不存在", merchantId));
            rtn.setDynamic(merchantId);
        }else {
            if (shop.getIsOpen() == 1) {
                rtn.setCode(0);
                rtn.setDesc("success");
                rtn.setRemark(MessageFormat.format("餐厅{0}营业中", merchantId));
                rtn.setDynamic(merchantId);
            }else {
                rtn.setCode(1);
                rtn.setDesc("success");
                rtn.setRemark(MessageFormat.format("餐厅{0}休息中", merchantId));
                rtn.setDynamic(merchantId);
            }
        }
        return gson1.toJson(rtn);
    }


    /**
     * 获取订单状态
     * @param orderId
     * @return
     */
    public String getOrderStatus(String orderId,String shopId) {
        String result = "";
        Rtn rtn = new Rtn();
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(orderId)) {
            return gson1.toJson(rtn);
        }
        try {
            OrderRequest request = new OrderRequest();
            request.setEleme_order_id(orderId);
            OOrder order = eleMeApiService.orderDetail(orderId,shopId);
            if(order==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(orderId);
                return gson.toJson(rtn);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 0);
            jsonObject.put("desc", "success");
            jsonObject.put("platformOrderId", orderId);
            jsonObject.put("orderStatus", new SysFacadeService().tranELOrderStatus(order.getStatus()));
            return jsonObject.toJSONString();
        }catch (ElemeException ex){
            rtn.setCode(-997);
            log = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex) {
            rtn.setCode(-999);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }catch (Exception ex){
            rtn.setCode(-998);
            log = sysFacadeService.functionRtn.apply(ex);
            log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
        }finally {
            if (log != null) {
                log.setLogId(orderId.concat(log.getLogId()));
                log.setTitle(MessageFormat.format("修改订单{0}状态失败", orderId));
                if (StringUtil.isEmpty(log.getRequest())) {
                    log.setRequest("{".concat(MessageFormat.format("\"eleme_order_id\":{0}", orderId)).concat("}"));
                }
                sysFacadeService.updSynLog(log);
                rtn.setDesc("发生异常");
                rtn.setLogId(log.getLogId());
                rtn.setRemark(MessageFormat.format("修改订单{0}状态失败!",orderId));
            }
        }
        return gson1.toJson(rtn);
    }


    /**
     * 批量获取餐厅状态
     * @param merchantIds 多个餐厅以逗号隔开
     * @return
     */
    public String getRestaurantStatus(String merchantIds) {
        String result = null;
        String rtnbuffer = "";
        Log log = null;
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(merchantIds)) {
            rtn.setCode(-1);
            rtn.setDesc("餐厅ID列表为空");
            rtn.setRemark("餐厅ID列表为空");
        }else {
            try {
                RestaurantRequest restaurantRequest= new RestaurantRequest();
                restaurantRequest.setRestaurant_ids(merchantIds);
                result = eleMeApiService.getRestaurantStatus(restaurantRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if (!StringUtil.isEmpty(obj.getData())) {
                    JSONObject jsonObject  = JSONObject.parseObject(getGson().toJson(obj.getData()));
                    Map batchStatus = (Map) jsonObject.get("batch_status");
                    if (!StringUtil.isEmpty(batchStatus.get("failed"))) {
                        List list = (List) batchStatus.get("failed");
                        for (int i = 0; i < list.size(); i++) {
                            rtnbuffer += StringUtil.isEmpty(rtnbuffer)?"":",";
                            rtn.setCode(-1);
                            rtn.setDesc("failure");
                            rtn.setDynamic(list.get(i).toString());
                            rtn.setRemark(MessageFormat.format("餐厅{0}没数据", list.get(i).toString()));
                            rtnbuffer += gson1.toJson(rtn);
                        }
                    }
                    String[] split = merchantIds.split(",");
                    for (int i = 0; i < split.length; i++) {
                        if (!StringUtil.isEmpty(batchStatus.get(split[i]))) {
                            Status status = getGson().fromJson(getGson().toJson(batchStatus.get(split[i])), Status.class);
                            rtnbuffer += StringUtil.isEmpty(rtnbuffer)?"":",";
                            if (!StringUtil.isEmpty(status)) {
                                if (status.getIsvalid() == 1 && status.getIsopen() == 1){
                                    rtn.setCode(0);
                                    rtn.setRemark(MessageFormat.format("餐厅{0}开店", split[i]));
                                }
                                if (status.getIsvalid() == 1 && status.getIsopen() == 0){
                                    rtn.setCode(1);
                                    rtn.setRemark(MessageFormat.format("餐厅{0}关店", split[i]));
                                }
                                rtn.setDesc("success");
                                rtn.setDynamic(split[i]);
                                rtnbuffer += gson1.toJson(rtn);
                            }
                        }
                    }
                    return rtnbuffer;
                }else {
                    rtn.setCode(-1);
                    rtn.setDesc("failure");
                    rtn.setDynamic(merchantIds);
                    rtn.setRemark("餐厅ID列表无对应数据");
                }
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }
            catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("获取餐厅{0}信息失败", merchantIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"restaurant_id\":{0}", merchantIds)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("获取餐厅{0}信息失败", merchantIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 同意退单
     * @param elemeOrderIds
     * @return
     */
    public String agreeRefund(String elemeOrderIds) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderIds);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderIds)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                String result = eleMeApiService.agreeRefund(elemeOrderIds);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("订单{0}不存在", elemeOrderIds));
                }
                rtn.setDesc("success");
            }catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            } catch (ElemeException ex) {
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex) {
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderIds.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("同意订单{0}退单失败", elemeOrderIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0}", elemeOrderIds)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("同意订单{0}退单失败!",elemeOrderIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }

    /**
     * 不同意退单
     * @param elemeOrderIds
     * @param reason
     * @return
     */
    public String disAgreeRefund(String elemeOrderIds, String reason) {
        Rtn rtn = new Rtn();
        rtn.setDynamic(elemeOrderIds);
        Log log = null;
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(elemeOrderIds)) {
            rtn.setCode(1);
            rtn.setDesc("订单id为空");
            rtn.setRemark("订单id为空");
        }else {
            try {
                OrderRequest orderRequest = new OrderRequest();
                orderRequest.setEleme_order_id(elemeOrderIds);
                orderRequest.setReason(reason);
                String result = eleMeApiService.disAgreeRefund(orderRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("订单{0}不存在", elemeOrderIds));
                }
            }catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            } catch (ElemeException ex) {
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }catch (Exception ex) {
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(elemeOrderIds.concat(log.getLogId()));
                    log.setTitle(MessageFormat.format("不同意订单{0}退单失败", elemeOrderIds));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"eleme_order_ids\":{0},\"reason\":{1}", elemeOrderIds, reason)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDynamic(elemeOrderIds);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("不同意订单{0}退单失败!",elemeOrderIds));
                }
            }
        }
        return gson1.toJson(rtn);
    }


    /**
     * 删除食物
     * @param foodid
     * @return
     */
    public String deleteFoods(String foodid){
        String result = null;
        Log log = null;
        Rtn rtn = new Rtn();
        rtn.setDynamic(foodid);
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if (StringUtil.isEmpty(foodid)) {
            rtn.setCode(-1);
            rtn.setDesc("食物ID为空");
            rtn.setRemark("食物ID为空");
        }else {
            try {
                OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
                oldFoodsRequest.setFood_id(foodid);
                result = eleMeApiService.delecFoods(oldFoodsRequest);
                Result obj = getGson().fromJson(result, Result.class);
                if ("ok".equals(obj.getMessage().toString())) {
                    rtn.setCode(0);
                    rtn.setRemark(obj.getMessage().toString());
                }else {
                    rtn.setCode(-1);
                    rtn.setRemark(MessageFormat.format("删除食物{0}失败", foodid));
                }
                rtn.setDesc("success");
            }catch (ElemeException ex){
                rtn.setCode(-997);
                log = sysFacadeService.functionRtn.apply(ex);
            }
            catch (ScheduleException ex) {
                rtn.setCode(-999);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }
            catch (Exception ex){
                rtn.setCode(-998);
                log = sysFacadeService.functionRtn.apply(ex);
                log.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            }finally {
                if (log != null) {
                    log.setLogId(log.getLogId());
                    log.setTitle(MessageFormat.format("删除食物{0}失败", foodid));
                    if (StringUtil.isEmpty(log.getRequest())) {
                        log.setRequest("{".concat(MessageFormat.format("\"foodid\":{0}", foodid)).concat("}"));
                    }
                    sysFacadeService.updSynLog(log);
                    rtn.setDesc("发生异常");
                    rtn.setLogId(log.getLogId());
                    rtn.setRemark(MessageFormat.format("删除食物{0}失败", foodid));
                }
            }
        }
        return  gson1.toJson(rtn);
    }

    /**
     * 获取code & token
     * @param stringMap
     * @return
     */
    public String getCodeAndAuthToken(Map<String,String[]> stringMap){
        Log log1 = null;
        // token入库
        AuthToken token = new AuthToken();
        token.setCode(stringMap.get("code")[0]);
        token.setState(stringMap.get("state")[0]);
        Token tt = eleMeApiService.getAuthToken(token.getCode(),token.getState());
        token.setToken(tt.getAccessToken());
        token.setToken_type(tt.getTokenType());
        token.setExpires_in(tt.getExpires());
        token.setRefresh_token(tt.getRefreshToken());
        try {
            //获取门店授权列表
            Map<String,Object> map = eleMeApiService.getAuthShops(tt);
            //添加或者修改token
            eleMeInnerService.addOrUpdateToken(token,map);
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
            log1.setLogId(log1.getLogId());
            log1.setTitle(MessageFormat.format("接受饿了么传入的code:", token.getCode()));
            if (StringUtil.isEmpty(log1.getRequest()))
                log1.setRequest("{".concat(MessageFormat.format("\"Code\":{0}", token.getCode())).concat("}"));
            log1.setRequest("{".concat(MessageFormat.format("\"Code\":{0}", token.getCode())).concat("}"));
            sysFacadeService.updSynLog(log1);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取历史订单
     * @return
     */
    public String getHistoryOrder() {
        List<Order> orders = eleMeInnerService.findAll();
        return getGson().toJson(orders);
    }

    public void formatEle(OrderEle order,JSONObject jsonObject){

        order.setActiveAt(DateTimeUtil.formatDateString(jsonObject.getString("activeAt"),"yyyy-MM-dd\'T\'HH:mm:ss"));
        order.setActivityTotal(jsonObject.getInteger("activityTotal"));
        order.setAddress(jsonObject.getString("address"));
        order.setBook(jsonObject.getBoolean("book"));
        order.setConsignee(jsonObject.getString("consignee"));
        order.setCreatedAt(DateTimeUtil.formatDateString(jsonObject.getString("createdAt"),"yyyy-MM-dd\'T\'HH:mm:ss"));
        order.setDaySn(jsonObject.getByte("daySn"));
        order.setDeliverFee(jsonObject.getDouble("deliverFee"));
        order.setDeliveryGeo(jsonObject.getString("deliveryGeo"));
        order.setDeliveryPoiAddress(jsonObject.getString("deliveryPoiAddress"));
        order.setDescription(jsonObject.getString("description"));
        order.setDowngraded(jsonObject.getBoolean("downgraded"));
        order.setElemePart(jsonObject.getDouble("elemePart"));
        List<OGoodsGroup> groups = getGoodsGroup(order,jsonObject);
        order.setGroups(groups);
        order.setHongbao(jsonObject.getDouble("hongbao"));
        order.setId(jsonObject.getString("id"));
        order.setIncome(jsonObject.getDouble("income"));
        order.setInvoiced(jsonObject.getBoolean("invoiced"));
        order.setOnlinePaid(jsonObject.getBoolean("onlinePaid"));
        order.setOpenId(jsonObject.getString("openId"));
        //优惠卷
        List<OActivity> orderActivities = getActivity(order,jsonObject);
        order.setOrderActivities(orderActivities);
        order.setOriginalPrice(jsonObject.getDouble("originalPrice"));
        order.setPackageFee(jsonObject.getDouble("packageFee"));
        List<String> phoneList = getPhoneList(order,jsonObject);
        order.setPhoneList(phoneList);
        order.setRefundStatus(OOrderRefundStatus.valueOf(jsonObject.getString("refundStatus")));
        order.setServiceFee(jsonObject.getDouble("serviceFee"));
        order.setServiceRate(jsonObject.getDouble("serviceRate"));
        order.setShopId(jsonObject.getLong("shopId"));
        order.setShopName(jsonObject.getString("shopName"));
        order.setShopPart(jsonObject.getDouble("shopPart"));
        order.setStatus(jsonObject.getString("status"));
        order.setTaxpayerId(jsonObject.getString("taxpayerId"));
        order.setTotalPrice(jsonObject.getDouble("totalPrice"));
        order.setUserId(jsonObject.getInteger("userId"));
    }

    //获取商品信息
    private List<OGoodsGroup> getGoodsGroup(OrderEle order,JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("groups");
        if(jsonArray==null || jsonArray.size()==0){
            return  new ArrayList<>();
        }
        //商品数组
        List<OGoodsGroup> groups = new ArrayList<>();
        for(int i=0 ;i<jsonArray.size();i++){
            OGoodsGroup goodsGroup = new OGoodsGroup();
            JSONObject json = jsonArray.getJSONObject(i);
            goodsGroup.setName(json.getString("name"));
            goodsGroup.setType(OOrderDetailGroupType.valueOf(json.getString("type")));
            JSONArray itemArray = json.getJSONArray("items");
            if(itemArray==null || itemArray.size()==0){
                goodsGroup.setItems(new ArrayList<>());
                continue;
            }
            //商品详情
            List<OGoodsItem> items = new ArrayList<>();
            for(int j=0;j<itemArray.size();j++){
                OGoodsItem item = new OGoodsItem();
                JSONObject jsonItem = itemArray.getJSONObject(j);
                //属性
                List<OGroupItemAttribute> attributes = new ArrayList<>();
                if(jsonItem.getJSONArray("additions")==null || jsonItem.getJSONArray("additions").size()==0){
                    item.setAttributes(attributes);
                }else {
                    for(int k =0;k<jsonItem.getJSONArray("additions").size();k++){
                        OGroupItemAttribute attribute = new OGroupItemAttribute();
                        attribute.setName(jsonItem.getJSONArray("additions").getJSONObject(k).getString("name"));
                        attribute.setValue(jsonItem.getJSONArray("additions").getJSONObject(k).getString("value"));
                    }
                    item.setAttributes(attributes);
                }
                //规格
                List<OGroupItemSpec> newSpecs = new ArrayList<>();
                if(jsonItem.getJSONArray("newSpecs")==null || jsonItem.getJSONArray("newSpecs").size()==0){
                    item.setNewSpecs(newSpecs);
                }else {
                    for(int m =0;m<jsonItem.getJSONArray("newSpecs").size();m++){
                        OGroupItemSpec newSpec = new OGroupItemSpec();
                        newSpec.setName(jsonItem.getJSONArray("newSpecs").getJSONObject(m).getString("name"));
                        newSpec.setValue(jsonItem.getJSONArray("newSpecs").getJSONObject(m).getString("value"));
                    }
                    item.setNewSpecs(newSpecs);
                }
                item.setBarCode(jsonItem.getString("barCode"));
                item.setCategoryId(jsonItem.getLong("categoryId"));
                item.setExtendCode(jsonItem.getString("extendCode"));
                item.setId(jsonItem.getLong("id"));
                item.setName(jsonItem.getString("name"));
                item.setPrice(jsonItem.getDouble("price"));
                item.setQuantity(jsonItem.getInteger("quantity"));
                item.setSkuId(jsonItem.getLong("skuId"));
                item.setTotal(jsonItem.getDouble("total"));
                item.setWeight(jsonItem.getDouble("userPrice"));
                items.add(item);
            }
            goodsGroup.setItems(items);
            groups.add(goodsGroup);
        }
        return groups;
    }

    //优惠卷
    private List<OActivity> getActivity(OrderEle order ,JSONObject jsonObject){
        JSONArray jsonArray = jsonObject.getJSONArray("orderActivities");
        if(jsonArray==null || jsonArray.size()==0){
           return new ArrayList<>();
        }
        List<OActivity> oActivities = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            OActivity activity = new OActivity();
            JSONObject json = jsonArray.getJSONObject(i);
            activity.setId(json.getLong("id"));
            activity.setName(json.getString("name"));
            activity.setCategoryId(json.getInteger("categoryId"));
            activity.setAmount(json.getDouble("categoryId"));
            oActivities.add(activity);
        }
        return oActivities;
    }


    private List<String> getPhoneList(OrderEle order,JSONObject jsonObject){
        if(StringUtil.isEmpty(jsonObject.get("phoneList"))){
           return new ArrayList<>();
        }
        List<String> list = (List<String>)jsonObject.get("phoneList");
        List<String> strings = new ArrayList<>();
        for(int i=0;i<list.size();i++){
            strings.add(list.get(i));
        }
        return strings;
    }
}

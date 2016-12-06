package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.entity.jdhome.*;
import com.wangjunneil.schedule.service.jdhome.JdHomeApiService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Service
public class JdHomeFacadeService {

    private static Logger log = Logger.getLogger(JdHomeFacadeService.class.getName());

    @Autowired
    private JdHomeApiService jdHomeApiService;

    @Autowired
    private JdHomeInnerService jdHomeInnerService;

    /**
     * 门店开业
     * @param shopId 商家门店Id
     * @param platformShopId 平台门店Id
     * @return String
     */
    public String startBusiness(String shopId,String platformShopId){
        Rtn rtn = new Rtn();
        rtn.setCode(1);
        rtn.setDesc("error");
        rtn.setRemark("京东未提供门店开/歇业接口,请登录京东到家APP进行开/歇操作");
        rtn.setDynamic(shopId);
        return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
    }

    /**
     * 门店歇业
     * @param shopId 商家门店Id
     * @param platformShopId 平台门店id
     * @return String
     */
    public String endBusiness(String shopId,String platformShopId){

        Rtn rtn = new Rtn();
        rtn.setCode(1);
        rtn.setDesc("error");
        rtn.setRemark("京东未提供门店开/歇业接口,请登录京东到家APP进行开/歇操作");
        rtn.setDynamic(shopId);
        return  new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create().toJson(rtn);
    }

    /**
     * 批量修改商品上下架
     * @param stockRequests 商品列表
     * @param shopId 商家编号
     * @return String 接口响应信息
     */
    public String updateAllStockOn(List<QueryStockRequest> stockRequests ,String shopId){
        try{
            String json = jdHomeApiService.updateAllStockOn(stockRequests,shopId);
            log.info("=====批量商品上下架接口返回信息:"+json+"=====");
            return json;
        }catch (Exception ex){
           return "商品批量上下架失败";
        }
    }

    /**
     * 新增商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String addShopCategory(shopCategory shopCategory){
        try {
            String json = jdHomeApiService.addShopCategory(shopCategory);
            log.info("=====新增商品分类接口返回信息:"+json+"=====");
            return json;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 修改商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String updateShopCategory(shopCategory shopCategory){
        try {
            String json = jdHomeApiService.updateShopCategory(shopCategory);
            log.info("=====修改商品分类接口返回信息:"+json+"=====");
            return  json;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 删除商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String deleteShopCategory(shopCategory shopCategory){
        try{
            String json = jdHomeApiService.deleteShopCategory(shopCategory);
            log.info("=====删除商品分类接口返回信息:"+json+"=====");
            return json;
        }catch (Exception e){
            return "";
        }

    }

    /**
     * 新增订单
     * @param jdParamJson 接口推送参数
     * @param shopId 门店Id
     * @return String 返回响应结果给到家平台
     */
    public String newOrder(String jdParamJson,String shopId){

        JSONObject jdParam = JSONObject.parseObject(jdParamJson);
        if(jdParam == null){
            return "京东推送参数为空";
        }
        String billId = jdParam.getString("billId");
        String statusId = jdParam.getString("statusId");
        String timestamp = jdParam.getString("timestamp");

        try {
            String json  = jdHomeApiService.newOrder(billId,statusId,timestamp,shopId);
            log.info("=====订单查询接口返回信息:"+json+"=====");
            JSONObject jsonObject =JSONObject.parseObject(json);
            JSONObject apiJson= JSONObject.parseObject(jsonObject.getString("data"));
            if("0".equals(jsonObject.get("code")) && "0".equals(apiJson.getString("code"))){
                log.info("=====订单接口推送成功=====");
            }else {
                log.info("=====订单接口推送失败=====");
                return "{\"code\":\"1\",\"msg\":\"failure\",\"data\":\"{}\"}";
            }
            JSONArray jsonArray = JSONObject.parseObject(apiJson.getString("result")).getJSONArray("resultList");
            if(jsonArray!=null && jsonArray.size()>0){
                List<OrderInfoDTO> orders = new ArrayList<>();
                for(int i=0; i<jsonArray.size();i++){
                    OrderInfoDTO order = new OrderInfoDTO();
                    //订单详情
                    JSONObject jsonOrder = jsonArray.getJSONObject(i);
                    order.setOrderId(jsonOrder.getLong("orderId"));
                    order.setSrcOrderId(jsonOrder.getString("srcOrderId"));
                    order.setSrcInnerType(jsonOrder.getInteger("srcInnerType"));
                    order.setSrcInnerOrderId(jsonOrder.getLong("srcInnerOrderId"));
                    order.setOrderType(jsonOrder.getInteger("orderType"));
                    order.setOrderStatus(jsonOrder.getInteger("orderStatus"));
                    order.setOrderStatusTime(jsonOrder.getDate("orderStatusTime"));
                    order.setOrderStartTime(jsonOrder.getDate("orderStartTime"));
                    order.setOrderPurchaseTime(jsonOrder.getDate("orderPurchaseTime"));
                    order.setOrderAgingType(jsonOrder.getInteger("orderAgingType"));
                    order.setOrderPreStartDeliveryTime(jsonOrder.getDate("orderPreStartDeliveryTime"));
                    order.setOrderPreEndDeliveryTime(jsonOrder.getDate("orderPreEndDeliveryTime"));
                    order.setOrderCancelTime(jsonOrder.getDate("orderCancelTime"));
                    order.setOrderCancelRemark(jsonOrder.getString("orderCancelRemark"));
                    order.setOrgCode(jsonOrder.getString("orgCode"));
                    order.setBuyerFullName(jsonOrder.getString("buyerFullName"));
                    order.setBuyerFullAddress(jsonOrder.getString("buyerFullAddress"));
                    order.setBuyerTelephone(jsonOrder.getString("buyerTelephone"));
                    order.setBuyerMobile(jsonOrder.getString("buyerMobile"));
                    order.setProduceStationNo(jsonOrder.getString("produceStationNo"));
                    order.setProduceStationName(jsonOrder.getString("produceStationName"));
                    order.setProduceStationNoIsv(jsonOrder.getString("produceStationNoIsv"));
                    order.setDeliveryStationNo(jsonOrder.getString("deliveryStationNo"));
                    order.setDeliveryStationName(jsonOrder.getString("deliveryStationName"));
                    order.setDeliveryCarrierNo(jsonOrder.getString("deliveryCarrierNo"));
                    order.setDeliveryCarrierName(jsonOrder.getString("deliveryCarrierName"));
                    order.setDeliveryBillNo(jsonOrder.getString("deliveryBillNo"));
                    order.setDeliveryPackageWeight(jsonOrder.getDouble("deliveryPackageWeight"));
                    order.setDeliveryConfirmTime(jsonOrder.getDate("deliveryConfirmTime"));
                    order.setOrderPayType(jsonOrder.getInteger("orderPayType"));
                    order.setOrderTotalMoney(jsonOrder.getLong("orderTotalMoney"));
                    order.setOrderDiscountMoney(jsonOrder.getLong("orderDiscountMoney"));
                    order.setOrderFreightMoney(jsonOrder.getLong("orderFreightMoney"));
                    order.setOrderBuyerPayableMoney(jsonOrder.getLong("orderBuyerPayableMoney"));
                    order.setPackagingMoney(jsonOrder.getLong("packagingMoney"));
                    order.setOrderInvoiceOpenMark(jsonOrder.getInteger("orderInvoiceOpenMark"));
                    order.setAdjustId(jsonOrder.getLong("adjustId"));
                    order.setAdjustIsExists(jsonOrder.getBoolean("adjustIsExists"));
                    order.setTs(jsonOrder.getDate("ts"));
                    //扩展类
                    order.setOrderExtend(getOrderExtend(jsonOrder.getJSONObject("orderExtend")));
                    //商品信息
                    order.setProductList(getProducts(jsonOrder.getJSONArray("product")));
                    //折扣信息
                    order.setDiscountList(getDiscounts(jsonOrder.getJSONArray("discount")));
                    orders.add(order);
                }
                if(orders !=null && orders.size()>0){
                    log.info("=====MongoDb insert Order start====");
                    try {
                        List list = new ArrayList<>();
                        jdHomeInnerService.addOrUpdateSyncOrder(orders);
                    }catch (Exception e){
                        return "{\"code\":\"2\",\"msg\":\"failure\",\"data\":\"{}\"}";
                    }
                    log.info("=====MongoDb insert Order end====");
                    return "{\"code\":\"0\",\"msg\":\"success\",\"data\":\"{}\"}";
                }
            }
        }catch (Exception e){
            return "";
        }
        return null;
    }

    /**
     * 订单扩展信息
     * @param jsonObject
     * @return
     */
    private OrderExtend getOrderExtend(JSONObject jsonObject){
        OrderExtend orderExtend = new OrderExtend();
        if(jsonObject == null){
            return orderExtend;
        }
        orderExtend.setBuyerCoordType(jsonObject.getInteger("OrderExtend"));
        orderExtend.setBuyerLng(jsonObject.getDouble("buyerLng"));
        orderExtend.setBuyerLat(jsonObject.getDouble("buyerLat"));
        orderExtend.setOrderInvoiceType(jsonObject.getString("orderInvoiceType"));
        orderExtend.setOrderInvoiceTitle(jsonObject.getString("orderInvoiceTitle"));
        orderExtend.setOrderInvoiceContent(jsonObject.getString("orderInvoiceContent"));
        orderExtend.setOrderBuyerRemark(jsonObject.getString("orderBuyerRemark"));
        orderExtend.setBusinessTag(jsonObject.getString("businessTag"));
        orderExtend.setEquipmentId(jsonObject.getString("equipmentId"));
        orderExtend.setBuyerPoi(jsonObject.getString("buyerPoi"));
        orderExtend.setBusinessTagId(jsonObject.getInteger("businessTagId"));
        orderExtend.setArtificerPortraitUrl(jsonObject.getString("artificerPortraitUrl"));
        return orderExtend;
    }

    /**
     * 订单商品信息
     * @param jsonArray
     * @return
     */
    private List<OrderProductDTO> getProducts(JSONArray jsonArray){
        List<OrderProductDTO> products = new ArrayList<>();
        if(jsonArray == null || jsonArray.size() ==0){
            return products;
        }
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            OrderProductDTO product = new OrderProductDTO();
            product.setAdjustId(jsonObject.getLong("adjustId"));
            product.setSkuId(jsonObject.getLong("skuId"));
            product.setSkuName(jsonObject.getString("skuName"));
            product.setSkuIdIsv(jsonObject.getString("skuIdIsv"));
            product.setSkuSpuId(jsonObject.getLong("skuSpuId"));
            product.setSkuJdPrice(jsonObject.getLong("skuJdPrice"));
            product.setSkuCount(jsonObject.getInteger("skuCount"));
            product.setIsGift(jsonObject.getBoolean("isGift"));
            product.setAdjustMode(jsonObject.getInteger("adjustMode"));
            product.setUpcCode(jsonObject.getString("upcCode"));
            product.setSkuStorePrice(jsonObject.getLong("skuStorePrice"));
            product.setSkuCostPrice(jsonObject.getLong("skuCostPrice"));
            product.setPromotionType(jsonObject.getInteger("PromotionType"));
            product.setSkuTaxRate(jsonObject.getString("skuTaxRate"));
            product.setPromotionId(jsonObject.getLong("promotionId"));
            products.add(product);
        }
        return products;
    }

    /**
     * 订单折扣信息
     * @param jsonArray
     * @return List
     */
    private List<OrderDiscountDTO> getDiscounts(JSONArray jsonArray){
        List<OrderDiscountDTO> discounts =  new ArrayList<>();
        if(jsonArray == null || jsonArray.size()==0){
            return discounts;
        }
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            OrderDiscountDTO discount = new OrderDiscountDTO();
            discount.setAdjustId(jsonObject.getLong("adjustId"));
            discount.setSkuId(jsonObject.getLong("skuId"));
            discount.setSkuIds(jsonObject.getString("skuIds"));
            discount.setDiscountType(jsonObject.getInteger("discountType"));
            discount.setDiscountDetailType(jsonObject.getInteger("discountDetailType"));
            discount.setDiscountCode(jsonObject.getString("discountCode"));
            discount.setDiscountPrice(jsonObject.getLong("discountPrice"));
            discounts.add(discount);
        }
        return discounts;
    }

    /**
     * 商家确认/取消接口
     * @param acceptOperate Entity
     * @return String
     */
    public String orderAcceptOperate(OrderAcceptOperate acceptOperate){
        try {
            String json = jdHomeApiService.orderAcceptOperate(acceptOperate);
            log.info("=====商家确认/取消接口返回信息:"+json+"=====");
            //返回成功/失败 若成功修改mongodb订单状态
            JSONObject jsonObject = JSONObject.parseObject(json);
            //业务接口返回结果
            JSONObject apiJson = JSONObject.parseObject(jsonObject.getString("data")) ;
            if("0".equals(jsonObject.getString("code")) && "0".equals(apiJson.getString("code"))){
               int status = 0;
               if(acceptOperate.getIsAgreed()){
                   status = Enum.getEnumDesc(Enum.OrderStatusJdHome.OrderReceived,Enum.OrderStatusJdHome.OrderReceived.toString()).get("code").getAsInt();
               }
               if(!acceptOperate.getIsAgreed()){
                   status = Enum.getEnumDesc(Enum.OrderStatusJdHome.OrderSysCancelled,Enum.OrderStatusJdHome.OrderSysCancelled.toString()).get("code").getAsInt();
               }
               jdHomeInnerService.updateStatus(acceptOperate,status);
            }
            return json;
        }catch (Exception e){
            return "";
        }
    }


    /**
     * 京东授权回调处理,接受京东传入的code,换取有效的token信息
     * @param tokenJson  返回的json字符串
     * @param companyId  商家编码
     */
    public void callback(String tokenJson,String companyId) {
        log.info("=====京东到家回调token数据:"+tokenJson+"=====");
        // token入库
        JdHomeAccessToken jdAccessToken = JSONObject.parseObject(tokenJson, JdHomeAccessToken.class);
        jdAccessToken.setCompanyId(companyId);
        if(!StringUtil.isEmpty(jdAccessToken.getCode())){
            //添加code
            jdHomeInnerService.addBackCode(jdAccessToken);
        }else {
            //添加token
            jdHomeInnerService.addRefreshToken(jdAccessToken);
        }
    }

    /**
     * 批量修改商品上下架
     * @param dishList 商品列表
     * @param doSale 上/下标示 0上架  1下架
     * @return String
     */
    public String updateAllStockOnAndOff(List<ParsFromPosInner> dishList,Integer doSale){
        if(dishList ==null || dishList.size()==0){
            return "批量上下架请求参数为空";
        }
        List<QueryStockRequest> requests = new ArrayList<>();
        //拼装请求参数
        for(int i=0;i<dishList.size();i++){
            QueryStockRequest stockRequest = new QueryStockRequest();
            ParsFromPosInner posInner = dishList.get(i);
            stockRequest.setDoSale(doSale);
            //查询到家商品Id
            String skuStr = querySkuInfo(posInner,stockRequest);
            if(!"".equals(skuStr)){
                return skuStr;
            }
            //查询到家门店Id
            String storeStr = getStoreInfoPageBean(posInner,stockRequest);
            if(!"".equals(skuStr)){
                return storeStr;
            }
            requests.add(stockRequest);
        }
        try{
            String json = jdHomeApiService.updateAllStockOn(requests, dishList.get(0).getShopId());
            log.info("=====批量商品上下架接口返回信息:"+json+"=====");
            return json;
        }catch (Exception ex) {
            return "批量修改商品上下架失败";
        }
    }

    /**
     * 查询商家商品信息列表
     * @param posInner 门店信息
     * @param stockRequest 商品信息
     * @return String
     */
    public String querySkuInfo(ParsFromPosInner posInner,QueryStockRequest stockRequest){
        String rtn = "";
        try {
            String json = jdHomeApiService.querySkuInfos(posInner.getDishId(),posInner.getShopId());
            log.info("=====查询商家商品信息接口返回信息:"+json+"=====");
            if(!StringUtil.isEmpty(json)){
                JSONObject jsonObject = JSON.parseObject(json);
                //判断接口是否调用成功
                if("0".equals(jsonObject.getString("code")) && "0".equals(JSONObject.parseObject(jsonObject.getString("data")).getString("code"))){
                    JSONArray array = JSONArray.parseArray(JSONObject.parseObject(JSONObject.parseObject(jsonObject.getString("data")).getString("result")).getString("result"));
                    if(array !=null && array.size()>0){
                        JSONObject js = array.getJSONObject(0);
                        stockRequest.setSkuId(js.getLong("skuId"));
                    }
                }
            }
        }catch (Exception e){
            return "获取平台商品信息失败";
        }
        return rtn;
    }

    /**
     * 根据查询条件分页获取门店基本信息
     * @param posInner 门店信息
     * @param stockRequest 商品信息
     * @return String
     */
    public String getStoreInfoPageBean(ParsFromPosInner posInner,QueryStockRequest stockRequest){
        String rtn ="";
        try {
            String storeJson = jdHomeApiService.getStoreInfoPageBean(posInner.getShopId());
            log.info("=====查询商家门店接口返回信息:"+storeJson+"=====");
            if(!StringUtil.isEmpty(storeJson)){
                JSONObject jss = JSONObject.parseObject(storeJson);
                //判断接口是否调用成功
                if("0".equals(jss.getString("code")) && "0".equals(JSONObject.parseObject(jss.getString("data")).getString("code"))){
                    JSONArray storeArray = JSONObject.parseObject(JSONObject.parseObject(jss.getString("data")).getString("result")).getJSONArray("resultList");
                    if(storeArray !=null && storeArray.size()>0){
                        JSONObject json2 = storeArray.getJSONObject(0);
                        stockRequest.setStationNo(json2.getString("stationNo"));
                    }
                }
            }
        }catch (Exception e){
            return "获取平台门店信息失败";
        }
        return rtn;
    }
}


package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.entity.jdhome.*;
import com.wangjunneil.schedule.service.jdhome.JdHomeApiService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.URL;
import o2o.openplatform.sdk.util.SignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
     * 批量修改商品上下架
     * @param stockRequests 商品列表
     * @return
     */
    public String updateAllStockOn(List<QueryStockRequest> stockRequests) throws Exception{
        String json = jdHomeApiService.updateAllStockOn(stockRequests);
        return  json;
    }

    /**
     * 新增商品分类
     * @param shopCategory
     * @return
     */
    public String addShopCategory(ShopCategory shopCategory)throws Exception{
        String json = jdHomeApiService.addShopCategory(shopCategory);
        return json;
    }

    /**
     * 修改商品分类
     * @param shopCategory
     * @return
     * @throws Exception
     */
    public String updateShopCategory(ShopCategory shopCategory)throws Exception{
        String json = jdHomeApiService.updateShopCategory(shopCategory);
        return  json;
    }

    /**
     * 删除商品分类
     * @param shopCategory
     * @return
     * @throws Exception
     */
    public String deleteShopCategory(ShopCategory shopCategory)throws Exception{
        String json = jdHomeApiService.deleteShopCategory(shopCategory);
        return json;
    }

    //新增推送订单
    public String newOrder(String billId,String statusId,String timestamp)throws Exception{
        String json  = jdHomeApiService.newOrder(billId,statusId,timestamp);
        JSONObject jsonObject =JSONObject.parseObject(json);
        if("0".equals(jsonObject.get("code"))){
            log.info("=====订单接口推送成功=====");
        }else {
            log.info("=====订单接口推送失败=====");
            return "{\"code\":\"1\",\"msg\":\"failure\",\"data\":\"{}\"}";
        }
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("result").getJSONArray("resultList");
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
                order.setOrderStartTime(jsonOrder.getDate("orderStatusTime"));
                order.setOrderStartTime(jsonOrder.getDate("orderStartTime"));
                order.setOrderPurchaseTime(jsonOrder.getDate("orderPurchaseTime"));
                order.setOrderAgingType(jsonOrder.getInteger("orderPurchaseTime"));
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
                order.setOrderExtend(getOrdeExtend(jsonOrder.getJSONObject("orderExtend")));
                //商品信息
                order.setProductList(getProducts(jsonOrder.getJSONArray("product")));
                //折扣信息
                order.setDiscountList(getDiscounts(jsonOrder.getJSONArray("orderDiscountList")));
                orders.add(order);
            }
            if(orders !=null && orders.size()>0){
                log.info("=====MongoDb insert Order start====");
                try {
                    jdHomeInnerService.addSyncOrders(orders);
                }catch (Exception e){

                }
                log.info("=====MongoDb insert Order end====");
                return "{\"code\":\"0\",\"msg\":\"success\",\"data\":\"{}\"}";
            }
        }
        return null;
    }

    //订单扩展类
    private OrderExtend getOrdeExtend(JSONObject jsonObject){
        OrderExtend orderExtend = new OrderExtend();
        if(orderExtend == null){
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

    //商品信息
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

    //折扣信息
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
}

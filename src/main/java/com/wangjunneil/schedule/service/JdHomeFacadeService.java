package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jdhome.*;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.service.jdhome.JdHomeApiService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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

    @Autowired
    private SysInnerService sysInnerService;

    /**
     * 批量修改商品上下架
     * @param stockRequests 商品列表
     * @return
     */
    public String updateAllStockOn(List<QueryStockRequest> stockRequests ,String shopId) throws JdHomeException{
        try{
            String json = jdHomeApiService.updateAllStockOn(stockRequests,shopId);
            return json;
        }catch (Exception ex){
           return "{lg:,plat:,rtn:{}}";
        }
    }

    /**
     * 新增商品分类
     * @param shopCategory
     * @return
     */
    public String addShopCategory(shopCategory shopCategory)throws JdHomeException{
        try {
            String json = jdHomeApiService.addShopCategory(shopCategory);
            return json;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 修改商品分类
     * @param shopCategory
     * @return
     * @throws Exception
     */
    public String updateShopCategory(shopCategory shopCategory)throws JdHomeException{
        try {
            String json = jdHomeApiService.updateShopCategory(shopCategory);
            return  json;
        }catch (Exception e){
            return "";
        }
    }

    /**
     * 删除商品分类
     * @param shopCategory
     * @return
     * @throws Exception
     */
    public String deleteShopCategory(shopCategory shopCategory)throws JdHomeException{
        try{
            String json = jdHomeApiService.deleteShopCategory(shopCategory);
            return json;
        }catch (Exception e){
            return "";
        }

    }

    //新增推送订单
    public String newOrder(String billId,String statusId,String timestamp,String shopId)throws Exception{
        /*String json  = jdHomeApiService.newOrder(billId,statusId,timestamp,shopId);*/

        String json = "{\n" +
            "\t\"code\": \"0\",\n" +
            "\t\"msg\": \"操作成功\",\n" +
            "\t\"data\": {\n" +
            "\t\t\"code\": \"0\",\n" +
            "\t\t\"msg\": \"操作成功\",\n" +
            "\t\t\"result\": {\n" +
            "\t\t\t\"pageNo\": \"1\",\n" +
            "\t\t\t\"pageSize\": \"20\",\n" +
            "\t\t\t\"maxPageSize\": \"100\",\n" +
            "\t\t\t\"totalCount\": \"100\",\n" +
            "\t\t\t\"resultList\": [\n" +
            "\t\t\t\t{\n" +
            "\t\t\t\t\t\"orderId\": \"10000103688888\",\n" +
            "\t\t\t\t\t\"srcOrderId\": \"100001036354906\",\n" +
            "\t\t\t\t\t\"srcInnerType\": \"0\",\n" +
            "\t\t\t\t\t\"srcInnerOrderId\": \"0\",\n" +
            "\t\t\t\t\t\"orderType\": \"13000\",\n" +
            "\t\t\t\t\t\"orderStatus\": \"20010\",\n" +
            "\t\t\t\t\t\"orderStatusTime\": \"2016-07-09 18:34:07\",\n" +
            "\t\t\t\t\t\"orderStartTime\": \"2016-07-09 15:40:30\",\n" +
            "\t\t\t\t\t\"orderPurchaseTime\": \"2016-07-09 15:40:50\",\n" +
            "\t\t\t\t\t\"orderAgingType\": \"12\",\n" +
            "\t\t\t\t\t\"orderPreStartDeliveryTime\": \"2016-07-09 17:41:00\",\n" +
            "\t\t\t\t\t\"orderPreEndDeliveryTime\": \"2016-07-09 17:41:00\",\n" +
            "\t\t\t\t\t\"orderCancelTime\": \"2016-07-09 18:34:07\",\n" +
            "\t\t\t\t\t\"orderCancelRemark\": \"其它\",\n" +
            "\t\t\t\t\t\"orgCode\": \"71948\",\n" +
            "\t\t\t\t\t\"buyerFullName\": \"张小明\",\n" +
            "\t\t\t\t\t\"buyerFullAddress\": \"上海市徐汇区乐山路19号广元西路乐山路，乐山路19号\",\n" +
            "\t\t\t\t\t\"buyerTelephone\": \"18816912316\",\n" +
            "\t\t\t\t\t\"buyerMobile\": \"18816912316\",\n" +
            "\t\t\t\t\t\"produceStationNo\": \"11000478\",\n" +
            "\t\t\t\t\t\"produceStationName\": \"SH031永和大王-广元店\",\n" +
            "\t\t\t\t\t\"produceStationNoIsv\": \"\",\n" +
            "\t\t\t\t\t\"deliveryStationNo\": \"11000478\",\n" +
            "\t\t\t\t\t\"deliveryStationName\": \"SH031永和大王-广元店\",\n" +
            "\t\t\t\t\t\"deliveryCarrierNo\": \"9966\",\n" +
            "\t\t\t\t\t\"deliveryCarrierName\": \"众包配送\",\n" +
            "\t\t\t\t\t\"deliveryBillNo\": \"1000001\",\n" +
            "\t\t\t\t\t\"deliveryPackageWeight\": \"0.10000000149011612\",\n" +
            "\t\t\t\t\t\"deliveryConfirmTime\": \"2016-07-09 18:34:07\",\n" +
            "\t\t\t\t\t\"orderPayType\": \"4\",\n" +
            "\t\t\t\t\t\"orderTotalMoney\": \"2500\",\n" +
            "\t\t\t\t\t\"orderDiscountMoney\": \"0\",\n" +
            "\t\t\t\t\t\"orderFreightMoney\": \"300\",\n" +
            "\t\t\t\t\t\"orderBuyerPayableMoney\": \"28\",\n" +
            "\t\t\t\t\t\"packagingMoney\": \"300\",\n" +
            "\t\t\t\t\t\"orderInvoiceOpenMark\": \"0\",\n" +
            "\t\t\t\t\t\"adjustIsExists\": \"true\",\n" +
            "\t\t\t\t\t\"adjustId\": \"0\",\n" +
            "\t\t\t\t\t\"ts\": \"2016-07-09 18:34:07\",\n" +
            "\t\t\t\t\t\"orderExtend\": {\n" +
            "\t\t\t\t\t\t\"orderId\": \"100001036354906\",\n" +
            "\t\t\t\t\t\t\"buyerCoordType\": \"2\",\n" +
            "\t\t\t\t\t\t\"buyerLng\": \"\",\n" +
            "\t\t\t\t\t\t\"buyerLat\": \"31.19627\",\n" +
            "\t\t\t\t\t\t\"orderInvoiceType\": \"\",\n" +
            "\t\t\t\t\t\t\"orderInvoiceTitle\": \"\",\n" +
            "\t\t\t\t\t\t\"orderInvoiceContent\": \"\",\n" +
            "\t\t\t\t\t\t\"orderBuyerRemark\": \"\",\n" +
            "\t\t\t\t\t\t\"businessTag\": \"dj_new_cashier;dj_bld;picking_up;\",\n" +
            "\t\t\t\t\t\t\"equipmentId\": \"\",\n" +
            "\t\t\t\t\t\t\"buyerPoi\": \"东大街东里社区\",\n" +
            "\t\t\t\t\t\t\"businessTagId\": \"\",\n" +
            "\t\t\t\t\t\t\"ordererName\": \"\",\n" +
            "\t\t\t\t\t\t\"ordererMobile\": \"\",\n" +
            "\t\t\t\t\t\t\"artificerPortraitUrl\": \"\"\n" +
            "\t\t\t\t\t},\n" +
            "\t\t\t\t\t\"product\": [{\n" +
            "\t\t\t\t\t\t\t\"id\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"orderId\": \"\",\n" +
            "\t\t\t\t\t\t\t\"adjustId\": \"\",\n" +
            "\t\t\t\t\t\t\t\"skuId\": \"\",\n" +
            "\t\t\t\t\t\t\t\"skuName\": \"\",\n" +
            "\t\t\t\t\t\t\t\"skuIdIsv\": \"\",\n" +
            "\t\t\t\t\t\t\t\"skuSpuId\": \"2400\",\n" +
            "\t\t\t\t\t\t\t\"skuJdPrice\": \"2400\",\n" +
            "\t\t\t\t\t\t\t\"skuCount\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"isGift\": \"true\",\n" +
            "\t\t\t\t\t\t\t\"adjustMode\": \"0\",\n" +
            "\t\t\t\t\t\t\t\"upcCode\": \"2302480000005\",\n" +
            "\t\t\t\t\t\t\t\"artificerId\": \"\",\n" +
            "\t\t\t\t\t\t\t\"artificerName\": \"\",\n" +
            "\t\t\t\t\t\t\t\"skuStorePrice\": \"690\",\n" +
            "\t\t\t\t\t\t\t\"skuCostPrice\": \"590\",\n" +
            "\t\t\t\t\t\t\t\"PromotionType\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"skuTaxRate\": \"\",\n" +
            "\t\t\t\t\t\t\t\"promotionId\": \"140828\"\n" +
            "\t\t\t\t\t\t}],\n" +
            "\t\t\t\t\t\"orderDiscountList\": [\n" +
            "\t\t\t\t\t\t{\n" +
            "\t\t\t\t\t\t\t\"id\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"orderId\": \"100001036354906\",\n" +
            "\t\t\t\t\t\t\t\"adjustId\": \"1000010248591821\",\n" +
            "\t\t\t\t\t\t\t\"skuId\": \"2002445142\",\n" +
            "\t\t\t\t\t\t\t\"skuIds\": \"2000830029,2002355309\",\n" +
            "\t\t\t\t\t\t\t\"discountType\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"discountDetailType\": \"1\",\n" +
            "\t\t\t\t\t\t\t\"discountCode\": \"H4a522\",\n" +
            "\t\t\t\t\t\t\t\"discountPrice\": \"602\"\n" +
            "\t\t\t\t\t\t}\n" +
            "\t\t\t\t\t]\n" +
            "\t\t\t\t}\n" +
            "\t\t\t]\n" +
            "\t\t},\n" +
            "\t\t\"detail\": \"\"\n" +
            "\t}\n" +
            "}";
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
                    jdHomeInnerService.addOrUpdateSyncOrder(orders);
                }catch (Exception e){
                    return "{\"code\":\"2\",\"msg\":\"failure\",\"data\":\"{}\"}";
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

    //商家确认/取消接单接口
    public String orderAcceptOperate(OrderAcceptOperate acceptOperate)throws JdHomeException{
        try {
            String json = jdHomeApiService.orderAcceptOperate(acceptOperate);
            //返回成功/失败 若成功修改mongodb订单状态
            JSONObject jsonObject = JSONObject.parseObject(json);
            if("0".equals(jsonObject.equals(jsonObject.getString("code")))){
               int status = 0;
               if(!acceptOperate.getIsAgreed()){
                   status = Enum.GetEnumDesc(Enum.OrderStatusJdHome.OrderReceived,Enum.OrderStatusJdHome.OrderReceived.toString()).getInteger("code");
               }
               if(acceptOperate.getIsAgreed()){
                   status = Enum.GetEnumDesc(Enum.OrderStatusJdHome.OrderSysCancelled,Enum.OrderStatusJdHome.OrderSysCancelled.toString()).getInteger("code");
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
    * @param code  回调code码
    * @param state 续传state
    */
    public void callback(String code, String state ,String companyId) {
        Cfg cfg = jdHomeInnerService.getJdCfg(companyId);
        String appKey = cfg.getAppKey();
        String appSecret = cfg.getAppSecret();
        String callbackUrl = cfg.getCallback();

        // 拼接请求地址
        String tokenUrl = MessageFormat.format(Constants.JD_REQUEST_TOKEN_URL, appKey, callbackUrl, code, state, appSecret);
        // Get请求获取token
        String returnJson = HttpUtil.get(tokenUrl);

        // token入库
        JdHomeAccessToken jdAccessToken = JSONObject.parseObject(returnJson, JdHomeAccessToken.class);
        jdAccessToken.setUsername(state);
        jdHomeInnerService.addAccessToken(jdAccessToken);
    }
}


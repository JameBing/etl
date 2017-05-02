package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.*;
import com.wangjunneil.schedule.entity.jdhome.*;
import com.wangjunneil.schedule.service.jdhome.JdHomeApiService;
import com.wangjunneil.schedule.service.jdhome.JdHomeInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
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
    private SysFacadeService sysFacadeService;

    /**
     * 门店开业/歇业
     * @param shopId 商家门店编号
     * @param status 门店状态，0正常营业1休息中
     * @return String
     */
    public String openOrCloseStore(String shopId,int status){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        //拼装返回格式
        if(StringUtil.isEmpty(shopId)){
            return gson.toJson(rtn);
        }
        String stationNo = getStoreInfoPageBean(shopId);
        if(StringUtil.isEmpty(stationNo)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("无此门店");
            rtn.setDynamic(shopId);
            return gson.toJson(rtn);
        }
        String operator = "zybwjzb";
        try {
            String json = jdHomeApiService.changeCloseStatus(shopId,stationNo,operator,status);
            log.info("=====门店开业/歇业接口返回信息:"+json+"=====");
            //format返回结果
            getResult(json,rtn,shopId);
        }catch (JdHomeException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(shopId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("门店{0}开业/歇业失败", shopId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"stationNo\":{1},\"operator\":{2},\"status\":{3}", shopId, stationNo,operator,status)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("门店{0}开业/歇业失败！",shopId));
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 查看门店状态
     * @param shopId
     * @return
     */
    public String getStoreStatus(String shopId){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        //拼装返回格式
        //判断参数是否有值
        if(StringUtil.isEmpty(shopId)){
            return gson.toJson(rtn);
        }
        try {
            String json = jdHomeApiService.getStoreInfoPageBean(shopId);
            log.info("===== 查询门店状态接口返回信息:"+json+"=====");
            JSONObject jss = JSONObject.parseObject(json);
            //判断接口是否调用成功
            if("0".equals(jss.getString("code")) && "0".equals(JSONObject.parseObject(jss.getString("data")).getString("code"))) {
                JSONArray storeArray = JSONObject.parseObject(JSONObject.parseObject(jss.getString("data")).getString("result")).getJSONArray("resultList");
                if(storeArray ==null ||storeArray.size()==0){
                    rtn.setCode(-1);
                    rtn.setDesc("success");
                    rtn.setRemark("无此门店");
                    rtn.setDynamic(shopId);
                    return gson.toJson(rtn);
                }
                JSONObject jsonObject = storeArray.getJSONObject(0);
                if(jsonObject.getInteger("closeStatus")==0){
                    rtn.setCode(0);
                    rtn.setDesc("success");
                    rtn.setRemark("营业中");
                    rtn.setDynamic(shopId);
                    return gson.toJson(rtn);
                }
                if(jsonObject.getInteger("closeStatus")==1){
                    rtn.setCode(1);
                    rtn.setDesc("success");
                    rtn.setRemark("休息中");
                    rtn.setDynamic(shopId);
                    return gson.toJson(rtn);
                }
            }else {
                rtn.setCode(-1);
                rtn.setDesc("success");
                rtn.setRemark("无此门店");
                rtn.setDynamic(shopId);
                return gson.toJson(rtn);
            }

        }catch (JdHomeException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(shopId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("查询门店{0}状态失败", shopId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0}", shopId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("查询门店{0}状态失败",shopId));
            }
            return gson.toJson(rtn);
        }
    }



    /**
     * 查看订单状态
     * @param orderId
     * @return
     */
    public String getOrderStatus(String orderId,String shopId){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        //拼装返回格式
        //判断参数是否有值
        if(StringUtil.isEmpty(orderId)){
            return gson.toJson(rtn);
        }
        try {
            OrderInfoDTO orderInfoDTO = getOneOrder(orderId,"",shopId);
            if(orderInfoDTO==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(orderId));
                return gson.toJson(rtn);
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code",0);
            jsonObject.put("desc","success");
            jsonObject.put("platformOrderId",orderId);
            jsonObject.put("orderStatus",new SysFacadeService().tranJHOrderStatus(orderInfoDTO.getOrderStatus()));
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(shopId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("查询门店{0}状态失败", shopId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0}", shopId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("查询门店{0}状态失败",shopId));
            }
            return gson.toJson(rtn);
        }
    }


    /**
     * 新增商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String addShopCategory(shopCategory shopCategory){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(shopCategory)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空，请检查");
            return gson.toJson(rtn);
        }
        try {
            String json = jdHomeApiService.addShopCategory(shopCategory);
            log.info("=====新增商品分类接口返回信息:"+json+"=====");
            //format返回结果
            getResult(json,rtn,shopCategory.getShopId());
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(shopCategory.getShopId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("新增商品分类{0}失败", shopCategory.getShopId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"categoryName\":{1}", shopCategory.getShopId(),shopCategory.getShopCategoryName())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopCategory.getShopId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("新增商品分类{0}失败！",shopCategory.getShopId()));
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 修改商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String updateShopCategory(shopCategory shopCategory){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(shopCategory)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空，请检查");
            return gson.toJson(rtn);
        }
        try {
            String json = jdHomeApiService.updateShopCategory(shopCategory);
            log.info("=====修改商品分类接口返回信息:"+json+"=====");
            //format返回结果
            getResult(json,rtn,shopCategory.getShopId());
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(shopCategory.getShopId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("修改商品分类{0}失败", shopCategory.getShopId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"categoryName\":{1}", shopCategory.getShopId(),shopCategory.getShopCategoryName())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopCategory.getShopId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("修改商品分类{0}失败！",shopCategory.getShopId()));
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 删除商品分类
     * @param shopCategory 商品类别Entity
     * @return String 接口响应信息
     */
    public String deleteShopCategory(shopCategory shopCategory){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(shopCategory)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空，请检查");
            return gson.toJson(rtn);
        }
        try{
            String json = jdHomeApiService.deleteShopCategory(shopCategory);
            log.info("=====删除商品分类接口返回信息:"+json+"=====");
            //format返回结果
            getResult(json,rtn,shopCategory.getShopId());
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(shopCategory.getShopId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("删除商品分类{0}失败", shopCategory.getShopId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"shop_id\":{0},\"categoryName\":{1}", shopCategory.getShopId(),shopCategory.getShopCategoryName())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(shopCategory.getShopId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("删除商品分类{0}失败！",shopCategory.getShopId()));
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 新增订单
     * @param jdParamJson 接口推送参数
     * @param shopId 门店Id
     * @return String 返回响应结果给到家平台
     */
    public String newOrder(String jdParamJson,String shopId){
        Result result = new Result();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(jdParamJson)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("订单推送请求参数为空");
            return gson.toJson(result);
        }
        JSONObject jdParam = JSONObject.parseObject(jdParamJson);
        String billId = jdParam.getString("billId");
        String statusId = jdParam.getString("statusId");
        String timestamp = jdParam.getString("timestamp");
        try {
            String json  = jdHomeApiService.newOrder(billId, statusId, timestamp, shopId);
            log.info("=====订单查询接口返回信息:"+json+"=====");
            JSONObject jsonObject =JSONObject.parseObject(json);
            JSONObject apiJson= JSONObject.parseObject(jsonObject.getString("data"));
            if("0".equals(jsonObject.get("code")) && "0".equals(apiJson.getString("code"))){
                log.info("=====订单接口推送成功=====");
            }else {
                log.info("=====订单接口推送失败=====");
                result.setCode(-2);
                result.setMsg("failure");
                result.setData("订单推送失败");
                return gson.toJson(result);
            }
            JSONArray jsonArray = JSONObject.parseObject(apiJson.getString("result")).getJSONArray("resultList");
            if(jsonArray!=null && jsonArray.size()>0){
                List<OrderInfoDTO> orders = new ArrayList<>();
                for(int i=0; i<jsonArray.size();i++){
                    //订单详情
                    JSONObject jsonOrder = jsonArray.getJSONObject(i);
                    orders.add( getOrderMain(jsonOrder));
                }
                if(orders !=null && orders.size()>0){
                    log.info("=====MongoDb insert Order start====");
                    List<OrderWaiMai> orderWaiMaiList = new ArrayList<OrderWaiMai>();
                    orders.forEach(o->{
                        OrderWaiMai orderWaiMai = new OrderWaiMai();
                        orderWaiMai.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
                        orderWaiMai.setShopId(o.getProduceStationNoIsv());
                        orderWaiMai.setOrderId(sysFacadeService.getOrderNum(o.getProduceStationNoIsv()));
                        orderWaiMai.setPlatformOrderId(String.valueOf(o.getOrderId()));
                        orderWaiMai.setOrder(o);
                        orderWaiMaiList.add(orderWaiMai);
                    });
                    sysFacadeService.updSynWaiMaiOrder(orderWaiMaiList);
                    //jdHomeInnerService.addOrUpdateSyncOrder(orders);
                    log.info("=====MongoDb insert Order end====");
                    result.setCode(0);
                    result.setMsg("success");
                    result.setData("订单推送成功");
                }
            }
        }catch (JdHomeException ex) {
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException e) {
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }finally {
            if (log1 !=null){
                log1.setLogId(billId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("订单{0}推送失败！", billId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}", billId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                result.setCode(-99);
                result.setMsg("failure");
                result.setData("订单推送失败");
            }
            return gson.toJson(result);
        }
    }

    /**
     * 订单主数据
     * @param jsonOrder
     * @return
     */
    private OrderInfoDTO getOrderMain(JSONObject jsonOrder){
        OrderInfoDTO order = new OrderInfoDTO();
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
        return order;
    }

    /**
     * 订单扩展信息
     * @param jsonObject 扩展类
     * @return Entity
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
     * @param jsonArray 商品数组
     * @return List
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
     * @param jsonArray 折扣信息
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
     * @param orderId 订单Id
     * @param shopId 门店Id
     * @param isAgree true 接单 false不接单
     * @return String
     */
    public String orderAcceptOperate(String orderId,String shopId,Boolean isAgree){
        Rtn rtn = new Rtn();
        Log log1 = null;
        int isRec =0;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(orderId) || StringUtil.isEmpty(shopId) || StringUtil.isEmpty(isAgree)){
            return gson.toJson(rtn);
        }
        try {
            OrderInfoDTO orderInfoDTO = getOneOrder(orderId,"",shopId);
            if(orderInfoDTO==null){
                rtn.setCode(-1);
                rtn.setRemark("订单不存在");
                rtn.setDesc("error");
                rtn.setDynamic(String.valueOf(orderId));
                return gson.toJson(rtn);
            }
            if(orderInfoDTO.getOrderStatus()==Constants.JH_ORDER_WAITING){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("code",Constants.POS_ORDER_NOT_RECEIVED);
                jsonObject.put("desc","error");
                jsonObject.put("remark","订单已确实过，请更新状态");
                jsonObject.put("orderId",orderId);
                jsonObject.put("orderStatus",new SysFacadeService().tranJHOrderStatus(orderInfoDTO.getOrderStatus()));
                return jsonObject.toJSONString();
            }

            String operator = "admin";
            String json = jdHomeApiService.orderAcceptOperate(orderId,shopId,isAgree,operator);
            log.info("=====商家确认/取消接口返回信息:"+json+"=====");
            //format返回结果
            getResult(json,rtn,orderId);
            if(!StringUtil.isEmpty(rtn.getCode()) && rtn.getCode()==-1){
                rtn.setCode(Constants.RETURN_ORDER_CODE);
            }
            //返回成功/失败 若成功修改mongodb订单状态
            JSONObject jsonObject = JSONObject.parseObject(json);
            //业务接口返回结果
            JSONObject apiJson = JSONObject.parseObject(jsonObject.getString("data")) ;
            if("0".equals(jsonObject.getString("code")) && "0".equals(apiJson.getString("code"))){
               int status = 0;
               if(isAgree){
                   isRec=1;
                   status =Constants.JH_ORDER_RECEIVED;
               }
               if(!isAgree){
                   isRec=2;
                   status = Constants.JH_ORDER_USER_CANCELLED;
               }
               jdHomeInnerService.updateStatus(orderId,status);
            }
            //更新门店是否接单标识字段
            jdHomeInnerService.updateIsReceived(orderId,isRec);
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(orderId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("订单:{0}确认失败！", orderId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}", orderId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(orderId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("订单:{0}确认失败！",orderId));
            }
            return gson.toJson(rtn);
        }
    }


    /**
     * 京东授权回调处理,接受京东传入的code,换取有效的token信息
     * @param tokenJson  返回的json字符串
     * @param companyId  商家编码
     */
    public void callback(String tokenJson,String companyId) {
        log.info("=====京东到家回调token数据:"+tokenJson+"=====");
        Log log1 = null;
        // token入库
        JdHomeAccessToken jdAccessToken = JSONObject.parseObject(tokenJson, JdHomeAccessToken.class);
        jdAccessToken.setCompanyId(companyId);
        try {
            if(!StringUtil.isEmpty(jdAccessToken.getCode())){
                //添加code
                jdHomeInnerService.addBackCode(jdAccessToken);
            }else {
                //添加token
                jdHomeInnerService.addRefreshToken(jdAccessToken);
            }
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            log1.setLogId(log1.getLogId());
            log1.setTitle(MessageFormat.format("接受京东传入的code:", jdAccessToken.getCode()));
            if (StringUtil.isEmpty(log1.getRequest()))
                log1.setRequest("{".concat(MessageFormat.format("\"Code\":{0}", jdAccessToken.getCode())).concat("}"));
                log1.setRequest("{".concat(MessageFormat.format("\"Code\":{0}", jdAccessToken.getCode())).concat("}"));
            sysFacadeService.updSynLog(log1);
        }
    }

    /**
     * 批量修改商品上下架
     * @param dishList 商品列表
     * @param doSale 上/下标示 0上架  1下架
     * @return String
     */
    public String updateAllStockOnAndOff(List<ParsFromPosInner> dishList,Integer doSale){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String rtnStr ="";
        //入参非空判断
        if(dishList ==null || dishList.size()==0 || StringUtil.isEmpty(dishList.get(0).getShopId())){
            return gson.toJson(rtn);
        }
        //查询到家门店Id
        String stationNO = getStoreInfoPageBean(dishList.get(0).getShopId());
        if(StringUtil.isEmpty(stationNO)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("无此门店");
            return gson.toJson(rtn);
        }
        try{
            //拼装请求参数
            for(int i=0;i<dishList.size();i++){
                List<QueryStockRequest> requests = new ArrayList<>();
                QueryStockRequest stockRequest = new QueryStockRequest();
                ParsFromPosInner posInner = dishList.get(i);
                stockRequest.setDoSale(doSale);
                stockRequest.setStationNo(stationNO);
                //查询到家商品Id
                String skuStr = querySkuInfo(posInner);
                if("".equals(skuStr)){
                    rtn.setCode(-1);
                    rtn.setDesc("error");
                    rtn.setRemark("无此商品");
                    rtn.setDynamic(posInner.getDishId());
                    rtnStr = rtnStr+gson.toJson(rtn)+",";
                    continue;
                }
                stockRequest.setSkuId(Long.parseLong(skuStr));
                requests.add(stockRequest);
                String json = jdHomeApiService.updateAllStockOn(requests, dishList.get(0).getShopId());
                log.info("=====批量商品上下架接口返回信息:"+json+"=====");
                //format返回结果
                rtnStr =rtnStr+ getResultList(json,posInner.getDishId())+",";
            }
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(dishList.get(0).getDishId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("批量商品{0}上下架失败！", dishList.get(0).getDishId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", dishList.get(0).getDishId())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic("");
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("批量商品{0}上下架失败！", dishList.get(0).getDishId()));
            }
            if(!StringUtil.isEmpty(rtnStr)){
                return rtnStr.substring(0,rtnStr.length()-1);
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 查询商品状态列表
     * @param dishList
     * @return
     */
    public String querySkuStatus(List<ParsFromPosInner> dishList){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String rtnStr ="",resStr ="";
        List<ParsFromPosInner> tempList = new ArrayList<>();
        if(dishList == null || dishList.size()==0){
            return gson.toJson(rtn);
        }
        //查询到家门店Id
        String stationNO = getStoreInfoPageBean(dishList.get(0).getShopId());
        if(StringUtil.isEmpty(stationNO)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("无此门店");
            return gson.toJson(rtn);
        }
        try {
            List<BaseStockCenterRequest> requests = new ArrayList<>();
            //拼装参数
            for(int i=0;i<dishList.size();i++){
                BaseStockCenterRequest stock = new BaseStockCenterRequest();
                ParsFromPosInner posInner = dishList.get(i);
                //查询到家商品Id
                String skuStr = querySkuInfo(posInner);
                if("".equals(skuStr)){
                    rtn.setCode(-1);
                    rtn.setDesc("error");
                    rtn.setRemark("无此商品");
                    rtn.setDynamic(posInner.getDishId());
                    rtnStr = rtnStr+gson.toJson(rtn)+",";
                    continue;
                }
                stock.setStationNo(stationNO);
                stock.setSkuId(Long.parseLong(skuStr));
                requests.add(stock);
                tempList.add(dishList.get(i));
            }
            String json  = jdHomeApiService.queryDishStatus(requests, dishList.get(0).getShopId());
            JSONObject jsonObject = JSON.parseObject(json);
            //判断接口是否调用成功
            if("0".equals(jsonObject.getString("code")) && "0".equals(JSONObject.parseObject(jsonObject.getString("data")).getString("retCode"))){
                JSONArray array = JSONArray.parseArray(JSONObject.parseObject(jsonObject.getString("data")).getString("data"));
                if(array !=null && array.size()>0){
                    for(int j =0 ;j<array.size();j++){
                        JSONObject son = array.getJSONObject(j);
                        rtn.setCode(son.getInteger("vendibility"));
                        rtn.setDynamic(StringUtil.isEmpty(tempList)?"":tempList.get(j).getDishId());
                        resStr = resStr+gson.toJson(rtn)+",";
                    }
                }
            }
        }catch (JdHomeException ex) {
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch(Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            if (log1 !=null){
                log1.setLogId(dishList.get(0).getDishId().concat(log1.getLogId()));
                log1.setTitle("批量查询商品状态失败！");
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", dishList.get(0).getDishId())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic("");
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark("批量查询商品状态失败！");
            }
            if(!StringUtil.isEmpty(resStr) ||!StringUtil.isEmpty(rtnStr)){
               return rtnStr.substring(0,rtnStr.length()-1)+ resStr.substring(0,resStr.length()-1);
            }
            return gson.toJson(rtn);
        }
    }

    /**
     * 查询商家商品信息列表
     * @param posInner 门店信息
     * @return String
     */
    public String querySkuInfo(ParsFromPosInner posInner){
        String rtn = "";
        Log log1 = null;
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
                       return String.valueOf(js.getLong("skuId"));
                    }
                }
            }
        }catch (JdHomeException ex) {
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("查询商家商品信息{0}失败！", posInner.getDishId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", posInner.getDishId())).concat("}"));
                sysFacadeService.updSynLog(log1);
                return "查询商家商品信息失败";
            }
        }
        return rtn;
    }

    /**
     * 根据查询条件分页获取门店基本信息
     * @param shopId 门店Id
     * @return String
     */
    public String getStoreInfoPageBean(String shopId){
        String rtn ="";
        Log log1 =null;
        try {
            String storeJson = jdHomeApiService.getStoreInfoPageBean(shopId);
            log.info("=====查询商家门店接口返回信息:"+storeJson+"=====");
            if(!StringUtil.isEmpty(storeJson)){
                JSONObject jss = JSONObject.parseObject(storeJson);
                //判断接口是否调用成功
                if("0".equals(jss.getString("code")) && "0".equals(JSONObject.parseObject(jss.getString("data")).getString("code"))){
                    JSONArray storeArray = JSONObject.parseObject(JSONObject.parseObject(jss.getString("data")).getString("result")).getJSONArray("resultList");
                    if(storeArray !=null && storeArray.size()>0){
                        JSONObject json2 = storeArray.getJSONObject(0);
                        return  json2.getString("stationNo");
                    }
                }
            }
        }catch (JdHomeException ex) {
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("查询商家门店{0}失败！", shopId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"dishId\":{0}", shopId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                return "查询商家门店消息失败";
            }
        }
        return rtn;
    }

    /**
     * 拣货完成消息推送
     * @param jsonObject
     * @return
     */
    public String pickFinishOrder( JSONObject jsonObject){
        Result result = new Result();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        String billId = jsonObject.getString("billId");
        String statusId = jsonObject.getString("statusId");

        int status = Integer.parseInt(statusId);
        if(StringUtil.isEmpty(billId)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("拣货完成推送消息接口订单号为空");
            return gson.toJson(result);
        }
        Long orderId = Long.parseLong(billId);
        if(jdHomeInnerService.getOrder(billId)==null){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("拣货完成推送接口消息推送的订单号没有匹配到订单");
            return gson.toJson(result);
        }
        try {
            jdHomeInnerService.updateStatus(billId,status);
            result.setCode(0);
            result.setMsg("success");
            result.setData("拣货完成推送成功");
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            result.setCode(-99);
            result.setMsg("failure");
            result.setData("拣货完成推送失败");
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("拣货完成推送失败{0}！",orderId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}",orderId)).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
            return gson.toJson(result);
        }
    }

    /**
     * 开始配送消息推送
     * @param jsonObject
     * @param flag true 修改order  false 修改status
     * @return
     */
    public String deliveryOrder(JSONObject jsonObject,Boolean flag){
        Result result = new Result();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        String billId = jsonObject.getString("billId");
        String statusId = jsonObject.getString("statusId");

        int status = Integer.parseInt(statusId);
        if(StringUtil.isEmpty(billId)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("开始配送推送消息接口订单号为空");
            return gson.toJson(result);
        }
        OrderWaiMai orderWaiMai = jdHomeInnerService.getOrder(billId);
        if(StringUtil.isEmpty(orderWaiMai)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("开始配送推送消息接口订单号没有匹配到订单");
            return gson.toJson(result);
        }
        try {
            if(!flag){
                jdHomeInnerService.updateStatus(billId,status); //修改状态
            }else {
                OrderInfoDTO orderInfo = getOneOrder(billId,statusId,orderWaiMai.getShopId());
                sysFacadeService.updateWaiMaiOrder(String.valueOf(orderInfo.getOrderId()), orderWaiMai); //修改订单
            }
            result.setCode(0);
            result.setMsg("success");
            result.setData("开始配送推送成功");
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            result.setCode(-99);
            result.setMsg("failure");
            result.setData("开始配送推送失败");
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("开始配送推送失败{0}！",billId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}",billId)).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
            return gson.toJson(result);
        }
    }

    /**
     * 订单妥投消息
     * @param jsonObject
     * @param flag true 修改order  false 修改status
     * @return
     */
    public String finishOrder(JSONObject jsonObject,Boolean flag){
        Result result = new Result();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        String billId = jsonObject.getString("billId");
        String statusId = jsonObject.getString("statusId");

        int status = Integer.parseInt(statusId);
        if(StringUtil.isEmpty(billId)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("订单妥投消息接口订单号为空");
            return gson.toJson(result);
        }
        Long orderId = Long.parseLong(billId);
        OrderWaiMai orderWaiMai = jdHomeInnerService.getOrder(billId);
        if(StringUtil.isEmpty(orderWaiMai)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("订单妥投消息接口订单号没有匹配到订单");
            return gson.toJson(result);
        }
        try {
            if(!flag){
                jdHomeInnerService.updateStatus(billId,status); //修改状态
            }else {
                OrderInfoDTO orderInfo = getOneOrder(billId,statusId,orderWaiMai.getShopId());
                sysFacadeService.updateWaiMaiOrder(String.valueOf(orderInfo.getOrderId()), orderWaiMai); //修改订单
            }
            result.setCode(0);
            result.setMsg("success");
            result.setData("订单妥投推送成功");
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            result.setCode(-99);
            result.setMsg("failure");
            result.setData("订单妥投推送失败");
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("订单妥投推送失败{0}！",orderId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}",orderId)).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
            return gson.toJson(result);
        }
    }

    /**
     * 用户取消消息
     * @param jsonObject
     * @param flag true 修改order  false 修改status
     * @return
     */
    public String userCancelOrder(JSONObject jsonObject,Boolean flag){
        Result result = new Result();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        String billId = jsonObject.getString("billId");
        String statusId = jsonObject.getString("statusId");

        int status = Integer.parseInt(statusId);
        if(StringUtil.isEmpty(billId)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("用户取消消息接口订单号为空");
            return gson.toJson(result);
        }
        OrderWaiMai orderWaiMai = jdHomeInnerService.getOrder(billId);
        if(StringUtil.isEmpty(orderWaiMai)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("用户取消消息接口订单号没有匹配到订单");
            return gson.toJson(result);
        }
        try {
            if(!flag){
                jdHomeInnerService.updateStatus(billId,status); //修改状态
            }else {
                OrderInfoDTO orderInfo = getOneOrder(billId,statusId,orderWaiMai.getShopId());
                sysFacadeService.updateWaiMaiOrder(String.valueOf(orderInfo.getOrderId()), orderWaiMai); //修改订单
            }
            result.setCode(0);
            result.setMsg("success");
            result.setData("用户取消推送成功");
        }catch (ScheduleException e){
            log1 = sysFacadeService.functionRtn.apply(e);

            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            result.setCode(-99);
            result.setMsg("failure");
            result.setData("用户取消推送失败");
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("用户取消推送失败{0}！",billId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}",billId)).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
            return gson.toJson(result);
        }
    }

    /**
     * 改变订单状态
     * @param jdParamJson 推送请求参数
     * @return
     */
    public String changeStatus(String jdParamJson,Boolean flag){
        String res = null;
        Result result = new Result();
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(jdParamJson)){
            result.setCode(-1);
            result.setMsg("failure");
            result.setData("订单状态推送接口请求参数为空");
            res = gson.toJson(result);
        }else {
            JSONObject jsonObject  = JSONObject.parseObject(jdParamJson);
            String statusId = jsonObject.getString("statusId");
            if(StringUtil.isEmpty(statusId)){
                result.setCode(-1);
                result.setMsg("failure");
                result.setData("订单状态推送接口订单状态为空");
                res =  gson.toJson(result);
            }else    {
                //配送中……
                if(Integer.parseInt(statusId)==Constants.JH_ORDER_DELIVERING){
                   res = this.deliveryOrder(jsonObject,flag);
                    //妥投成功
                }else if(Integer.parseInt(statusId)==Constants.JH_ORDER_CONFIRMED){
                    res = this.finishOrder(jsonObject,flag);
                    //用户取消
                }else if(Integer.parseInt(statusId)==Constants.JH_ORDER_USER_CANCELLED){
                   res = this.userCancelOrder(jsonObject,flag);
                }else {
                    result.setCode(-1);
                    result.setMsg("error");
                    result.setData("此订单状态无需修改");
                    res = gson.toJson(result);
                }
                if (!StringUtil.isEmpty(res) && (new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create().fromJson(res,Result.class).getCode() == 0)){
                    if(!flag){
                        sysFacadeService.topicMessageOrderStatus(Constants.PLATFORM_WAIMAI_JDHOME,Integer.valueOf(jsonObject.getString("statusId")), jsonObject.getString("billId"),null,null);
                    }else {
                        OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai(Constants.PLATFORM_WAIMAI_JDHOME,jsonObject.getString("billId"));
                        sysFacadeService.topicMessageOrderStatusAll(Constants.PLATFORM_WAIMAI_JDHOME,orderWaiMai.getShopId(),orderWaiMai);
                    }
                }
            }
        }
        return  res;
    }

    /**
     * 查询单个订单
     * @param billId 订单Id
     * @param status 订单状态
     * @param shopId 门店Id
     * @return
     */
    public OrderInfoDTO getOneOrder(String billId,String status,String shopId){
        Log log1 =null;
        OrderInfoDTO rtnOrder = new OrderInfoDTO();
        try {
            String json  = jdHomeApiService.newOrder(billId, status, "", shopId);
            log.info("=====订单查询接口返回信息:"+json+"=====");
            JSONObject jsonObject =JSONObject.parseObject(json);
            JSONObject apiJson= JSONObject.parseObject(jsonObject.getString("data"));
            if("0".equals(jsonObject.get("code")) && "0".equals(apiJson.getString("code"))){
                JSONArray jsonArray = JSONObject.parseObject(apiJson.getString("result")).getJSONArray("resultList");
                if(jsonArray ==null || jsonArray.size() == 0){
                    return rtnOrder;
                }
                rtnOrder = getOrderMain(jsonArray.getJSONObject(0));
            }
        }catch (JdHomeException ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }finally {
            if (log1 !=null){
                log1.setLogId(log1.getLogId());
                log1.setTitle(MessageFormat.format("获取新订单",billId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"orderId\":{0}",billId)).concat("}"));
                sysFacadeService.updSynLog(log1);
            }
        }
        return rtnOrder;
    }


    //接口返回结果转换标准格式
    private void getResult(String rtnJson,Rtn rtn,String param){
        Result result = JSONObject.parseObject(rtnJson, Result.class);
        if(result.getCode()==0){
            JSONObject json = JSONObject.parseObject(result.getData());
            rtn.setCode(json.getInteger("code"));
            rtn.setDesc("success");
            rtn.setRemark(json.getString("msg"));
            rtn.setDynamic(param);
        }else {
            rtn.setCode(result.getCode());
            rtn.setDesc("failure");
            rtn.setRemark(result.getMsg());
            rtn.setDynamic(param);
        }
    }

    //接口返回结果转换标准格式
    private String getResultList(String rtnJson,String dishId){
        Gson gson = new GsonBuilder().registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        Rtn rtn = new Rtn();
        Result result = JSONObject.parseObject(rtnJson, Result.class);
        if(result.getCode()==0){
            JSONObject json = JSONObject.parseObject(result.getData());
            rtn.setCode(json.getInteger("retCode"));
            rtn.setDesc("success");
            rtn.setRemark(json.getString("retMsg"));
            rtn.setDynamic(dishId);
        }else {
            rtn.setCode(result.getCode());
            rtn.setDesc("failure");
            rtn.setRemark(result.getMsg());
            rtn.setDynamic(dishId);
        }
        return gson.toJson(rtn);
    }
}


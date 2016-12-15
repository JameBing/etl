package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.activemq.StaticObj;
import com.wangjunneil.schedule.activemq.Topic.TopicMessageProducer;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.Data;
import com.wangjunneil.schedule.entity.baidu.OrderShop;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.eleme.Order;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jdhome.OrderExtend;
import com.wangjunneil.schedule.entity.jdhome.OrderInfoDTO;
import com.wangjunneil.schedule.entity.jp.JPAccessToken;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.tm.TmallAccessToken;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.service.jp.JpApiService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.management.JMException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
/**
 *
 * Created by wangjun on 8/8/16.
 */
@Service
public class SysFacadeService {

    private static Logger log = Logger.getLogger(SysFacadeService.class.getName());

    @Autowired
    private SysInnerService sysInnerService;

    @Autowired
    private JpApiService jpApiService;

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrder")
    private TopicMessageProducer topicMessageProducerWaiMaiOrder;

    @Autowired
    @Qualifier("topicDestinationWaiMaiOrder")
    private Destination topicDestinationWaiMaiOrder;

    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatus")
    private TopicMessageProducer topicMessageProducerOrderStatus;

    @Autowired
    @Qualifier("topicDestinationWaiMaiOrderStatus")
    private Destination topicDestinationWaiMaiOrderStatus;

    public Cfg findJdCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_JD);
    }

    public Cfg findTmCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_TM);
    }

    public Cfg findJPCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_JP);
    }
    public Cfg findElemeCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_WAIMAI_ELEME);
    }

    public Cfg findZ8Cfg() {
        return sysInnerService.findCfg("z8");
    }

    public List<Cfg> findAllCfg() {
        return sysInnerService.findAllCfg();
    }

    public JdAccessToken getJdToken() {
        return sysInnerService.getJdToken();
    }

    public TmallAccessToken getTmToken() {
        return sysInnerService.getTmToken();
    }

    public Z8AccessToken getZ8Token(){
        return sysInnerService.getZ8Token();
    }

    public JPAccessToken getJpToken() throws ScheduleException {
        JPAccessToken jpAccessToken = sysInnerService.getJPToken();
        if (jpAccessToken == null) {
            jpAccessToken = jpApiService.requestToken(findJPCfg());
            sysInnerService.addJPAccessToken(jpAccessToken);
        } else {
            Date expireTime = jpAccessToken.getExpireDate();
            if (DateTimeUtil.now().after(expireTime))
                jpAccessToken = sysInnerService.getJPToken();
        }
        return jpAccessToken;
    }

    public void addPlatform(Cfg cfg,String editType) throws ScheduleException {
        sysInnerService.addPlatform(cfg,editType);
    }

    public void delPlatform(Cfg cfg) throws ScheduleException {
        sysInnerService.delPlatform(cfg);
    }

    public void addPlatformStatus(Status status, String platformType, Boolean scheduleState){
        if(scheduleState) {
            status.setStartup(true);
            if(status.getStartupTime() == null){
                status.setStartupTime(DateTimeUtil.now());
            }
        }else{
            status.setStartup(false);
            if(status.getShutdownTime() == null){
                status.setShutdownTime(DateTimeUtil.now());
            }
        }
        sysInnerService.addPlatformStatus(status, platformType,scheduleState);
    }

    public void initializeReset(Status status){
        sysInnerService.initializeReset(status);
    }

    /*======================================外卖=========================================================*/

    public int getSerialNum(String date,String moudle){
        return sysInnerService.getSerialNum(date,moudle);
    }


    //生成外卖订单编号
    public String getOrderNum(String shopId) {
        String strShopId = shopId.length() > 5 ? shopId.substring(0, 5) : shopId;
        String date = DateTimeUtil.nowDateString("yyyyMMdd").substring(2, 8);
        Integer integerShopId;
        try {
            integerShopId = Integer.valueOf(strShopId);
        } catch (Exception ex) {
            integerShopId = 99999;
        }
        return "W" + String.format("%05d", integerShopId) + "99" + date + String.format("%06d", Integer.valueOf(getSerialNum(date, "order")));
    }

    //订单插入
    public void updSynWaiMaiOrder(OrderWaiMai orderWaiMai) throws  BaiDuException,JdHomeException,ElemeException,MeiTuanException,JMException{
        try{
            //order Insert/update
            sysInnerService.updSynWaiMaiOrder(orderWaiMai);
            //topic message to MQ Server
            System.out.println(formatOrder2Pos(orderWaiMai));
            if (StaticObj.mqTransportTopicOrder){
            topicMessageProducerWaiMaiOrder.sendMessage(topicDestinationWaiMaiOrder, formatOrder2Pos(orderWaiMai),orderWaiMai.getShopId());
            }
        }catch (ScheduleException ex){
            switch (orderWaiMai.getPlatform()){
                case Constants.PLATFORM_WAIMAI_BAIDU:
                    throw new BaiDuException(ex.getClass().getName(),"百度订单插入失败!","",new Throwable().getStackTrace());
                case Constants.PLATFORM_WAIMAI_JDHOME:
                    throw new JdHomeException("京东订单插入失败!",ex);
                case Constants.PLATFORM_WAIMAI_MEITUAN:
                    throw new MeiTuanException("美团订单插入失败!",ex);
                case Constants.PLATFORM_WAIMAI_ELEME:
                    throw new ElemeException("饿了么订单插入失败!",ex);
            }
        }catch (Exception e){
                    switch (orderWaiMai.getPlatform()){
                        case Constants.PLATFORM_WAIMAI_BAIDU:
                            throw new JMException("发送百度订单消息失败");
                        case Constants.PLATFORM_WAIMAI_JDHOME:
                            throw new JMException("发送京东订单消息失败");
                        case Constants.PLATFORM_WAIMAI_MEITUAN:
                            throw new JMException("发送美团订单消息失败");
                        case Constants.PLATFORM_WAIMAI_ELEME:
                            throw new JMException("发送饿了么订单消息失败");
            }
        }
    }

    //订单查询
    public OrderWaiMai findOrderWaiMai(String  platform,String platformOrderId){
        return sysInnerService.findOrderWaiMai(platform,platformOrderId);
    }

    //订单插入 list
    public  void  updSynWaiMaiOrder(List<OrderWaiMai> orderWaiMaiList) throws JdHomeException{
        orderWaiMaiList.forEach(v->{
          try   {
            sysInnerService.updSynWaiMaiOrder(v);
              System.out.println(formatOrder2Pos(v));
              if (StaticObj.mqTransportTopicOrder){
                 topicMessageProducerWaiMaiOrder.sendMessage(topicDestinationWaiMaiOrder,formatOrder2Pos(v),v.getShopId());
              }
          }catch (Exception ex){
              //待补充
        }});
    }

    //格式化订单返回给Pos
    public JSONObject formatOrder2Pos(OrderWaiMai orderWaiMai){
        JSONObject jsonObject = new JSONObject();
        JSONObject rtn = new JSONObject();
        jsonObject.put("platform",orderWaiMai.getPlatform());
        jsonObject.put("shopId",orderWaiMai.getShopId());
        jsonObject.put("orderId",orderWaiMai.getOrderId());
        jsonObject.put("platOrderId",orderWaiMai.getPlatformOrderId());
        switch (orderWaiMai.getPlatform()){
            case Constants.PLATFORM_WAIMAI_BAIDU :
                rtn =  formatBaiDuOrder((Data) orderWaiMai.getOrder(), jsonObject);
                break;
            case Constants.PLATFORM_WAIMAI_JDHOME :
                rtn =  formatJdHomeOrder((OrderInfoDTO)orderWaiMai.getOrder(),jsonObject);
                break;
            case Constants.PLATFORM_WAIMAI_MEITUAN :
                rtn =  formatMeiTuanOrder((OrderInfo)orderWaiMai.getOrder(),jsonObject);
                break;
            case Constants.PLATFORM_WAIMAI_ELEME :
                rtn =  formatEleme((Order)orderWaiMai.getOrder(),jsonObject);
                break;
            default:break;
        }
        return rtn;
    }

    //格式化百度订单
    private JSONObject formatBaiDuOrder(Data data ,JSONObject jsonObject){
        if(StringUtil.isEmpty(data)){
            data = new Data();
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("header",getBaiDuHeader(data,jsonObject));
        rtnJson.put("user",getBaiDuUsers(data));
        rtnJson.put("productList",getBaiDuProducts(data));
        rtnJson.put("discountList",getBaiDuDiscount(data));
        return rtnJson;
    }

    //获取百度订单头部信息
    private JSONArray getBaiDuHeader(Data data,JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(data.getOrder())){
            return jsonArray;
        }
        if(StringUtil.isEmpty(data.getShop())){
            data.setShop(new OrderShop());
        }
        jsonObject.put("platShopId", data.getShop().getBaiduShopId());
        jsonObject.put("platStationName",data.getShop().getName());
        jsonObject.put("poiCode","");
        jsonObject.put("poiName","");
        jsonObject.put("poiAddress","");
        jsonObject.put("poiPhone","");
        jsonObject.put("orderIndex",data.getOrder().getOrderIndex());
        jsonObject.put("orderType","");
        jsonObject.put("orderStatus",tranBdOrderStatus(data.getOrder().getStatus()));
        jsonObject.put("orderStatusTime","");
        jsonObject.put("orderStartTime",data.getOrder().getCreateTime()==0?"":DateTimeUtil.dateFormat(new Date(data.getOrder().getCreateTime()),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime",data.getOrder().getConfirmTime());
        jsonObject.put("orderPurchaseTime", "");
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately",data.getOrder().getSendImmediately());
        jsonObject.put("expectTimeMode",data.getOrder().getExpectTimeMode());
        jsonObject.put("orderPreDeliveryTime","");
        jsonObject.put("expectSendTime",data.getOrder().getSendTime());
        jsonObject.put("riderArrivalTime",data.getOrder().getDeliveryTime());
        jsonObject.put("riderPickupTime",data.getOrder().getPickupTime());
        jsonObject.put("riderPickupNo",data.getOrder().getDeliveryPhone());
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime",data.getOrder().getCancelTime());
        jsonObject.put("orderCancelRemark","");
        jsonObject.put("is_third_shipping",data.getOrder().getDeliveryParty());
        jsonObject.put("deliveryStationNo",data.getShop().getShopId());
        jsonObject.put("deliveryStationName",data.getShop().getName());
        jsonObject.put("deliveryCarrierNo","");
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo","");
        jsonObject.put("deliveryPackageWeight","");
        jsonObject.put("deliveryConfirmTime", "");
        jsonObject.put("orderFinishTime",data.getOrder().getFinishedTime());
        jsonObject.put("orderPayType",data.getOrder().getPayType());
        jsonObject.put("orderTotalMoney",data.getOrder().getTotalFee());
        jsonObject.put("orderDiscountMoney",data.getOrder().getDiscountFee());
        jsonObject.put("orderFreightMoney",data.getOrder().getSendFee());
        jsonObject.put("packagingMoney",data.getOrder().getPackageFee());
        jsonObject.put("orderBuyerPayableMoney",data.getOrder().getUserFee());
        jsonObject.put("orderShopFee",data.getOrder().getShopFee());
        jsonObject.put("orderOriginPrice","");
        jsonObject.put("serviceRate","");
        jsonObject.put("serviceFee","");
        jsonObject.put("hongbao","");
        jsonObject.put("orderBuyerRemark",data.getOrder().getRemark());
        jsonObject.put("orderInvoiceOpenMark",data.getOrder().getNeedInvoice());
        jsonObject.put("orderInvoiceType","");
        jsonObject.put("orderInvoiceTitle",data.getOrder().getInvoiceTitle());
        jsonObject.put("orderInvoiceContent","");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取百度订单用户信息
    private JSONArray getBaiDuUsers(Data data ){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(data.getUser())){
            return jsonArray;
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platOrderId",StringUtil.isEmpty(data.getOrder())?"":data.getOrder().getOrderId());
        jsonObject.put("buyerFullName",data.getUser().getName());
        jsonObject.put("buyerFullAddress",data.getUser().getAddress());
        jsonObject.put("buyerTelephone",data.getUser().getPhone());
        jsonObject.put("buyerMobile",data.getUser().getPhone());
        jsonObject.put("province",data.getUser().getProvince());
        jsonObject.put("city",data.getUser().getCity());
        jsonObject.put("district",data.getUser().getDistrict());
        jsonObject.put("gender",data.getUser().getGender());
        jsonObject.put("buyerLng",StringUtil.isEmpty(data.getUser().getCoord())?"":data.getUser().getCoord().getLongitude());
        jsonObject.put("buyerLat",StringUtil.isEmpty(data.getUser().getCoord())?"":data.getUser().getCoord().getLatitude());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取百度订单商品信息
    private JSONArray getBaiDuProducts(Data data){
        JSONArray jsonArray = new JSONArray();

        if(data.getProducts()==null || data.getProducts().size()==0){
            return jsonArray;
        }
        for(int i=0;i<data.getProducts().size();i++){
            for (int j=0;j<data.getProducts().get(i).length;j++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("platOrderId",StringUtil.isEmpty(data.getOrder())?"":data.getOrder().getOrderId());
                jsonObject.put("skuType",data.getProducts().get(i)[j].getType());
                jsonObject.put("skuId",data.getProducts().get(i)[j].getBaiduProductId());
                jsonObject.put("skuName",data.getProducts().get(i)[j].getProductName());
                jsonObject.put("skuIdIsv",data.getProducts().get(i)[j].getOtherDishId());
                jsonObject.put("price",data.getProducts().get(i)[j].getProductPrice());
                jsonObject.put("quantity",data.getProducts().get(i)[j].getProductAmount());
                jsonObject.put("isGift","");
                jsonObject.put("upcCode",data.getProducts().get(i)[j].getUpc());
                jsonObject.put("boxNum",data.getProducts().get(i)[j].getPackageAmount());
                jsonObject.put("boxPrice",data.getProducts().get(i)[j].getPackagePrice());
                jsonObject.put("productFee",data.getProducts().get(i)[j].getPackageFee());
                jsonObject.put("unit","");
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    //获取百度订单折扣信息
    private JSONArray getBaiDuDiscount(Data data){
        JSONArray jsonArray = new JSONArray();

        if(data.getDiscount()==null && data.getDiscount().size()==0){
            return jsonArray;
        }
        data.getDiscount().forEach(discount->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId",StringUtil.isEmpty(data.getOrder().getOrderId())?"":data.getOrder().getOrderId());
            jsonObject.put("discountType",discount.getType());
            jsonObject.put("discountCode","");
            jsonObject.put("discountPrice",discount.getFee());
            jsonObject.put("skuId","");
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }


    //格式化京东到家订单
    private JSONObject formatJdHomeOrder(OrderInfoDTO orderInfoDTO,JSONObject jsonObject){
        if(StringUtil.isEmpty(orderInfoDTO)){
            orderInfoDTO = new OrderInfoDTO();
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("header",getJdHomeHeader(orderInfoDTO,jsonObject));
        rtnJson.put("user",getJdHomeUsers(orderInfoDTO));
        rtnJson.put("productList",getJdHomeProducts(orderInfoDTO));
        rtnJson.put("discountList",getJdHomeDiscount(orderInfoDTO));
        return rtnJson;
    }

    //获取京东到家订单头部信息
    private JSONArray getJdHomeHeader(OrderInfoDTO orderInfo,JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("platShopId",orderInfo.getProduceStationNo());
        jsonObject.put("platStationName",orderInfo.getProduceStationName());
        jsonObject.put("poiCode",orderInfo.getOrgCode());
        jsonObject.put("poiName","");
        jsonObject.put("poiAddress","");
        jsonObject.put("poiPhone","");
        jsonObject.put("orderIndex","");
        jsonObject.put("orderType",orderInfo.getOrderType());
        jsonObject.put("orderStatus",tranJHOrderStatus(orderInfo.getOrderStatus()));
        jsonObject.put("orderStatusTime",StringUtil.isEmpty(orderInfo.getOrderStatusTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderStatusTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderStartTime",StringUtil.isEmpty(orderInfo.getOrderStartTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderStartTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime","");
        jsonObject.put("orderPurchaseTime", StringUtil.isEmpty(orderInfo.getOrderPurchaseTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderPurchaseTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderAgingType",orderInfo.getOrderAgingType());
        jsonObject.put("deliveryImmediately","");
        jsonObject.put("expectTimeMode","");
        jsonObject.put("orderPreDeliveryTime",StringUtil.isEmpty(orderInfo.getOrderPreEndDeliveryTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderPreEndDeliveryTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("expectSendTime","");
        jsonObject.put("riderArrivalTime","");
        jsonObject.put("riderPickupTime","");
        jsonObject.put("riderPickupNo","");
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime",StringUtil.isEmpty(orderInfo.getOrderCancelTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderCancelTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderCancelRemark",orderInfo.getOrderCancelRemark());
        jsonObject.put("is_third_shipping","");
        jsonObject.put("deliveryStationNo",orderInfo.getProduceStationNoIsv());
        jsonObject.put("deliveryStationName","");
        jsonObject.put("deliveryCarrierNo",orderInfo.getProduceStationNoIsv());
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo",orderInfo.getDeliveryBillNo());
        jsonObject.put("deliveryPackageWeight",orderInfo.getDeliveryPackageWeight());
        jsonObject.put("deliveryConfirmTime", StringUtil.isEmpty(orderInfo.getDeliveryConfirmTime())?"":DateTimeUtil.dateFormat(orderInfo.getDeliveryConfirmTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderFinishTime","");
        jsonObject.put("orderPayType",orderInfo.getOrderPayType());
        jsonObject.put("orderTotalMoney",orderInfo.getOrderTotalMoney());
        jsonObject.put("orderDiscountMoney",orderInfo.getOrderDiscountMoney());
        jsonObject.put("orderFreightMoney",orderInfo.getOrderFreightMoney());
        jsonObject.put("packagingMoney",orderInfo.getPackagingMoney());
        jsonObject.put("orderBuyerPayableMoney",orderInfo.getOrderBuyerPayableMoney());
        jsonObject.put("orderShopFee","");
        jsonObject.put("orderOriginPrice","");
        jsonObject.put("serviceRate","");
        jsonObject.put("serviceFee","");
        jsonObject.put("hongbao","");
        jsonObject.put("orderBuyerRemark","");
        jsonObject.put("orderInvoiceOpenMark",orderInfo.getOrderInvoiceOpenMark());
        jsonObject.put("orderInvoiceType",StringUtil.isEmpty(orderInfo.getOrderExtend())?"":orderInfo.getOrderExtend().getOrderInvoiceType());
        jsonObject.put("orderInvoiceTitle",StringUtil.isEmpty(orderInfo.getOrderExtend())?"":orderInfo.getOrderExtend().getOrderInvoiceTitle());
        jsonObject.put("orderInvoiceContent",StringUtil.isEmpty(orderInfo.getOrderExtend())?"":orderInfo.getOrderExtend().getOrderInvoiceContent());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取京东到家订单用户信息
    private JSONArray getJdHomeUsers(OrderInfoDTO orderInfo ){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(orderInfo.getOrderExtend())){
            orderInfo.setOrderExtend(new OrderExtend());
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platOrderId",orderInfo.getOrderId());
        jsonObject.put("buyerFullName",orderInfo.getBuyerFullName());
        jsonObject.put("buyerFullAddress",orderInfo.getBuyerFullAddress());
        jsonObject.put("buyerTelephone",orderInfo.getBuyerTelephone());
        jsonObject.put("buyerMobile",orderInfo.getBuyerMobile());
        jsonObject.put("province","");
        jsonObject.put("city","");
        jsonObject.put("district","");
        jsonObject.put("gender","");
        jsonObject.put("buyerLng",orderInfo.getOrderExtend().getBuyerLng());
        jsonObject.put("buyerLat",orderInfo.getOrderExtend().getBuyerLat());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取京东到家订单商品信息
    private JSONArray getJdHomeProducts(OrderInfoDTO orderInfo){
        JSONArray jsonArray = new JSONArray();

        if(orderInfo.getProductList()==null || orderInfo.getProductList().size()==0){
            return jsonArray;
        }
        orderInfo.getProductList().forEach(product->{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId",orderInfo.getOrderId());
            jsonObject.put("skuType","");
            jsonObject.put("skuId",product.getSkuId());
            jsonObject.put("skuName",product.getSkuName());
            jsonObject.put("skuIdIsv",product.getSkuIdIsv());
            jsonObject.put("price",product.getSkuJdPrice());
            jsonObject.put("quantity",product.getSkuCount());
            jsonObject.put("isGift",product.getIsGift());
            jsonObject.put("upcCode",product.getUpcCode());
            jsonObject.put("boxNum","");
            jsonObject.put("boxPrice","");
            jsonObject.put("productFee","");
            jsonObject.put("unit","");
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    //获取京东到家订单折扣信息
    private JSONArray getJdHomeDiscount(OrderInfoDTO orderInfo){
        JSONArray jsonArray = new JSONArray();

        if(orderInfo.getDiscountList()==null && orderInfo.getDiscountList().size()==0){
            return jsonArray;
        }
        orderInfo.getDiscountList().forEach(discount -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId", orderInfo.getOrderId());
            jsonObject.put("discountType", discount.getDiscountType());
            jsonObject.put("discountCode", discount.getDiscountCode());
            jsonObject.put("discountPrice", discount.getDiscountPrice());
            jsonObject.put("skuId", discount.getSkuId());
            jsonArray.add(jsonObject);
        });
        return jsonArray;
    }

    //格式化美团订单
    private JSONObject formatMeiTuanOrder(OrderInfo orderInfo ,JSONObject jsonObject){
        if(StringUtil.isEmpty(orderInfo)){
            orderInfo = new OrderInfo();
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("header",getMeiTuanHeader(orderInfo, jsonObject));
        rtnJson.put("user",getMeiTuanUsers(orderInfo));
        rtnJson.put("productList",getMeiTuanProducts(orderInfo));
        rtnJson.put("discountList",getMeiTuanDiscount(orderInfo));

        return rtnJson;
    }

    //获取美团订单头部信息
    private JSONArray getMeiTuanHeader(OrderInfo orderInfo,JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("platShopId","");
        jsonObject.put("platStationName","");
        jsonObject.put("poiCode",orderInfo.getApppoicode());
        jsonObject.put("poiName",orderInfo.getWmpoiname());
        jsonObject.put("poiAddress",orderInfo.getWmpoiaddress());
        jsonObject.put("poiPhone",orderInfo.getWmpoiphone());
        jsonObject.put("orderIndex",orderInfo.getDayseq());
        jsonObject.put("orderType","");
        jsonObject.put("orderStatus",tranMTOrderStatus(orderInfo.getStatus()));
        jsonObject.put("orderStatusTime",orderInfo.getUtime()==0?"":DateTimeUtil.dateFormat(new Date(orderInfo.getUtime()), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderStartTime","");
        jsonObject.put("orderConfirmTime","");
        jsonObject.put("orderPurchaseTime", "");
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately","");
        jsonObject.put("expectTimeMode","");
        jsonObject.put("orderPreDeliveryTime",orderInfo.getDeliverytime()==0?"":DateTimeUtil.dateFormat(new Date(orderInfo.getDeliverytime()), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("expectSendTime","");
        jsonObject.put("riderArrivalTime","");
        jsonObject.put("riderPickupTime","");
        jsonObject.put("riderPickupNo","");
        jsonObject.put("riderPhone",orderInfo.getShipperphone());
        jsonObject.put("orderCancelTime","");
        jsonObject.put("orderCancelRemark","");
        jsonObject.put("isThirdShipping",orderInfo.getIsthirdshipping());
        jsonObject.put("deliveryStationNo","");
        jsonObject.put("deliveryStationName","");
        jsonObject.put("deliveryCarrierNo","");
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo","");
        jsonObject.put("deliveryPackageWeight","");
        jsonObject.put("deliveryConfirmTime", "");
        jsonObject.put("orderFinishTime","");
        jsonObject.put("orderPayType",orderInfo.getPaytype());
        jsonObject.put("orderTotalMoney",orderInfo.getTotal());
        jsonObject.put("orderDiscountMoney","");
        jsonObject.put("orderFreightMoney",orderInfo.getShippingfee());
        jsonObject.put("packagingMoney","");
        jsonObject.put("orderBuyerPayableMoney","");
        jsonObject.put("orderShopFee","");
        jsonObject.put("orderOriginPrice",orderInfo.getOriginalprice());
        jsonObject.put("serviceRate","");
        jsonObject.put("serviceFee","");
        jsonObject.put("hongbao","");
        jsonObject.put("orderBuyerRemark",orderInfo.getCaution());
        jsonObject.put("orderInvoiceOpenMark",orderInfo.getHasinvoiced());
        jsonObject.put("orderInvoiceType","");
        jsonObject.put("orderInvoiceTitle",orderInfo.getInvoicetitle());
        jsonObject.put("orderInvoiceContent","");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取美团订单用户信息
    private JSONArray getMeiTuanUsers(OrderInfo orderInfo ){
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platOrderId",orderInfo.getOrderid());
        jsonObject.put("buyerFullName",orderInfo.getRecipientname());
        jsonObject.put("buyerFullAddress",orderInfo.getRecipientaddress());
        jsonObject.put("buyerTelephone",orderInfo.getRecipientphone());
        jsonObject.put("buyerMobile",orderInfo.getRecipientphone());
        jsonObject.put("province","");
        jsonObject.put("city",orderInfo.getCityid());
        jsonObject.put("district","");
        jsonObject.put("gender","");
        jsonObject.put("buyerLng",orderInfo.getLongitude());
        jsonObject.put("buyerLat",orderInfo.getLatitude());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取美团订单商品信息
    private JSONArray getMeiTuanProducts(OrderInfo orderInfo){
        JSONArray jsonArray = new JSONArray();

        if(orderInfo.getDetail()==null || orderInfo.getDetail().length==0){
            return jsonArray;
        }
        for(int i=0;i<orderInfo.getDetail().length;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId",orderInfo.getOrderid());
            jsonObject.put("skuType","");
            jsonObject.put("skuId",orderInfo.getDetail()[i].getSku_id());
            jsonObject.put("skuName",orderInfo.getDetail()[i].getFood_name());
            jsonObject.put("skuIdIsv",orderInfo.getDetail()[i].getApp_food_code());
            jsonObject.put("price",orderInfo.getDetail()[i].getPrice());
            jsonObject.put("quantity",orderInfo.getDetail()[i].getQuantity());
            jsonObject.put("isGift","");
            jsonObject.put("upcCode","");
            jsonObject.put("boxNum",orderInfo.getDetail()[i].getBox_num());
            jsonObject.put("boxPrice",orderInfo.getDetail()[i].getBox_price());
            jsonObject.put("productFee","");
            jsonObject.put("unit",orderInfo.getDetail()[i].getUnit());
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    //获取美团订单折扣信息
    private JSONArray getMeiTuanDiscount(OrderInfo orderInfo){
        JSONArray jsonArray = new JSONArray();

        if(orderInfo.getExtras()==null && orderInfo.getExtras().length==0){
            return jsonArray;
        }
       for(int i=0;i<orderInfo.getExtras().length;i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId", orderInfo.getOrderid());
            jsonObject.put("discountType", orderInfo.getExtras()[i].getType());
            jsonObject.put("discountCode",orderInfo.getExtras()[i].getRemark());
            jsonObject.put("discountPrice", orderInfo.getExtras()[i].getReduce_fee());
            jsonObject.put("skuId", "");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    //格式化饿了么订单
    private  JSONObject formatEleme(Order order ,JSONObject jsonObject){
        if(StringUtil.isEmpty(order)){
            order = new Order();
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("header",getElemeHeader(order, jsonObject));
        rtnJson.put("user",getElemeUsers(order));
        rtnJson.put("productList",getElmeProducts(order));
        rtnJson.put("discountList",getElemeDiscount(order));
        return rtnJson;
    }

    //获取饿了么订单头部信息
    private JSONArray getElemeHeader(Order order,JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("platShopId",order.getRestaurantid());
        jsonObject.put("platStationName",order.getRestaurantname());
        jsonObject.put("poiCode","");
        jsonObject.put("poiName","");
        jsonObject.put("poiAddress","");
        jsonObject.put("poiPhone","");
        jsonObject.put("orderIndex",order.getRestaurantnumber());
        jsonObject.put("orderType","");
        jsonObject.put("orderStatus",tranELOrderStatus(order.getStatuscode()));
        jsonObject.put("orderStatusTime","");
        jsonObject.put("orderStartTime",StringUtil.isEmpty(order.getCreatedat())?"":DateTimeUtil.dateFormat(order.getCreatedat(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime","");
        jsonObject.put("orderPurchaseTime", StringUtil.isEmpty(order.getActiveat())?"":DateTimeUtil.dateFormat(order.getActiveat(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately","");
        jsonObject.put("expectTimeMode","");
        jsonObject.put("orderPreDeliveryTime",StringUtil.isEmpty(order.getDelivertime())?"":DateTimeUtil.dateFormat(order.getDelivertime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("expectSendTime","");
        jsonObject.put("riderArrivalTime","");
        jsonObject.put("riderPickupTime","");
        jsonObject.put("riderPickupNo","");
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime","");
        jsonObject.put("orderCancelRemark","");
        jsonObject.put("isThirdShipping","");
        jsonObject.put("deliveryStationNo",order.getRestaurantid());
        jsonObject.put("deliveryStationName",order.getRestaurantname());
        jsonObject.put("deliveryCarrierNo","");
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo","");
        jsonObject.put("deliveryPackageWeight","");
        jsonObject.put("deliveryConfirmTime", "");
        jsonObject.put("orderFinishTime","");
        jsonObject.put("orderPayType",order.getIsonlinepaid());
        jsonObject.put("orderTotalMoney",order.getTotalprice());
        jsonObject.put("orderDiscountMoney","");
        jsonObject.put("orderFreightMoney",order.getDeliverfee());
        jsonObject.put("packagingMoney",order.getPackagefee());
        jsonObject.put("orderBuyerPayableMoney","");
        jsonObject.put("orderShopFee","");
        jsonObject.put("orderOriginPrice",order.getOriginalprice());
        jsonObject.put("serviceRate",order.getServicerate());
        jsonObject.put("serviceFee",order.getServicefee());
        jsonObject.put("hongbao",order.getHongbao());
        jsonObject.put("orderBuyerRemark",order.getDescription());
        jsonObject.put("orderInvoiceOpenMark","");
        jsonObject.put("orderInvoiceType","");
        jsonObject.put("orderInvoiceTitle",order.getInvoice());
        jsonObject.put("orderInvoiceContent","");
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取饿了么订单用户信息
    private JSONArray getElemeUsers(Order order ){
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platOrderId",order.getOrderid());
        jsonObject.put("buyerFullName",order.getConsignee());
        jsonObject.put("buyerFullAddress",order.getDeliverypoiaddress());
        if(order.getPhonelist()!=null && order.getPhonelist().size()>0){
            String str ="";
            for (int i=0;i<order.getPhonelist().size();i++){
               str = str+order.getPhonelist().get(i).toString()+",";
            }
            jsonObject.put("buyerTelephone",str.substring(0,str.length()-1));
            jsonObject.put("buyerMobile",str.substring(0,str.length()-1));
        }else {
            jsonObject.put("buyerTelephone","");
            jsonObject.put("buyerMobile","");
        }
        jsonObject.put("province","");
        jsonObject.put("city","");
        jsonObject.put("district","");
        jsonObject.put("gender","");
        jsonObject.put("buyerLng",order.getDeliverygeo());
        jsonObject.put("buyerLat",order.getDeliverygeo());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取饿了么订单商品信息
    private JSONArray getElmeProducts(Order order){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(order.getDetail())){
            return jsonArray;
        }
        if(order.getDetail().getGroup()==null ||order.getDetail().getGroup().size()==0){
            return jsonArray;
        }
        for(int i=0;i<order.getDetail().getGroup().size();i++){
            for(int j=0;j<order.getDetail().getGroup().get(i).size();j++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("platOrderId",order.getOrderid());
                jsonObject.put("skuType","");
                jsonObject.put("skuId",order.getDetail().getGroup().get(i).get(j).getId());
                jsonObject.put("skuName",order.getDetail().getGroup().get(i).get(j).getName());
                jsonObject.put("skuIdIsv","");
                jsonObject.put("price",order.getDetail().getGroup().get(i).get(j).getPrice());
                jsonObject.put("quantity",order.getDetail().getGroup().get(i).get(j).getQuantity());
                jsonObject.put("isGift","");
                jsonObject.put("upcCode","");
                jsonObject.put("boxNum","");
                jsonObject.put("boxPrice","");
                jsonObject.put("productFee","");
                jsonObject.put("unit","");
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    //获取饿了么订单折扣信息
    private JSONArray getElemeDiscount(Order order){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(order.getDetail())){
            return jsonArray;
        }
        if(order.getDetail().getExtra()==null && order.getDetail().getExtra().size()==0){
            return jsonArray;
        }
        for(int i=0;i<order.getDetail().getExtra().size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId", order.getOrderid());
            jsonObject.put("discountType", "");
            jsonObject.put("discountCode",order.getDetail().getExtra().get(i).getName());
            jsonObject.put("discountPrice", order.getDetail().getExtra().get(i).getPrice());
            jsonObject.put("skuId", "");
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    //异常处理 匿名函数  ?需要验证代码可行性
        public    Function<Object,Log> functionRtn =(t)->{
            Log log1 = new Log();
            String logId = DateTimeUtil.dateFormat(DateTimeUtil.now(),"yyyyMMddHHmmssSSS");
            log1.setLogId(logId);
            log1.setType("E");
            log1.setDateTime(log1.getDateTime());
            switch (t.getClass().getName().toLowerCase()){
                case "com.wangjunneil.schedule.common.baiduexception":
                BaiDuException baiDuException = (BaiDuException)t;
                log1.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                log1.setMessage(baiDuException.getMessage());
                log1.setRequest(baiDuException.getRequestStr());
                log1.setCatchExName("BaiduException");
                log1.setInnerExName(baiDuException.getInnerExName());
                log1.setStackInfo(baiDuException.getStackInfo());
                break;
                case "com.wangjunneil.schedule.common.jdhomeexception":
                    JdHomeException jdHomeException = (JdHomeException)t;
                    log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
                    log1.setMessage(jdHomeException.getMessage());
                    log1.setRequest(jdHomeException.getRequestStr());
                    log1.setCatchExName("JdHomeException");
                    log1.setInnerExName(jdHomeException.getInnerExName());
                    log1.setStackInfo(jdHomeException.getStackInfo());
                    break;
                case "com.wangjunneil.schedule.common.elemeexception":
                    ElemeException elemeException = (ElemeException)t;
                    log1.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
                    log1.setMessage(elemeException.getMessage());
                    log1.setRequest(elemeException.getRequestStr());
                    log1.setCatchExName("ElemeException");
                    log1.setInnerExName(elemeException.getInnerExName());
                    log1.setStackInfo(elemeException.getStackInfo());

                    break;
                case "com.wangjunneil.schedule.common.meituanexception":
                    MeiTuanException meiTuanException = (MeiTuanException)t;
                     log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
                     log1.setMessage(meiTuanException.getMessage());
                    log1.setRequest(meiTuanException.getRequestStr());
                    log1.setCatchExName("MeiTuanException");
                    log1.setInnerExName(meiTuanException.getInnerExName());
                    log1.setStackInfo(meiTuanException.getStackInfo());
                    break;
                case "java.lang.exception":
                    Exception exception = (Exception)t;
                     log1.setMessage(exception.getMessage());
                    log1.setCatchExName("Exception");
                    log1.setInnerExName(exception.getClass().getName());
                    StringBuffer sb = new StringBuffer();
                    StackTraceElement[] traces = exception.getStackTrace();
                    for (int i = 0; i < traces.length; i++) {
                        StackTraceElement element = traces[i];
                        sb.append(element.toString() + "\n");
                    }
                    log1.setStackInfo(sb.toString());
                    break;
                case "com.wangjunneil.schedule.common.scheduleexception":
                   ScheduleException scheduleException = (ScheduleException)t;
                    log1.setMessage(scheduleException.getMessage());
                    log1.setCatchExName("ScheduleException");
                    log1.setInnerExName(scheduleException.getClass().getName());
                    StringBuffer sb1 = new StringBuffer();
                    StackTraceElement[] traces2 = scheduleException.getStackTrace();
                    for (int i = 0; i < traces2.length; i++) {
                        StackTraceElement element = traces2[i];
                        sb1.append(element.toString() + "\n");
                    }
                    log1.setStackInfo(sb1.toString());
                    break;
                default:
                    Exception exception1 = (Exception)t;
                    log1.setMessage(exception1.getMessage());
                    log1.setCatchExName("Exception");
                    log1.setInnerExName(exception1.getClass().getName());
                    StringBuffer stringBuffer = new StringBuffer();
                    StackTraceElement[] stackTrace = exception1.getStackTrace();
                    for (int i = 0; i < stackTrace.length; i++) {
                        StackTraceElement element = stackTrace[i];
                        stringBuffer.append(element.toString() + "\n");
                    }
                    log1.setStackInfo(stringBuffer.toString());
                    break;
        }
        return  log1;
    } ;

    //日志插入
    public void updSynLog(Log log){
          sysInnerService.updSynLog( log);
    }

    //格式化百度订单状态
    private int tranBdOrderStatus(Integer status){
        switch (status){
            case Constants.BD_SUSPENDING:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.BD_CONFIRMED:
               return Constants.POS_ORDER_CONFIRMED;
            case Constants.BD_DELIVERY:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.BD_COMPLETED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.BD_CANCELED:
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化京东到家订单状态
    private int tranJHOrderStatus(int status){
        switch (status){
            case Constants.JH_ORDER_WAITING:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.JH_ORDER_RECEIVED:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.JH_ORDER_DELIVERING:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.JH_ORDER_CONFIRMED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.JH_ORDER_USER_CANCELLED:
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化美团订单状态
    private int tranMTOrderStatus(int status){
        switch (status){
            case Constants.MT_STATUS_CODE_UNPROCESSED:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.MT_STATUS_CODE_CONFIRMED:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.MT_STATUS_CODE_COMPLETED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.MT_STATUS_CODE_CANCELED:
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化饿了么订单状态
    private int tranELOrderStatus(int status){
        switch (status){
            case Constants.EL_STATUS_CODE_UNPROCESSED:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.EL_STATUS_CODE_PROCESSED_AND_VALID:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.EL_STATUS_CODE_SUCCESS:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.EL_STATUS_CODE_INVALID:
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //topic message to mq server  [message:order status]
    public  void topicMessageOrderStatus(String platform,Integer status,String platformOrderId,String orderId,String shopId){
        OrderWaiMai orderWaiMai = null;
        String tmp = "\"orderId\":\"{0}\"，\"orderStatus\":\"{1}\",\"shopId\":\"{2}\"",shop = shopId;
        boolean boolSend = true;
        JsonObject jsonMessage = new JsonObject();
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_BAIDU,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.addProperty("baidu", MessageFormat.format(tmp,orderWaiMai==null?"":orderWaiMai.getOrderId(),orderWaiMai==null?"":status,orderWaiMai==null?"":shop));
                jsonMessage.addProperty("jdhome",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("meituan",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("eleme",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                break;
            case  Constants.PLATFORM_WAIMAI_JDHOME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_JDHOME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.addProperty("baidu",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("jdhome", MessageFormat.format(tmp,orderWaiMai==null?"":orderWaiMai.getOrderId(),orderWaiMai==null?"":status,orderWaiMai==null?"":shop));
                jsonMessage.addProperty("meituan",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("eleme",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                break;
            case  Constants.PLATFORM_WAIMAI_MEITUAN:
                jsonMessage.addProperty("baidu",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("jdhome", MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("meituan",MessageFormat.format(tmp,orderId,status,shop));
                jsonMessage.addProperty("eleme",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                break;
            case  Constants.PLATFORM_WAIMAI_ELEME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_ELEME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.addProperty("baidu",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("jdhome",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("meituan",MessageFormat.format(tmp,"","",orderWaiMai==null?"":shop));
                jsonMessage.addProperty("eleme", MessageFormat.format(tmp,orderWaiMai==null?"":orderWaiMai.getOrderId(),orderWaiMai==null?"":status,orderWaiMai==null?"":shop));
                break;
            default:
                boolSend = false;
                break;
        }
        if (boolSend & StaticObj.MqTransportTopicOrderStatus){
            topicMessageProducerOrderStatus.sendMessage(topicDestinationWaiMaiOrderStatus,new Gson().toJson(jsonMessage),shop);
        }
    }

}

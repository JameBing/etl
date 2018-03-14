package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducerAsync;
import com.wangjunneil.schedule.activemq.StaticObj;
import com.wangjunneil.schedule.activemq.Topic.EkpMessageProducer;
import com.wangjunneil.schedule.activemq.Topic.TopicMessageProducerAsync;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.Data;
import com.wangjunneil.schedule.entity.baidu.OrderShop;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.PushRecord;
import com.wangjunneil.schedule.entity.eleme.OrderEle;
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
import com.wangjunneil.schedule.utility.RandomUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import eleme.openapi.sdk.api.enumeration.order.OOrderStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.management.JMException;
import java.util.Date;
import java.util.List;
import java.util.Random;
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


    /**生产者*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync;
    /**生产者1*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync1")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync1;
    /**生产者2*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync2")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync2;
    /**生产者3*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync3")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync3;
    /**生产者4*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync4")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync4;
    /**生产者5*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync5")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync5;
    /**生产者6*/
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderAsync6")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderAsync6;

    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync;

    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync1")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync1;
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync2")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync2;
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync3")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync3;
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync4")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync4;
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync5")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync5;
    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAsync6")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAsync6;


    @Autowired
    @Qualifier("topicMessageProducerWaiMaiOrderStatusAllSync")
    private TopicMessageProducerAsync topicMessageProducerWaiMaiOrderStatusAllSync;

    //Crm生产者
    @Autowired
    @Qualifier("queueMessageProducerWaiMaiOrderToCrm")
    private QueueMessageProducerAsync queueMessageProducerWaiMaiOrderToCrm;

    //ZT生产者
    @Autowired
    @Qualifier("queueMessageProducerWaiMaiOrderToZt")
    private QueueMessageProducerAsync queueMessageProducerWaiMaiOrderToZt;


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
        String strShopId = shopId.length() >= 8 ? shopId.substring(3, 8) : shopId;
        String date = DateTimeUtil.nowDateString("yyyyMMddHHmmss");
        Integer integerShopId;
        try {
            integerShopId = Integer.valueOf(strShopId);
        } catch (Exception ex) {
            integerShopId = 99999;
        }
        return "SW" + String.format("%05d", integerShopId) + "01" + date + RandomUtil.generateNumber(2);
    }

    //订单插入
    public void updSynWaiMaiOrder(OrderWaiMai orderWaiMai) throws  BaiDuException,JdHomeException,ElemeException,MeiTuanException,JMException{
        try{
            //order Insert/update
            sysInnerService.updSynWaiMaiOrder(orderWaiMai);
            //topic message to MQ Server
            if (StaticObj.mqTransportTopicOrder){
                //topicMessageProducerWaiMaiOrder.sendMessage(topicDestinationWaiMaiOrder, formatOrder2Pos(orderWaiMai),orderWaiMai.getShopId());
                //选择MQ地址
                setMqOrderAddress(orderWaiMai.getSellerShopId(),orderWaiMai);

                //推送crm&zt
                if(!StringUtil.isEmpty(orderWaiMai.getShopId()) && "800".equals(orderWaiMai.getShopId().substring(0,3))){
                    JSONObject crm = formatOrder2Pos(orderWaiMai);
                    //线程给crm用
                    Runnable runCrm = new Runnable() {
                        @Override
                        public void run() {
                            //插入推送记录表
                            sysInnerService.addPushRecords(orderWaiMai.getOrderId(),1);//1.crm 2.zt
                            //推送订单to CRM
                            crm.put("orderId",orderWaiMai.getOrderId());
                            queueMessageProducerWaiMaiOrderToCrm.init(crm,orderWaiMai.getSellerShopId());
                            new Thread(queueMessageProducerWaiMaiOrderToCrm).start();
                        }
                    };
                    Thread threadCrm = new Thread(runCrm);
                    threadCrm.start();
                    //线程给中台用
                    Runnable runZt = new Runnable() {
                        @Override
                        public void run() {
                            //推送订单 to 中台
                            queueMessageProducerWaiMaiOrderToZt.init(crm,orderWaiMai.getSellerShopId());
                            new Thread(queueMessageProducerWaiMaiOrderToZt).start();
                            //插入推送记录表
                            sysInnerService.addPushRecords(orderWaiMai.getOrderId(),2);//1.crm 2.zt
                        }
                    };
                    Thread threadZt = new Thread(runZt);
                    threadZt.start();
                }
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

    //订单查询
    public List<OrderWaiMai> findOrderWaiMaiList(String  platform,String start,String end){
        return sysInnerService.findOrderWaiMaiList(platform,start,end);
    }

    //订单插入 list
    public  void  updSynWaiMaiOrder(List<OrderWaiMai> orderWaiMaiList) throws JdHomeException{
        orderWaiMaiList.forEach(v->{
            try {
                sysInnerService.updSynWaiMaiOrder(v);
                log.info(formatOrder2Pos(v));
                //topicMessageProducerWaiMaiOrder.sendMessage(topicDestinationWaiMaiOrder, formatOrder2Pos(v), v.getShopId());
                setMqOrderAddress(v.getSellerShopId(),v);

                //推送crm&zt
                if(!StringUtil.isEmpty(v.getShopId()) && "800".equals(v.getShopId().substring(0,3))){
                    JSONObject crm = formatOrder2Pos(v);
                    //线程给crm用
                    Runnable runCrm = new Runnable() {
                        @Override
                        public void run() {
                            //插入推送记录表
                            sysInnerService.addPushRecords(v.getOrderId(),1);//1.crm 2.zt
                            //推送订单to CRM
                            crm.put("orderId",v.getOrderId());
                            queueMessageProducerWaiMaiOrderToCrm.init(crm,v.getSellerShopId());
                            new Thread(queueMessageProducerWaiMaiOrderToCrm).start();
                        }
                    };
                    Thread threadCrm = new Thread(runCrm);
                    threadCrm.start();
                    //线程给中台用
                    Runnable runZt = new Runnable() {
                        @Override
                        public void run() {
                            //推送订单 to 中台
                            queueMessageProducerWaiMaiOrderToZt.init(crm,v.getSellerShopId());
                            new Thread(queueMessageProducerWaiMaiOrderToZt).start();
                            //插入推送记录表
                            sysInnerService.addPushRecords(v.getOrderId(),2);//1.crm 2.zt
                        }
                    };
                    Thread threadZt = new Thread(runZt);
                    threadZt.start();
                }

             }catch (ScheduleException ex){
             }catch (Exception ex){
             }});
    }


    //修改外卖订单
    public void updateWaiMaiOrder(String orderId,OrderWaiMai orderWaiMai){
        sysInnerService.updateWaiMaiOrder(orderId,orderWaiMai.getOrder());
        //topicMessageProducerOrderStatusAll.sendMessage(topicDestinationWaiMaiOrderStatusAll,formatOrder2Pos(orderWaiMai),orderWaiMai.getShopId());
        topicMessageProducerWaiMaiOrderStatusAllSync.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
        new Thread(topicMessageProducerWaiMaiOrderStatusAllSync).start();
    }

    //推送CRM
    public void push2Crm(String orderId){
        OrderWaiMai orderWaiMai = sysInnerService.findOrderWaiMaiByOrderId(orderId);
        if(!StringUtil.isEmpty(orderWaiMai)){
            //推送订单to CRM
            JSONObject crm = formatOrder2Pos(orderWaiMai);
            crm.put("orderId",orderWaiMai.getOrderId());
            queueMessageProducerWaiMaiOrderToCrm.init(crm,orderWaiMai.getSellerShopId());
            new Thread(queueMessageProducerWaiMaiOrderToCrm).start();
            //插入推送记录表
            sysInnerService.updatePushTimes(orderWaiMai,1);//1.crm 2.zt
        }
    }

    //推送历史订单CRM
    public void pushHistoryOrder2Crm(OrderWaiMai orderWaiMai){
        if(!StringUtil.isEmpty(orderWaiMai.getShopId()) && "800".equals(orderWaiMai.getShopId().substring(0,3))){
            JSONObject crm = formatOrder2Pos(orderWaiMai);
            crm.put("orderId",orderWaiMai.getOrderId());
            queueMessageProducerWaiMaiOrderToCrm.init(crm,orderWaiMai.getSellerShopId());
            new Thread(queueMessageProducerWaiMaiOrderToCrm).start();
            //插入推送记录表
            sysInnerService.updatePushTimes(orderWaiMai,1);//1.crm 2.zt
        }
    }

    //推送中台
    public void push2ZT(String orderId){
        OrderWaiMai orderWaiMai = sysInnerService.findOrderWaiMaiByOrderId(orderId);
        //推送订单to CRM
        JSONObject crm = formatOrder2Pos(orderWaiMai);
        crm.put("orderId",orderWaiMai.getOrderId());
        //推送订单 to 中台
        queueMessageProducerWaiMaiOrderToZt.init(crm,orderWaiMai.getSellerShopId());
        new Thread(queueMessageProducerWaiMaiOrderToZt).start();
        //插入推送记录表
        sysInnerService.updatePushTimes(orderWaiMai,2);//1.crm 2.zt
    }


    //格式化订单返回给Pos
    public JSONObject formatOrder2Pos(OrderWaiMai orderWaiMai){
        JSONObject jsonObject = new JSONObject();
        JSONObject rtn = new JSONObject();
        jsonObject.put("platform", orderWaiMai.getPlatform());
        jsonObject.put("shopId",orderWaiMai.getShopId());
        //jsonObject.put("sellerShopId",orderWaiMai.getSellerShopId());
        jsonObject.put("orderId",orderWaiMai.getOrderId());
        jsonObject.put("platOrderId",orderWaiMai.getPlatformOrderId());
        //jsonObject.put("createTime",orderWaiMai.getCreateTime());
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
                rtn =  formatEleme((OrderEle) orderWaiMai.getOrder(),jsonObject);
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
        //rtnJson.put("orderId",jsonObject.getString("orderId"));
        rtnJson.put("header",getBaiDuHeader(data, jsonObject));
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
        jsonObject.put("poiCode",data.getShop().getShopId());
        jsonObject.put("poiName",data.getShop().getName());
        jsonObject.put("poiAddress","");
        jsonObject.put("poiPhone","");
        jsonObject.put("orderIndex",data.getOrder().getOrderIndex());
        jsonObject.put("orderType","");
        jsonObject.put("orderStatus",tranBdOrderStatus(data.getOrder().getStatus()));
        jsonObject.put("orderStatusTime","");
        jsonObject.put("orderStartTime",data.getOrder().getCreateTime()==0?"":DateTimeUtil.dateFormat(new Date(Long.parseLong(data.getOrder().getCreateTime().toString())*1000), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime",data.getOrder().getConfirmTime());
        jsonObject.put("orderPurchaseTime", "");
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately",data.getOrder().getSendImmediately());//1 立即送达  2 预约单
        jsonObject.put("expectTimeMode",data.getOrder().getExpectTimeMode());
        jsonObject.put("orderPreDeliveryTime","");
        jsonObject.put("expectSendTime",data.getOrder().getSendTime());
        jsonObject.put("riderArrivalTime",data.getOrder().getDeliveryTime());
        jsonObject.put("riderPickupTime",data.getOrder().getPickupTime());
        jsonObject.put("riderPickupNo",data.getOrder().getDeliveryPhone());
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime",data.getOrder().getCancelTime());
        jsonObject.put("orderCancelRemark","");
        jsonObject.put("isThirdShipping",data.getOrder().getDeliveryParty());
        jsonObject.put("deliveryStationNo",data.getShop().getShopId());
        jsonObject.put("deliveryStationName",data.getShop().getName());
        jsonObject.put("deliveryCarrierNo","");
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo","");
        jsonObject.put("deliveryPackageWeight","");
        jsonObject.put("deliveryConfirmTime", "");
        jsonObject.put("orderFinishTime",data.getOrder().getFinishedTime());
        jsonObject.put("orderPayType",data.getOrder().getPayType());
        jsonObject.put("orderTotalMoney",StringUtil.isEmpty(data.getOrder().getTotalFee())?0:data.getOrder().getTotalFee()*0.01);
        jsonObject.put("orderDiscountMoney",StringUtil.isEmpty(data.getOrder().getDiscountFee())?0:data.getOrder().getDiscountFee()*0.01);
        jsonObject.put("orderFreightMoney",StringUtil.isEmpty(data.getOrder().getSendFee())?0:data.getOrder().getSendFee()*0.01);
        jsonObject.put("packagingMoney",StringUtil.isEmpty(data.getOrder().getPackageFee())?0:data.getOrder().getPackageFee()*0.01);
        jsonObject.put("orderBuyerPayableMoney",StringUtil.isEmpty(data.getOrder().getUserFee())?0:data.getOrder().getUserFee()*0.01);
        jsonObject.put("orderShopFee",StringUtil.isEmpty(data.getOrder().getShopFee())?0:data.getOrder().getShopFee()*0.01);
        jsonObject.put("orderOriginPrice",StringUtil.isEmpty(data.getOrder().getTotalFee())?0:data.getOrder().getTotalFee()*0.01);
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
                jsonObject.put("skuName",data.getProducts().get(i)[j].getProductName()+(data.getProducts().get(i)[j].getProductFeatures().size()==0?""
                    :"("+data.getProducts().get(i)[j].getProductFeatures().get(0).getOption()+")"));
                jsonObject.put("skuIdIsv",data.getProducts().get(i)[j].getOtherDishId());
                jsonObject.put("price",StringUtil.isEmpty(data.getProducts().get(i)[j].getProductPrice())?0:Integer.parseInt(data.getProducts().get(i)[j].getProductPrice())*0.01);
                jsonObject.put("quantity",data.getProducts().get(i)[j].getProductAmount());
                jsonObject.put("isGift","");
                jsonObject.put("upcCode",data.getProducts().get(i)[j].getUpc());
                jsonObject.put("boxNum",data.getProducts().get(i)[j].getPackageAmount());
                jsonObject.put("boxPrice",StringUtil.isEmpty(data.getProducts().get(i)[j].getPackagePrice())?0:data.getProducts().get(i)[j].getPackagePrice()*0.01);
                jsonObject.put("productFee",StringUtil.isEmpty(data.getProducts().get(i)[j].getProductFee())?0:data.getProducts().get(i)[j].getProductFee()*0.01);
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
            jsonObject.put("discountCode",discount.getDesc());
            jsonObject.put("discountPrice",StringUtil.isEmpty(discount.getFee())?0:discount.getFee()*0.01);
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
        rtnJson.put("header",getJdHomeHeader(orderInfoDTO, jsonObject));
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
        jsonObject.put("poiCode",orderInfo.getProduceStationNoIsv());
        jsonObject.put("poiName",orderInfo.getProduceStationName());
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
        jsonObject.put("expectSendTime","1");
        jsonObject.put("riderArrivalTime","");
        jsonObject.put("riderPickupTime","");
        jsonObject.put("riderPickupNo","");
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime",StringUtil.isEmpty(orderInfo.getOrderCancelTime())?"":DateTimeUtil.dateFormat(orderInfo.getOrderCancelTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderCancelRemark",orderInfo.getOrderCancelRemark());
        jsonObject.put("isThirdShipping","");
        jsonObject.put("deliveryStationNo",orderInfo.getProduceStationNoIsv());
        jsonObject.put("deliveryStationName","");
        jsonObject.put("deliveryCarrierNo",orderInfo.getProduceStationNoIsv());
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo",orderInfo.getDeliveryBillNo());
        jsonObject.put("deliveryPackageWeight",orderInfo.getDeliveryPackageWeight());
        jsonObject.put("deliveryConfirmTime", StringUtil.isEmpty(orderInfo.getDeliveryConfirmTime())?"":DateTimeUtil.dateFormat(orderInfo.getDeliveryConfirmTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderFinishTime","");
        jsonObject.put("orderPayType",orderInfo.getOrderPayType());
        jsonObject.put("orderTotalMoney",StringUtil.isEmpty(orderInfo.getOrderTotalMoney())?0:orderInfo.getOrderTotalMoney()*0.01);
        jsonObject.put("orderDiscountMoney",StringUtil.isEmpty(orderInfo.getOrderDiscountMoney())?0:orderInfo.getOrderDiscountMoney()*0.01);
        jsonObject.put("orderFreightMoney",StringUtil.isEmpty(orderInfo.getOrderFreightMoney())?0:orderInfo.getOrderFreightMoney()*0.01);
        jsonObject.put("packagingMoney",StringUtil.isEmpty(orderInfo.getPackagingMoney())?0:orderInfo.getPackagingMoney()*0.01);
        jsonObject.put("orderBuyerPayableMoney",StringUtil.isEmpty(orderInfo.getOrderBuyerPayableMoney())?0:orderInfo.getOrderBuyerPayableMoney()*0.01);
        jsonObject.put("orderShopFee","");
        jsonObject.put("orderOriginPrice",StringUtil.isEmpty(orderInfo.getOrderTotalMoney())?0:orderInfo.getOrderTotalMoney()*0.01);
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
            jsonObject.put("price",StringUtil.isEmpty(product.getSkuJdPrice())?0:product.getSkuJdPrice());
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
            jsonObject.put("discountPrice", StringUtil.isEmpty(discount.getDiscountPrice())?0:discount.getDiscountPrice());
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
        jsonObject.put("orderStatusTime",orderInfo.getUtime()==0?"":DateTimeUtil.dateFormat(new Date(orderInfo.getUtime()*1000), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderStartTime",orderInfo.getUtime()==0?"":DateTimeUtil.dateFormat(new Date(orderInfo.getUtime()*1000), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime","");
        jsonObject.put("orderPurchaseTime", "");
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately","");
        jsonObject.put("expectTimeMode","");
        jsonObject.put("orderPreDeliveryTime",orderInfo.getDeliverytime()==0?"":DateTimeUtil.dateFormat(new Date(orderInfo.getDeliverytime()*1000), "yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("expectSendTime","1");
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
        jsonObject.put("orderTotalMoney",orderInfo.getOriginalprice());
        jsonObject.put("orderDiscountMoney","");
        jsonObject.put("orderFreightMoney",orderInfo.getShippingfee());
        jsonObject.put("packagingMoney","");
        jsonObject.put("orderBuyerPayableMoney",orderInfo.getTotal());
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
        jsonObject.put("buyerMobile",StringUtil.isEmpty(orderInfo.getRecipientphone())?"":orderInfo.getRecipientphone().substring(0,11));
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
            jsonObject.put("skuName",orderInfo.getDetail()[i].getFood_name()+ (StringUtil.isEmpty(orderInfo.getDetail()[i].getSpec())?"":"("+orderInfo.getDetail()[i].getSpec()+")")+
                (StringUtil.isEmpty(orderInfo.getDetail()[i].getFood_property())?"":"("+orderInfo.getDetail()[i].getFood_property()+")"));
            jsonObject.put("skuIdIsv",orderInfo.getDetail()[i].getSku_id());
            jsonObject.put("price",orderInfo.getDetail()[i].getPrice());
            jsonObject.put("quantity",orderInfo.getDetail()[i].getQuantity());
            jsonObject.put("isGift","");
            jsonObject.put("upcCode","");
            jsonObject.put("boxNum",orderInfo.getDetail()[i].getBox_num());
            jsonObject.put("boxPrice",orderInfo.getDetail()[i].getBox_price());
            jsonObject.put("productFee",orderInfo.getDetail()[i].getPrice());
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
    private  JSONObject formatEleme(OrderEle order ,JSONObject jsonObject){
        if(StringUtil.isEmpty(order)){
            order = new OrderEle();
        }
        JSONObject rtnJson = new JSONObject();
        rtnJson.put("header",getElemeHeader(order, jsonObject));
        rtnJson.put("user",getElemeUsers(order));
        rtnJson.put("productList",getElmeProducts(order));
        rtnJson.put("discountList",getElemeDiscount(order));
        return rtnJson;
    }

    //获取饿了么订单头部信息
    private JSONArray getElemeHeader(OrderEle order, JSONObject jsonObject){
        JSONArray jsonArray = new JSONArray();

        jsonObject.put("platShopId",order.getShopId());
        jsonObject.put("platStationName",order.getShopName());
        jsonObject.put("poiCode",order.getShopId());
        jsonObject.put("poiName",order.getShopName());
        jsonObject.put("poiAddress","");
        jsonObject.put("poiPhone","");
        jsonObject.put("orderIndex",order.getDaySn());
        jsonObject.put("orderType","");
        jsonObject.put("orderStatus",tranELOrderStatus1(order.getStatus()));
        jsonObject.put("orderStatusTime","");
        jsonObject.put("orderStartTime",order.getCreatedAt()==null?"":DateTimeUtil.dateFormat(order.getCreatedAt(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderConfirmTime","");
        jsonObject.put("orderPurchaseTime", order.getActiveAt()==null?"":DateTimeUtil.dateFormat(order.getActiveAt(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("orderAgingType","");
        jsonObject.put("deliveryImmediately","");
        jsonObject.put("expectTimeMode","");
        jsonObject.put("orderPreDeliveryTime",order.getDeliverTime()==null?"":DateTimeUtil.dateFormat(order.getDeliverTime(),"yyyy-MM-dd HH:mm:ss"));
        jsonObject.put("expectSendTime", "1");
        jsonObject.put("riderArrivalTime","");
        jsonObject.put("riderPickupTime","");
        jsonObject.put("riderPickupNo","");
        jsonObject.put("riderPhone","");
        jsonObject.put("orderCancelTime","");
        jsonObject.put("orderCancelRemark","");
        jsonObject.put("isThirdShipping","");
        jsonObject.put("deliveryStationNo",order.getOpenId());
        jsonObject.put("deliveryStationName",order.getShopName());
        jsonObject.put("deliveryCarrierNo","");
        jsonObject.put("deliveryCarrierName","");
        jsonObject.put("deliveryBillNo","");
        jsonObject.put("deliveryPackageWeight","");
        jsonObject.put("deliveryConfirmTime", "");
        jsonObject.put("orderFinishTime","");
        jsonObject.put("orderPayType",order.getOnlinePaid()==true?2:1);
        jsonObject.put("orderTotalMoney",order.getOriginalPrice());
        jsonObject.put("orderDiscountMoney","");
        jsonObject.put("orderFreightMoney",order.getDeliverFee());
        jsonObject.put("packagingMoney",order.getPackageFee());
        jsonObject.put("orderBuyerPayableMoney",order.getTotalPrice());
        jsonObject.put("orderShopFee","");
        jsonObject.put("orderOriginPrice",order.getOriginalPrice());
        jsonObject.put("serviceRate",order.getServiceRate());
        jsonObject.put("serviceFee",order.getServiceFee());
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
    private JSONArray getElemeUsers(OrderEle order ){
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("platOrderId",order.getId());
        jsonObject.put("buyerFullName",order.getConsignee());
        jsonObject.put("buyerFullAddress",order.getAddress());
        if(order.getPhoneList()!=null && order.getPhoneList().size()>0){
            String str ="";
            for (int i=0;i<order.getPhoneList().size();i++){
               str = str+order.getPhoneList().get(i).toString()+",";
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
        jsonObject.put("buyerLng",order.getDeliveryGeo());
        jsonObject.put("buyerLat",order.getDeliveryGeo());
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    //获取饿了么订单商品信息
    private JSONArray getElmeProducts(OrderEle order){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(order.getGroups())){
            return jsonArray;
        }
        if(order.getGroups()==null ||order.getGroups().size()==0){
            return jsonArray;
        }
        for(int i=0;i<order.getGroups().size();i++){
            for(int j=0;j<order.getGroups().get(i).getItems().size();j++){
                String spec ="";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("platOrderId",order.getId());
                jsonObject.put("skuType","");
                jsonObject.put("skuId",order.getGroups().get(i).getItems().get(j).getId());
                int size = order.getGroups().get(i).getItems().get(j).getNewSpecs().size();
                for(int k=0;k<size;k++){
                    spec=spec+order.getGroups().get(i).getItems().get(j).getNewSpecs().get(k).toString()+",";
                }
                jsonObject.put("skuName",order.getGroups().get(i).getItems().get(j).getName()+(StringUtil.isEmpty(spec)?"":"("+spec+")"));
                jsonObject.put("skuIdIsv",order.getGroups().get(i).getItems().get(j).getExtendCode());
                jsonObject.put("price",order.getGroups().get(i).getItems().get(j).getPrice());
                jsonObject.put("quantity",order.getGroups().get(i).getItems().get(j).getQuantity());
                jsonObject.put("isGift","");
                jsonObject.put("upcCode","");
                jsonObject.put("boxNum","");
                jsonObject.put("boxPrice","");
                jsonObject.put("productFee",order.getGroups().get(i).getItems().get(j).getPrice());
                jsonObject.put("unit","");
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

    //获取饿了么订单折扣信息
    private JSONArray getElemeDiscount(OrderEle order){
        JSONArray jsonArray = new JSONArray();

        if(StringUtil.isEmpty(order.getOrderActivities())){
            return jsonArray;
        }
        if(order.getOrderActivities()==null && order.getOrderActivities().size()==0){
            return jsonArray;
        }
        for(int i=0;i<order.getOrderActivities().size();i++){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("platOrderId", order.getId());
            jsonObject.put("discountType", "");
            jsonObject.put("discountCode",order.getOrderActivities().get(i).getName());
            jsonObject.put("discountPrice", order.getOrderActivities().get(i).getAmount());
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
      /*  //发送消息到EKP
        ekpMessageProducerMessage.sendMessage(ekpDestinationException,new Gson().toJson(log));*/
    }

    //格式化百度订单状态
    public int tranBdOrderStatus(Integer status){
        switch (status){
            case Constants.BD_SUSPENDING:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.BD_CONFIRMED:
               return Constants.POS_ORDER_CONFIRMED;
            case Constants.BD_PICKUP:
                return Constants.POS_ORDER_DISPATCH_GET;
            case Constants.BD_DELIVERY:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.BD_COMPLETED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.BD_CANCELED:
                return Constants.POS_ORDER_CANCELED;
            case Constants.BD_DISPATCHER_NOT_GET:
                return Constants.POS_ORDER_DIS_NOT_GET;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化京东到家订单状态
    public int tranJHOrderStatus(int status){
        switch (status){
            case Constants.JH_ORDER_WAITING:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.JH_ORDER_RECEIVED:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.JH_ORDER_WAITING_TO_GET:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.JH_ORDER_DELIVERING:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.JH_ORDER_CONFIRMED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.JH_ORDER_USER_CANCELLED:
                return Constants.POS_ORDER_CANCELED;
            case Constants.JH_ORDER_USER_CANCELLED_APPLY:
                return Constants.POS_ORDER_CANCELED;
            case Constants.BD_DISPATCHER_NOT_GET:
                return Constants.POS_ORDER_DIS_NOT_GET;
            case Constants.JH_DELIVERY_DISPACTER:
                return Constants.POS_ORDER_DISPATCH_GET;
            case Constants.JH_DELIVERY_CONFIRMED:
                return Constants.POS_ORDER_COMPLETED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化美团订单状态
    public int tranMTOrderStatus(int status){
        switch (status){
            case Constants.MT_STATUS_CODE_UNPROCESSED:
                return Constants.POS_ORDER_SUSPENDING;
            case Constants.MT_STATUS_CODE_CONFIRMED:
                return Constants.POS_ORDER_CONFIRMED;
            case Constants.MT_STATUS_CODE_DELIVERY:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.MT_STATUS_CODE_COMPLETED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.MT_STATUS_CODE_CANCELED:
                return Constants.POS_ORDER_CANCELED;
            case Constants.MT_STATUS_CODE_RECEIVED:
                return Constants.POS_ORDER_DISPATCH_GET;
            case Constants.MT_STATUS_CODE_DELIVERYED:
                return Constants.POS_ORDER_DELIVERY;
            case Constants.MT_STATUS_CODE_DISPACHER_FINISHED:
                return Constants.POS_ORDER_COMPLETED;
            case Constants.MT_STATUS_CODE_DISPACHER_CANCELED:
                return Constants.POS_ORDER_CANCELED;
            case Constants.BD_DISPATCHER_NOT_GET:
                return Constants.POS_ORDER_DIS_NOT_GET;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }


    //格式化饿了么订单状态
    public int tranELOrderStatus(OOrderStatus status){
        switch (status){
            case unprocessed:
                return Constants.POS_ORDER_SUSPENDING;
            case valid:
                return Constants.POS_ORDER_CONFIRMED;
            case settled:
                return Constants.POS_ORDER_COMPLETED;
            case refunding:
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化饿了么订单状态
    public int tranELOrderStatus1(String status){
        switch (status){
            case "unprocessed":
                return Constants.POS_ORDER_SUSPENDING;
            case "valid":
                return Constants.POS_ORDER_CONFIRMED;
            case "settled":
                return Constants.POS_ORDER_COMPLETED;
            case "refunding":
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化饿了么配送状态
    public int tranELDeliveryStatus(String status){
        switch (status){
            case "tobeAssignedCourier":
                return Constants.POS_ORDER_CONFIRMED;
            case "tobeFetched":
                return Constants.POS_ORDER_DISPATCH_GET;
            case "delivering":
                return Constants.POS_ORDER_DELIVERY;
            case "completed":
                return Constants.POS_ORDER_COMPLETED;
            case "cancelled":
                return Constants.POS_ORDER_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //topic message to mq server  [message:order status]
    public  void topicMessageOrderStatus(String platform,Integer status,String platformOrderId,String orderId,String shopId,OOrderStatus elemeSatus){
        OrderWaiMai orderWaiMai = null;
        String tmp = "\"orderId\":\"{0}\"，\"orderStatus\":\"{1}\",\"shopId\":\"{2}\"",shop = shopId;
        boolean boolSend = true;
        JSONObject jsonMessage = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectEmpty = new JSONObject();
        jsonObjectEmpty.put("orderId", "");
        jsonObjectEmpty.put("orderStatus", "");
        jsonObjectEmpty.put("shopId", "");
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_BAIDU,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", orderWaiMai == null ? "" : String.valueOf(tranBdOrderStatus(status)));
                jsonObject.put("shopId", shop);
                jsonMessage.put("baidu", jsonObject);

                jsonMessage.put("jdhome", jsonObjectEmpty);
                jsonMessage.put("meituan", jsonObjectEmpty);
                jsonMessage.put("eleme", jsonObjectEmpty);
                break;
            case  Constants.PLATFORM_WAIMAI_JDHOME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_JDHOME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }

                jsonMessage.put("baidu", jsonObjectEmpty);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", orderWaiMai == null ? "" : String.valueOf(tranJHOrderStatus(status)));
                jsonObject.put("shopId", shop);
                jsonMessage.put("jdhome",jsonObject);
                jsonMessage.put("meituan", jsonObjectEmpty);
                jsonMessage.put("eleme", jsonObjectEmpty);
                break;
            case  Constants.PLATFORM_WAIMAI_MEITUAN:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_MEITUAN,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.put("baidu", jsonObjectEmpty);
                jsonMessage.put("jdhome", jsonObjectEmpty);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", tranMTOrderStatus(status));
                jsonObject.put("shopId", shop);
                jsonMessage.put("meituan", jsonObject);
                jsonMessage.put("eleme", jsonObjectEmpty);
                break;
            case  Constants.PLATFORM_WAIMAI_ELEME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_ELEME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.put("baidu",jsonObjectEmpty);
                jsonMessage.put("jdhome", jsonObjectEmpty);
                jsonMessage.put("meituan", jsonObjectEmpty);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", orderWaiMai == null ? "" : tranELOrderStatus(elemeSatus));
                jsonObject.put("shopId", shop);
                jsonMessage.put("eleme", jsonObject);
                break;
            default:
                boolSend = false;
                break;
        }
        if (boolSend & StaticObj.mqTransportTopicOrderStatus){
            //topicMessageProducerOrderStatus.sendMessage(topicDestinationWaiMaiOrderStatus,new Gson().toJson(jsonMessage),shop);
            log.info("=====状态过滤器value:"+shop);
            //选择推送订单状态的MQ
            setMqOrderStatusAddress(shop,jsonMessage,shop);

        }
    }

    //topic message to mq server  [message:order delivery]
    public  void topicMessageOrderDelivery(String platform,Integer status,String platformOrderId,String dispatcherMobile,String dispatcherName,String shopId){
        OrderWaiMai orderWaiMai = null;
        String tmp = "\"orderId\":\"{0}\"，\"orderStatus\":\"{1}\",\"shopId\":\"{2}\"",shop = shopId;
        boolean boolSend = true;
        JSONObject jsonMessage = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObjectEmpty = new JSONObject();
        jsonObjectEmpty.put("orderId", "");
        jsonObjectEmpty.put("orderStatus", "");
        jsonObjectEmpty.put("shopId", "");
        switch (platform){
            case  Constants.PLATFORM_WAIMAI_MEITUAN:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_MEITUAN,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.put("baidu", jsonObjectEmpty);
                jsonMessage.put("jdhome", jsonObjectEmpty);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", tranMTOrderStatus(status));
                jsonObject.put("shopId", shop);
                jsonObject.put("dispatcherMobile",dispatcherMobile);
                jsonObject.put("dispatcherName",dispatcherName);
                jsonMessage.put("meituan", jsonObject);
                jsonMessage.put("eleme", jsonObjectEmpty);
                break;
            case  Constants.PLATFORM_WAIMAI_ELEME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_ELEME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.put("baidu", jsonObjectEmpty);
                jsonMessage.put("jdhome", jsonObjectEmpty);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", status);
                jsonObject.put("shopId", shop);
                jsonObject.put("dispatcherMobile",dispatcherMobile);
                jsonObject.put("dispatcherName",dispatcherName);
                jsonMessage.put("meituan", jsonObjectEmpty);
                jsonMessage.put("eleme", jsonObject);
                break;
            case  Constants.PLATFORM_WAIMAI_JDHOME:
                orderWaiMai = findOrderWaiMai(Constants.PLATFORM_WAIMAI_JDHOME,platformOrderId);
                if (orderWaiMai==null){
                    boolSend = false;  //不发送message
                }else   {
                    shop = orderWaiMai.getShopId();
                }
                jsonMessage.put("baidu", jsonObjectEmpty);
                jsonMessage.put("jdhome", jsonObject);
                jsonObject.put("orderId", orderWaiMai == null ? "" : platformOrderId);
                jsonObject.put("orderStatus", tranJHOrderStatus(status));
                jsonObject.put("shopId", shop);
                jsonObject.put("dispatcherMobile",dispatcherMobile);
                jsonObject.put("dispatcherName",dispatcherName);
                jsonMessage.put("meituan", jsonObjectEmpty);
                jsonMessage.put("eleme", jsonObjectEmpty);
                break;
            default:
                boolSend = false;
                break;
        }
        if (boolSend & StaticObj.mqTransportTopicOrderStatus){
            //topicMessageProducerOrderStatus.sendMessage(topicDestinationWaiMaiOrderStatus,new Gson().toJson(jsonMessage),shop);
            log.info("推送配送订单start");
            log.info("=====配送状态过滤器value:"+shop);
            //选择推送订单状态的MQ
            setMqOrderStatusAddress(shop,jsonMessage,shop);
            /*topicMessageProducerWaiMaiOrderStatusAsync.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync).start();*/
        }
    }

    //topic message to mq server  [message:order statusAll]
    public void topicMessageOrderStatusAll(String platform,String shopId,OrderWaiMai orderWaiMai){
        if(StaticObj.mqTransportTopicOrder){
            //topicMessageProducerOrderStatusAll.sendMessage(topicDestinationWaiMaiOrderStatusAll,formatOrder2Pos(orderWaiMai),shopId);
            topicMessageProducerWaiMaiOrderStatusAllSync.init(formatOrder2Pos(orderWaiMai),shopId);
            new Thread(topicMessageProducerWaiMaiOrderStatusAllSync).start();
        }
    }


    //选择推送订单的MQ地址
    private void setMqOrderAddress(String sellerId,OrderWaiMai orderWaiMai){
        if(StringUtil.isEmpty(sellerId)){
            return;
        }
        String shopId = sellerId.substring(0,5);
        if("80010".equals(shopId)){  //上海 MQ1 130
            topicMessageProducerWaiMaiOrderAsync1.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync1).start();
        }else if("80020".equals(shopId) || "80049".equals(shopId)|| "80042".equals(shopId)){ //合肥 蚌埠 马芜  MQ2 44
           /* topicMessageProducerWaiMaiOrderAsync2.setTopicJmsTemplate(topicJmsTemplate2);
            topicMessageProducerWaiMaiOrderAsync2.setDestination(topicDestinationWaiMaiOrder2);*/
            topicMessageProducerWaiMaiOrderAsync2.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync2).start();
        }else if("80012".equals(shopId) || "80052".equals(shopId) ||"80045".equals(shopId)){ //南京  南昌 MQ3 45
           /* topicMessageProducerWaiMaiOrderAsync3.setTopicJmsTemplate(topicJmsTemplate3);
            topicMessageProducerWaiMaiOrderAsync3.setDestination(topicDestinationWaiMaiOrder3);*/
            topicMessageProducerWaiMaiOrderAsync3.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync3).start();
        }else if("80044".equals(shopId) || "80022".equals(shopId) || "80051".equals(shopId) ||"80016".equals(shopId) ||"80038".equals(shopId)){ //山东 南通 重庆 贵阳MQ4  46
           /* topicMessageProducerWaiMaiOrderAsync4.setTopicJmsTemplate(topicJmsTemplate4);
            topicMessageProducerWaiMaiOrderAsync4.setDestination(topicDestinationWaiMaiOrder4);*/
            topicMessageProducerWaiMaiOrderAsync4.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync4).start();
        }else if("80024".equals(shopId) || "80034".equals(shopId)){ //苏杭 郑州  MQ5 42
           /* topicMessageProducerWaiMaiOrderAsync5.setTopicJmsTemplate(topicJmsTemplate5);
            topicMessageProducerWaiMaiOrderAsync5.setDestination(topicDestinationWaiMaiOrder5);*/
            topicMessageProducerWaiMaiOrderAsync5.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync5).start();
        }else if("80036".equals(shopId)|| "80018".equals(shopId) || "80046".equals(shopId)){ //武汉 京津 徐州 MQ6 43
            /*topicMessageProducerWaiMaiOrderAsync6.setTopicJmsTemplate(topicJmsTemplate6);
            topicMessageProducerWaiMaiOrderAsync6.setDestination(topicDestinationWaiMaiOrder6);*/
            topicMessageProducerWaiMaiOrderAsync6.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync6).start();
        }else{
           /* topicMessageProducerWaiMaiOrderAsync.setTopicJmsTemplate(topicJmsTemplate); //MQ 41 西安 长沙 成都 广深
            topicMessageProducerWaiMaiOrderAsync.setDestination(topicDestinationWaiMaiOrder);*/
            topicMessageProducerWaiMaiOrderAsync.init(formatOrder2Pos(orderWaiMai),orderWaiMai.getSellerShopId());
            new Thread(topicMessageProducerWaiMaiOrderAsync).start();
        }
    }

    //选择推送订单的MQ地址
    private void setMqOrderStatusAddress(String sellerId,JSONObject jsonMessage,String shop){
        if(StringUtil.isEmpty(sellerId)){
            return;
        }
        String shopId = sellerId.substring(0,5);
        if("80010".equals(shopId)){ //上海 MQ1 130
           /* topicMessageProducerWaiMaiOrderStatusAsync1.setTopicJmsTemplate(topicJmsTemplate1);
            topicMessageProducerWaiMaiOrderStatusAsync1.setDestination(topicDestinationWaiMaiOrderStatus1);*/
            topicMessageProducerWaiMaiOrderStatusAsync1.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync1).start();
        }else if("80020".equals(shopId) || "80049".equals(shopId)|| "80042".equals(shopId)){ //合肥 蚌埠 马芜  MQ2 44
            /*topicMessageProducerWaiMaiOrderStatusAsync2.setTopicJmsTemplate(topicJmsTemplate2);
            topicMessageProducerWaiMaiOrderStatusAsync2.setDestination(topicDestinationWaiMaiOrderStatus2);*/
            topicMessageProducerWaiMaiOrderStatusAsync2.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync2).start();
        }else if("80012".equals(shopId) || "80052".equals(shopId) ||"80045".equals(shopId)){ //南京  南昌 MQ3  45
          /*  topicMessageProducerWaiMaiOrderStatusAsync3.setTopicJmsTemplate(topicJmsTemplate3);
            topicMessageProducerWaiMaiOrderStatusAsync3.setDestination(topicDestinationWaiMaiOrderStatus3);*/
            topicMessageProducerWaiMaiOrderStatusAsync3.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync3).start();
        }else if("80044".equals(shopId) || "80022".equals(shopId) || "80051".equals(shopId) ||"80016".equals(shopId) ||"80038".equals(shopId)){ //山东 南通 重庆 贵阳MQ4  46
            /*topicMessageProducerWaiMaiOrderStatusAsync4.setTopicJmsTemplate(topicJmsTemplate4);
            topicMessageProducerWaiMaiOrderStatusAsync4.setDestination(topicDestinationWaiMaiOrderStatus4);*/
            topicMessageProducerWaiMaiOrderStatusAsync4.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync4).start();
        }else if("80024".equals(shopId) || "80034".equals(shopId)){ //苏杭 郑州  MQ5 42
            /*topicMessageProducerWaiMaiOrderStatusAsync5.setTopicJmsTemplate(topicJmsTemplate5);
            topicMessageProducerWaiMaiOrderStatusAsync5.setDestination(topicDestinationWaiMaiOrderStatus5);*/
            topicMessageProducerWaiMaiOrderStatusAsync5.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync5).start();
        }else if("80036".equals(shopId)|| "80018".equals(shopId) || "80046".equals(shopId)){ //武汉 京津 徐州 MQ6 43
           /* topicMessageProducerWaiMaiOrderStatusAsync6.setTopicJmsTemplate(topicJmsTemplate6);
            topicMessageProducerWaiMaiOrderStatusAsync6.setDestination(topicDestinationWaiMaiOrderStatus6);*/
            topicMessageProducerWaiMaiOrderStatusAsync6.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync6).start();
        }
        else {
            /*topicMessageProducerWaiMaiOrderStatusAsync.setTopicJmsTemplate(topicJmsTemplate); //MQ 41   西安 长沙 成都 广深
            topicMessageProducerWaiMaiOrderStatusAsync.setDestination(topicDestinationWaiMaiOrderStatus);*/
            topicMessageProducerWaiMaiOrderStatusAsync.init(new Gson().toJson(jsonMessage),shop);
            new Thread(topicMessageProducerWaiMaiOrderStatusAsync).start();
        }
    }

}

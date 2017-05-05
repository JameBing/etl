package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.activemq.Queue.QueueMessageProducer;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.z8.*;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.service.z8.Z8ApiService;
import com.wangjunneil.schedule.service.z8.Z8InnerService;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class Z8FacadeService {

    private static Logger log = Logger.getLogger(Z8FacadeService.class.getName());

    @Autowired
    private Z8InnerService z8InnerService;

    @Autowired
    private Z8ApiService z8ApiService;

    //@Autowired
    private QueueMessageProducer messageProducer;

    // --------------------------------------------------------------------------------------------------- public method

    public void callback(String code, String state, Cfg cfg) {
        String params = "grant_type=authorization_code&client_id="+cfg.getAppKey()+"&redirect_uri="+cfg.getCallback()+"&code="+code+"&state="+state+"&client_secret="+cfg.getAppSecret();
        log.info("z8 url params:"+params);//TODO
        String returnJson = HttpUtil.post(Constants.Z8_REQUEST_TOKEN_URL, params);
        log.info("z8 token:"+returnJson);//TODO
        Z8AccessToken z8AccessToken = JSONObject.parseObject(returnJson, Z8AccessToken.class);
        z8InnerService.addAccessToken(z8AccessToken);
    }

    public List<Z8CrmOrder> syncOrder() {
        // 调用检索订单接口服务
        List<Z8CrmOrder> crmOrders = new ArrayList<>();
        z8ApiService.z8OrderSearch(crmOrders);
        if(crmOrders == null || crmOrders.size()==0) return null;

//        /* 存储等待出库的订单链 */
        List<Z8CrmOrder> waitOutStockOrders = new ArrayList<>();

        crmOrders.stream().forEach(p -> {
            // 等待出库的订单需要同步到中台
            if ("2".equals(p.getStatus()))
                waitOutStockOrders.add(p);
        });

        // 更新同步的折800订单到mongodb
        int size = crmOrders.size();
        if (size != 0) {
            z8InnerService.addSyncOrder(crmOrders);
            log.info("the new batch of orders(" + crmOrders.size() + ") store completion");
        }

        return waitOutStockOrders;
    } // end method syncOrder

    public String getControlStatus(){
        Cfg cfg = z8InnerService.getZ8Cfg();
        return JSONObject.toJSONString(cfg);
    }

    public String getHistoryOrder(Map<String, String> paramMap, Page<Z8CrmOrder> page){
        Page<Z8CrmOrder> returnPage = z8InnerService.getHistoryOrder(paramMap, page);
        return JSONObject.toJSONString(returnPage);
    }

    public int syncOrderByCond(Map<String, String> paramMap) throws ScheduleException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Z8CrmOrder> orders = new ArrayList<>();
        String startDate = paramMap.get("startDate").toString().replace(" ","T");
        String endDate = paramMap.get("endDate").toString().replace(" ","T");
        z8ApiService.z8OrderSearchByCond(startDate, endDate, orders);
        /* 默认从第一页开始同步查询 */
        if (log.isInfoEnabled())
            log.info("End the order synchronization summary size { " + orders.size() + " }");

        List<Z8CrmOrder> crmOrders = z8InnerService.historyOrder(paramMap);
        if (log.isInfoEnabled())
            log.info("End the order local db query summary size { " + crmOrders.size() + " }");

        List<Z8CrmOrder> needSupplementOrders = new ArrayList<>();
        List<Z8CrmOrder> needSendMsgList = new ArrayList<>();//统计需要发送的消息列表
        orders.stream().forEach(t -> {
            long count = crmOrders.stream()
                .filter(p -> t.getId().equals(p.getId()) && t.getStatus().equals(p.getStatus()))
                .count();

            if (count == 0) {   // 需补单
                //TODO 判断订单行是否有退款单

                //对订单状态限制，只向mq发送待发货状态的订单
                if ("2".contains(t.getStatus())) {
                    List<Z8CrmOrder> list = new ArrayList<Z8CrmOrder>();//每次只发一条订单
                    list.add(t);
                    String messageJson = JSONObject.toJSONString(list);
                    messageProducer.sendZ8OrderMessage(messageJson);
                    needSendMsgList.add(t);
                }
                needSupplementOrders.add(t);

                if (log.isInfoEnabled())  {
                    StringBuffer sb = new StringBuffer("the synchronization order [" + t.getId() + "] ");
                    //if (refundIds.size() != 0)  sb.append("refundIds ").append(refundIds).append(' ');
                    sb.append("supplement finished");
                    log.info(sb.toString());
                    sb = null;
                }
            } else {

            }
        });

        int size = needSupplementOrders.size();
        if (size != 0)
            z8InnerService.addSyncOrders(needSupplementOrders);
        if (log.isInfoEnabled())
            log.info("End supplement order operation, need supplement sum " + size + ", need send msg sum " + needSendMsgList.size() + ", waste time "+ stopWatch.getTotalTimeSeconds() + " second");

        return needSendMsgList.size();//TODO 暂时修改为实际发送的消息数
    }

}

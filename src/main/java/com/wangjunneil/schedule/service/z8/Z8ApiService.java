package com.wangjunneil.schedule.service.z8;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.JobZ8;
import com.wangjunneil.schedule.entity.z8.*;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.utility.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import com.wangjunneil.schedule.common.Constants;
/**
 * Created by wangjun on 8/1/16.
 */
@Service
public class Z8ApiService {

    /**
     * 日志定义
     */
    private static Logger log = Logger.getLogger(Z8ApiService.class.getName());

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    private Z8InnerService z8InnerService;

    @Autowired
    private Z8JobService z8JobService;


    public void z8OrderSearch(List list) {
        String nowTime = DateTimeUtil.nowDateString("yyyy-MM-dd'T'HH:mm:ss");
        String condTime = null;

        JobZ8 job = z8JobService.getJob("syncZ8Order");
        if (job == null) {
            // 首次同步, 以当前时间向前1天为开始时间, 当前时间为截止时间
            condTime = DateTimeUtil.preDateString("yyyy-MM-dd'T'HH:mm:ss", -1);
        } else {
            // 非首次同步, 上次同步时间为开始时间, 当前时间为截止时间
            Date preExecuteTime = job.getExecuteTime();
//            condTime = DateTimeUtil.dateSetTimeZone(preExecuteTime, "yyyy-MM-dd'T'HH:mm");
            condTime = DateTimeUtil.dateFormat(preExecuteTime, "yyyy-MM-dd'T'HH:mm:ss");
        }
        // 首次同步时间为一天的数据, 默认认为一天的交易量
        // 非首次同步为5分钟的订单量, 默认认为5分钟内
//        int sort_rule = 0;  //0：订单创建时间降序,默认 1：订单创建时间升序 10：订单最后修改时间降序 11：订单最后修改时间升序（不传sort_rule的话默认传为0：订单创建时间降序）
//        int order_state = 0;    //订单状态,1-待付款 2-等待发货 3-已发货(待确认收货) 5-交易成功 7-交易关闭 0-全部

        Date now = new Date();
        job = new JobZ8("syncZ8Order", now);

        try {
            orders(condTime, nowTime, 1, 0, list);
            job.setStatus("success");
        } catch (Exception e) {
            log.error(e.toString());
            job.setStatus("failure");
            job.setMsg(e.getMessage());
            return;
        }finally {
            z8JobService.addJob(job);
            log.info("job update. " + condTime + " - " + nowTime);
        }

    }

    public void z8OrderSearchByCond(String startTime, String endTime, List list) throws ScheduleException {
        orders(startTime, endTime, 1, 0, list);
    }

    /**
     * 根据条件全量同步订单列表, 由于每次最多获取100条订单, 所以此方法为递归方法, 根据响应计算页数
     * @param startTime
     * @param endTime
     * @param page
     * @param totalSize
     *
     */
    private void orders(String startTime, String endTime, int page, int totalSize, List list) {
        Z8AccessToken z8AccessToken = z8InnerService.getAccessToken();
        String token = z8AccessToken.getAccess_token();
        Cfg cfg = z8InnerService.getZ8Cfg();
        if (token == null) {
            log.error("z8 token_info is empty, authorize has not finished.");
        }
        String appKey = cfg.getAppKey();
        String url = Constants.Z8_URL + "orders.json";
        String reqParam = "page="+String.valueOf(page) +"&per_page=100&start_time=" + startTime + "&end_time=" + endTime + "&sort_rule=0&order_state=0" +
            "&access_token="+token+"&app_key="+appKey;
        String result = StringUtil.retParamAsc(reqParam);//参数ASCII排序
        String sign = MD5Util.encrypt32(token + StringUtil.retParamAscAdd(reqParam) + token).toUpperCase();//各参数ASCII排序，token+多个(key+value)+token进行MD5
        String paramResult = result+"&sign="+sign;
        try {
            String resultStr = HttpsUtil.getFormZ8(url + "?" + paramResult);

            JSONObject json = JSONObject.parseObject(resultStr);
            if (json.containsKey("errorMsg")) {
                log.error(json.getString("errorMsg"));
                return;
            }

            if (!json.containsKey("total_num")) return;

            if (totalSize == 0 && json.containsKey("total_num")) {
                totalSize = Integer.parseInt(json.getString("total_num"));
            }

            if(totalSize==0) return;

            int pageSize = totalSize/ 100;
            if(totalSize % 100 > 0)  pageSize += 1;
            JSONArray ordersTemp = json.getJSONArray("orders");
            for(int j=0;j<ordersTemp.size();j++){
                list.add(adapterZ8Order(ordersTemp.getJSONObject(j)));
            }
            if(page < pageSize) {
                orders(startTime, endTime, ++page, totalSize, list);
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.toString());
            e.printStackTrace();
        } catch (KeyManagementException e) {
            log.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    public Z8CrmOrder adapterZ8Order(JSONObject jsonObj){
        Z8CrmOrder z8CrmOrder = new Z8CrmOrder();
        z8CrmOrder.setId(jsonObj.getLong("id"));
        z8CrmOrder.setUrl(jsonObj.getString("url"));
        z8CrmOrder.setSeller_id(jsonObj.getString("seller_id"));
        z8CrmOrder.setSeller_nickname(jsonObj.getString("seller_nickname"));
        z8CrmOrder.setOrder_price(jsonObj.getString("order_price"));
        z8CrmOrder.setGoods_price(jsonObj.getString("goods_price"));
        z8CrmOrder.setDiscount_price(jsonObj.getString("discount_price"));
        z8CrmOrder.setPostage(jsonObj.getString("postage"));
        z8CrmOrder.setOos(jsonObj.getString("oos"));
        z8CrmOrder.setStatus(jsonObj.getString("status"));
        z8CrmOrder.setDeliver_time_limit(jsonObj.getString("deliver_time_limit"));
        z8CrmOrder.setSeller_comment(jsonObj.getString("seller_comment"));
        z8CrmOrder.setBuyer_comment(jsonObj.getString("buyer_comment"));
        z8CrmOrder.setCreated_at(DateTimeUtil.addHour(DateTimeUtil.parseDateString(jsonObj.getString("created_at")), -8));//解决绑定数据库是时区不同，时间多8小时问题
        if (jsonObj.containsKey("close_time"))
            z8CrmOrder.setClose_time(DateTimeUtil.addHour(DateTimeUtil.parseDateString(jsonObj.getString("close_time")), -8));//解决绑定数据库是时区不同，时间多8小时问题
        z8CrmOrder.setClose_reason(jsonObj.getString("close_reason"));
        if (jsonObj.containsKey("updated_at"))
            z8CrmOrder.setUpdated_at(DateTimeUtil.addHour(DateTimeUtil.parseDateString(jsonObj.getString("updated_at")), -8));//解决绑定数据库是时区不同，时间多8小时问题
        if (jsonObj.containsKey("express_time"))
            z8CrmOrder.setExpress_time(DateTimeUtil.addHour(DateTimeUtil.parseDateString(jsonObj.getString("express_time")), -8));//解决绑定数据库是时区不同，时间多8小时问题
        if (jsonObj.containsKey("pay_time"))
            z8CrmOrder.setPay_time(DateTimeUtil.addHour(DateTimeUtil.parseDateString(jsonObj.getString("pay_time")), -8));//解决绑定数据库是时区不同，时间多8小时问题
        z8CrmOrder.setExpress_no(jsonObj.getString("express_no"));
        z8CrmOrder.setExpress_company(jsonObj.getString("express_company"));
        z8CrmOrder.setNickname(jsonObj.getString("nickname"));
        z8CrmOrder.setSeller_comment_type(jsonObj.getInteger("seller_comment_type"));

        Invoice_info invoice_info = new Invoice_info();
        invoice_info = jsonObj.getObject("invoice", Invoice_info.class);
        Address_info address_info = new Address_info();
        address_info = jsonObj.getObject("address", Address_info.class);
        z8CrmOrder.setInvoice(invoice_info);
        z8CrmOrder.setAddress(address_info);
        z8CrmOrder.setProducts((List<Products_info>) jsonObj.get("products"));

        return z8CrmOrder;
    }


//    private JdClient getZ8Client() throws JdException {
//        Z8AccessToken token = z8InnerService.getAccessToken();
//        Cfg cfg = z8InnerService.getZ8Cfg();
//
//        if (token == null) {
//            log.error("z800 token_info is empty, authorize has not finished.");
//            throw new JdException("商户还未进行授权操作");
//        }
//
//        String refreshToken = token.getRefresh_token();
//        String appKey = cfg.getAppKey();
//        String appSecret = cfg.getAppSecret();
//
//        Date now = new Date();
//        Date expireDate = token.getExpire_Date();
//        if (expireDate.before(now)) {   // 若当前时间小于失效时间则刷新token
//            String tokenUrl = MessageFormat.format(Constants.JD_REFRESH_TOKEN_URL, appKey, appSecret, refreshToken);
//            String returnJson = HttpUtil.get(tokenUrl);
//            JSONObject jsonObject = JSONObject.parseObject(returnJson);
//            if (!"0".equals(jsonObject.getString("code"))) {
//                log.error(jsonObject.getString("error_description"));
//                throw new JdException("重新请求token失败");
//            }
//            token = JSONObject.parseObject(returnJson, JdAccessToken.class);
//            jdInnerService.addAccessToken(token);
//        }
//
//        String accessToken = token.getAccess_token();
//        JdClient jdClient = new DefaultJdClient(Constants.JD_SERVICE_URL, accessToken, appKey, appSecret);
//        return jdClient;
//    }

}

package com.wangjunneil.schedule.service.mt;

import com.alibaba.fastjson.JSONObject;
import com.meituan.mos.sdk.common.SignedRequestsHelper;
import com.sun.xml.internal.bind.v2.TODO;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.MD5Util;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.xml.ws.ServiceMode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuxin
 * @since 2016-11-14.
 */
@Service
public class MtApiService {

    private static Logger log = Logger.getLogger(MtApiService.class.getName());

    private long timestamp =0l;

    private static final String app_id ="0000";

    private String sig ="";

    //TODO 端点

    private static final String end_point = "https://mos.meituan.com/document/sdk";

    private static final String consumer_secret ="000";

    private static final String UTF8_CHARSET = "UTF-8";

    //门店开业
    private static final String OPEN_SHOP_URL = "http://test.waimaiopen.meituan.com/api/v1/poi/open";

    //门店歇业
    private static final String CLOSE_SHOP_URL = "http://test.waimaiopen.meituan.com/api/v1/poi/close";

    //商家确认订单
    private static final String ORDER_CONFIRM_URL = "http://test.waimaiopen.meituan.com/api/v1/order/confirm";

    //商家取消订单
    private static final String ORDER_CANCEL_URL = "http://test.waimaiopen.meituan.com/api/v1/order/cancel";

    //订单明细
    private static final String ORDER_DETAIL_URL = "http://test.waimaiopen.meituan.com/api/v1/order/getOrderDetail";


    //region 门店的开业及歇业

    //门店开业
    public String openShop(String code)
    {
        String params = "app_id=" + app_id + "&app_poi_code=" + code + "&timestamp=" + new Date().getTime() + "&consumer_secret=" + consumer_secret;
        String url = OPEN_SHOP_URL + "?" + params;
        String sig = MD5Util.encrypt32(url);
        params = params+"& sig="+sig;
        return HttpUtil.post(OPEN_SHOP_URL,params);
    }

    //门店歇业
    public String closeShop(String code)
    {
        String params = "app_id=" + app_id + "&app_poi_code=" + code + "&timestamp=" + new Date().getTime() + "&consumer_secret=" + consumer_secret;
        String url = CLOSE_SHOP_URL + "?" + params;
        String sig = MD5Util.encrypt32(params);
        params = params+"& sig="+sig;
        return HttpUtil.post(CLOSE_SHOP_URL,params);

        //TODO 使用工具类生成sig
        /*
            Map<String, String> params = new HashMap<String, String>();
            params.put("app_poi_code",code);
            timestamp =new Date().getTime();
            params.put("timestamp",String.valueOf(timestamp));
            String sig = SignedRequestsHelper.signRequest(consumer_secret, end_point, CLOSE_SHOP_URL, "POST", params);
            System.out.println(sig);
            return null;
            return HttpUtil.post(CLOSE_SHOP_URL,jsonObject.toJSONString());
        */
    }

    //endregion


    //region 订单接口（商家确认订单及商家取消订单）

    //商家确认订单
    public String getConfirmOrder(int orderid)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("order_id",orderid);
        String params = "app_id=" + app_id + "&order_id=" + jsonObject.toJSONString() + "&timestamp=" + new Date().getTime() + "&consumer_secret=" + consumer_secret;
        String url = ORDER_CONFIRM_URL + "?" + params;
        String sig = MD5Util.encrypt32(url);
        params = params+"& sig="+sig;
        return HttpUtil.post(ORDER_CONFIRM_URL,params);
    }

    //商家取消订单
    public String getCancelOrder(int orderid,String reason,String reasoncode)
    {
        String params = "app_id=" + app_id + "&order_id=" + orderid + "&reason=" + reason + "&reason_code=" + reasoncode + "&timestamp=" + new Date().getTime() + "&consumer_secret=" + consumer_secret;
        String url = ORDER_CANCEL_URL + "?" + params;
        String sig = MD5Util.encrypt32(url);
        params = params+"& sig="+sig;
        return HttpUtil.post(ORDER_CANCEL_URL,params);
    }

    //订单推送(已支付)
    public String newOrder(String orderId,String status)
    {
        String params = "app_id=" + app_id + "&order_id=" + orderId + "&status=" + status + "&timestamp=" + new Date().getTime() + "&consumer_secret=" + consumer_secret;
        String url = ORDER_DETAIL_URL + "?" + params;
        String sig = MD5Util.encrypt32(url);
        params = params+"& sig="+sig;
        return HttpUtil.post(ORDER_DETAIL_URL,params);
    }
    
    //endregion


}

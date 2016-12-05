package com.wangjunneil.schedule.service.meituan;

import com.google.gson.Gson;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.mt.FoodRequest;
import com.wangjunneil.schedule.entity.mt.OrderRequest;
import com.wangjunneil.schedule.entity.mt.ShopRequest;
import com.wangjunneil.schedule.entity.mt.SysParams;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.MD5Util;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * @author liuxin
 * @since 2016-11-14.
 */
@Service
public class MeiTuanApiService {

    private static Logger log = Logger.getLogger(MeiTuanApiService.class.getName());

    private static final String app_id ="0000";
    private static final String consumer_secret ="000";

    //门店开业
    private static final String OPEN_SHOP_URL = "http://test.waimaiopen.meituan.com/api/v1/poi/open";

    //门店歇业
    private static final String CLOSE_SHOP_URL = "http://test.waimaiopen.meituan.com/api/v1/poi/close";

    //商家确认订单
    private static final String ORDER_CONFIRM_URL = "http://test.waimaiopen.meituan.com/api/v1/order/confirm";

    //商家取消订单
    private static final String ORDER_CANCEL_URL = "http://test.waimaiopen.meituan.com/api/v1/order/cancel";
    //修改库存
    private static final String ONLINE_URL = "http://test.waimaiopen.meituan.com/api/v1/food/sku/stock";

    //订单明细
    private static final String ORDER_DETAIL_URL = "http://test.waimaiopen.meituan.com/api/v1/order/getOrderDetail";

    public String getSystemUrl(String pathUrl,Object obj) throws ScheduleException {
        try {
            SysParams sysParams = new SysParams();
            sysParams.setApp_id(app_id);
            Map<String, Object> map = StringUtil.getMap(sysParams);
            if (obj != null) {
                Map<String, Object> lmap = StringUtil.getMap(obj);
                map.putAll(lmap);
            }
            String params = pathUrl + "?" + StringUtil.getUrlParamsByMap(map) + consumer_secret;
            sysParams.setSig(sysParams.getMD5(params));
            return pathUrl + "?" + StringUtil.getUrlParamsByObject(sysParams);
        } catch (Exception ex) {
            throw new ScheduleException(Constants.PLATFORM_WAIMAI_MEITUAN, ex.getClass().getName(), "签名计算失败", pathUrl+"\r\n"+new Gson().toJson(obj), new Throwable().getStackTrace());
        }
    }

    //region 门店的开业及歇业

    //门店开业
    public String openShop(ShopRequest obj) throws ScheduleException {
        String url = getSystemUrl(OPEN_SHOP_URL, OPEN_SHOP_URL);
        return HttpUtil.post(url,StringUtil.getUrlParamsByObject(obj));
    }

    //门店歇业
    public String closeShop(ShopRequest obj) throws ScheduleException {
        String url = getSystemUrl(CLOSE_SHOP_URL, OPEN_SHOP_URL);
        return HttpUtil.post(url,StringUtil.getUrlParamsByObject(obj));
    }

    //endregion


    //region 订单接口（商家确认订单及商家取消订单）

    //商家确认订单
    public String getConfirmOrder(OrderRequest obj) throws ScheduleException {
        String url = getSystemUrl(ORDER_CONFIRM_URL,obj);
        return HttpUtil.post(url,StringUtil.getUrlParamsByObject(obj));
    }

    //商家取消订单
    public String getCancelOrder(OrderRequest obj) throws ScheduleException {
        String url = getSystemUrl(ORDER_CANCEL_URL,obj);
        return HttpUtil.post(url,StringUtil.getUrlParamsByObject(obj));
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
    //上下架
    public String setFrame(FoodRequest obj) throws ScheduleException {
        String url = getSystemUrl(ONLINE_URL, obj);
        return HttpUtil.post(url,StringUtil.getUrlParamsByObject(obj));
    }

}

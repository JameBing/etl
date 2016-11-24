package com.wangjunneil.schedule.service.eleme;

import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import com.wangjunneil.schedule.utility.URL;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeApiService {
    private static Logger log = Logger.getLogger(EleMeApiService.class.getName());
    public static final String KEY = "0170804777";
    public static final String SERVICEC = "87217cb263701f90316236c4df00d9352fb1da76";
    public static final String PATH = "http://v2.openapi.ele.me/food/";


    public String getSystemUrl(String pathUrl, Object obj) throws ElemaException {
        try {
            SysParams sysParams = new SysParams();
            sysParams.setConsumer_key(KEY);
            Map<String, String> map = StringUtil.getMap(sysParams);
            Map<String, String> lmap = StringUtil.getMap(obj);
            map.putAll(lmap);
            System.out.println(map);
            String sig = EleMeUtils.genSig(pathUrl, map, SERVICEC);
            sysParams.setSig(sig);
            return EleMeUtils.genUrl(pathUrl, sysParams);
        } catch (Exception e) {
            throw new ElemaException("签名计算失败", e);
        }
    }

    /**
     * 查询新订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String getNewOrder(NewOrder obj) throws ElemaException{
        String url = getSystemUrl(URL.URL_ELENE_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        System.out.println("查询新订单"+requstUrl);
        return HttpUtil.elmGet(requstUrl);
    }

    /**
     * 拉取新订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String pullNewOrder(NewOrder obj) throws ElemaException{
        String url = getSystemUrl(URL.URL_ELEME_PULL_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        System.out.println("拉取新订单"+requstUrl);
        return HttpUtil.elmGet(requstUrl);
    }

//    /**
//     * 拉取新订单回调
//     * @param obj
//     * @return
//     * @throws ElemaException
//     */
//    public static  String postNewOrderBack(NewOrder obj) throws ElemaException {
//        String url = getSystemUrl(PATH, obj);
//        System.out.println("");
//        return HttpUtil.post(url, StringUtil.getUrlParamsByObject(obj));
//    }

    /**
     * 门店开业
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String startBusiness(BusinessStatus obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_SHOP_ON, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        System.out.println("门店开业");
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 门店歇业
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String endBusiness(BusinessStatus obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_SHOP_ON, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        System.out.println("门店歇业");
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }
    /**
     * 获取订单详情
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String getOrderDetail(OrderRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_GET_ORDER, obj.getEleme_order_id());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        System.out.println("订单详情");
        return HttpUtil.elmGet(requstUrl);
    }

    /**
     * 取消订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String cancelorder(CancelStatus obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_CANCEL_ORDER, obj.getEleme_order_id());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        System.out.println("取消订单");
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 添加食品接口
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String addFoods(FoodsRequest obj) throws ElemaException {
        String url = getSystemUrl(URL.URL_ELEME_ADD_FOODS, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByObject(obj));
    }

//    public static void main(String[] arg0) throws ElemaException {
//        FoodsRequest foodsRequest = new FoodsRequest();
//        foodsRequest.setFood_category_id("123123");
//        foodsRequest.setName("测试");
//        foodsRequest.setPrice("20.0");
//        foodsRequest.setDescription("测试");
//        foodsRequest.setMax_stock("1000");
//        foodsRequest.setStock("10");
//        System.out.println(addFoods(foodsRequest));
//    }

}

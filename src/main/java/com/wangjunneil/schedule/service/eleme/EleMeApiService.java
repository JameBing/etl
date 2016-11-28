package com.wangjunneil.schedule.service.eleme;

import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import com.wangjunneil.schedule.utility.URL;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeApiService {
    private static Logger log = Logger.getLogger(EleMeApiService.class.getName());
    public static final String KEY = "9938016757";
    public static final String SERVICEC = "b01973894f15afb94745a49c8411a7e7c425cea5";
    public static final String PATH = "http://v2.openapi.ele.me/new_food_category/";


    public String getSystemUrl(String pathUrl, Object obj) throws ElemaException {
        try {
            SysParams sysParams = new SysParams();
            sysParams.setConsumer_key(KEY);
            Map<String, String> map = StringUtil.getMap(sysParams);
            if (obj != null) {
                Map<String, String> lmap = StringUtil.getMap(obj);
                map.putAll(lmap);
            }
            System.out.println(map.toString());
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
    public String getNewOrder(OrderRequest obj) throws ElemaException{
        String url = getSystemUrl(URL.URL_ELENE_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        return HttpUtil.elmGet(requstUrl);
    }

    /**
     * 拉取新订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String pullNewOrder(OrderPushRequest obj) throws ElemaException{
        String url = getSystemUrl(URL.URL_ELEME_PULL_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
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
    public String startBusiness(RestaurantRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_SHOP_ON, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 门店歇业
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String endBusiness(RestaurantRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_SHOP_ON, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
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
        return HttpUtil.elmGet(requstUrl);
    }

    /**
     * 取消订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String cancelorder(OrderRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_CANCEL_ORDER, obj.getEleme_order_id());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
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

    /**
     * 添加分类接口
     * @throws ElemaException
     */
    public String addCategory(FoodsCategoryRequest obj) throws ElemaException {
        String url = getSystemUrl(PATH, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     *获取商家信息
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String getShop(RestaurantRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_SHOP_INFO, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.elmGet(url);
    }

    /**
     * 获取所属餐厅
     * @throws ElemaException
     */
    public String getAffiliationShop() throws ElemaException {
        String url = getSystemUrl(URL.URL_ELEME_SHOP_ID, null);
        return HttpUtil.elmGet(url);
    }

    public static void main(String[] arg) throws ElemaException {
        FoodsCategoryRequest categoryRequest = new FoodsCategoryRequest();
        categoryRequest.setName("日日日");
        categoryRequest.setRestaurant_id("2063064");

        DisplayAttribute displayAttribute = new DisplayAttribute();
        ArrayList<Times> timeses = new ArrayList<>();
        Times times = new Times();
        times.setStart_time("06:06:06");
        times.setEnd_time("08:08:08");
        timeses.add(times);
        displayAttribute.setTimes(timeses);
        ArrayList<DisplayAttribute> displayAttributes = new ArrayList<>();
        displayAttributes.add(displayAttribute);
        categoryRequest.setDisplay_attribute(displayAttributes);

        EleMeApiService eleMeApiService = new EleMeApiService();
        System.out.println(eleMeApiService.addCategory(categoryRequest));
    }

}

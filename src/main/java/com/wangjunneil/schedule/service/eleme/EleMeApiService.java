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
    public static final String KEY = "9938016757";
    public static final String SERVICEC = "b01973894f15afb94745a49c8411a7e7c425cea5";
    public static final String RESTAURANTID = "2063064";


    public String getSystemUrl(String pathUrl, Object obj) throws ElemaException {
        try {
            SysParams sysParams = new SysParams();
            sysParams.setConsumer_key(KEY);
            Map<String, String> map = StringUtil.getMap(sysParams);
            if (obj != null) {
                Map<String, String> lmap = StringUtil.getMap(obj);
                map.putAll(lmap);
            }
            String sig = EleMeUtils.genSig(pathUrl, map, SERVICEC);
            sysParams.setSig(sig);
            return EleMeUtils.genUrl(pathUrl, sysParams);
        } catch (Exception e) {
            throw new ElemaException("签名计算失败", e);
        }
    }

    /**
     * 获取所属餐厅ID
     * @throws ElemaException
     */
    public String getAffiliationShop() throws ElemaException {
        String url = getSystemUrl(URL.URL_ELEME_RESTAURANT_ID, null);
        return HttpUtil.elmGet(url);
    }

    /**
     * 餐厅状态
     * @param obj 属性is_open ：1营业/0不营业
     * @return
     * @throws ElemaException
     */
    public String startBusiness(RestaurantRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_RESTAURANT_ON, RESTAURANTID);
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 添加食品接口
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String addFoods(OldFoodsRequest obj) throws ElemaException {
        String url = getSystemUrl(URL.URL_ELEME_ADD_FOODS, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 拉取新订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String pullNewOrder(OrderRequest obj) throws ElemaException{
        String url = getSystemUrl(URL.URL_ELEME_PULL_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        return HttpUtil.elmGet(requstUrl);
    }

    /**
     * 确定订单
     * @param obj
     * @return
     * @throws ElemaException
     */
    public String firmOrder(OrderRequest obj) throws ElemaException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_PULL_FIRM_ORDER, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.elmPut(url, StringUtil.getUrlParamsByObject(obj));
    }


    public static void main(String[] arg) throws ElemaException {
//        OldFoodsRequest oldFoodsRequest = new OldFoodsRequest();
//        oldFoodsRequest.setFood_category_id(18424637);
//        oldFoodsRequest.setName("测试商品");
//        oldFoodsRequest.setPrice(0.01f);
//        oldFoodsRequest.setDescription("测试商品");
//        oldFoodsRequest.setMax_stock(1000);
//        oldFoodsRequest.setStock(100);
        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setRestaurant_id(RESTAURANTID);
        orderRequest.setStatus(2);
        orderRequest.setEleme_order_id("101619479623393171");
        EleMeApiService eleMeApiService = new EleMeApiService();
        System.out.println(eleMeApiService.firmOrder(orderRequest));

    }

}

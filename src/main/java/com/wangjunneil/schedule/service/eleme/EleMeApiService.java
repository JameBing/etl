package com.wangjunneil.schedule.service.eleme;

import com.google.gson.Gson;
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

    public static final String KEY = "9938016757";
    public static final String SERVICEC = "b01973894f15afb94745a49c8411a7e7c425cea5";
    public static final String RESTAURANTID = "2063064";

    public String getSystemUrl(String pathUrl, Object obj) throws ScheduleException {
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
        } catch (Exception ex) {
            throw new ScheduleException(Constants.PLATFORM_WAIMAI_ELEME, ex.getClass().getName(), "签名计算失败", pathUrl+"\r\n"+new Gson().toJson(obj), new Throwable().getStackTrace());
        }
    }

    /**
     * 获取所属餐厅ID
     * @throws ScheduleException
     */
    public String getAffiliationShop() throws ScheduleException {
        String url = getSystemUrl(URL.URL_ELEME_RESTAURANT_ID, null);
        return HttpUtil.get2(url);
    }

    /**
     * 餐厅状态(新)
     * @param obj 属性is_open ：1营业/0不营业
     * @return
     * @throws ScheduleException
     */
    public String setRestaurantStatus(RestaurantRequest obj) throws ScheduleException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_RESTAURANT_ON, RESTAURANTID);
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 添加食品接口
     * @param obj
     * @return
     * @throws ScheduleException
     */
    public String addFoods(OldFoodsRequest obj) throws ScheduleException {
        String url = getSystemUrl(URL.URL_ELEME_ADD_FOODS, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 拉取新订单
     * @param obj
     * @return
     * @throws ScheduleException
     */
    public String pullNewOrder(OrderRequest obj) throws ScheduleException{
        String url = getSystemUrl(URL.URL_ELEME_PULL_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByObject(obj));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 食品上下架(旧) 食品上下架需先调用获取餐厅食物分类ID、获取餐厅食物ID 接口
     * @param obj 属性stock ：大于0上架/0下架
     * @return
     * @throws ElemaException
     */
    public String Upordownframe(OldFoodsRequest obj)throws ScheduleException{
        String pathURL = MessageFormat.format(URL.URL_ELEME_UPORDOWNFRAME_FOODS, obj.getFood_id().toString());
        obj.setFood_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 获取餐厅食物分类ID food_category_id
     * @return
     * @throws ElemaException
     */

    public String FoodsClassify()throws ScheduleException{
        String pathURL = MessageFormat.format(URL.URL_ELEME_CLASSIFY_FOODS, RESTAURANTID);
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 获取餐厅食物ID food_id
     * @return
     * @throws ElemaException
     */

    public String GetFoodsId(OldFoodsRequest obj)throws ScheduleException{
        String pathURL = MessageFormat.format(URL.URL_ELEME_GETFOODSID, obj.getFood_category_id().toString());
        obj.setFood_category_id("");
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 查询订单详情
     * @return
     * @throws ElemaException
     */

    public  String OrderDetail(OrderRequest obj)throws ScheduleException{
        String pathURL = MessageFormat.format(URL.URL_ELEME_ORDER_DETAIL, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 订单状态变更  (确认取消订单)
     * 修改订单状态订单
     * @param obj
     * @return
     * @throws ScheduleException
     */
    public  String StateChange(OrderRequest obj) throws ScheduleException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_STATE_CHANGE_ORDER, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByObject(obj));
    }

    public String upOrderStatus(OrderRequest obj) throws ScheduleException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_ORDER_STATUS, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByObject(obj));
    }

    /**
     * 查询餐厅菜单
     * @return
     * @throws ElemaException
     */
    public String restaurantmenu() throws ScheduleException {
        String pathURL = MessageFormat.format(URL.URL_ELEME_RESTAURANT_MENU, RESTAURANTID);
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }
}

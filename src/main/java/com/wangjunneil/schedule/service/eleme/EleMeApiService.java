package com.wangjunneil.schedule.service.eleme;

import com.google.gson.Gson;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.Map;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeApiService {
    @Autowired
    private SysFacadeService sysFacadeService;

    /**
     * 对url进行签名
     * @param pathUrl
     * @param obj 参与签名参数
     * @return
     */
    public String getSystemUrl(String pathUrl, Object obj) throws ScheduleException,ElemeException {
        try {
            Cfg elemeCfg = sysFacadeService.findElemeCfg();
            SysParams sysParams = new SysParams();
            sysParams.setConsumer_key(elemeCfg.getAppKey());
            Map<String, String> map = StringUtil.getMap(sysParams);
            if (obj != null) {
                Map<String, String> lmap = StringUtil.getMap(obj);
                map.putAll(lmap);
            }
            String sig = EleMeUtils.genSig(pathUrl, map, elemeCfg.getAppSecret());
            sysParams.setSig(sig);
            return EleMeUtils.genUrl(pathUrl, sysParams);
        } catch (Exception ex) {
            throw new ElemeException( ex.getClass().getName(), "签名计算失败", pathUrl+"\r\n"+new Gson().toJson(obj), new Throwable().getStackTrace());
        }
    }

    /**
     * 获取所属餐厅ID
     */
        public String getAffiliationShop() throws ScheduleException,ElemeException {
            String url = getSystemUrl(Constants.URL_ELEME_RESTAURANT_ID, null);
        return HttpUtil.get2(url);
    }

    /**
     * 餐厅状态
     * @param obj 属性is_open ：1营业/0不营业
     * @return
     */
    public String setRestaurantStatus(RestaurantRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
           String pathURL = MessageFormat.format(Constants.URL_ELEME_RESTAURANT_ON, obj.getRestaurant_id().toString());
           obj.setRestaurant_id("");
           String url = getSystemUrl(pathURL, obj);
           return HttpUtil.put(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 添加食品接口
     * @param obj
     * @return
     */
    public String addFoods(OldFoodsRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url = getSystemUrl(Constants.URL_ELEME_ADD_FOODS, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 拉取新订单
     * @param obj
     * @return
     */
    public String pullNewOrder(OrderRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException{
        String url = getSystemUrl(Constants.URL_ELEME_PULL_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 获取新订单
     * @param obj
     * @return
     */
    public String findNewOrder(OrderRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException{
        String url = getSystemUrl(Constants.URL_ELENE_NEW_ORDER, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 更新食物接口
     * @param obj
     * @return
     */
    public String upFoods(OldFoodsRequest obj)throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException{
        String pathURL = MessageFormat.format(Constants.URL_ELEME_UPORDOWNFRAME_FOODS, obj.getFood_id().toString());
        obj.setFood_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 获取餐厅食物分类ID food_category_id
     * @return
     */
    public String foodsClassify(String restaurantId)throws ScheduleException,ElemeException{
        String pathURL = MessageFormat.format(Constants.URL_ELEME_RESTAURANT_FOODCATEGORY, restaurantId);
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 查询食物列表
    * @return
    */
    public String getFoodsList(String categoryId)throws ScheduleException,ElemeException{
        String pathURL = MessageFormat.format(Constants.URL_ELEME_GETFOODSID, categoryId);
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 查询订单详情
     * @return
     */
    public String orderDetail(OrderRequest obj)throws ScheduleException,ElemeException{
        String pathURL = MessageFormat.format(Constants.URL_ELEME_ORDER_DETAIL, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.get2(url);
    }

    /**
     * 订单状态变更  (确认取消订单)
     * @param obj
     * @return
     */
    public String upOrderStatus(OrderRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_ORDER_STATUS, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 查询餐厅菜单
     * no param
     * @return
     */
    public String restaurantMenu(RestaurantRequest obj) throws ScheduleException,ElemeException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_RESTAURANT_MENU, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.get2(url);
    }

    /**
     * 通过第三方id获取平台食物id
     * @param obj
     * @return
     */
    public String getFoodId(OldFoodsRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url = getSystemUrl(Constants.URL_ELEME_TP_FOOD_ID, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     *批量上下架
     * @param obj 属性stock ：大于0上架/0下架
     * @return
     */
    public String upBatchFrame(OldFoodsRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url = getSystemUrl(Constants.URL_ELEME_UPORDOWNFRAME_FOODS_LIST, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 批量删除食物
     * @param obj
     * @return
     */
    public String delectAllFoods(OldFoodsRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url = getSystemUrl(Constants.URL_ELEME_DELETE_FOODS_LIST, obj);
        return HttpUtil.delete(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 配送信息
     * @param obj
     * @return
     */
    public String getDeliveryInfo(OrderRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        String url = getSystemUrl(Constants.URL_ELEME_DELIVERY, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 同意退单
     * @param orderid
     * @return
     */
    public String agreeRefund(String orderid) throws ElemeException, ScheduleException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_AGREE_REFUND, orderid);
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.post(url, "");
    }

    /**
     * 不同意退单
     * @param obj
     * @return
     */
    public String disAgreeRefund(OrderRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_DISAGREE_REFUND, obj.getEleme_order_id().toString());
        obj.setEleme_order_id("");
        String url = getSystemUrl(pathURL, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 绑定商户ID
     * @param obj
     * @return
     */
    public String bingDing(RestaurantRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        String url = getSystemUrl(Constants.URL_ELEME_BINGDING_RESTAURANTID, obj);
        return HttpUtil.post(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 重新绑定商户ID
     * @param obj
     * @return
     */
    public String againBingDing(RestaurantRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        String url = getSystemUrl(Constants.URL_ELEME_BINGDING_RESTAURANTID, obj);
        return HttpUtil.put(url, StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
    }

    /**
     * 通过第三方ID查询餐厅ID
     * @param obj
     * @return
     */
    public String getRestaurantId(RestaurantRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException, IOException {
        String url = getSystemUrl(Constants.URL_ELEME_BINGDING_RESTAURANTID, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 获取餐厅信息
     * @return
     */
    public String getRestaurantInfo(RestaurantRequest obj) throws ElemeException, ScheduleException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_RESTAURANT_INFO, obj.getRestaurant_id().toString());
        String url = getSystemUrl(pathURL, null);
        return HttpUtil.get2(url);
    }

    /**
     * 获取餐厅状态
     * @param obj
     * @return
     */
    public String getRestaurantStatus(RestaurantRequest obj) throws ElemeException, ScheduleException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String url = getSystemUrl(Constants.URL_ELEME_RESTAURANT_STATUS, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }
}

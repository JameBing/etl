package com.wangjunneil.schedule.service.eleme;

import com.google.gson.Gson;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.eleme.*;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.service.EleMeFacadeService;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import eleme.openapi.sdk.api.entity.order.OOrder;
import eleme.openapi.sdk.api.entity.product.OCategory;
import eleme.openapi.sdk.api.entity.product.OItem;
import eleme.openapi.sdk.api.entity.product.OItemIdWithSpecIds;
import eleme.openapi.sdk.api.entity.shop.OShop;
import eleme.openapi.sdk.api.entity.user.OAuthorizedShop;
import eleme.openapi.sdk.api.entity.user.OUser;
import eleme.openapi.sdk.api.enumeration.order.OInvalidateType;
import eleme.openapi.sdk.api.enumeration.shop.OShopProperty;
import eleme.openapi.sdk.api.exception.ServiceException;
import eleme.openapi.sdk.api.service.OrderService;
import eleme.openapi.sdk.api.service.ProductService;
import eleme.openapi.sdk.api.service.ShopService;
import eleme.openapi.sdk.api.service.UserService;
import eleme.openapi.sdk.config.Config;
import eleme.openapi.sdk.oauth.OAuthClient;
import eleme.openapi.sdk.oauth.response.Token;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.*;

/**
 * Created by admin on 2016/11/17.
 */
@Service
public class EleMeApiService {

    @Autowired
    private SysFacadeService sysFacadeService;
    @Autowired
    private EleMeInnerService eleMeInnerService;

    private static Logger logInfo = Logger.getLogger(EleMeApiService.class.getName());

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
    public OShop setRestaurantStatus(RestaurantRequest obj) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(obj.getRestaurant_id());
        ShopService shopService = new ShopService(getSysConfig(), getSysToken(shopEle==null?obj.getRestaurant_id():shopEle.getShopId()));
        Map<OShopProperty,Object> properties = new HashMap<OShopProperty,Object>();
        if("1".equals(obj.getIs_open())){
            properties.put(OShopProperty.isOpen,1);
        }else {
            properties.put(OShopProperty.isOpen,0);
        }
        OShop oShop = shopService.updateShop(Long.parseLong(shopEle==null?obj.getRestaurant_id():shopEle.getShopId()), properties);
        return oShop;
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
        return null;
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
    public OOrder orderDetail(String orderId,String shopId)throws ScheduleException,ElemeException,Exception{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        OrderService orderService = new OrderService(getSysConfig(), getSysToken(shopEle==null?shopId:shopEle.getShopId()));
        OOrder oOrder = orderService.getOrder(orderId);
        return oOrder;
    }

    /**
     * 订单确认订单  (确认订单)
     * @param orderId
     * @return
     */
    public void configOrderStatus(String orderId,String shopId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        OrderService orderService = new OrderService(getSysConfig(), getSysToken(shopEle==null?shopId:shopEle.getShopId()));
        orderService.confirmOrderLite(orderId);
    }

    /**
     * 订单取消订单
     * @param orderId
     * @return
     */
    public void cancelOrderStatus(String orderId,String shopId) throws ScheduleException,ElemeException,ServiceException {
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        OrderService orderService = new OrderService(getSysConfig(), getSysToken(shopEle==null?shopId:shopEle.getShopId()));
        orderService.cancelOrderLite(orderId, OInvalidateType.fakeOrder, "无法取得联系");
    }

    /**
     * 查询餐厅菜单
     * no param
     * @return
     */
    public String restaurantMenu(RestaurantRequest obj) throws ScheduleException,ElemeException ,Exception{
        String pathURL = MessageFormat.format(Constants.URL_ELEME_RESTAURANT_MENU, obj.getRestaurant_id());
        obj.setRestaurant_id("");
        String url = getSystemUrl(pathURL, obj);
        String requstUrl = MessageFormat.format(url + "&{0}", StringUtil.getUrlParamsByMap(StringUtil.getMap(obj)));
        return HttpUtil.get2(requstUrl);
    }

    /**
     * 通过门店id获取商品分类Id
     * @param sellerId
     * @return
     */
    public List<OCategory> getCategoryId(String sellerId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(sellerId);
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle==null?sellerId:shopEle.getShopId()));
        return  productService.getShopCategories(Long.parseLong(shopEle==null?sellerId:shopEle.getShopId()));
    }

    /**
     * 通过商品分类id获取商品
     * @param categoryId
     * @return
     */
    public Map<Long, OItem> getCategProducts(Long categoryId,String shopId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle ==null?shopId:shopEle.getShopId()));
        return productService.getItemsByCategoryId(categoryId);
    }
    /**
     * 通过商家商品id获取商品信息
     * @param shopId  dishId
     * @return
     */
    public OItem getProductByExtendCode(Long shopId,String dishId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId.toString());
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle ==null?shopId.toString():shopEle.getShopId()));
        return productService.getItemByShopIdAndExtendCode(shopEle ==null?shopId:Long.parseLong(shopEle.getShopId()), dishId);
    }

    /**
     * 通过商品id获取商品信息
     * @param dishId
     * @return
     */
    public OItem getProductById(String dishId,String shopId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle ==null?shopId.toString():shopEle.getShopId()));
        return productService.getItem(Long.parseLong(dishId));
    }


    /**
     *批量上架
     * @param specIds
     * @return
     */
    public void upBatchFrame(List<OItemIdWithSpecIds> specIds,String shopId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle ==null?shopId.toString():shopEle.getShopId()));
        productService.batchOnShelf(specIds);
    }

    /**
     *批量下架
     * @param specIds
     * @return
     */
    public void downBatchFrame(List<OItemIdWithSpecIds> specIds ,String shopId) throws ScheduleException,ElemeException,ServiceException{
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(shopId);
        ProductService productService = new ProductService(getSysConfig(), getSysToken(shopEle ==null?shopId:shopEle.getShopId()));
        productService.batchOffShelf(specIds);
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
     * 删除食物
     * @param obj
     * @return
     */
    public String delecFoods(OldFoodsRequest obj) throws ScheduleException,ElemeException,IOException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String pathURL = MessageFormat.format(Constants.URL_ELEME_DELETE_FOODS, obj.getFood_id().toString());
        obj.setFood_id("");
        String url = getSystemUrl(pathURL, obj);
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
    public OShop getRestaurantInfo(RestaurantRequest obj) throws ElemeException, ScheduleException,ServiceException {
        judgeTokenExpire(); //判断token是否失效
        ShopEle shopEle = eleMeInnerService.getSellerId(obj.getRestaurant_id());
        ShopService shopService = new ShopService(getSysConfig(),getSysToken(shopEle==null?obj.getRestaurant_id():shopEle.getShopId()));
        OShop shop = shopService.getShop(Long.parseLong(shopEle==null?obj.getRestaurant_id():shopEle.getShopId()));
        return shop;
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

    //系统参数config
    private Config getSysConfig(){
        return new Config(false,Constants.ELEME_APP_KEY,Constants.ELEME_APP_SECRET);
    }

    //系统参数token
    private Token getSysToken(String shopId)throws ElemeException, ScheduleException{
        Token token = new Token();
        AuthToken authToken = eleMeInnerService.getAccessToken(shopId);
        token.setAccessToken(authToken.getToken());
        token.setExpires(authToken.getExpires_in());
        token.setRefreshToken(authToken.getRefresh_token());
        token.setTokenType(authToken.getToken_type());
        return token;
    }

    /**
     * 获取token信息
     * @param authCode
     * @param state
     * @return
     */
    public Token getAuthToken (String authCode,String state){
        //配置类
        Config config = new Config(false,Constants.ELEME_APP_KEY,Constants.ELEME_APP_SECRET);
        //授权类
        OAuthClient client = new OAuthClient(config);
        //获取授权URl
        String authUrl = client.getAuthUrl(Constants.ELEME_CALLBACK_URL,"all",state);
        //获取token
        Token token = client.getTokenByCode(authCode,Constants.ELEME_CALLBACK_URL);
        return token;
    }

    /**
     * 获取授权门店列表
     * @param token
     */
    public Map<String,Object> getAuthShops (Token token) throws ServiceException{
        UserService userService = new UserService(getSysConfig(), token);
        OUser oUser = userService.getUser();
        String userId = String.valueOf(oUser.getUserId());
        List<OAuthorizedShop>  shopList = oUser.getAuthorizedShops();
        Map<String,Object> rtnMap = new HashMap<>();
        rtnMap.put("userId",userId);
        rtnMap.put("shops",shopList);
        return rtnMap;
    }
    /**
     * 如果token过期refresh token
     */
    public void refreshToken()throws ScheduleException{
        //实例化一个配置类
        Config config = new Config(false,Constants.ELEME_APP_KEY,Constants.ELEME_APP_SECRET);
        //使用config对象，实例化一个授权类
        OAuthClient client = new OAuthClient(config);
        AuthToken auth = eleMeInnerService.getToken();
        if(auth!=null){
            //根据refreshToken,刷新token
            Token token = client.getTokenByRefreshToken(auth.getRefresh_token());
            auth.setToken(token.getAccessToken());
            auth.setToken_type(token.getTokenType());
            auth.setExpires_in(token.getExpires());
            auth.setRefresh_token(token.getRefreshToken());
            eleMeInnerService.addOrUpdateToken(auth,null);
        }
    }

    //判断token是否失效
    private void judgeTokenExpire() throws ScheduleException{
        AuthToken authToken = eleMeInnerService.getToken();
        Date nowDate =  new Date();
        //token失效 刷选token
        if(nowDate.getTime()>authToken.getExpire_Date().getTime()){
            refreshToken();
        }
    }
}

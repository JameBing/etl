package com.wangjunneil.schedule.utility;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
public final class URL {
    /**
     * 京东到家接口URL
     */
    //京东到家商品编码批量修改门店商品上下架
    public static final String  URL_JDHOME_STORE_ON = "https://openo2o.jd.com/mockapi/stock/updateVendibility";

    //京东到家新增商家店内分类信息
    public static final String  URL_ADD_SHOP_CATEGORY = "https://openo2o.jd.com/mockapi/pms/addShopCategory";

    //京东到家修改商家店内分类信息
    public static final String URL_UPDATE_SHOP_CATEGORY = "https://openo2o.jd.com/mockapi/pms/updateShopCategory";

    //京东到家删除商家店内分类信息
    public static final String URL_DELETE_SHOP_CATEGORY = "https://openo2o.jd.com/mockapi/pms/delShopCategory";

    //京东到家新增推送订单
    public static final String URL_NEW_ORDER =  "https://openo2o.jd.com/mockapi/order/es/query";

    //京东到家商家确认接单接口
    public static final String URL_ORDER_ACCEPT_OPERATE ="https://openo2o.jd.com/mockapi/ocs/orderAcceptOperate";

    //京东到家查询商家商品信息列表
    public static final String URL_QUERY_SKU_INFO ="https://openo2o.jd.com/mockapi/pms/querySkuInfos";

    //京东到家根据查询条件分页获取门店基本信息
    public static final String STORE_INFO_PAGEBEAN ="https://openo2o.jd.com/mockapi/djstore/getStoreInfoPageBean";


    /*************************************************************************************************************************/
    /**
     * 饿了吗接口url
     */
    //获取所属餐厅Id
    public static final String URL_ELEME_RESTAURANT_ID = "http://v2.openapi.ele.me/restaurant/own/";
    //饿了么商家开关店
    public static final String URL_ELEME_RESTAURANT_ON = "http://v2.openapi.ele.me/restaurant/{0}/business_status/";
    //饿了么查询新订单
    public static final String URL_ELENE_NEW_ORDER = "http://v2.openapi.ele.me/order/new/";
    //饿了么拉取新订单
    public static final String URL_ELEME_PULL_NEW_ORDER = "http://v2.openapi.ele.me/order/pull/new/";
    //饿了么修改订单状态
    public static final String URL_ELEME_ORDER_STATUS = "http://v2.openapi.ele.me/order/{0}/status/";
    //添加食物
    public static final String URL_ELEME_ADD_FOODS = "http://v2.openapi.ele.me/food/";
    //食品上下架、更新食物
    public static final String URL_ELEME_UPORDOWNFRAME_FOODS = "http://v2.openapi.ele.me/food/{0}/";
    //获取餐厅食物分类
    public static final String URL_ELEME_CLASSIFY_FOODS = "http://v2.openapi.ele.me/restaurant/{0}/food_categories/";
    //获取餐厅食物ID
    public static final String URL_ELEME_GETFOODSID = "http://v2.openapi.ele.me/food_category/{0}/foods/";
    //查询订单详情
    public static final String URL_ELEME_ORDER_DETAIL = "http://v2.openapi.ele.me/order/{0}/";
    //查询餐厅菜单
    public static final String URL_ELEME_RESTAURANT_MENU = "http://v2.openapi.ele.me/restaurant/{0}/menu/";
    //通过地单方id获取餐厅食物ID
    public static final String URL_ELEME_TP_FOOD_ID = "http://v2.openapi.ele.me/foods/tp_food_id/";




}

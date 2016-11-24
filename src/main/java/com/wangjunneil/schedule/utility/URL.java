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
    public static final String  URL_JDHOME_STORE_ON = "https://openo2o.jd.com/djapi/stock/updateVendibility";

    //京东到家新增商家店内分类信息
    public static final String  URL_ADD_SHOP_CATEGORY = "https://openo2o.jd.com/djapi/pms/addShopCategory";

    //京东到家修改商家店内分类信息
    public static final String URL_UPDATE_SHOP_CATEGORY = "https://openo2o.jd.com/djapi/pms/updateShopCategory";

    //京东到家删除商家店内分类信息
    public static final String URL_DELETE_SHOP_CATEGORY = "https://openo2o.jd.com/djapi/pms/delShopCategory";

    //京东到家新增推送订单
    public static final String URL_NEW_ORDER =  "https://openo2o.jd.com/djapi/order/es/query";

    //京东到家商家确认接单接口
    public static final String URL_ORDER_ACCEPT_OPERATE ="https://openo2o.jd.com/djapi/ocs/orderAcceptOperate";

    /*************************************************************************************************************************/
    /**
     * 饿了吗接口url
     */
    //饿了么商家开关电
    public static final String URL_ELEME_SHOP_ON = "http://v2.openapi.ele.me/restaurant/{0}/business_status/";
    //饿了么查询新订单
    public static final String URL_ELENE_NEW_ORDER = "http://v2.openapi.ele.me/order/new/";
    //饿了么拉取新订单
    public static final String URL_ELEME_PULL_NEW_ORDER = "http://v2.openapi.ele.me/order/pull/new/";
    //获取订单详情
    public static final String URL_ELEME_GET_ORDER = "http://v2.openapi.ele.me/order/{0}/";
    //取消订单
    public static final String URL_ELEME_CANCEL_ORDER = "http://v2.openapi.ele.me/order/{0}/status/";
    //添加食物
    public static final String URL_ELEME_ADD_FOODS = "http://v2.openapi.ele.me/food/";


}

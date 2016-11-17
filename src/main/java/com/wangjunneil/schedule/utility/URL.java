package com.wangjunneil.schedule.utility;

/**
 * @author yangyongbing
 * @since2016/11/15.
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

}

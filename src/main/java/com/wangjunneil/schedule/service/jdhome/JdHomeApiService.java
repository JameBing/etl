package com.wangjunneil.schedule.service.jdhome;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.entity.baidu.Shop;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.QueryStockRequest;
import com.wangjunneil.schedule.entity.jdhome.ShopCategory;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import com.wangjunneil.schedule.utility.URL;
import o2o.openplatform.sdk.util.SignUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Service
public class JdHomeApiService {

    private static Logger log = Logger.getLogger(JdHomeApiService.class.getName());

    private String token = "585e8e9c-63da-43b4-8360-4fd38f778859";

    private String app_key = "811f96f894614a1bbcbff480330e6eb3";

    private String appSecret = "d4c20ee551eb4fb19795da5a83102b24";

    private String timeStamp = "";

    private String sign = "";

    private String format = "json";

    private String v = "1.0";

    private String jd_param_json = "";


    //批量修改商品上架
    public String updateAllStockOn(List<QueryStockRequest> stockRequests)throws Exception{
        Map<String,Object> param = getSysMap(); //系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("listBaseStockCenterRequest",stockRequests);
        jd_param_json = jsonObject.toJSONString();
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param));
    }

    //新增商品分类
    public String addShopCategory(ShopCategory shopCategory)throws Exception{
        Map<String,Object> param = getSysMap();//系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("pid",shopCategory.getPid());
        jsonObject.put("shopCategoryName",shopCategory.getShopCategoryName());
        jsonObject.put("shopCategoryLevel",shopCategory.getShopCategoryLevel());
        jsonObject.put("sort",shopCategory.getSort());
        jsonObject.put("createPin",shopCategory.getCreatePin());
        jd_param_json = jsonObject.toJSONString();
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_ADD_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    //修改商品分类
    public String updateShopCategory(ShopCategory shopCategory) throws Exception{
        Map<String,Object> param = getSysMap();//系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("id",shopCategory.getId());
        jsonObject.put("shopCategoryName",shopCategory.getShopCategoryName());
        jd_param_json = jsonObject.toJSONString();
        param.put("jd_param_json", jd_param_json);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_UPDATE_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    //删除商品分类
    public String deleteShopCategory(ShopCategory shopCategory) throws Exception{
        Map<String,Object> param = getSysMap();//系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("id",shopCategory.getId());
        jd_param_json = jsonObject.toJSONString();
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_DELETE_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    public String newOrder(String billId,String statusId,String timestamp) throws Exception{
        Map<String,Object> param = getSysMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",billId);
        jsonObject.put("orderStatus",statusId);
        jsonObject.put("orderPurchaseTime_begin",DateTimeUtil.formatDateString(timestamp,"yyyy-MM-dd HH:mm:ss"));
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:"+StringUtil.getUrlParamsByMap(param)+"======");
        return HttpUtil.post(URL.URL_NEW_ORDER,StringUtil.getUrlParamsByMap(param));
    }

    //商家确认/取消接单接口
    public String orderAcceptOperate(OrderAcceptOperate acceptOperate)throws Exception{
        Map<String,Object> param = getSysMap();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",acceptOperate.getOrderId());
        jsonObject.put("isAgreed", acceptOperate.getIsAgreed());
        jsonObject.put("operator", acceptOperate.getOperator());
        param.put("jd_param_json",jsonObject);
        try {
            sign = SignUtils.getSign(param,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:"+StringUtil.getUrlParamsByMap(param)+"======");
        return HttpUtil.post(URL.URL_ORDER_ACCEPT_OPERATE,StringUtil.getUrlParamsByMap(param));

    }


    //获取系统参数map对象
    private Map<String,Object> getSysMap(){
        Map<String ,Object> sysParam = new HashMap<String,Object>();
        sysParam.put("token",token);
        sysParam.put("app_key",app_key);
        sysParam.put("format",format);
        sysParam.put("v",v);
        sysParam.put("timestamp",DateTimeUtil.dateFormat(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return  sysParam ;
    }
}

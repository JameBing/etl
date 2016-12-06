package com.wangjunneil.schedule.service.jdhome;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.entity.jdhome.*;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import com.wangjunneil.schedule.utility.URL;
import o2o.openplatform.sdk.util.SignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private String appSecret="";

    private String sign="";

    private String jd_param_json="";

    @Autowired
    private JdHomeInnerService jdHomeInnerService;

    //批量修改商品上架
    public String updateAllStockOn(List<QueryStockRequest> stockRequests,String shopId)throws Exception{
        SignParams signParams = getSignParams(shopId);//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        String rtnStr = "";
        //应用参数是否有值
        if(stockRequests !=null && stockRequests.size()>0){
            int page = 1;
            int pageSize = Constants.STOCK_REQUEST_COUNT;// 配置参数
            int begin = 0;
            int end = pageSize;
            for (int i = 0; i <= stockRequests.size() / pageSize; i++) {
                List<QueryStockRequest> list = StringUtil.setListPageDate(begin, end, stockRequests);
                if (list != null) {
                    jsonObject.put("listBaseStockCenterRequest",list);
                    signParams.setJd_param_json(jsonObject.toJSONString());
                    param.put("jd_param_json",jsonObject);
                    try {
                        sign = SignUtils.getSign(signParams,appSecret);
                        param.put("sign",sign);
                    }catch (Exception e){
                        throw new JdHomeException("签名失败",e);
                    }
                    log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
                    rtnStr = rtnStr + HttpUtil.post(URL.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param))+",";
                    begin = pageSize * page;
                    end = pageSize * (page + 1);
                    page++;
                }
            }
        }else{
            try {
                sign = SignUtils.getSign(signParams,appSecret);
                param.put("sign",sign);
            }catch (Exception e){
                throw new JdHomeException("签名失败",e);
            }
            log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
            rtnStr = HttpUtil.post(URL.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param));
        }

        if(rtnStr.length()>0){
            return rtnStr.substring(0,rtnStr.length()-1);
        }
        return rtnStr;
    }

    //查询商家商品信息
    public String querySkuInfos(String upcCode,String shopId)throws Exception{
        SignParams signParams = getSignParams(shopId);
        Map<String,Object> param = getSysMap(signParams);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("upcCode",upcCode);
        jd_param_json = jsonObject.toJSONString();
        signParams.setJd_param_json(jd_param_json);
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_QUERY_SKU_INFO,StringUtil.getUrlParamsByMap(param));
    }

    //根据查询条件分页获取门店基本信息
    public String getStoreInfoPageBean(String shopId) throws Exception{
        SignParams signParams = getSignParams(shopId);
        Map<String,Object> param = getSysMap(signParams);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outSystemId",shopId);
        jsonObject.put("currentPage",1);
        jd_param_json = jsonObject.toJSONString();
        signParams.setJd_param_json(jd_param_json);
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.STORE_INFO_PAGEBEAN,StringUtil.getUrlParamsByMap(param));
    }

    //新增商品分类
    public String addShopCategory(shopCategory shopCategory)throws Exception{
        SignParams signParams = getSignParams(shopCategory.getShopId());//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("pid",shopCategory.getPid());
        jsonObject.put("shopCategoryName",shopCategory.getShopCategoryName());
        jsonObject.put("shopCategoryLevel",shopCategory.getShopCategoryLevel());
        jsonObject.put("sort",shopCategory.getSort());
        jsonObject.put("createPin",shopCategory.getCreatePin());
        jd_param_json = jsonObject.toJSONString();
        signParams.setJd_param_json(jd_param_json);
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_ADD_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    //修改商品分类
    public String updateShopCategory(shopCategory shopCategory) throws Exception{
        SignParams signParams = getSignParams(shopCategory.getShopId());//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("id",shopCategory.getId());
        jsonObject.put("shopCategoryName",shopCategory.getShopCategoryName());
        jd_param_json = jsonObject.toJSONString();
        signParams.setJd_param_json(jd_param_json);
        param.put("jd_param_json", jd_param_json);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_UPDATE_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    //删除商品分类
    public String deleteShopCategory(shopCategory shopCategory) throws Exception{
        SignParams signParams = getSignParams(shopCategory.getShopId());//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();//应用参数
        jsonObject.put("id",shopCategory.getId());
        jd_param_json = jsonObject.toJSONString();
        signParams.setJd_param_json(jd_param_json);
        param.put("jd_param_json",jd_param_json);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:" + StringUtil.getUrlParamsByMap(param) + "======");
        return HttpUtil.post(URL.URL_DELETE_SHOP_CATEGORY,StringUtil.getUrlParamsByMap(param));
    }

    //新增订单
    public String newOrder(String billId,String statusId,String timestamp,String shopId) throws Exception{
        SignParams signParams = getSignParams(shopId);//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",billId);
        jsonObject.put("orderStatus",statusId);
        signParams.setJd_param_json(jsonObject.toJSONString());
        param.put("jd_param_json",jsonObject);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:"+StringUtil.getUrlParamsByMap(param)+"======");
        return HttpUtil.post(URL.URL_NEW_ORDER,StringUtil.getUrlParamsByMap(param));
    }

    //商家确认/取消接单接口
    public String orderAcceptOperate(OrderAcceptOperate acceptOperate)throws Exception{
        SignParams signParams = getSignParams(acceptOperate.getShopId());//签名参数
        Map<String,Object> param = getSysMap(signParams); //系统参数
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("orderId",acceptOperate.getOrderId());
        jsonObject.put("isAgreed", acceptOperate.getIsAgreed());
        jsonObject.put("operator", acceptOperate.getOperator());
        signParams.setJd_param_json(jsonObject.toJSONString());
        param.put("jd_param_json",jsonObject);
        try {
            sign = SignUtils.getSign(signParams,appSecret);
            param.put("sign",sign);
        }catch (Exception e){
            throw new JdHomeException("签名失败",e);
        }
        log.info("======Params:"+StringUtil.getUrlParamsByMap(param)+"======");
        return HttpUtil.post(URL.URL_ORDER_ACCEPT_OPERATE,StringUtil.getUrlParamsByMap(param));
    }


    //获取系统参数map对象
    private Map<String,Object> getSysMap(SignParams signParams){
        Map<String ,Object> sysParam = new HashMap<String,Object>();
        sysParam.put("token",signParams.getToken());
        sysParam.put("app_key",signParams.getApp_key());
        sysParam.put("format",signParams.getFormat());
        sysParam.put("v",signParams.getV());
        sysParam.put("timestamp",signParams.getTimestamp());
        return  sysParam ;
    }

    //根据门店编号获取签名参数
    private SignParams getSignParams(String shopId){
        JdHomeAccessToken jdHomeAccessToken = jdHomeInnerService.getAccessToken(shopId);
        SignParams signParam = new SignParams();
        if(jdHomeAccessToken == null){
            return signParam;
        }
        appSecret = jdHomeAccessToken.getAppSecret();
        signParam.setToken(jdHomeAccessToken.getToken());
        signParam.setApp_key(jdHomeAccessToken.getAppKey());
        return  signParam ;
    }
}

package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.entity.baidu.Order;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.entity.baidu.SysParamsSerializer;
import com.wangjunneil.schedule.entity.common.ParsFormPosSerializer;
import com.wangjunneil.schedule.entity.common.ParsFromPos;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.common.ParsFromPosInnerSerializer;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class WMFacadeService {

    private static Logger log = Logger.getLogger(WMFacadeService.class.getName());



    @Autowired
    private BaiDuFacadeService baiDuFacadeService;

    @Autowired
    private JdHomeFacadeService jdHomeFacadeService;

    @Autowired
    private EleMeFacadeService eleMeFacadeService;

    @Autowired
    private MeiTuanFacadeService meiTuanFacadeService;


    @Autowired
    private SysFacadeService sysFacadeService;

    //回调地址入口处理方法
    public String appReceiveCallBack(Map<String,String[]> stringMap,String platform){
        String result = "";
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:

                break;
            case Constants.PLATFORM_WAIMAI_JDHOME:

                break;
            case Constants.PLATFORM_WAIMAI_MEITUAN:
                switch (stringMap.get("push_action")[0]){
                    case "1": //新订单
                        eleMeFacadeService.getNewOrder(stringMap.get("eleme_order_ids")[0]);
                        break;
                    case "2": //订单状态变更
                        eleMeFacadeService.orderChange(stringMap.get("eleme_order_id")[0],stringMap.get("new_status")[0]);
                        break;
                    case "3": //退单状态推送
                        eleMeFacadeService.chargeBack(stringMap.get("eleme_order_id")[0],stringMap.get("refund_status")[0]);
                        break;
                    case "4": //订单配送状态推送

                        break;
                    default: break;
                }
                break;
            case Constants.PLATFORM_WAIMAI_ELEME:

                break;
            default:break;
        }

        return result;
    }

    //获取供应商-百度
    public String getSupplier(){
        return baiDuFacadeService.getSupplier();
    }

    //门店开业
    public String startBusiness(Map<String,String[]> stringMap){

        return shopOpt(stringMap, "shop.open");
    }

    public String startBusiness(ParsFromPos parsFromPos){
        String result = "{baidu:{0},jdhome:{1},meituan:{2},eleme:{3}}",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        result_baidu = baiDuFacadeService.startBusiness(parsFromPos.getBaidu().getShopId(),parsFromPos.getBaidu().getPlatformShopId());
        result_jdhome = jdHomeFacadeService.startBusiness(parsFromPos.getJdhome().getShopId(),parsFromPos.getJdhome().getPlatformShopId());
        //result_eleme = eleMeFacadeService.pullNewOrder()
        return null;
    }

    //门店歇业
    public String endBusiness(Map<String,String[]> stringMap){

        return shopOpt(stringMap, "shop.close");
    }

    private String shopOpt(Map<String,String[]> stringMap,String dynamic){
        String result = "{baidu:{0},jdhome:{1},meituan:{2},eleme:{3}}",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        for (String k:stringMap.keySet()){
            switch (k){
                case Constants.PLATFORM_WAIMAI_BAIDU:
                    switch (dynamic){
                        case "shop.open":
                            result_baidu = baiDuFacadeService.startBusiness(stringMap.get("shopId")[0],stringMap.get("platform_shopId")[0]);
                            break;
                        case "shop.close":

                            result_baidu = baiDuFacadeService.endBusiness(stringMap.get("shopId")[0], stringMap.get("platform_shopId")[0]);
                            break;
                    }

                    break;
                case Constants.PLATFORM_WAIMAI_JDHOME:
                    break;
                default:break;
            }
        }
        return MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme);
    }

    //菜品上架
    public String online(Map<String,String[]> stringMap){

        return  null;
    }

    //菜品下架
    public String offline(Map<String,String[]> stringMap){

        return  null;
    }

    private String dishOpt(Map<String,String[]> stringMap,String dynamic){

        String result = "{baidu:{0},jdhome:{1},meituan:{2},eleme:{3}}",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(ParsFromPos.class,new ParsFormPosSerializer())
                                     .registerTypeAdapter(ParsFromPosInner.class,new ParsFromPosInnerSerializer())
                                     .disableHtmlEscaping().create();
        ParsFromPosInner parsFromPosInner;
        for (String k:stringMap.keySet()){
            for (String v:stringMap.get(k)){
                parsFromPosInner = gson.fromJson(v,ParsFromPosInner.class);
                switch (k){
                    case Constants.PLATFORM_WAIMAI_BAIDU:
                        switch (dynamic){
                            case "dish.online":
                                result_baidu += (StringUtil.isEmpty(result_baidu)?"":",")
                                             + baiDuFacadeService.online(parsFromPosInner.getPlatformShopId(),parsFromPosInner.getShopId(),parsFromPosInner.getPlatformDishId(),parsFromPosInner.getDishId());
                                break;
                            case "dish.offline":
                                //result_baidu +=(StringUtil.isEmpty(result_baidu)?"":",")
                                break;
                        }

                        break;
                    case Constants.PLATFORM_WAIMAI_JDHOME:
                        break;
                    default:break;
                }
            }
        }
        return MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme);
    }

    //平台订单推送【消息型】
    public String orderPost(Map<String,String[]> stringMap,String platform){
       String result = null;
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                             .disableHtmlEscaping().create();
                SysParams sysParams = gson.fromJson(map2Json(stringMap),SysParams.class);
                result = baiDuFacadeService.orderPost(sysParams);
                break;
            case Constants.PLATFORM_WAIMAI_JDHOME:
                result = jdHomeFacadeService.newOrder(stringMap.get("billId")[0],stringMap.get("statusId")[0],stringMap.get("timestamp")[0],"10054394");
                //result = map2Json(stringMap);
                break;
            default:break;
        }
        return  result;
    }

    //平台订单状态推送
    public String orderStatus(Map<String,String[]> stringMap,String platform){
          String result = null;
          switch (platform){
              case Constants.PLATFORM_WAIMAI_BAIDU:
                  Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                               .disableHtmlEscaping().create();
                  SysParams sysParams = gson.fromJson(map2Json(stringMap),SysParams.class);
                 // result = baiDuFacadeService.orderStatus()
                  break;
              case Constants.PLATFORM_WAIMAI_JDHOME:

                  break;
              default:break;
          }
          return  result;
    }


    //确认订单
    public String orderConfirm(Map<String,String[]> stringMap){
       return orderOpt(stringMap,"order.confirm");
    }

    //取消订单
    public  String orderCancel(Map<String,String[]> stringMap){
      return orderOpt(stringMap, "order.cancel");
    }

    //订单操作
    private  String orderOpt(Map<String,String[]> stringMap,String command){
        String result = "{baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]}",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        for (String k :stringMap.keySet()){
            switch (k){
                case Constants.PLATFORM_WAIMAI_BAIDU:
                    for(String id:stringMap.get(k)[0].toString().split(",")){
                        switch (command){
                            case "order.confirm":
                                result_baidu += (result_baidu == null?"":",")+baiDuFacadeService.orderConfirm(id);
                                break;
                            case "order.cancel":
                                result_baidu += (result_baidu == null?"":",")+baiDuFacadeService.orderCancel(id);
                                break;
                            default:break;
                        }

                    }
                    break;
                case  Constants.PLATFORM_WAIMAI_JDHOME:
                    break;
                default:break;
            }
        }
        return MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme);
    }

    //Other
    public static<T> String map2Json(Map<String,T> map){
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(map);
    }

}

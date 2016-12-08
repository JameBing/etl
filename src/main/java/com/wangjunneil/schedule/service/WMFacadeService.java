package com.wangjunneil.schedule.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.entity.baidu.Order;
import com.wangjunneil.schedule.entity.baidu.Shop;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.entity.baidu.SysParamsSerializer;
import com.wangjunneil.schedule.entity.common.*;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.service.baidu.BaiDuApiService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by yangwanbin on 2016-11-17.
 */
@Service
public class WMFacadeService {

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

    //========================下行接口=================================/

    //回调地址入口处理方法
    public String appReceiveCallBack(Map<String,String[]> stringMap,String platform){
        Rtn rtn = new Rtn();
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String result = "";
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                switch (stringMap.get("cmd")[0]){
                    case Constants.BAIDU_CMD_ORDER_CREATE:                    //订单创建
                        orderPost(stringMap,platform);
                        break;
                    case  Constants.BAIDU_CMD_ORDER_STATUS_PUSH:       //订单状态推送
                        orderStatus(stringMap,platform);
                        break;
                    default:
                        result = baiDuFacadeService.responseNoPars("resp");
                        break;
                }
                break;
            case Constants.PLATFORM_WAIMAI_JDHOME:
                //保存&更新token
                jdHomeFacadeService.callback(stringMap.get("token")!=null?stringMap.get("token")[0]:stringMap.get("code")[0],stringMap.get("sid")[0]);
                break;
            case Constants.PLATFORM_WAIMAI_MEITUAN:
                if(stringMap.size()==0){
                    rtn.setCode(200);
                    return gson1.toJson(rtn);
                }
                switch (stringMap.get("status")[0]){
                    case "2":             //订单创建(用户已支付)
                        new Gson().toJson(stringMap);
                        meiTuanFacadeService.newOrder(functionMap2Json.apply(stringMap));
                        break;
                    case  "4":       //商家已确认
                        orderStatus(stringMap,platform);
                        break;
                    case  "8":       //交易完成
                        orderStatus(stringMap,platform);
                        break;
                    default:
                        result = baiDuFacadeService.responseNoPars("resp");
                        break;
                }

                break;
            case Constants.PLATFORM_WAIMAI_ELEME:
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
            default:break;
        }

        return result;
    }


    private   Function<Map<String, String[]>, SysParams> functionMap2SysParams = (m) -> {
        Map<String, String> map = new HashMap<String, String>();
        m.keySet().forEach(k -> {
            map.put(k, m.get(k)[0]);
        });
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .disableHtmlEscaping().create();
        return gson.fromJson(map2Json(map), SysParams.class);
    };

    private Function<Map<String,String[]>,JsonObject> functionMap2Json = (m) ->{
       JsonObject jsonObject = new JsonObject();
        m.keySet().forEach(k -> {
            jsonObject.addProperty(k,m.get(k)[0]);
        });
        return jsonObject;
    };

    //平台订单推送【消息型】
    public String orderPost(Map<String,String[]> stringMap,String platform){
       String result = "";
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                    result = baiDuFacadeService.orderPost(functionMap2SysParams.apply(stringMap));
                break;
            case Constants.PLATFORM_WAIMAI_JDHOME:
                result = jdHomeFacadeService.newOrder(stringMap.get("jd_param_json")[0],stringMap.get("sid")[0]);
                break;
            default:break;
        }
        return  result;
    }

    //平台订单状态推送
    public String orderStatus(Map<String,String[]> stringMap,String platform){
          String result = "";
          switch (platform){
              case Constants.PLATFORM_WAIMAI_BAIDU:
                  result = baiDuFacadeService.orderStatus(functionMap2SysParams.apply(stringMap));
                  break;
              case Constants.PLATFORM_WAIMAI_JDHOME:
                    result = jdHomeFacadeService.changeStatus(stringMap.get("jd_param_json")[0]);
                  break;
              default:break;
          }
          return  result;
    }

//=========================上行接口====================================//

    //===============门店=================/
     //获取供应商-百度
     public String getSupplier(){
    return baiDuFacadeService.getSupplier();
}

    //修改商户信息-百度
    public String shopUpdate(JsonObject jsonObject){
        return baiDuFacadeService.shopUpdate(jsonObject);
    }

    //新建门店
    public String shopCreate(JsonObject json){
        String result = "{baidu:{0},jdhome:{1},meituan:{2},eleme:{3}}",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        result_baidu = baiDuFacadeService.shopCreate(json);
        return "{".concat(MessageFormat.format(result,result_baidu,result_jdhome,result_meituan,result_eleme)).concat("}");
    }

    //门店开业
    public String shopOpen(ParsFromPos parsFromPos){
        String result = "baidu:{0},jdhome:{1},meituan:{2},eleme:{3}",
            result_baidu = "",
            result_jdhome = "",
            result_eleme = "",
            result_meituan = "";
        result_baidu = baiDuFacadeService.shopOpen(parsFromPos.getBaidu().getPlatformShopId(), parsFromPos.getBaidu().getShopId());
        result_jdhome = jdHomeFacadeService.startBusiness(parsFromPos.getJdhome().getShopId(),parsFromPos.getJdhome().getPlatformShopId());
        result_eleme = eleMeFacadeService.setRestaurantStatus(parsFromPos.getEleme().getShopId(),"1");
        result_meituan = meiTuanFacadeService.openShop(parsFromPos.getMeituan().getShopId());
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    //门店歇业
    public String shopClose(ParsFromPos parsFromPos){
        String result = "baidu:{0},jdhome:{1},meituan:{2},eleme:{3}", result_baidu = "", result_jdhome = "", result_eleme = "", result_meituan = "";
        result_baidu = baiDuFacadeService.shopClose(parsFromPos.getBaidu().getPlatformShopId(), parsFromPos.getBaidu().getShopId());
        result_jdhome = jdHomeFacadeService.endBusiness(parsFromPos.getJdhome().getShopId(), parsFromPos.getJdhome().getPlatformShopId());
        result_eleme = eleMeFacadeService.setRestaurantStatus(parsFromPos.getEleme().getShopId(),"0");
        result_meituan = meiTuanFacadeService.openShop(parsFromPos.getMeituan().getShopId());
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    //===============菜品=================/
    //新增菜品分类
    public String dishCategoryCreate(JsonObject json){
        String result = "baidu:{0},jdhome:{1},meituan:{2},eleme:{3}",
            result_baidu = "",
            result_jdhome = "",
            result_eleme = "",
            result_meituan = "";
        result_baidu = baiDuFacadeService.dishCategoryCreate(json);
        return  "{".concat(MessageFormat.format(result,result_baidu,result_jdhome,result_meituan,result_eleme)).concat("}");
    }

    //新增菜品
    public String dishCreate(JsonObject json){
        String result = "baidu:{0},jdhome:{1},meituan:{2},eleme:{3}",
            result_baidu = "",
            result_jdhome = "",
            result_eleme = "",
            result_meituan = "";
        result_baidu = baiDuFacadeService.dishCreate(json);
        return  "{".concat(MessageFormat.format(result,result_baidu,result_jdhome,result_meituan,result_eleme)).concat("}");
    }

    //菜品查看
    public String dishGet(ParsFromPos parsFromPos){
        String result = "baidu:{0},jdhome:{1},meituan:{2},eleme:{3}",
            result_baidu = "",
            result_jdhome = "",
            result_eleme = "",
            result_meituan = "";
        String platShopId = StringUtil.isEmpty(parsFromPos.getBaidu().getPlatformShopId())?"":parsFromPos.getBaidu().getPlatformShopId(),
            shopId = StringUtil.isEmpty(parsFromPos.getBaidu().getShopId())?"":parsFromPos.getBaidu().getShopId(),
            platDishId = StringUtil.isEmpty(parsFromPos.getBaidu().getPlatformDishId())?"":parsFromPos.getBaidu().getPlatformDishId(),
            dishId = StringUtil.isEmpty(parsFromPos.getBaidu().getDishId())?"":parsFromPos.getBaidu().getDishId();
        result_baidu = baiDuFacadeService.dishGet(platShopId,shopId,platDishId,dishId);
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    private Function<ParsFromPosInner,String > function1=(list)->{
        return baiDuFacadeService.dishOpt(list.getPlatformShopId(),list.getShopId(),list.getPlatformDishId(),list.getDishId(),"dish.online");
    };

    //菜品上架
    public String dishOnline(ParsFormPos2 parsFormPos2){
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]", result_baidu = "", result_jdhome = "", result_eleme = "", result_meituan = "";
        result_baidu = parsFormPos2.getBaidu().stream().map(e->function1.apply(e)).reduce("",(x,y)->x.concat(StringUtil.isEmpty(x)?"":",").concat(y));
        result_jdhome = jdHomeFacadeService.updateAllStockOnAndOff(parsFormPos2.getJdhome(),0); //0上架 1下架
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    private Function<ParsFromPosInner,String > function2=(list)->{
        return baiDuFacadeService.dishOpt(list.getPlatformShopId(), list.getShopId(), list.getPlatformDishId(), list.getDishId(), "dish.offline");
    };

    //菜品下架
    public String dishOffline(ParsFormPos2 parsFormPos2){
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]", result_baidu = "", result_jdhome = "", result_eleme = "", result_meituan = "";
        result_baidu = parsFormPos2.getBaidu().stream().map(e->function2.apply(e)).reduce("",(x,y)->x.concat(StringUtil.isEmpty(x)?"":",").concat(y));
        result_jdhome = jdHomeFacadeService.updateAllStockOnAndOff(parsFormPos2.getJdhome(), 1); //0上架 1下架
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    //===============订单=================/
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
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]",
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
        return "{".concat( MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    //===============Other=================/
    public static<T> String map2Json(Map<String,T> map){
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        return gson.toJson(map);
    }

}

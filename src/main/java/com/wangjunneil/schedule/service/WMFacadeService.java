package com.wangjunneil.schedule.service;

import com.google.gson.*;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.entity.baidu.SysParamsSerializer;
import com.wangjunneil.schedule.entity.common.*;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.*;
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
        Gson gson1 = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        String result = "";
        switch (platform){
            case Constants.PLATFORM_WAIMAI_BAIDU:
                switch (stringMap.get("cmd")[0]){
                    case Constants.BAIDU_CMD_ORDER_CREATE:                    //订单创建
                      result =   orderPost(stringMap,platform);
                        break;
                    case  Constants.BAIDU_CMD_ORDER_STATUS_PUSH:       //订单状态推送
                     result =    orderStatus(stringMap,platform);
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
                    result = "{\"data\":\"ok\"}";
                }else {
                    switch (stringMap.get("status")[0]) {
                        case "2":             //订单创建(用户已支付)
                            Map<String, String[]> map = new HashMap<String, String[]>();
                            map.putAll(stringMap);
                            String[] strArr = {"extras", "detail"};
                            map.put("ZY_ETL_JSON_KEYS", strArr);
                            result = meiTuanFacadeService.newOrder(functionMap2Json.apply(map));
                            break;
                        case "4":       //商家已确认
                            orderStatus(stringMap, platform);
                            break;
                        case "8":       //交易完成
                            orderStatus(stringMap, platform);
                            break;
                        default:
                            result = baiDuFacadeService.responseNoPars("resp");
                            break;
                    }
                }
                break;
            case Constants.PLATFORM_WAIMAI_ELEME:
                switch (stringMap.get("push_action")[0]){
                    case "1": //新订单
                      result =    eleMeFacadeService.getNewOrder(stringMap.get("eleme_order_ids")[0]);
                        break;
                    case "2": //订单状态变更
                      result =    eleMeFacadeService.orderChange(stringMap.get("eleme_order_id")[0], stringMap.get("new_status")[0]);
                        break;
                    case "3": //退单状态推送
                     result =   eleMeFacadeService.chargeBack(stringMap.get("eleme_order_id")[0], stringMap.get("refund_status")[0]);
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
        if (m.containsKey("ZY_ETL_JSON_KEYS")){
                m.keySet().forEach(k->{
                    if (Arrays.asList(m.get("ZY_ETL_JSON_KEYS")).contains(k)){
                        try {
                            jsonObject.add(k,new JsonParser().parse(java.net.URLDecoder.decode(m.get(k)[0],"utf-8")));
                        }catch (Exception ex){
                        }
                    }
                    else{
                        jsonObject.addProperty(k,m.get(k)[0]);
                    }
                });
        }
        else{
            m.keySet().forEach(k->{
                jsonObject.addProperty(k,m.get(k)[0]);
            });
          }
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
                result = jdHomeFacadeService.newOrder(stringMap.get("jd_param_json")[0], stringMap.get("sid")[0]);
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
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]",
            result_baidu = "",
            result_jdhome = "",
            result_eleme = "",
            result_meituan = "";
        result_baidu = baiDuFacadeService.shopOpen(parsFromPos.getBaidu().getPlatformShopId(), parsFromPos.getBaidu().getShopId());
        result_jdhome = jdHomeFacadeService.openOrCloseStore(parsFromPos.getJdhome().getShopId(), 0);
        result_eleme = eleMeFacadeService.setRestaurantStatus(parsFromPos.getEleme().getShopId(),"1");
        result_meituan = meiTuanFacadeService.openShop(parsFromPos.getMeituan().getShopId());
        return "{".concat(MessageFormat.format(result, result_baidu, result_jdhome, result_meituan, result_eleme)).concat("}");
    }

    //门店歇业
    public String shopClose(ParsFromPos parsFromPos){
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]", result_baidu = "", result_jdhome = "", result_eleme = "", result_meituan = "";
        result_baidu = baiDuFacadeService.shopClose(parsFromPos.getBaidu().getPlatformShopId(), parsFromPos.getBaidu().getShopId());
        result_jdhome = jdHomeFacadeService.openOrCloseStore(parsFromPos.getJdhome().getShopId(), 1);
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
    public String orderConfirm(ParsFromPos parsFromPos){
       return orderOpt(parsFromPos,"order.confirm",true);
    }

    //取消订单
    public  String orderCancel(ParsFromPos parsFromPos){
      return orderOpt(parsFromPos, "order.cancel",false);
    }

    //订单操作
    private  String orderOpt(ParsFromPos parsFromPos,String command,Boolean isAgree){
        String result = "baidu:[{0}],jdhome:[{1}],meituan:[{2}],eleme:[{3}]",
            result_baidu = null,
            result_jdhome = null,
            result_eleme = null,
            result_meituan = null;
        if(parsFromPos.getBaidu()!=null){
            for(String id:parsFromPos.getBaidu().getOrderId().split(",")){
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
        }
        if(parsFromPos.getJdhome()!=null){
            for(String id:parsFromPos.getJdhome().getOrderId().split(",")){
                  result_jdhome +=(result_jdhome == null?"":",")+jdHomeFacadeService.orderAcceptOperate(id, parsFromPos.getJdhome().getShopId(), isAgree);
                }
            }
        if(parsFromPos.getMeituan()!=null){
            for(String id:parsFromPos.getMeituan().getOrderId().split(",")){
                result_meituan +=(result_meituan == null?"":",")+meiTuanFacadeService.getConfirmOrder(Long.parseLong(id));
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

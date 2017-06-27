package com.wangjunneil.schedule.service.baidu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.*;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.utility.HttpUtil;

import java.lang.reflect.Type;
import java.text.MessageFormat;
import java.util.Iterator;

/**
 * Created by yangwanbin on 2016-11-14.
 */

@Service
public class BaiDuApiService {
    private static Logger log = Logger.getLogger(BaiDuApiService.class.getName());
    //打包参数(包含计算sign)

    public  String getRequestPars(String cmd,Object obj,String sellerId ) throws ScheduleException,BaiDuException{
            Gson gson;
            try {
                SysParams sysParams = new SysParams();
                sysParams.setCmd(cmd);
                if(obj == null){
                    sysParams.setBody("");
                    gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer()).disableHtmlEscaping().create();
                }
                else {
                Class<?> clazz = Class.forName(obj.getClass().getName()+"Serializer");

                gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                    .registerTypeAdapter(obj.getClass(), (Object) clazz.newInstance()).disableHtmlEscaping().create();
                sysParams.setBody(obj);
            }

            String params = "body={0}&cmd={1}&timestamp={2}&version={3}&ticket={4}&source={5}&encrypt={6}&secret={7}";
            String bodyStr = gson.toJson(sysParams.getBody());
            if(!StringUtil.isEmpty(sellerId) && sellerId.length()>5){
                String shopId = sellerId.substring(0,5);
                getSourceAndSecret(shopId,sysParams);
            }
            if(sellerId.length()==5) {
                getSourceAndSecretBySource(sellerId,sysParams);
            }
            params = MessageFormat.format(params, JSONObject.parse(bodyStr), sysParams.getCmd(), sysParams.getTimestamp(), sysParams.getVersion(), sysParams.getTicket()
                , String.valueOf(sysParams.getSource()), sysParams.getEncrypt(), sysParams.getSecret());
            params = StringUtil.retParamAsc(params);
            params = StringUtil.chinaToUnicode(params);
            return params.concat(MessageFormat.format("&sign={0}", StringUtil.getMD5(params)));
//                String signJson = gson.toJson(sysParams);
//                //对所有的/进行转义
//                signJson = signJson.replace("/", "\\/");
//                //中文字符安转unicode
//                signJson = sysParams.chinaToUnicode(signJson);
//
//                sysParams.setSign(sysParams.getMD5(signJson));
//               // String requestJson = gson.toJson(sysParams);
//                return  MultipartPars(sysParams);

            }
            catch (Exception ex){
                throw  new BaiDuException(ex.getClass().getName(),"计算签名过程异常",cmd+"\r\n"+new Gson().toJson(obj),new Throwable().getStackTrace());
            }
    }

    public String getRequestPars(SysParams sysParams) {
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .disableHtmlEscaping().create();
//
//        String signJson = gson.toJson(sysParams);
//        //对所有的/进行转义
//        signJson = signJson.replace("/", "\\/");
//        //中文字符安转unicode
//        signJson = sysParams.chinaToUnicode(signJson);
//
//        sysParams.setSign(sysParams.getMD5(signJson));
//        String requestJson = gson.toJson(sysParams);
//        return  requestJson;

        String params = "body={0}&cmd={1}&timestamp={2}&version={3}&ticket={4}&source={5}&encrypt={6}&secret={7}";
        //String bodyStr = gson.toJson(sysParams.getBody());

        params =  MessageFormat.format(params,gson.toJson(sysParams.getBody()),sysParams.getCmd(),sysParams.getTimestamp(),sysParams.getVersion(),sysParams.getTicket()
            ,String.valueOf(sysParams.getSource()),sysParams.getEncrypt(),sysParams.getSecret());
        params = StringUtil.retParamAsc(params);
        params = StringUtil.chinaToUnicode(params);
        return params.concat(MessageFormat.format("&sign={0}", StringUtil.getMD5(params)));
    }

    //消息型接口Response
    public String responseStr(SysParams sysParams){

        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .disableHtmlEscaping().create();
        String ticket = sysParams.getTicket();
        String timeStamp = sysParams.getTimestamp();
        String params = "body={0}&cmd={1}&timestamp={2}&version={3}&ticket={4}&source={5}&encrypt={6}&secret={7}";
        String bodyStr = gson.toJson(sysParams.getBody());
        getSourceAndSecretBySource(sysParams.getSource(),sysParams);
        params = MessageFormat.format(params, bodyStr, sysParams.getCmd(), timeStamp, sysParams.getVersion(), ticket
            , String.valueOf(sysParams.getSource()), sysParams.getEncrypt(), sysParams.getSecret());
        params = StringUtil.retParamAsc(params);
        params = StringUtil.chinaToUnicode(params);
        System.out.println("签名参数："+params);
        JsonObject json = new JsonObject();
        JsonObject jsonBody = new JsonObject();
        JsonObject jsonData = new JsonObject();
        jsonBody.addProperty("errno", 0);
        jsonBody.addProperty("error","success");
       switch (sysParams.getCmd()){
           case Constants.BAIDU_CMD_RESP+"."+Constants.BAIDU_CMD_ORDER_CREATE:
               //String data = new JsonParser().parse(new JsonParser().parse(gson.toJson(sysParams.getBody())).getAsJsonObject().get("data").toString()).getAsJsonObject().get("source_order_id").toString();
               JSONObject jsonObject = JSONObject.parseObject(gson.toJson(sysParams.getBody()));
               String data = JSONObject.parseObject(jsonObject.getString("data")).getString("source_order_id");
               jsonData.addProperty("source_order_id",data.replaceAll("\"",""));
               jsonBody.add("data", jsonData);
               break;
           case Constants.BAIDU_CMD_RESP +"."+Constants.BAIDU_CMD_ORDER_STATUS_PUSH:
               break;
           default:
               break;
       }
        json.add("body",jsonBody);
        json.addProperty("cmd", sysParams.getCmd());
        json.addProperty("source",sysParams.getSource());
        json.addProperty("ticket",ticket);
        json.addProperty("timestamp",timeStamp);
        json.addProperty("version",3);
        json.addProperty("sign",StringUtil.getMD5(params));
        return json.toString();
    }

    //region 商户

    //查看供应商
    public Supplier  getSupplierList() throws  ScheduleException,BaiDuException{

        String requestStr = getRequestPars("supplier.list", null,"");
        //String requestStr = "body={}&cmd=supplier.list&encrypt=&secret=9aa9bef2dd361398&source=65062&ticket=CBB291F6-33BE-57CC-8FE3-441FE6E7BA6C&timestamp=1430719064&version=3";
        // requestStr = requestStr + "&sign=" + SysParams.getMD5(requestStr);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Data.class, new DataSerializer())
            .registerTypeAdapter(Supplier.class, new SupplierSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        if (body.getErrno().equals("0")) {
            Supplier supplier = gson.fromJson(gson.toJson(body.getData()), Supplier.class);
            return supplier;

        }else {
            throw  new BaiDuException("ScheduleException","获取供应商信息失败",requestStr,new Throwable().getStackTrace());
         }
        }

    //创建商户
    public String shopCreate(JsonObject jsonBody) throws ScheduleException,BaiDuException {
        SysParams sysParams = new SysParams();
        sysParams.setCmd("shop.create");
        sysParams.setBody(jsonBody);
        String requestStr = getRequestPars(sysParams);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        response = StringUtil.unicodeToChina(response);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Shop.class, new ShopSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            }).disableHtmlEscaping().create();
        sysParams = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(sysParams.getBody().toString(), Body.class);
        Shop shop1 = gson.fromJson(body.getData().toString(), Shop.class);
        JsonObject jsonObject = Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno()));
        jsonObject.addProperty("dynamic", shop1.getBaiduShopId());
        return gson.toJson(jsonObject);
    }


    /**
     * 门店开业
     *
     * @param shop 商户实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
      public String shopOpen(Shop shop) throws  ScheduleException,BaiDuException{
          String requestStr = getRequestPars("shop.open", shop,shop.getShopId());
          String response =  HttpUtil.post2(Constants.BAIDU_URL, requestStr,null,"utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
          Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                       .registerTypeAdapter(Shop.class,new ShopSerializer())
                                       .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                                           @Override
                                           public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                                               if (aDouble == aDouble.longValue())
                                                   return new JsonPrimitive(aDouble.longValue());
                                               return new JsonPrimitive(aDouble);
                                           }
                                       })
                                       .registerTypeAdapter(Body.class, new BodySerializer()).disableHtmlEscaping().create();
          SysParams sysParams = gson.fromJson(response,SysParams.class);
          //暂不考虑验证返回值中的sign签名合法性
          Body body = gson.fromJson(gson.toJson(sysParams.getBody()),Body.class);
          return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(body.getErrno())));
      }


    /**
     * 门店歇业
     *
     * @param shop 商户实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String shopClose(Shop shop) throws  ScheduleException,BaiDuException{
        String requestStr = getRequestPars("shop.close", shop,shop.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Shop.class, new ShopSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }

    /**
     * 商户状态
     *
     * @param shop 商户实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String shopStatus(Shop shop) throws  ScheduleException,BaiDuException{
        String requestStr = getRequestPars("shop.status.get", shop,shop.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Shop.class, new ShopSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        if(!body.getErrno().equals("0")){
            return Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())).toString();
        }
        Shop shop1 = gson.fromJson(body.getData().toString(), Shop.class);
        JsonObject jsonObject = Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno()));
        jsonObject.addProperty("baidu_shop_id", shop1.getBaiduShopId());
        jsonObject.addProperty("sys_status",shop1.getSysStatus());
        jsonObject.addProperty("business_stauts",shop1.getBusinessStauts());
        return gson.toJson(jsonObject);
    }

    //endregion


    //region 菜品

    /**
     * 新增菜品分类
     *
     * @param jsonBody 菜品分类
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String dishCategoryCreate(JsonObject jsonBody) throws  ScheduleException,BaiDuException {
        SysParams sysParams = new SysParams();
        sysParams.setBody(jsonBody);
        sysParams.setCmd("dish.category.create");
        String requestStr = getRequestPars(sysParams);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        response = StringUtil.unicodeToChina(response);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Category.class, new ShopSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            }).disableHtmlEscaping().create();
        sysParams = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        String dynamicStr = "";
        JsonObject jsonObject = Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno()));
        JsonParser jsonParser = new JsonParser();
        JsonElement jsonElement = jsonParser.parse(gson.toJson(body.getData()));
        if (jsonElement.isJsonArray()) {
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            Iterator it = jsonArray.iterator();
            if (it.hasNext()) {
                Category category = gson.fromJson((JsonElement) it.next(), Category.class);
                dynamicStr.concat(StringUtil.isEmpty(dynamicStr) ? "" : ",").concat(category.getId());
            }
        }
        jsonObject.addProperty("dynamic", dynamicStr);
        return gson.toJson(jsonObject);
    }

    /**
     * 新增菜品
     *
     * @param jsonBody 菜品
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String dishCreate(JsonObject jsonBody) throws ScheduleException,BaiDuException {
        SysParams sysParams = new SysParams();
        sysParams.setBody(jsonBody);
        sysParams.setCmd("dish.create");
        String requestStr = getRequestPars(sysParams);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        response = StringUtil.unicodeToChina(response);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Dish.class, new ShopSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            }).disableHtmlEscaping().create();
        sysParams = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        Dish dish = gson.fromJson(gson.toJson(body.getData()), Dish.class);
        JsonObject jsonObject = Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno()));
        jsonObject.addProperty("dynamic", dish.getBaiduDishId());
        return gson.toJson(jsonObject);
    }

    /**
     * 菜品查看
     *
     * @param dish 菜品
     * @return
     */
    public String dishGet(Dish dish) throws ScheduleException,BaiDuException{
        String requestStr = getRequestPars("dish.get",dish,dish.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL,requestStr,null,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);

        response = StringUtil.unicodeToChina(response);
        return response;
    }

    /**
     * 上架
     *
     * @param dish 菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String dishOnline(Dish dish) throws  ScheduleException,BaiDuException{
        String requestStr = getRequestPars("dish.online", dish,dish.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Dish.class, new DishSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }

    /**
     * 下架
     *
     * @param dish 菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String dishOffline(Dish dish) throws ScheduleException,BaiDuException{
        String requestStr = getRequestPars("dish.offline", dish,dish.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Dish.class, new DishSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }

    //批量查询门店商品上下架状态
    public String getDishStatus(Dish dish)throws ScheduleException,BaiDuException{
        String requestStr = getRequestPars("dish.search", dish,dish.getShopId());
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Dish.class, new DishSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        if(body.getErrno().equals("20253")){
            return body.getErrno();
        }
        if(body.getErrno().equals("0")){
            return gson.toJson(body.getData());
        }
        return  null;
    }

    //endregion


    //region 订单
    public String orderGet(Order order,String shopId) throws  ScheduleException,BaiDuException{
        String requestStr = getRequestPars("order.get", order,shopId);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        return response;
    }


    /**
     * 确认订单
     *
     * @param order 订单实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String orderConfirm(Order order,String shopId) throws  Exception{
        String requestStr = getRequestPars("order.confirm", order, shopId);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Order.class, new OrderSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }

    /**
     * 取消订单
     *
     * @param order 订单实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String orderCancel(Order order,String shopId) throws Exception{
        String requestStr = getRequestPars("order.cancel", order,shopId);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Order.class, new OrderSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext jsonSerializationContext) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            })
            .disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response, SysParams.class);
        Body body = gson.fromJson(gson.toJson(sysParams.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }
    //endregion

    private String MultipartPars(SysParams sysParams) {
        StringBuilder sb = new StringBuilder();
        //cmd
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"cmd\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getCmd());
        sb.append("\r\n");

        //timestamp
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"timestamp\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getTimestamp());
        sb.append("\r\n");

        //version
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"version\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getVersion());
        sb.append("\r\n");

        //ticket
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"ticket\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getTicket());
        sb.append("\r\n");

        //source
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"source\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getSource());
        sb.append("\r\n");

        //sign
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"sign\"");
        sb.append("\r\n\r\n");
        sb.append(sysParams.getSign());
        sb.append("\r\n");

        //body
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"body\"");
        sb.append("\r\n\r\n");
        sb.append(new Gson().toJson(sysParams.getBody(), SysParams.class));
        sb.append("\r\n");

        //encrypt
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"encrypt\"");
        sb.append("\r\n\r\n");
        sb.append(new Gson().toJson(sysParams.getEncrypt()));
        sb.append("\r\n");

        return sb.toString();
    }


    /**
     * 修改商户信息
     *
     * @param
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String shopUpdate(JsonObject jsonObject) throws ScheduleException,BaiDuException {
        SysParams sysParams = new SysParams();
        sysParams.setCmd("shop.update");
        sysParams.setBody(jsonObject);
        String requestStr = getRequestPars(sysParams);
        //String requestStr = getRequestPars("shop.update", shop);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, null, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
            .registerTypeAdapter(Body.class, new BodySerializer())
            .registerTypeAdapter(Shop.class, new ShopSerializer())
            .registerTypeAdapter(Double.class, new JsonSerializer<Double>() {
                @Override
                public JsonElement serialize(Double aDouble, Type type, JsonSerializationContext context) {
                    if (aDouble == aDouble.longValue())
                        return new JsonPrimitive(aDouble.longValue());
                    return new JsonPrimitive(aDouble);
                }
            }).disableHtmlEscaping().create();
        SysParams sysParams1 = gson.fromJson(response, SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Body body = gson.fromJson(gson.toJson(sysParams1.getBody()), Body.class);
        return gson.toJson(Enum.getEnumDesc(Enum.ReturnCodeBaiDu.R0, Integer.valueOf(body.getErrno())));
    }

    //获取合作方ID和密钥
    private  void getSourceAndSecret(String shopId ,SysParams sysParams){
        if("80010".equals(shopId)){
            sysParams.setSource(Constants.BAIDU_SOURCE_SH);
            sysParams.setSecret(Constants.BAIDU_SECRET_SH);
        }
        if("80024".equals(shopId)){
            sysParams.setSource(Constants.BAIDU_SOURCE_SZ);
            sysParams.setSecret(Constants.BAIDU_SECRET_SZ);
        }
    }

    //获取合作方ID和密钥
    private  void getSourceAndSecretBySource(String sourceId ,SysParams sysParams){
        if("30618".equals(sourceId)){
            sysParams.setSource(Constants.BAIDU_SOURCE_SH);
            sysParams.setSecret(Constants.BAIDU_SECRET_SH);
        }
        if("30916".equals(sourceId)){
            sysParams.setSource(Constants.BAIDU_SOURCE_SZ);
            sysParams.setSecret(Constants.BAIDU_SECRET_SZ);
        }
    }
}


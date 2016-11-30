package com.wangjunneil.schedule.service.baidu;

import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.utility.HttpUtil;
import java.lang.reflect.Field;
import java.sql.Struct;
import java.text.MessageFormat;

/**
 * Created by yangwanbin on 2016-11-14.
 */

@Service
public class BaiDuApiService  {


    //打包参数(包含计算sign)
    public   String GetRequestPars(String cmd,Object obj ) throws ScheduleException{
            try {
                Class<?> clazz = Class.forName(obj.getClass().getName()+"Serializer");

                Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                    .registerTypeAdapter(obj.getClass(), (Object)clazz.newInstance()).disableHtmlEscaping().create();

                SysParams sysParams = new SysParams();
                sysParams.setCmd(cmd);
                sysParams.setBody(obj);
                String params = "body={0}&cmd={1}&timestamp={2}&version={3}&ticket={4}&source={5}&encrypt={6}";

                params =  String.format(params,gson.toJson(sysParams.getBody()),sysParams.getCmd(),sysParams.getTimestamp(),sysParams.getVersion(),sysParams.getTicket()
                                              ,sysParams.getSource(),sysParams.getEncrypt());
                params = StringUtil.retParamAsc(params);
                String signStr = sysParams.chinaToUnicode(params);
                return params.concat(String.format("&sign={0}",sysParams.getMD5(signStr)));
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
                throw  new ScheduleException(Constants.PLATFORM_WAIMAI_BAIDU,ex.getClass().getName(),"计算签名过程异常",cmd+"\r\n"+new Gson().toJson(obj),new Throwable().getStackTrace());
            }


    }

    public String GetRequestPars(SysParams sysParams){
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                                     .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();

        String signJson = gson.toJson(sysParams);
        //对所有的/进行转义
        signJson = signJson.replace("/", "\\/");
        //中文字符安转unicode
        signJson = sysParams.chinaToUnicode(signJson);

        sysParams.setSign(sysParams.getMD5(signJson));
        String requestJson = gson.toJson(sysParams);
        return  requestJson;
    }

    //region 商户

    /**
     * 门店开业
     * @param   shop  商户实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
      public String startBusiness(Shop shop) throws  ScheduleException{
          String requestStr = GetRequestPars("shop.open", shop);
          String response =  HttpUtil.post2(Constants.BAIDU_URL, requestStr, Constants.CONTENTTYPE_MULTIPART, "utf-8", null, null, Constants.PLATFORM_WAIMAI_BAIDU);
          Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
              .registerTypeAdapter(Shop.class,new ShopSerializer())
              .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
          SysParams sysParams = gson.fromJson(response,SysParams.class);
          //暂不考虑验证返回值中的sign签名合法性
          Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
          return Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
      }

    /**
     * 门店歇业
     * @param   shop  商户实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String endBusiness(Shop shop) throws  ScheduleException{
        String requestStr = GetRequestPars("shop.close",shop);
        String response = HttpUtil.post2(Constants.BAIDU_URL, requestStr, Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Shop.class, new ShopSerializer())
                                     .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
    }

    //endregion


    //region 菜品

    /**
     * 上架
     * @param   dish  菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String online(Dish dish) throws  ScheduleException{
        String requestStr = GetRequestPars("dish.online",dish);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr,Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Dish.class,new DishSerializer())
                                     .registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
    }

    /**
     * 下架
     * @param   dish  菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String offline(Dish dish) throws ScheduleException{
        String requestStr = GetRequestPars("dish.offline",dish);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr,Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
       Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                    .registerTypeAdapter(Dish.class, new DishSerializer())
                                    .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
    }

    //endregion


    //region 订单
    public SysParams orderGet(Order order) throws  ScheduleException{
      String requestStr = GetRequestPars("order.get",order);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr,Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Order.class,new OrderSerializer())
                                     .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();

        return gson.fromJson(response,SysParams.class);
    }


    /**
     * 确认订单
     * @param   order  订单实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String orderConfirm(Order order) throws  ScheduleException{

        String requestStr = GetRequestPars("order.confirm",order);
        String response = HttpUtil.post(Constants.BAIDU_URL, requestStr, Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Order.class,new OrderSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
    }

    /**
     * 取消订单
     * @param   order  订单实体对象
     * @return "{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String orderCancel(Order order) throws ScheduleException{
        String requestStr = GetRequestPars("order.cancel",order);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr,Constants.CONTENTTYPE_MULTIPART,"utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Order.class,new OrderSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return  Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,Integer.valueOf(result.getErrno())).toString();
    }
    //endregion

    private  String MultipartPars(SysParams sysParams){
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
        sb.append(new Gson().toJson(sysParams.getBody(),SysParams.class));
        sb.append("\r\n");

        //encrypt
        sb.append(Constants.BOUNDARY);
        sb.append("Content-Disposition:form-data;name=\"encrypt\"");
        sb.append("\r\n\r\n");
        sb.append(new Gson().toJson(sysParams.getEncrypt()));
        sb.append("\r\n");

        return sb.toString();
    }


}


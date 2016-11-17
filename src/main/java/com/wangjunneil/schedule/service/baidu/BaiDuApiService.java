package com.wangjunneil.schedule.service.baidu;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.baidu.*;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.utility.HttpUtil;

import java.text.MessageFormat;

/**
 * Created by yangwanbin on 2016-11-14.
 */

@Service
public class BaiDuApiService  {


    private static Logger log = Logger.getLogger(BaiDuApiService.class.getName());


    //打包参数(包含计算sign)
    public   String GetRequestPars(String cmd,Object obj ) throws BaiDuException{
            try {
                Class<?> clazz = Class.forName(obj.getClass().getName()+"Serializer");

                Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class, new SysParamsSerializer())
                    .registerTypeAdapter(obj.getClass(), (Object)clazz.newInstance()).disableHtmlEscaping().create();

                SysParams sysParams = new SysParams();
                sysParams.setCmd(cmd);
                sysParams.setBody(obj);
                String signJson = gson.toJson(sysParams);
                //对所有的/进行转义
                signJson = signJson.replace("/", "\\/");
                //中文字符安转unicode
                signJson = sysParams.chinaToUnicode(signJson);

                sysParams.setSign(sysParams.getMD5(signJson));
                String requestJson = gson.toJson(sysParams);
                return  requestJson;
            }
            catch (Exception ex){ throw  new BaiDuException("签名计算失败",ex);}


    }


    //region 商户

    /**
     * 门店开业
     * @param   shop  商户实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
      public String startBusiness(Shop shop) throws  BaiDuException{

        String requestStr = GetRequestPars("shop.open", shop);
        String response =  HttpUtil.post(Constants.BAIDU_URL, requestStr);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Shop.class,new ShopSerializer())
                                     .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
//          String response ="{\n" +
//              "    \"body\": {\n" +
//              "        \"errno\": 20253,\n" +
//              "        \"error\": \"测试\",\n" +
//              "        \"data\": true\n" +
//              "    },\n" +
//              "    \"cmd\": \"resp.shop.open\",\n" +
//              "    \"sign\": \"A7575AD331CE651F87EB2CFEFD2ADF37\",\n" +
//              "    \"source\": \"65400\",\n" +
//              "    \"ticket\": \"CBB291F6-33BE-57CC-8FE3-441FE6E7BA6C\",\n" +
//              "    \"timestamp\": 1452504385,\n" +
//              "    \"version\": \"2.0\"\n" +
//              "}";
         SysParams sysParams = gson.fromJson(response,SysParams.class);
          //暂不考虑验证返回值中的sign签名合法性
          Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
          return MessageFormat.format("baidu:{0}",Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,String.valueOf(result.getErrno())));
      }

    /**
     * 门店歇业
     * @param   shop  商户实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String endBusiness(Shop shop) throws  BaiDuException{
        String requestStr = GetRequestPars("shop.close",shop);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Shop.class, new ShopSerializer())
                                     .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        //暂不考虑验证返回值中的sign签名合法性
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return MessageFormat.format("baidu:{0}",Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,String.valueOf(result.getErrno())));
    }

    //endregion


    //region 菜品

    /**
     * 上架
     * @param   dish  菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String online(Dish dish) throws  BaiDuException{
        String requestStr = GetRequestPars("dish.online",dish);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr);
        Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                     .registerTypeAdapter(Dish.class,new DishSerializer())
                                     .registerTypeAdapter(Result.class,new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return MessageFormat.format("baidu:{0}",Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,String.valueOf(result.getErrno())));
    }

    /**
     * 下架
     * @param   dish  菜品实体对象
     * @return "baidu:{code:0,desc:\"成功\",remark:\"\"}"
     */
    public String offline(Dish dish) throws BaiDuException{
        String requestStr = GetRequestPars("dish.offline",dish);
        String response = HttpUtil.post(Constants.BAIDU_URL,requestStr);
       Gson gson = new GsonBuilder().registerTypeAdapter(SysParams.class,new SysParamsSerializer())
                                    .registerTypeAdapter(Dish.class, new DishSerializer())
                                    .registerTypeAdapter(Result.class, new ResultSerializer()).disableHtmlEscaping().create();
        SysParams sysParams = gson.fromJson(response,SysParams.class);
        Result result = gson.fromJson(sysParams.getBody().toString(),Result.class);
        return MessageFormat.format("baidu:{0}",Enum.GetEnumDesc(Enum.ReturnCodeBaiDu.R0,String.valueOf(result.getErrno())));
    }

    //endregion


    //region 订单



    //endregion
}


package com.wangjunneil.schedule.common;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.sun.java.util.jar.pack.*;
import com.wangjunneil.schedule.common.EnumDescription;
import com.wangjunneil.schedule.utility.StringUtil;
import org.eclipse.jetty.util.Fields;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Created by yangwanbin on 2016-11-14.
 */
public class Enum {

    //订单状态--百度外卖
    public enum OrderTypeBaiDu
    {
       @EnumDescription(code = 1,desc = "待确认")
        R1,
       @EnumDescription(code = 5,desc = "已确认")
        R5,
       @EnumDescription(code = 7,desc = "正在取餐")
        R7,
       @EnumDescription(code = 8,desc = "正在配送")
        R8,
       @EnumDescription(code = 9,desc = "已完成")
        R9,
       @EnumDescription(code = 10,desc = "已取消")
        R10
    }


    //错误码--百度外卖
    public enum ReturnCodeBaiDu{
    @EnumDescription(code = 0,desc = "success",remark = "成功")
    R0,
    @EnumDescription(code = 1,desc = "error",remark = "未知错误")
    R1,
    @EnumDescription(code = 20253,desc = "商户不存在",remark = "商户不存在")
    R20253,
    @EnumDescription(code = 20108,desc = "命令不支持",remark = "命令不支持")
    R20108,
    @EnumDescription(code = 20109,desc = "来源不支持",remark = "来源不支持")
    R20109,
    @EnumDescription(code = 20110,desc = "签名格式不正确",remark = "签名格式不正确")
    R20110,
    @EnumDescription(code = 20113,desc = "ticket格式不正确",remark = "ticket格式不正确")
    R20113,
    @EnumDescription(code = 20114,desc = "签名不匹配",remark = "签名不匹配")
    R20114
    }

<<<<<<< HEAD
=======
    //订单状态--京东到家
    public enum OrderStatusJdHome{
        @EnumDescription(code = 20010,desc = "锁定")
        OrderLock,
        @EnumDescription(code = 20020,desc = "用户取消")
        OrderUserCancelled,
        @EnumDescription(code = 20040,desc = "系统取消")
        OrderSysCancelled,
        @EnumDescription(code = 41000,desc = "待接单")
        OrderWaiting,
        @EnumDescription(code = 41010,desc = "已接单")
        OrderReceived,
        @EnumDescription(code = 33040,desc = "配送中")
        OrderDelivering,
        @EnumDescription(code = 33060,desc = "已妥投")
        OrderConfirmed
    }

>>>>>>> 60c7926829de2186cf4f8eeced49bc5fc1f079d3
    //根据枚举类型值获取枚举注释
      public static JSONObject GetEnumDesc(Object obj,int code){
        JSONObject json = new JSONObject();
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        String[] names = obj.toString().split(".");
        for(Field f:fields){
            if(f.getName().equals("R"+String.valueOf(code))){
             Annotation annotation = f.getAnnotations()[0];//注释只有一个，多个的话这里需要增强
             json.put("code",AnnotationUtils.getValue(annotation,"code"));
             json.put("desc",AnnotationUtils.getValue(annotation,"desc"));
             json.put("remark",AnnotationUtils.getValue(annotation,"remark"));
             break;
            }
        }
        return json;
    }
}

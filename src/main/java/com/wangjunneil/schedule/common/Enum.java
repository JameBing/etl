package com.wangjunneil.schedule.common;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.EnumDescription;
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
        A,
       @EnumDescription(code = 5,desc = "已确认")
        B,
       @EnumDescription(code = 7,desc = "正在取餐")
        C,
       @EnumDescription(code = 8,desc = "正在配送")
        D,
       @EnumDescription(code = 9,desc = "已完成")
        E,
       @EnumDescription(code = 10,desc = "已取消")
        F
    }



    //根据枚举类型值获取枚举注释
      public static JSONObject GetEnumDesc(Object obj){
        JSONObject json = new JSONObject();
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f:fields){
            if(f.getName().equals(obj.toString())){
             Annotation annotation = f.getAnnotations()[0];//注释只有一个，多个的话这里需要增强
             json.put("code",AnnotationUtils.getValue(annotation,"code"));
             json.put("desc",AnnotationUtils.getValue(annotation,"desc"));
             break;
            }
        }
        return json;
    }
}

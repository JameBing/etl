package com.wangjunneil.schedule.common;
import com.google.gson.JsonElement;
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

    //订单状态--美团外卖
    public enum OrderTypeMeiTuan
    {
        @EnumDescription(code = 1,desc = "用户已提交订单")
        R1,
        @EnumDescription(code = 2,desc = "可推送到APP方平台也可推送到商家")
        R5,
        @EnumDescription(code = 3,desc = "商家已收到")
        R7,
        @EnumDescription(code = 4,desc = "商家已确认")
        R8,
        @EnumDescription(code = 8,desc = "已完成")
        R9,
        @EnumDescription(code = 9,desc = "已取消")
        R10
    }

    //错误码--饿了么
    public enum ReturnCodeEleMe{
        @EnumDescription(code = 1000,desc = "权限错误")
        PERMISSION_DENIED,
        @EnumDescription(code = 1001,desc = "签名错误")
        SIGNATURE_ERROR,
        @EnumDescription(code = 1002,desc = "系统级参数错误")
        SYSTEM_PARAM_ERROR,
        @EnumDescription(code = 1003,desc = "系统级参数错误")
        INVALID_CONSUMER,
        @EnumDescription(code = 1004,desc = "非法请求参数")
        INVALID_REQUEST_PARAM,
        @EnumDescription(code = 1005,desc = "在线支付订单验证错误")
        INVALID_ONLINE_PAYMENT_ORDER_VALIDATION,
        @EnumDescription(code = 1006,desc = "系统错误")
        SYSTEM_ERROR,
        @EnumDescription(code = 1007,desc = "饿了么业务系统错误")
        ELEME_SYSTEM_ERROR,
        @EnumDescription(code = 1008,desc = "开放平台错误")
        OPENAPI_SYSTEM_ERROR,
        @EnumDescription(code = 1009,desc = "超过请求限制")
        RATE_LIMIT_REACHED,
        @EnumDescription(code = 1010,desc = "GnuPG公钥未找到")
        GPG_KEY_NOT_FOUND,
        @EnumDescription(code = 1011,desc = "开放平台应用未找到")
        APPLICATION_NOT_FOUND,
        @EnumDescription(code = 1012,desc = "订单未找到")
        ORDER_NOT_FOUND,
        @EnumDescription(code = 1013,desc = "订单已取消")
        ORDER_CANCELED,
        @EnumDescription(code = 1014,desc = "无效地理位置")
        INVALID_GEO,
        @EnumDescription(code = 1015,desc = "订单已存在")
        ORDER_EXISTED,
        @EnumDescription(code = 1016,desc = "食物已存在")
        FOOD_EXISTED,
        @EnumDescription(code = 1017,desc = "图片上传失败")
        IMAGE_UPLOAD_ERROR,
        @EnumDescription(code = 1018,desc = "无效的订单状态")
        INVALID_ORDER_STATUS,
        @EnumDescription(code = 1019,desc = "无效的食物分类")
        INVALID_FOOD_CATEGORY,
        @EnumDescription(code = 1020,desc = "无效的食物")
        INVALID_FOOD,
        @EnumDescription(code = 1021,desc = "已回复评论")
        COMMENT_REPLY_ERROR
    }
    //订单状态--饿了么
    public enum OrderStatusEleMe{
        @EnumDescription(code = -1,desc = "订单已取消")
        STATUS_CODE_INVALID,
        @EnumDescription(code = 0,desc = "订单未处理")
        STATUS_CODE_UNPROCESSED,
        @EnumDescription(code = 1,desc = "订单等待餐厅确认")
        STATUS_CODE_PROCESSING,
        @EnumDescription(code = 2,desc = "订单已处理")
        STATUS_CODE_PROCESSED_AND_VALID,
        @EnumDescription(code = 9,desc = "订单已完成")
        STATUS_CODE_SUCCESS
    }
    //订单取消原因类型--饿了么
    public enum CancelOrderEleMe{
        @EnumDescription(code = 0,desc = "其它原因")
        OTHERS,
        @EnumDescription(code = 1,desc = "假订单")
        TYPE_FAKE_ORDER,
        @EnumDescription(code = 2,desc = "重复订单")
        TYPE_DUPLICATE_ORDER,
        @EnumDescription(code = 3,desc = "联系不上餐厅")
        TYPE_FAIL_CONTACT_RESTAURANT,
        @EnumDescription(code = 4,desc = "联系不上用户")
        TYPE_FAIL_CONTACT_USER,
        @EnumDescription(code = 5,desc = "食物已售完")
        TYPE_FOOD_SOLDOUT,
        @EnumDescription(code = 6,desc = "餐厅已打烊")
        TYPE_RESTAURANT_CLOSED,
        @EnumDescription(code = 7,desc = "超出配送范围")
        TYPE_TOO_FAR,
        @EnumDescription(code = 8,desc = "餐厅太忙")
        TYPE_RST_TOO_BUSY,
        @EnumDescription(code = 9,desc = "用户无理由退单")
        TYPE_FORCE_REJECT_ORDER,
        @EnumDescription(code = 10,desc = "配送方检测餐品不合格")
        TYPE_DELIVERY_CHECK_FOOD_UNQUALIFIED,
        @EnumDescription(code = 11,desc = "由于配送过程问题,用户退单")
        TYPE_DELIVERY_FAULT,
        @EnumDescription(code = 12,desc = "订单被替换")
        TYPE_REPLACE_ORDER,
        @EnumDescription(code = 13,desc = "用户取消订单")
        TYPE_USR_CANCEL_ORDER,
        @EnumDescription(code = 14,desc = "餐厅长时间未接单，订单自动取消")
        TYPE_SYSTEM_AUTO_CANCEL,
    }


    //根据枚举类型值获取枚举注释
      public static JsonObject getEnumDesc(Object obj,String name){
        JsonObject json = new JsonObject();
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        for(Field f:fields){
            if(f.getName().equals(name)){
             Annotation annotation = f.getAnnotations()[0];//注释只有一个，多个的话这里需要增强
             json.addProperty("code",AnnotationUtils.getValue(annotation, "code").toString());
             json.addProperty("desc", AnnotationUtils.getValue(annotation, "desc").toString());
             json.addProperty("remark",AnnotationUtils.getValue(annotation,"remark").toString());
             break;
            }
        }
        return json;
    }

    //根据枚举类型值获取枚举注释
    public static JsonObject getEnumDesc(Object obj,Object code){
        JsonObject json = new JsonObject();
        java.lang.reflect.Field[] fields = obj.getClass().getDeclaredFields();
        String[] names = obj.toString().split(".");
        for(Field f:fields){
            if(f.getName().equals("R"+String.valueOf(code))){
                Annotation annotation = f.getAnnotations()[0];//注释只有一个，多个的话这里需要增强
                json.addProperty("code",AnnotationUtils.getValue(annotation,"code").toString());
                json.addProperty("desc",AnnotationUtils.getValue(annotation,"desc").toString());
                json.addProperty("remark",AnnotationUtils.getValue(annotation,"remark").toString());
                break;
            }
        }
        return json;
    }
}

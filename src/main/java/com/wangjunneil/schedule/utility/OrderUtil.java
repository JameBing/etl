package com.wangjunneil.schedule.utility;

import com.wangjunneil.schedule.common.Constants;

/**
 * @since 2017/5/4.
 * @author yangyongbing
 */
public class OrderUtil {
    //格式化百度订单状态
    public static  int tranBdOrderStatus(Integer status){
        switch (status){
            case Constants.POS_ORDER_SUSPENDING:
                return Constants.BD_SUSPENDING;
            case Constants.POS_ORDER_CONFIRMED:
                return Constants.BD_CONFIRMED;
            case Constants.POS_ORDER_DELIVERY:
                return Constants.BD_DELIVERY;
            case Constants.POS_ORDER_COMPLETED:
                return Constants.BD_COMPLETED;
            case Constants.POS_ORDER_CANCELED:
                return Constants.BD_CANCELED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化京东到家订单状态
    public static int tranJHOrderStatus(int status){
        switch (status){
            case Constants.POS_ORDER_SUSPENDING:
                return Constants.JH_ORDER_WAITING;
            case Constants.POS_ORDER_CONFIRMED:
                return Constants.JH_ORDER_RECEIVED;
            case Constants.POS_ORDER_DELIVERY:
                return Constants.JH_ORDER_DELIVERING;
            case Constants.POS_ORDER_COMPLETED:
                return Constants.JH_ORDER_CONFIRMED;
            case Constants.POS_ORDER_CANCELED:
                return Constants.JH_ORDER_USER_CANCELLED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }

    //格式化美团订单状态
    public static int tranMTOrderStatus(int status){
        switch (status){
            case Constants.POS_ORDER_SUSPENDING:
                return Constants.MT_STATUS_CODE_UNPROCESSED;
            case Constants.POS_ORDER_CONFIRMED:
                return Constants.MT_STATUS_CODE_CONFIRMED;
            case Constants.POS_ORDER_DELIVERY:
                return Constants.MT_STATUS_CODE_DELIVERY;
            case Constants.POS_ORDER_COMPLETED:
                return Constants.MT_STATUS_CODE_COMPLETED;
            case Constants.POS_ORDER_CANCELED:
                return Constants.MT_STATUS_CODE_CANCELED;
            case Constants.POS_ORDER_DISPATCH_GET:
                return Constants.MT_STATUS_CODE_RECEIVED;
            default:
                return Constants.POS_ORDER_OTHER;
        }
    }


    //格式化饿了么订单状态
    public static String tranELOrderStatus(int status){
        switch (status){
            case Constants.POS_ORDER_SUSPENDING:
                return "unprocessed";
            case Constants.POS_ORDER_CONFIRMED:
                return "valid";
            case Constants.POS_ORDER_DISPATCH_GET:
                return "tobeFetched";
            case Constants.POS_ORDER_DELIVERY:
                return "delivering";
            case Constants.POS_ORDER_COMPLETED:
                return "settled";
            case Constants.POS_ORDER_CANCELED:
                return "invalid";
            default:
                return "";
        }
    }

}

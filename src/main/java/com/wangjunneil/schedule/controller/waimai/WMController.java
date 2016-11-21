package com.wangjunneil.schedule.controller.waimai;

import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.common.EnumDescription;
import com.wangjunneil.schedule.entity.baidu.Shop;
import com.wangjunneil.schedule.service.WMFacadeService;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by yangwanbin on 2016-11-14.
 */
@Controller
@RequestMapping("/waimai")
public class WMController {

    private static Logger log = Logger.getLogger(WMController.class.getName());

    @Autowired
    private WMFacadeService wmFacadeService;

//region 商户

    /**
     * 门店开业
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/startBusiness.php", method = RequestMethod.GET)
    public String startBusiness(PrintWriter out, HttpServletResponse resp) throws ScheduleException {

            resp.setContentType("text/html; charset=utf-8");
            //out.println(Enum.GetEnumDesc(Enum.OrderTypeBaiDu.E));
            Shop shop = new Shop();
            shop.setShopId("6225");
            //String result = baiDuApiService.GetRequestPars("shop.get", shop).toString();

            out.println("");

     return  null;
    }


    /**
     * 门店歇业
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/endBusiness.php", method = RequestMethod.GET)
    public String endBusiness(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }

//endregion



//region 商品

    /**
     * 上架
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/online.php", method = RequestMethod.GET)
    public String online(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }

    /**
     * 下架
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/offline.php", method = RequestMethod.GET)
    public String offline(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }

//endregion


//region 订单

    /**
     * 获取订单【消息型】 平台推送的订单
     *
     * @param out   响应输出流对象
     * @param resq  浏览器请求对象
     * @return
     */
    @RequestMapping(value = {"/baidu/order/new","/djsw/newOrder"}, method = RequestMethod.GET)
    public String orderPost(PrintWriter out, HttpServletRequest resq) {
        switch (resq.getPathInfo().toLowerCase()){
            case "/waimai/baidu/order/new": //百度
                out.println(wmFacadeService.orderPost(resq.getParameterMap(),Constants.PLATFORM_WAIMAI_BAIDU));
                break;
            case  "/waimai/djsw/neworder": //京东到家
                break;
            default:break;
        }

        return  null;
    }

    /**
     * 获取订单【主动抓取】 抓取平台订单
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/orderGet.php", method = RequestMethod.GET)
    public String orderGet(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }

    /**
     * 确认订单
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/orderConfirm.php", method = RequestMethod.GET)
    public String orderConfirm(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }


    /**
     * 取消订单
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/orderCancel.php", method = RequestMethod.GET)
    public String orderCancel(PrintWriter out, HttpServletResponse resp) throws SchedulerException {

        out.println("");
        return  null;
    }

//endregion

    //备注：需要提供接口用于中台系统下发门店编码对照信息

}

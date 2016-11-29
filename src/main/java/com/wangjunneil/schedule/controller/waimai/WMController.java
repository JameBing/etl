package com.wangjunneil.schedule.controller.waimai;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.bcel.internal.generic.SWITCH;
import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.common.Enum;
import com.wangjunneil.schedule.common.EnumDescription;
import com.wangjunneil.schedule.entity.baidu.Shop;
import com.wangjunneil.schedule.entity.baidu.SysParams;
import com.wangjunneil.schedule.entity.common.FlowNum;
import com.wangjunneil.schedule.service.WMFacadeService;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.log4j.Logger;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartRequest;
/**
 * Created by yangwanbin on 2016-11-14.
 */
@Controller
@RequestMapping("/waimai")
public class WMController {

    @Autowired
    private WMFacadeService wmFacadeService;

    @RequestMapping(value = {"/jdhome","/baidu","/eleme","/meituan"})
    public String  appCallback(PrintWriter out,HttpServletRequest request, HttpServletResponse response){
        String result = "",platform = null;
        Map<String,String[]> stringMap;
        response.setContentType("application/json;charset=uft-8");
        switch (request.getPathInfo().toLowerCase()){
            case "/waimai/baidu":  //百度
                platform = Constants.PLATFORM_WAIMAI_BAIDU;
                stringMap = request.getParameterMap();
                break;
            case "/waimai/jdhome": //京东到家
                platform = Constants.PLATFORM_WAIMAI_JDHOME;
                stringMap = request.getParameterMap();
                break;
            case "/waimai/eleme":  //饿了么
                platform = Constants.PLATFORM_WAIMAI_ELEME;
                stringMap = request.getParameterMap();
                break;
            case "/waimai/meituan": //美团
                platform = Constants.PLATFORM_WAIMAI_MEITUAN;
                stringMap = request.getParameterMap();
                break;
            default:
                stringMap = new HashMap<String,String[]>();
                break;
        }
        out.println(wmFacadeService.appReceiveCallBack(stringMap,platform));
        return  null;
    }
//region 商户

    /**
     * 门店开业
     *
     * @param out   响应输出流对象
     * @param request 请求对象 ｛baidu:{shopId:"",platformId:""},jdhome:{},meituan:{},eleme:{}｝
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/startBusiness.php", method = RequestMethod.GET)
    public String startBusiness( PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws ScheduleException {
        response.setContentType("application/json;charset=uft-8");
        out.println(wmFacadeService.startBusiness(request.getParameterMap()));
     return  null;
    }


    /**
     * 门店歇业
     *
     * @param out   响应输出流对象
     * @param request 请求对象
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/endBusiness.php", method = RequestMethod.GET)
    public String endBusiness(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        response.setContentType("application/json;charset=uft-8");
        out.println(wmFacadeService.endBusiness(request.getParameterMap()));
        return  null;
    }

//endregion



//region 商品

    /**
     * 上架
     *
     * @param out   响应输出流对象
     * @param request {baidu:[{shopId:"",platformShopId:"",dishId:"",platformDishId:""}],jdhome:[],eleme:[],meituan:[]}
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/online", method = RequestMethod.POST)
    public String online( PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        response.setContentType("application/json;charset=uft-8");
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = request.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            String str = sb.toString();
            //out.println(wmFacadeService.online(request.getParameterMap()));
            out.println(str);
        }catch (Exception ex){

        }
        return  null;
    }

    /**
     * 下架
     *
     * @param out   响应输出流对象
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/offline", method = RequestMethod.GET)
    public String offline(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        response.setContentType("application/json;charset=uft-8");
        out.println(wmFacadeService.online(request.getParameterMap()));
        return  null;
    }

//endregion


//region 订单

    /**
     * 获取订单【消息型】 平台推送的订单
     *
     * @param out   响应输出流对象
     * @param response  浏览器请求对象
     * @return
     */
    @RequestMapping(value = {"/baidu/order/new","/djsw/newOrder"}, method = {RequestMethod.GET,RequestMethod.POST})
    public String orderPost(PrintWriter out, HttpServletRequest request,HttpServletResponse response) {

        String platform = null;
        switch (request.getPathInfo().toLowerCase()){
            case "/waimai/baidu/order/new": //百度
                platform = Constants.PLATFORM_WAIMAI_BAIDU;
                response.setContentType("text/html; charset=utf-8");
                break;
            case  "/waimai/djsw/neworder": //京东到家
                platform = Constants.PLATFORM_WAIMAI_JDHOME;
                break;
            default:
                break;
        }
        if (!StringUtil.isEmpty(platform))
        out.println(wmFacadeService.orderPost(request.getParameterMap(),platform));
        return  null;
    }

    /**
     * 订单状态推送【消息型】 平台推送订单的状态变更
     *
     * @param out   响应输出流对象
     * @param request  浏览器请求对象
     * @return
     */
    @RequestMapping(value = {"/baidu/order/status","/djsw/orderAdjust"},method = {RequestMethod.GET,RequestMethod.POST})
    public String orderStatus(PrintWriter out,HttpServletRequest request,HttpServletResponse response){;
        String platfrom = null;
        switch (request.getPathInfo().toLowerCase()){
            case "/waimai/baidu/order/status"://百度
                platfrom = Constants.PLATFORM_WAIMAI_BAIDU;
                response.setContentType("text/html; charset=utf-8");
                break;
            case "/waimai/djsw/orderAdjust": //京东到家
                platfrom = Constants.PLATFORM_WAIMAI_JDHOME;
                break;
            default:break;
        }
       out.println( wmFacadeService.orderStatus(request.getParameterMap(),platfrom));
        return  null;
    }

    /**
     * 获取订单【主动抓取】 抓取平台订单
     *
     * @param out   响应输出流对象
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/orderGet.php", method = RequestMethod.GET)
    public String orderGet(PrintWriter out, HttpServletResponse response) throws SchedulerException {

        out.println("");
        return  null;
    }

    /**
     * 确认订单
     * @param out   响应输出流对象
     * @param response  浏览器响应对象
     * @request request 浏览器请求对象 约定格式：{baidu:"0001,0002",jdhome:"0003,0004"}
     * @return
     */
    @RequestMapping(value = "/orderConfirm.php", method = RequestMethod.GET)
    public String orderConfirm(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        response.setContentType("application/json;charset=uft-8");
        String reponseStr = wmFacadeService.orderConfirm(request.getParameterMap());
        out.println(reponseStr);
        return  null;
    }


    /**
     * 取消订单
     *
     * @param out   响应输出流对象
     * @param response  浏览器响应对象
     * @return
     */
    @RequestMapping(value = "/orderCancel.php", method = RequestMethod.GET)
    public String orderCancel(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws SchedulerException {
        response.setContentType("application/json;charset=uft-8");
        out.println(wmFacadeService.orderCancel(request.getParameterMap()));
        return  null;
    }



//endregion

    //备注：需要提供接口用于中台系统下发门店编码对照信息

    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String test1(PrintWriter out,HttpServletRequest request, HttpServletResponse response) throws  ScheduleException{
        String tmp = "{\n" +
            "    \"body\": {\n" +
            "        \"errno\": 0,\n" +
            "        \"error\": \"success\"\n" +
            "    },\n" +
            "    \"cmd\": \"resp.shop.list\",\n" +
            "    \"encrypt\": \"\",\n" +
            "    \"sign\": \"549B9BE31349B53F9ACE0657D17DBC86\",\n" +
            "    \"source\": \"30325\",\n" +
            "    \"ticket\": \"D7E3F9E1-BC38-0073-1612-C437748254D2\",\n" +
            "    \"timestamp\": 1479105805,\n" +
            "    \"version\": \"3\"\n" +
            "}";

        String flowNum = "{\"date\":\"20161128\",\"moudle\":\"order\",\"flowNum\":2000}";//实例请求参数
        out.println(HttpUtil.post2("http://127.0.0.1:9001/mark/waimai/test2", "cmd=232323","multipart/form-data","utf-8",null,null,Constants.PLATFORM_WAIMAI_BAIDU));//运行方法，这里输出：
        return null;
    }

    @RequestMapping(value = "/test2",method = RequestMethod.POST)
    public String test2(PrintWriter out,HttpServletRequest request, HttpServletResponse response){

        return null;
    }
}

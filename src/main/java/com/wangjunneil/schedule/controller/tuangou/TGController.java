package com.wangjunneil.schedule.controller.tuangou;

import com.wangjunneil.schedule.entity.tuangou.CouponRequest;
import com.wangjunneil.schedule.service.TGFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyongbing
 * @since  2017/3/20.
 */
@Controller
@MultipartConfig
@RequestMapping("/tuangou")
public class TGController {

    @Autowired
    private TGFacadeService tgFacadeService;

    @RequestMapping(value = "/callBack",method = RequestMethod.POST)
    @ResponseBody
    public String  appCallback(PrintWriter out,HttpServletRequest request, HttpServletResponse response){
        response.setContentType("text/html;charset=utf-8");
        Map<String,String[]> stringMap = new HashMap<>();
        stringMap = request.getParameterMap();
        String json = tgFacadeService.appCallBack(stringMap);
        out.println(json);
        return null;
    }

    //已验卷码查询
    @ResponseBody
    @RequestMapping(value = "/coupon/queryById",method = RequestMethod.POST,consumes = "application/json;charset=utf-8")
    public String CouponQueryById(PrintWriter out,HttpServletRequest request,HttpServletResponse response,@RequestBody CouponRequest couponRequest){
        String couponCode = couponRequest.getCouponCode();
        String ePoiId = couponRequest.getePoiId();
        String json = tgFacadeService.couponQueryById(couponCode, ePoiId);
        out.println(json);
        return  null;
    }

    //验卷准备
    @ResponseBody
    @RequestMapping(value = "/coupon/couponPrepare",method = RequestMethod.POST)
    public String CouponPrepare(PrintWriter out,HttpServletRequest request,HttpServletResponse response,@RequestBody CouponRequest couponRequest){
        response.setContentType("text/html;charset=utf-8");
        String couponCode = couponRequest.getCouponCode();
        String ePoiId = couponRequest.getePoiId();
        String json = tgFacadeService.couponPrepare(couponCode, ePoiId);
        out.println(json);
        return null;
    }

    //执行验卷
    @ResponseBody
    @RequestMapping(value = "/coupon/couponConsume",method = RequestMethod.POST)
    public String CouponConsume(PrintWriter out,HttpServletRequest request,HttpServletResponse response,@RequestBody CouponRequest couponRequest){
        response.setContentType("text/html;charset=utf-8");
        String json = tgFacadeService.couponConsume(couponRequest);
        out.println(json);
        return null;
    }

    //取消验卷
    @ResponseBody
    @RequestMapping(value = "/coupon/couponCancel",method = RequestMethod.POST)
    public String CouponCancel(PrintWriter out,HttpServletRequest request,HttpServletResponse response,@RequestBody CouponRequest couponRequest){
        response.setContentType("text/html;charset=utf-8");
        String json = tgFacadeService.couponCancel(couponRequest);
        out.println(json);
        return null;
    }


    //查询绑定门店某天在新美大的验券历史
    @ResponseBody
    @RequestMapping(value = "/coupon/queryListByDate",method = RequestMethod.POST)
    public String queryListByDate (PrintWriter out,HttpServletRequest request,HttpServletResponse response,@RequestBody CouponRequest couponRequest){
        response.setContentType("text/html;charset=utf-8");
        String json = tgFacadeService.queryListByDate(couponRequest);
        out.println(json);
        return null;
    }
}

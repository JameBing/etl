package com.wangjunneil.schedule.controller.meituan;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.service.MeiTuanFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * @author liuxin
 * @since 2016-11-17.
 */
@Controller
@RequestMapping("/mt")
public class MeiTuanController {

    private static Logger log = Logger.getLogger(MeiTuanController.class.getName());

    @Autowired
    private MeiTuanFacadeService mtFacadeService;


    //测试开店接口
    @RequestMapping(value = "/mtapi/openshop",method = RequestMethod.GET)
    public String openShop(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws ScheduleException{
        resp.setContentType("text/html;charset=utf-8");
        String code = "test_poi_01";
        String params = mtFacadeService.openShop(code);
        out.println(params);
        out.close();
        return null;
    }

    //测试关店接口
    @RequestMapping(value = "/mtapi/closeshop",method = RequestMethod.GET)
    public String closeShop(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws ScheduleException{
        resp.setContentType("text/html;charset=utf-8");
        String code = "test_poi_01";
        String params = mtFacadeService.closeShop(code);
        out.println(params);
        out.close();
        return null;
    }


    //测试商家确认订单接口
    @RequestMapping(value = "/mtapi/confirmOrder",method = RequestMethod.GET)
    public String confirmOrder(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        long order_id = 2703090006L;
        String params = mtFacadeService.getConfirmOrder(order_id);
        out.println(params);
        out.close();
        return null;
    }


    //测试商家取消订单接口
    @RequestMapping(value = "/mtapi/cancelOrder",method = RequestMethod.GET)
    public String cancelOrder(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp) throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        long order_id = 2703210907L;
        String reason = "测试取消订单";
        String reason_code = "1001";
        String params = mtFacadeService.getCancelOrder(order_id, reason, reason_code);
        out.println(params);
        out.close();
        return null;
    }




    //获取订单信息添加数据库
    @ResponseBody
    @RequestMapping(value = "/mtapi/newOrder",method = RequestMethod.GET)
    public JSONObject newOrder(HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        String orderId = req.getParameter("order_id ");
        String status = req.getParameter("status");
        String rtnJson = mtFacadeService.newOrder(orderId,status);
        return  null;
    }

}

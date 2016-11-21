package com.wangjunneil.schedule.controller.mt;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.service.MtFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.logging.Logger;

/**
 * Created by liuxin on 2016-11-17.
 */
@Controller
@RequestMapping("/mt")
public class MtController {


    private static Logger log = Logger.getLogger(MtController.class.getName());

    @Autowired
    private MtFacadeService mtFacadeService;


    //测试关店接口
    public String closeStore(PrintWriter out,HttpServletRequest resq, HttpServletResponse resp){
        resp.setContentType("text/html;charset=utf-8");
        String code = "22222";
        String params = mtFacadeService.closeShop(code);
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

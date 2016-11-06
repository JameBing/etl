package com.wangjunneil.schedule.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Notify;
import com.wangjunneil.schedule.service.SysFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Properties;

/**
 * Created by xuzhicheng on 8/25/16.
 */
@Controller
@RequestMapping("/sys")
public class SysController {
    @Autowired
    private SysFacadeService sysFacadeService;

    @RequestMapping(value="/findAllPlatform.php")
    public String findAllPlatform(PrintWriter out, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        Properties props = new Properties();
        props.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
        String activeMQ = props.getProperty("mq.host.url");
        JSONObject json = new JSONObject();
        json.put("platformList", sysFacadeService.findAllCfg());
        json.put("activeMQ",activeMQ);
        String returnJson = JSONObject.toJSONString(json);
        out.print(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value="/addPlatform.php")
    public String addPlatform(PrintWriter out,HttpServletRequest request,HttpServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        Properties props = new Properties();
        StringBuffer callBack = new StringBuffer();
        String appKey = request.getParameter("appKey");
        String appSecret = request.getParameter("appSecret");
        String platform = request.getParameter("platform");
        String optionField = request.getParameter("optionField");
        String notifyType = request.getParameter("notifyType");
        String notifyAddr = request.getParameter("notifyAddr");
        String editType = request.getParameter("editType");
        props.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
        String domain = props.getProperty("server.http.domain");
        if(Constants.PLATFORM_JD.equals(platform)){
            callBack.append("http://").append(domain).append("/mark/jd/callback");
        }else if(Constants.PLATFORM_TM.equals(platform)){
            callBack.append("http://").append(domain).append("/mark/tmall/callback");
        }else if("meituan".equals(platform)){
            callBack.append("http://").append(domain).append("/mark/meituan/callback");
        }else if("z8".equals(platform)){
            callBack.append("http://").append(domain).append("/mark/zhe800/callback");
        }
        Notify notify = new Notify(notifyType,notifyAddr);
        Cfg cfg = new Cfg(platform,appKey, appSecret, optionField, callBack.toString());
        cfg.setNotify(notify);
        sysFacadeService.addPlatform(cfg,editType);
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    @RequestMapping(value = "/delPlatform.php")
    public String delPlatform(PrintWriter out, HttpServletResponse response, HttpServletRequest request) throws Exception {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        Properties props = new Properties();
        StringBuffer callBack = new StringBuffer();
        String appKey = request.getParameter("appKey");
        String appSecret = request.getParameter("appSecret");
        String platform = request.getParameter("platform");
        String notifyAddr = request.getParameter("notifyAddr");
        String optionField = "";
        if("juanpi".equals(platform)){
            optionField = request.getParameter("optionField");
        }

        props.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
        String domain = props.getProperty("server.http.domain");
        if(Constants.PLATFORM_JD.equals(platform)){
            callBack.append("http://").append(domain).append("/mark/jd/callback");
        }else if(Constants.PLATFORM_TM.equals(platform)){
            callBack.append("http://").append(domain).append("/mark/tmall/callback");
        }
        Cfg cfg = new Cfg(platform,appKey,appSecret,optionField,callBack.toString());
        sysFacadeService.delPlatform(cfg);
        out.println("{\"status\":0}");
        out.close();
        return null;
    }
}

package com.wangjunneil.schedule.controller.sys;

import com.wangjunneil.schedule.entity.sys.User;
import com.wangjunneil.schedule.service.CmsFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created by wangjun on 8/8/16.
 */
@Controller
@RequestMapping("/cms")
public class CmsController {

    @Autowired
    private CmsFacadeService cmsFacadeService;

    @RequestMapping(value = "/login.php")
    public String login(PrintWriter out, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        user = cmsFacadeService.login(user);
        if (user == null) {
            out.println("{\"status\":1,\"message\":\"username or password error\"}");
        } else {
            request.getSession().setAttribute("user", user);
            out.println("{\"status\":0}");
        }

        out.close();
        return null;
    }

    @RequestMapping(value = "/addUser.php")
    public String addUser(PrintWriter out, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        Properties props = new Properties();
        StringBuffer callBack = new StringBuffer();
        try {
            props.load(this.getClass().getClassLoader().getResourceAsStream("server.properties"));
            String domain = props.getProperty("server.http.domain");
            callBack.append("http://").append(domain).append("/mark/jd/callback");
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            int expireIn = Integer.parseInt(request.getParameter("expireIn"));
            User user = new User(username, password, expireIn);
            String returnJson = cmsFacadeService.addUser(user);

            out.println(returnJson);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/findAllUser.php")
    public String findAllUser(PrintWriter out, HttpServletResponse response, HttpServletRequest request) {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        String returnJson = cmsFacadeService.findAllUser();
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/delUser.php")
    public String delUser(PrintWriter out, HttpServletResponse response, HttpServletRequest request){
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        int expireIn = Integer.parseInt(request.getParameter("expireIn"));
        User user = new User(username,password,expireIn);
        String returnJson = cmsFacadeService.delUser(user);
        out.println(returnJson);
        out.close();
        return null;
    }



}

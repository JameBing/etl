package com.wangjunneil.schedule.interceptor;

import com.wangjunneil.schedule.entity.sys.User;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by xuzhicheng on 2016/9/1.
 */
public class SessionController extends HandlerInterceptorAdapter {
    private static Logger log = Logger.getLogger(SessionController.class.getName());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendError(777);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        try {
            super.postHandle(request, response, handler, modelAndView);
        } catch (Exception e) {

        }
    }
}

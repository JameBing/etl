package com.wangjunneil.schedule.interceptor;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by wangjun on 8/3/16.
 */
public class AccessController extends HandlerInterceptorAdapter {

    private static Logger log = Logger.getLogger(AccessController.class.getName());

    private String whitelist;

    public void setWhitelist(String whitelist) {
        this.whitelist = whitelist;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 未配置白名单列表默认不控制访问
        if (whitelist == null || "".equals(whitelist)) return true;

        String ip = request.getRemoteAddr();
        if (log.isInfoEnabled()) {
            log.info("[" + ip + "] request " + request.getRequestURI() + " params " + request.getParameterMap());
        }

        List<String> list = getWhiteList(whitelist);
        long count = list.stream().filter(ip::equals).count();

        // 访客存在白名单
        if (count != 0) {
            // 设置统一消息头
            response.setContentType("application/json; charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            // 通过本次请求
            return true;
        }

        log.info("non-white list, Access Denied");
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws IOException {
        // response.setContentType("application/json; charset=UTF-8");
        // response.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String returnJson;
        if (ex != null) {
            log.error(ex.toString());

            String exMessage = ex.getMessage();
            exMessage = exMessage == null || "".equals(exMessage) ? "系统错误" : exMessage;
            returnJson = "{\"status\":1,\"message\":\"" + exMessage + "\"}";
        } else {
            returnJson ="{\"status\":0}";
        }

        out.println(returnJson);
        out.close();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }

    // -------------------------------------------------------------------------------------------------- private method

    /**
     * 根据配置文件中的白名单字符串获取可能分割的白名单列表
     *
     * @param whitelist 白名单字符串, 如 221.6.35.18,192.168.1.180
     * @return 白名单IP地址列表, 如 [221.6.35.18,192.168.1.180]
     */
    private List<String> getWhiteList(String whitelist) {
        whitelist = whitelist.trim();

        // 配置容错, 可能的分隔符结尾
        if (whitelist.endsWith(",")) {
            log.warn("the node [server.http.whiteList] configuration has question");
            whitelist = whitelist.substring(0, whitelist.lastIndexOf(","));
        }


        List<String> list = new ArrayList<>();
        String [] whites = whitelist.split(",");
        for (String white : whites) {
            list.add(white.trim());
        }

        return list;
    }

}

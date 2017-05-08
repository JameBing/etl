package com.wangjunneil.schedule.controller.waimai;

import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.OrderWaiMaiHistory;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.z8.Z8CrmOrder;
import com.wangjunneil.schedule.service.WMFacadeService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangyongbing
 * @since 2017/5/4.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private WMFacadeService wmFacadeService;

    /**
     * 今日订单查询
     * @param out
     * @param request
     * @return
     */
    @RequestMapping(value = "/getNowDayOrder.php")
    public String getNowDayOrderOrder(PrintWriter out, HttpServletRequest request){
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("sellerId", request.getParameter("sellerId"));
        paramMap.put("shopId", request.getParameter("shopId"));
        paramMap.put("orderId", request.getParameter("orderId"));
        paramMap.put("platformOrderId", request.getParameter("platformOrderId"));
        paramMap.put("orderStatus", request.getParameter("orderStatus"));
        paramMap.put("platform", request.getParameter("platform"));
        paramMap.put("receive", request.getParameter("receive"));

        Page<OrderWaiMai> page = new Page<>();
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson =wmFacadeService.getNowDayOrder(paramMap, page);
        //去除json 空格、回车、换行符、制表符  否则转json失败
        if(!StringUtil.isEmpty(returnJson)){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(returnJson);
            returnJson = m.replaceAll("");
        }
        out.println(returnJson);
        out.close();
        return null;
    }


    /**
     * 历史订单查询
     * @param out
     * @param request
     * @return
     */
    @RequestMapping(value = "/getHistoryOrder.php")
    public String getHistoryOrder(PrintWriter out, HttpServletRequest request){
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("sellerId", request.getParameter("sellerId"));
        paramMap.put("shopId", request.getParameter("shopId"));
        paramMap.put("orderId", request.getParameter("orderId"));
        paramMap.put("platformOrderId", request.getParameter("platformOrderId"));
        paramMap.put("orderStatus", request.getParameter("orderStatus"));
        paramMap.put("platform", request.getParameter("platform"));
        paramMap.put("receive", request.getParameter("receive"));
        paramMap.put("startDate",request.getParameter("startDate"));
        paramMap.put("endDate",request.getParameter("endDate"));

        Page<OrderWaiMaiHistory> page = new Page<>();
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson =wmFacadeService.getHistoryOrder(paramMap, page);
        //去除json 空格、回车、换行符、制表符  否则转json失败
        if(!StringUtil.isEmpty(returnJson)){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(returnJson);
            returnJson = m.replaceAll("");
        }
        out.println(returnJson);
        out.close();
        return null;
    }


    /**
     * 异常日志查询
     * @param out
     * @param request
     * @return
     */
    @RequestMapping(value = "/getLogInfo.php")
    public String getLogsInfo(PrintWriter out, HttpServletRequest request){
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("logId", request.getParameter("logId"));
        paramMap.put("platform", request.getParameter("platform"));
        paramMap.put("exceptionType", request.getParameter("exceptionType"));
        paramMap.put("startDate",request.getParameter("startDate"));
        paramMap.put("endDate",request.getParameter("endDate"));

        Page<Log> page = new Page<>();
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson =wmFacadeService.getLogsInfo(paramMap, page);
        //去除json 空格、回车、换行符、制表符  否则转json失败
        if(!StringUtil.isEmpty(returnJson)){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(returnJson);
            returnJson = m.replaceAll("");
        }
        out.println(returnJson);
        out.close();
        return null;
    }

}

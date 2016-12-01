package com.wangjunneil.schedule.controller.tm;

import com.taobao.api.FileItem;
import com.taobao.api.request.LogisticsOnlineSendRequest;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Page;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.tm.TmallCrmOrder;
import com.wangjunneil.schedule.entity.tm.TmallCrmRefund;
import com.wangjunneil.schedule.entity.tm.TmallOrderRequest;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.TmallFacadeService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Controller
@RequestMapping("/tmall")
public class TmallController {

    private static Logger log = LoggerFactory.getLogger(Constants.PLATFORM_TM);

    @Autowired
    private TmallFacadeService tmallFacadeService;

    @Autowired
    private SysFacadeService sysFacadeService;

    @Autowired
    private SysInnerService sysInnerServer;

    // --------------------------------------------------------------------------------------------------- public method

    @RequestMapping(value = "/callback")
    public String callback(HttpServletResponse resp, HttpServletRequest req) throws IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");

        String error = req.getParameter("error");
        if (error != null) {
            String errorDesc = req.getParameter("error_description");
            log.error(errorDesc);

            resp.sendRedirect("/console/main.html#/error?message=" + errorDesc);
            return null;
        }

        String code = req.getParameter("code");
        String state = req.getParameter("state");

        try {
            Cfg cfg = sysFacadeService.findTmCfg();
            tmallFacadeService.callback(code, state, cfg);

            resp.sendRedirect("/console/main.html");
        } catch (Exception e) {
            log.error(e.toString());
            resp.sendRedirect("/console/main.html#/error?message=Tmall authorize callback error");
        }

        return null;
    }

    @RequestMapping(value = "/controlStatus.php")
    public String controlStatus(PrintWriter out){
        String returnJson = tmallFacadeService.getControlStatus();
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/startListening.php")
    public String startListening(PrintWriter out) throws ScheduleException {
        Cfg cfg = sysFacadeService.findTmCfg();
        tmallFacadeService.startListening(cfg);
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_TM, true);
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    @RequestMapping(value = "/stopListening.php")
    public String stopListening(PrintWriter out) {
        Cfg cfg = sysFacadeService.findTmCfg();
        tmallFacadeService.stopListening(cfg);
        Status status = new Status();
        sysFacadeService.addPlatformStatus(status, Constants.PLATFORM_TM, false);
        out.println("{\"status\":0}");
        out.close();
        return null;
    }

    @RequestMapping(value = "/getHistoryOrder.php")
    public String getHistoryOrder(PrintWriter out, HttpServletRequest req) {
        TmallOrderRequest tmallOrderRequest = new TmallOrderRequest();
        tmallOrderRequest.setSkuId(req.getParameter("skuId"));
        tmallOrderRequest.settId(req.getParameter("orderId"));
        tmallOrderRequest.setOrderState(req.getParameter("orderState"));
        tmallOrderRequest.setProductName(req.getParameter("productName"));
        tmallOrderRequest.setTelephone(req.getParameter("telephone"));
        tmallOrderRequest.setStartDate(req.getParameter("startDate"));
        tmallOrderRequest.setEndDate(req.getParameter("endDate"));

        Page<TmallCrmOrder> page = new Page<>();
        String currentPage = req.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = req.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson = tmallFacadeService.getHistoryOrder(tmallOrderRequest, page);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/getRefundOrder.php")
    public String getRefundOrder(PrintWriter out, HttpServletRequest request){
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("tId", request.getParameter("tId"));
        paramMap.put("refundId", request.getParameter("refundId"));
        paramMap.put("status", request.getParameter("status"));
        paramMap.put("title", request.getParameter("title"));
        paramMap.put("sid", request.getParameter("sid"));
        paramMap.put("startDate", request.getParameter("startDate"));
        paramMap.put("endDate", request.getParameter("endDate"));

        Page<TmallCrmRefund> page = new Page<>();
        String currentPage = request.getParameter("currentPage");
        if (currentPage == null || "".equals(currentPage))
            currentPage = "1";
        String pageSize = request.getParameter("pageSize");
        if (pageSize != null && !"".equals(pageSize))
            page.setPageSize(Integer.parseInt(pageSize));
        page.setCurrentPage(Integer.parseInt(currentPage));

        String returnJson = tmallFacadeService.getRefundOrder(paramMap, page);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/outStock.php")
    public String outStock(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        long tid = Long.valueOf(req.getParameter("tid"));
        String outSid = req.getParameter("outSid");
        String companyCode = req.getParameter("companyCode");

        LogisticsOnlineSendRequest request = new LogisticsOnlineSendRequest();
        request.setTid(tid);
        request.setOutSid(outSid);
        request.setCompanyCode(companyCode);
        String returnJson;
        returnJson = tmallFacadeService.orderOutStock(request);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/dummySend.php")
    public String dummySend(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        long tid = Long.valueOf(req.getParameter("tid"));
        String returnJson = tmallFacadeService.orderDummySend(tid);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/logisticsList.php")
    public String logisticsList(PrintWriter out) throws ScheduleException {
        String returnJson = tmallFacadeService.logisticsList();
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/quantityUpdate.php")
    public String quantityUpdate(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        String outerId = req.getParameter("outerId");//商品id
        String outerSkuId = req.getParameter("outerSkuId");//商品skuId
        String num = req.getParameter("num");//库存数

        String returnJson = tmallFacadeService.skuQuantityUpdate(outerId, outerSkuId, num);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/fixOrder.php")
    public String fixOrder(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        String startDate    = req.getParameter("startDate");
        String endDate      = req.getParameter("endDate");

        TmallOrderRequest orderRequest = new TmallOrderRequest();
        orderRequest.setStartDate(startDate);
        orderRequest.setEndDate(endDate);

        Cfg cfg = sysFacadeService.findTmCfg();
        int size = tmallFacadeService.syncOrderByCond(cfg, orderRequest);
        String returnJson = "{\"status\":1,\"message\":" + size + "}";

        out.println(returnJson);
        out.close();
        return null;
    }


    /**
     * 提供日期范围内查询订单方法：本地拉订单数据使用
     * @param out
     * @param req
     * @return
     * @throws ScheduleException
     */
    @RequestMapping(value = "/queryAllOrder.php")
    public String queryAllOrderByCond(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        String startDate    = req.getParameter("startDate");
        String endDate      = req.getParameter("endDate");

        TmallOrderRequest orderRequest = new TmallOrderRequest();
        orderRequest.setStartDate(startDate);
        orderRequest.setEndDate(endDate);

        int size = tmallFacadeService.queryAllByCond(orderRequest);
        String returnJson = "{\"status\":1,\"message\":" + size + "}";

        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/refundReview.php")
    public String refundReview(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));
        paramMap.put("refundVersion", req.getParameter("refundVersion"));
        paramMap.put("operator", req.getParameter("operator"));
        paramMap.put("result", req.getParameter("result"));
        paramMap.put("message", req.getParameter("message"));

        String returnJson = tmallFacadeService.refundReview(paramMap);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/getTmUserNick.php")
    public String getTmUserNick(PrintWriter out, HttpServletRequest req) {
        String returnJson = sysInnerServer.getTmToken().getTaobao_user_nick();
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/refundAgree.php")
    public String refundAgree(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("amount", req.getParameter("amount"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));
        paramMap.put("refundVersion", req.getParameter("refundVersion"));
        paramMap.put("code",req.getParameter("code"));

        String returnJson = tmallFacadeService.refundAgree(paramMap);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/sellerAddrList.php")
    public String sellerAddrList(PrintWriter out) throws ScheduleException {
        String returnJson = tmallFacadeService.sellerAddrList();
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/returnGoodsAgree.php")
    public String returnGoodsAgree(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));
        paramMap.put("refundVersion", req.getParameter("refundVersion"));
        paramMap.put("name", req.getParameter("name"));
        paramMap.put("address", req.getParameter("address"));
        paramMap.put("post", req.getParameter("post"));
        paramMap.put("tel", req.getParameter("tel"));
        paramMap.put("mobile", req.getParameter("mobile"));
        paramMap.put("remark", req.getParameter("remark"));
        paramMap.put("sellerAddressId", req.getParameter("sellerAddressId"));

        String returnJson = tmallFacadeService.returnGoodsAgree(paramMap);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/getRefuseReasonList.php")
    public String getRefuseReasonList(PrintWriter out, HttpServletRequest req) throws ScheduleException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));

        String returnJson = tmallFacadeService.getRefuseReasonList(paramMap);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/refundRefuse.php")
    public String refundRefuse(PrintWriter out, HttpServletRequest req) throws ScheduleException, IOException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));
        paramMap.put("refundVersion", req.getParameter("refundVersion"));
        paramMap.put("refuseMessage", req.getParameter("refuseMessage"));
        paramMap.put("refuseReasonId", req.getParameter("refuseReasonId"));
        FileItem fileItem = getUploadFile(req);
        String returnJson = tmallFacadeService.returnGoodsRefuse(paramMap, fileItem);
        out.println(returnJson);
        out.close();
        return null;
    }

    @RequestMapping(value = "/returnGoodsRefuse.php")
    public String returnGoodsRefuse(PrintWriter out, HttpServletRequest req) throws ScheduleException, IOException {
        Map<String, String > paramMap = new HashMap<>();
        paramMap.put("refundId", req.getParameter("refundId"));
        paramMap.put("refundPhase", req.getParameter("refundPhase"));
        paramMap.put("refundVersion", req.getParameter("refundVersion"));
        paramMap.put("refuseReasonId", req.getParameter("refuseReasonId"));
        FileItem fileItem = getUploadFile(req);
        String returnJson = tmallFacadeService.returnGoodsRefuse(paramMap, fileItem);
        out.println(returnJson);
        out.close();
        return null;
    }

    private FileItem getUploadFile(HttpServletRequest req) throws IllegalStateException, IOException {
        FileItem fileItem = null;
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(req.getSession().getServletContext());
        //判断 req 是否有文件上传,即多部分请求
        if(multipartResolver.isMultipart(req)){
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)req;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while(iter.hasNext()){
                //取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if(file != null){
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if(myFileName.trim() !=""){
                        fileItem = new FileItem(myFileName, file.getBytes());
                        log.info("upload image:" + fileItem.getFileName());
                    }
                }
            }
        }
        return fileItem;
    }


























    /**
     * 对已订阅的消息进行授权
     *
     * @param out   响应输出流对象
     * @param resp  浏览器响应对象
     * @param request   客户端请求对象
     * @return
     */
//    @RequestMapping(value = "/tmcUserPermit")
//    public String tmcUserPermit(PrintWriter out, HttpServletResponse resp, HttpServletRequest request) {
//        resp.setCharacterEncoding("utf-8");
//
//        String appkey = "23431719";
//        String secret = "308a1c2563be288a03c1b1eae31beeda";
//        String sessionKey = "620121400fa99c65443679ZZe287124c49b7d574a21a9152232897825";
//
//        String topics = request.getParameter("topics");
//        //对已订阅的消息进行授权
//        TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
//        TmcUserPermitRequest req = new TmcUserPermitRequest();
//        req.setTopics(topics);//消息主题列表，用半角逗号分隔。当用户订阅的topic是应用订阅的子集时才需要设置，不设置表示继承应用所订阅的所有topic，一般情况建议不要设置。
//        TmcUserPermitResponse rsp = null;
//        try {
//            rsp = client.execute(req, sessionKey);
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }
//        out.println(rsp.getBody());
//        out.close();
//        return null;
//    }

}

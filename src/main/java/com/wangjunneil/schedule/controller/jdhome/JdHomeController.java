package com.wangjunneil.schedule.controller.jdhome;


import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.QueryStockRequest;
import com.wangjunneil.schedule.entity.jdhome.shopCategory;
import com.wangjunneil.schedule.service.JdHomeFacadeService;
import com.wangjunneil.schedule.utility.HttpsUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import o2o.openplatform.sdk.dto.WebRequestDTO;
import o2o.openplatform.sdk.util.SignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangyongbing
 * @since 2016/11/15.
 */
@Controller
@RequestMapping("/jdHome")
public class JdHomeController {

    private static Logger log = Logger.getLogger(JdHomeController.class.getName());

    @Autowired
    private JdHomeFacadeService jdHomeFacadeService;

    /**
     * 门店/歇业开业
     * @param out
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/openStore",method = RequestMethod.GET)
    public String openStore(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        //应用参数
        List<QueryStockRequest> listBaseStockCenterRequest = new ArrayList<QueryStockRequest>();
        QueryStockRequest queryStockRequest = new QueryStockRequest();
        // 测试数据
        String shopId = "10054394";
        queryStockRequest.setStationNo("10006172");
        queryStockRequest.setSkuId(1997342L);
        queryStockRequest.setDoSale(0);
        listBaseStockCenterRequest.add(queryStockRequest);
        String returnJson = jdHomeFacadeService.updateAllStockOn(listBaseStockCenterRequest,shopId);
        out.println(returnJson);
        out.close();
        return  null;
    }

    /**
     * 新增商品分类
     * @param out
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/addShopCategory", method=RequestMethod.GET)
    public String addShopCategory(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        shopCategory shopCategory = new shopCategory();
        shopCategory.setShopId("20001");
        shopCategory.setPid(000L);
        shopCategory.setShopCategoryLevel(0);
       // shopCategory.setCreatePin("aaa");//
        shopCategory.setSort(1);
        shopCategory.setShopCategoryName("bb");
        String returnJson = jdHomeFacadeService.addShopCategory(shopCategory);
        out.println(returnJson);
        out.close();
        return  null;
    }

    /**
     * 修改商品分类
     * @param out
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateShopCategory", method=RequestMethod.GET)
    public String updateShopCategory(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        shopCategory shopCategory = new shopCategory();
        shopCategory.setShopId("20001");
        shopCategory.setId(111L);
        shopCategory.setShopCategoryName("ccc");
        String returnJson = jdHomeFacadeService.updateShopCategory(shopCategory);
        out.println(returnJson);
        out.close();
        return  null;
    }

    /**
     * 删除商品分类
     * @param out
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/deleteShopCategory", method=RequestMethod.GET)
    public String deleteShopCategory(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        shopCategory shopCategory = new shopCategory();
        shopCategory.setShopId("20001");
        shopCategory.setId(111L);
        String returnJson = jdHomeFacadeService.deleteShopCategory(shopCategory);
        out.println(returnJson);
        out.close();
        return  null;
    }

    @ResponseBody
    @RequestMapping(value = "/djsw/newOrder",method = RequestMethod.GET)
    public JSONObject newOrder(@RequestBody JSONObject jsonObject)throws Exception{
        if(jsonObject == null){
            return jsonObject;
        }
        JSONObject json = jsonObject.getJSONObject("jd_param_json");
        String billId = json.getString("billId");
        String statusId = json.getString("statusId");
        String timestamp = json.getString("timestamp");
        String shopId = json.getString("shopId");
        String rtnJson = jdHomeFacadeService.newOrder(billId,statusId,timestamp,shopId);
        return  null;
    }

    /**
     * 商家确认接单接口
     * @param out
     * @param req
     * @param resp
     * @return
     */
    @RequestMapping(value = "/orderAcceptOperate",method = RequestMethod.GET)
    public String orderAcceptOperate(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        OrderAcceptOperate acceptOperate = new OrderAcceptOperate();
        acceptOperate.setOrderId("100001016163464");
        acceptOperate.setIsAgreed(true);
        acceptOperate.setOperator("yang");
        String json = jdHomeFacadeService.orderAcceptOperate(acceptOperate);
        out.println(json);
        out.close();
        return null;
    }

    @RequestMapping(value = "/testAddOrder",method = RequestMethod.GET)
    public String test(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        String json = jdHomeFacadeService.newOrder("", "", "","");
        out.println(json);
        out.close();
        return null;
    }


    public static void main(String[] args) throws Exception{
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("token", "585e8e9c-63da-43b4-8360-4fd38f778859");
        param.put("app_key", "811f96f894614a1bbcbff480330e6eb3");
        param.put("timestamp", "2015-09-29 16:35:27");
        param.put("sign", "360AD907950DDB9679C0D8888CFDCA2F");//4DB83B4CFBFEFBDB205326CE72402019//360AD907950DDB9679C0D8888CFDCA2F
        param.put("format", "json");
        param.put("v", "1.0");
        param.put("jd_param_json", "{\"pageNo\":\"1\",\"pageSize\":\"100\",\"beginOrderStartTime\":\"2015-09-29 00:00:00\",\"endOrderStartTime\":\"2015-09-29 23:59:59\", \"orderStatus\":\"90000\"}");

        /**
         * 计算签名实体
         */
        WebRequestDTO w = new WebRequestDTO();
        w.setApp_key("811f96f894614a1bbcbff480330e6eb3");
        w.setFormat("json");
        w.setJd_param_json("{\"pageNo\":\"1\",\"pageSize\":\"100\",\"beginOrderStartTime\":\"2015-09-29 00:00:00\",\"endOrderStartTime\":\"2015-09-29 23:59:59\", \"orderStatus\":\"90000\"}");
        w.setTimestamp("2015-09-29 16:35:27");
        w.setToken("585e8e9c-63da-43b4-8360-4fd38f778859");
        w.setV("1.0");
        try {
            System.out.println(SignUtils.getSign(w, "d4c20ee551eb4fb19795da5a83102b24"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = HttpsUtil.post("https://openo2o.jd.com/djapi/order/query", StringUtil.getUrlParamsByMap(param));
        System.out.println(result);
    }
}

package com.wangjunneil.schedule.controller.jdhome;


import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.entity.common.ParsFromPos;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.QueryStockRequest;
import com.wangjunneil.schedule.entity.jdhome.shopCategory;
import com.wangjunneil.schedule.service.JdHomeFacadeService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpsUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import com.wangjunneil.schedule.utility.URL;
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
import java.io.PrintWriter;
import java.util.*;

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
        List<ParsFromPosInner> listBaseStockCenterRequest = new ArrayList<ParsFromPosInner>();
        String shopId = "8888";
        ParsFromPosInner inner = new ParsFromPosInner();
        inner.setDishId("6666");
        inner.setShopId(shopId);
        listBaseStockCenterRequest.add(inner);
        /*for(int i=0;i<60;i++){
            QueryStockRequest queryStockRequest = new QueryStockRequest();
            queryStockRequest.setStationNo("10054394");
            queryStockRequest.setSkuId(1997342L);
            queryStockRequest.setDoSale(0);
            listBaseStockCenterRequest.add(queryStockRequest);
        }*/
        // 测试数据
        String returnJson = jdHomeFacadeService.updateAllStockOnAndOff(listBaseStockCenterRequest,0);
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
        shopCategory.setShopId("10054394");
        shopCategory.setPid(000L);
        shopCategory.setShopCategoryLevel(3);
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
        shopCategory.setShopId("10054394");
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
        shopCategory.setShopId("10054394");
        shopCategory.setId(111L);
        String returnJson = jdHomeFacadeService.deleteShopCategory(shopCategory);
        out.println(returnJson);
        out.close();
        return  null;
    }

    @RequestMapping(value = "/djsw/newOrder",method = RequestMethod.GET)
    public String newOrder(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
        String billId = "10003129";
        String statusId = "32000";
        String timestamp = "2015-10-16 13:23:30";
        String shopId = "10054394";
        //String rtnJson = jdHomeFacadeService.newOrder(billId,statusId,timestamp,shopId);
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
        acceptOperate.setShopId("10054394");
        acceptOperate.setOrderId("10000101612233");
        acceptOperate.setIsAgreed(false);
        acceptOperate.setOperator("yang");
        String json = jdHomeFacadeService.orderAcceptOperate(acceptOperate);
        out.println(json);
        out.close();
        return null;
    }

    @RequestMapping(value = "/testAddOrder",method = RequestMethod.GET)
    public String test(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");
      //  jdHomeFacadeService.callback("{\"token\":\"e22bb0bc-2b3e-4b35-9dfe-0234be439066\",\"expires_in\":\"31104000\",\"time\":\"1480406979337\",\"uid\":\"yangwanbin\",\"user_nick\":\"yangwanbin\"}","7244171");
        //out.println(json);
        String json = "{\"billId\":\"624573044000041\",\"statusId\":\"3333\"}";
        jdHomeFacadeService.pickFinishOrder(json,"4444");
        out.close();
        return null;
    }


    public static void main(String[] args) throws Exception{

        /**
         * 计算签名实体
         */
        WebRequestDTO w = new WebRequestDTO();
        w.setApp_key("1e51b69a380948e9a7697006beae0c92");
        w.setFormat("json");
       // w.setJd_param_json("{\"pageNo\":\"1\",\"pageSize\":\"100\",\"beginOrderStartTime\":\"2015-09-29 00:00:00\",\"endOrderStartTime\":\"2015-09-29 23:59:59\", \"orderStatus\":\"90000\"}");
        w.setTimestamp(DateTimeUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
        w.setToken("e22bb0bc-2b3e-4b35-9dfe-0234be439066");
        w.setV("1.0");
        w.setJd_param_json(null);
      /*  try {
            System.out.println(SignUtils.getSign(w, "d0200b5d27b14ff7b875ce1c6f0cf753"));
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        String sign = SignUtils.getSign(w, "d0200b5d27b14ff7b875ce1c6f0cf753");
        System.out.println(SignUtils.getSign(w, "d0200b5d27b14ff7b875ce1c6f0cf753"));
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("token","e22bb0bc-2b3e-4b35-9dfe-0234be439066");
        param.put("app_key","1e51b69a380948e9a7697006beae0c92");
        param.put("timestamp",w.getTimestamp());
        param.put("sign",sign);
        param.put("format","json");
        param.put("v","1.0");
        //param.put("jd_param_json","{\"pageNo\":\"1\",\"pageSize\":\"100\",\"beginOrderStartTime\":\"2015-09-29 00:00:00\",\"endOrderStartTime\":\"2015-09-29 23:59:59\", \"orderStatus\":\"90000\"}");

        String result = HttpsUtil.post(URL.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param));
        System.out.println(result);
    }
}

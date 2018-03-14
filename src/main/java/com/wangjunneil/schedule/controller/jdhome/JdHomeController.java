package com.wangjunneil.schedule.controller.jdhome;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.eleme.ShopEle;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.shopCategory;
import com.wangjunneil.schedule.service.EleMeFacadeService;
import com.wangjunneil.schedule.service.JdHomeFacadeService;
import com.wangjunneil.schedule.service.MeiTuanFacadeService;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

    @Autowired
    private EleMeFacadeService eleMeFacadeService;

    @Autowired
    private EleMeInnerService eleMeInnerService;

    @Autowired
    private MeiTuanFacadeService meiTuanFacadeService;

    @Autowired
    private SysFacadeService sysFacadeService;


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
        String shopId = "6666";
      /*  ParsFromPosInner inner = new ParsFromPosInner();
        inner.setDishId("6666");
        inner.setShopId(shopId);
        listBaseStockCenterRequest.add(inner);*/
        for(int i=0;i<10;i++){
            ParsFromPosInner inner = new ParsFromPosInner();
            inner.setDishId("88888");
            inner.setShopId(shopId);
            listBaseStockCenterRequest.add(inner);
        }
        // 测试数据
        String returnJson = jdHomeFacadeService.updateAllStockOnAndOff(listBaseStockCenterRequest,0);
        out.println(returnJson);
        out.close();
        return  null;
    }

    /**
     *
     *
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
        shopCategory.setShopId("2063064");
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
        shopCategory.setShopId("2063064");
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
        shopCategory.setShopId("2063064");
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
        acceptOperate.setOrderId("624573044000041");
        acceptOperate.setIsAgreed(false);
        acceptOperate.setOperator("yang");
       // String json = jdHomeFacadeService.orderAcceptOperate(acceptOperate);
       // out.println(json);
        out.close();
        return null;
    }

    @RequestMapping(value = "/testAddOrder",method = RequestMethod.GET)
    public String test(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception{
        resp.setContentType("text/html;charset=utf-8");

        /*List<ParsFromPosInner> dishList = new ArrayList<>();
        ParsFromPosInner posInner = new ParsFromPosInner();
        posInner.setDishId("50022");
        posInner.setShopId("2063064");
        dishList.add(posInner);
        ParsFromPosInner posInner1 = new ParsFromPosInner();
        posInner1.setDishId("1000");
        posInner1.setShopId("2063064");
        dishList.add(posInner1);
        ParsFromPosInner posInner2 = new ParsFromPosInner();
        posInner2.setDishId("50023");
        posInner2.setShopId("2063064");
        ParsFromPosInner posInner3 = new ParsFromPosInner();
        posInner3.setDishId("50020");
        posInner3.setShopId("2063064");
        dishList.add(posInner2);
        ParsFromPosInner posInner4 = new ParsFromPosInner();
        posInner4.setDishId("2222");
        posInner4.setShopId("2063064");
        dishList.add(posInner3);
        dishList.add(posInner4);*/
       // String json = jdHomeFacadeService.OrderJDZBDelivery("1111111","80010309");
        OrderWaiMai orderWaiMai = sysFacadeService.findOrderWaiMai("eleme","3019184111443869746");
        JSONObject jsonObject = sysFacadeService.formatOrder2Pos(orderWaiMai);

        /*jdHomeFacadeService.orderAcceptOperate("4444","2063064",true);*/
        //out.println(json);
        return null;
    }

    public static void main(String[] args) throws Exception{

       /* *//**
         * 计算签名实体
         *//*
        WebRequestDTO w = new WebRequestDTO();
        w.setApp_key("1e51b69a380948e9a7697006beae0c92");
        w.setFormat("json");
       // w.setJd_param_json("{\"pageNo\":\"1\",\"pageSize\":\"100\",\"beginOrderStartTime\":\"2015-09-29 00:00:00\",\"endOrderStartTime\":\"2015-09-29 23:59:59\", \"orderStatus\":\"90000\"}");
        w.setTimestamp(DateTimeUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss"));
        w.setToken("e22bb0bc-2b3e-4b35-9dfe-0234be439066");
        w.setV("1.0");
        w.setJd_param_json(null);
      *//*  try {
            System.out.println(SignUtils.getSign(w, "d0200b5d27b14ff7b875ce1c6f0cf753"));
        } catch (Exception e) {
            e.printStackTrace();
        }*//*
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

        String result = HttpsUtil.post(Constants.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param));
        System.out.println(result);*/

    }

    @RequestMapping(value = "/addShop",method = RequestMethod.GET)
    public String addShop(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception {
        resp.setContentType("text/html;charset=utf-8");
        String aa = "[\n" +
            "{\"shopId\":\"2135849\",\"shopName\":\"紫燕百味鸡（中桥店）\",\"sellerId\":\"80048011\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135850\",\"shopName\":\"紫燕百味鸡(沁扬店)\",\"sellerId\":\"80048010\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135852\",\"shopName\":\"紫燕百味鸡（稻香店）\",\"sellerId\":\"80048016\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135853\",\"shopName\":\"紫燕百味鸡(梅村店)\",\"sellerId\":\"80048008\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135854\",\"shopName\":\"紫燕百味鸡（五星店）\",\"sellerId\":\"80048002\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135855\",\"shopName\":\"紫燕百味鸡（新永丰）\",\"sellerId\":\"80048019\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135856\",\"shopName\":\"紫燕百味鸡（城色店）\",\"sellerId\":\"80048037\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135861\",\"shopName\":\"紫燕百味鸡(太湖店)\",\"sellerId\":\"80048005\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135862\",\"shopName\":\"紫燕百味鸡（叙康店）\",\"sellerId\":\"80048038\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135864\",\"shopName\":\"紫燕百味鸡(南湖店)\",\"sellerId\":\"80048040\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135867\",\"shopName\":\"紫燕百味鸡（盛岸店）\",\"sellerId\":\"80048032\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135869\",\"shopName\":\"紫燕百味鸡(山北店)\",\"sellerId\":\"80048023\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135872\",\"shopName\":\"紫燕百味鸡(蓉湖店)\",\"sellerId\":\"80048021\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2135873\",\"shopName\":\"紫燕百味鸡（颐和湾店）\",\"sellerId\":\"80048028\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146239\",\"shopName\":\"紫燕百味鸡(上马墩店)\",\"sellerId\":\"80048034\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146241\",\"shopName\":\"紫燕百味鸡（兴源店）\",\"sellerId\":\"80048027\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146244\",\"shopName\":\"紫燕百味鸡(东亭店)\",\"sellerId\":\"80048004\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146247\",\"shopName\":\"紫燕百味鸡（春潮店）\",\"sellerId\":\"80048006\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146249\",\"shopName\":\"紫燕百味鸡（五爱店）\",\"sellerId\":\"80048020\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146251\",\"shopName\":\"紫燕百味鸡(蠡湖店)\",\"sellerId\":\"80048014\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146256\",\"shopName\":\"紫燕百味鸡（民丰店）\",\"sellerId\":\"80048025\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146257\",\"shopName\":\"紫燕百味鸡（学士店）\",\"sellerId\":\"80048042\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146258\",\"shopName\":\"紫燕百味鸡(春江店)\",\"sellerId\":\"80048043\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146261\",\"shopName\":\"紫燕百味鸡（瑞江店）\",\"sellerId\":\"80048046\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146262\",\"shopName\":\"紫燕百味鸡（新坊前店）\",\"sellerId\":\"80048048\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146264\",\"shopName\":\"紫燕百味鸡（湖滨店）\",\"sellerId\":\"80048049\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146266\",\"shopName\":\"紫燕百味鸡（东绛店）\",\"sellerId\":\"80048050\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146267\",\"shopName\":\"紫燕百味鸡（西漳店）\",\"sellerId\":\"80048051\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146270\",\"shopName\":\"紫燕百味鸡(堰桥店)\",\"sellerId\":\"80048053\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146282\",\"shopName\":\"紫燕百味鸡（定安店）\",\"sellerId\":\"80048100\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146283\",\"shopName\":\"紫燕百味鸡（湖塘店）\",\"sellerId\":\"80048103\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146285\",\"shopName\":\"紫燕百味鸡（北直街店）\",\"sellerId\":\"80048109\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146289\",\"shopName\":\"紫燕百味鸡(翠竹店)\",\"sellerId\":\"80048102\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146295\",\"shopName\":\"紫燕百味鸡（紫阳店）\",\"sellerId\":\"80048114\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146296\",\"shopName\":\"紫燕百味鸡(河海店)\",\"sellerId\":\"80048116\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146297\",\"shopName\":\"紫燕百味鸡（锦绣店）\",\"sellerId\":\"80048117\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"2146298\",\"shopName\":\"紫燕百味鸡（浦南店）\",\"sellerId\":\"80048115\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "{\"shopId\":\"157660212\",\"shopName\":\"紫燕百味鸡（柏庄店）\",\"sellerId\":\"80048059\",\"platForm\":\"eleme\",\"city\":\"无锡\"},\n" +
            "\n" +
            "]";

        JSONArray jsonArray = JSONArray.parseArray(aa);
        List<ShopEle> shopEles = new ArrayList<>();
        for(int i=0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            ShopEle shopEle  = new ShopEle();
            shopEle.setPlatForm(jsonObject.getString("platForm"));
            shopEle.setCity(jsonObject.getString("city"));
            shopEle.setShopId(jsonObject.getString("shopId"));
            shopEle.setSellerId(jsonObject.getString("sellerId"));
            shopEle.setShopName(jsonObject.getString("shopName"));
            shopEles.add(shopEle);
        }
        eleMeInnerService.addSyncShops(shopEles);
        out.print("success");
        return null;
    }
}

package com.wangjunneil.schedule.controller.jdhome;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.ParsFromPosInner;
import com.wangjunneil.schedule.entity.eleme.ShopEle;
import com.wangjunneil.schedule.entity.jdhome.OrderAcceptOperate;
import com.wangjunneil.schedule.entity.jdhome.QueryStockRequest;
import com.wangjunneil.schedule.entity.jdhome.shopCategory;
import com.wangjunneil.schedule.service.EleMeFacadeService;
import com.wangjunneil.schedule.service.JdHomeFacadeService;
import com.wangjunneil.schedule.service.MeiTuanFacadeService;
import com.wangjunneil.schedule.service.SysFacadeService;
import com.wangjunneil.schedule.service.eleme.EleMeInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpsUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import o2o.openplatform.sdk.dto.WebRequestDTO;
import o2o.openplatform.sdk.util.SignUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        shopCategory.setShopId("80010265");
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

       /* List<ParsFromPosInner> dishList = new ArrayList<>();
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


        String json = eleMeFacadeService.restaurantMenu("2063064");
        out.println(json);
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

        String result = HttpsUtil.post(Constants.URL_JDHOME_STORE_ON, StringUtil.getUrlParamsByMap(param));
        System.out.println(result);
    }

    @RequestMapping(value = "/addShop",method = RequestMethod.GET)
    public String addShop(PrintWriter out,HttpServletRequest req, HttpServletResponse resp)throws Exception {
        resp.setContentType("text/html;charset=utf-8");
        String aa = "[\n" +
            "{\"shopId\":\"1992564\",\"shopName\":\"紫燕百味鸡（三里亭苑店）\",\"sellerId\":\"80024501\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992547\",\"shopName\":\"紫燕百味鸡（崇贤镇店）\",\"sellerId\":\"80024504\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992555\",\"shopName\":\"紫燕百味鸡（香积寺路店）\",\"sellerId\":\"80024505\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992567\",\"shopName\":\"紫燕百味鸡（凯旋路店）\",\"sellerId\":\"80024506\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992565\",\"shopName\":\"紫燕百味鸡（秋涛路店）\",\"sellerId\":\"80024507\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992556\",\"shopName\":\"紫燕百味鸡（公平路店）\",\"sellerId\":\"80024508\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992559\",\"shopName\":\"紫燕百味鸡（朝晖一店）\",\"sellerId\":\"80024509\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992571\",\"shopName\":\"紫燕百味鸡（仙林店）\",\"sellerId\":\"80024510\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992550\",\"shopName\":\"紫燕百味鸡（甘长苑店）\",\"sellerId\":\"80024511\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992569\",\"shopName\":\"紫燕百味鸡（蓝桥名苑店）\",\"sellerId\":\"80024512\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992566\",\"shopName\":\"紫燕百味鸡（朝晖二店）\",\"sellerId\":\"80024513\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992563\",\"shopName\":\"紫燕百味鸡（金都店）\",\"sellerId\":\"80024514\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992570\",\"shopName\":\"紫燕百味鸡（ 景芳路店）\",\"sellerId\":\"80024515\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992551\",\"shopName\":\"紫燕百味鸡（绍兴路店）\",\"sellerId\":\"80024516\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992549\",\"shopName\":\"紫燕百味鸡（华丰村店）\",\"sellerId\":\"80024517\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992575\",\"shopName\":\"紫燕百味鸡（后市街店）\",\"sellerId\":\"80024519\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992557\",\"shopName\":\"紫燕百味鸡（余杭塘路店）\",\"sellerId\":\"80024520\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992574\",\"shopName\":\"紫燕百味鸡（采菱路店）\",\"sellerId\":\"80024521\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992579\",\"shopName\":\"紫燕百味鸡（育才路店）\",\"sellerId\":\"80024522\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992554\",\"shopName\":\"紫燕百味鸡（陆家圩店）\",\"sellerId\":\"80024523\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992577\",\"shopName\":\"紫燕百味鸡（复兴南街店）\",\"sellerId\":\"80024524\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992578\",\"shopName\":\"紫燕百味鸡（崇化路店）\",\"sellerId\":\"80024526\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992561\",\"shopName\":\"紫燕百味鸡（王家弄店）\",\"sellerId\":\"80024527\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992560\",\"shopName\":\"紫燕百味鸡（翠苑店）\",\"sellerId\":\"80024528\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992576\",\"shopName\":\"紫燕百味鸡（衢江路店）\",\"sellerId\":\"80024529\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992573\",\"shopName\":\"紫燕百味鸡（大学路店）\",\"sellerId\":\"80024530\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992562\",\"shopName\":\"紫燕百味鸡（莲花街店）\",\"sellerId\":\"80024531\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992568\",\"shopName\":\"紫燕百味鸡（新华路店）\",\"sellerId\":\"80024532\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992558\",\"shopName\":\"紫燕百味鸡（新市街店）\",\"sellerId\":\"80024533\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992572\",\"shopName\":\"紫燕百味鸡（长庆街店）\",\"sellerId\":\"80024534\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992581\",\"shopName\":\"紫燕百味鸡（天目山路）\",\"sellerId\":\"80024535\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
            "{\"shopId\":\"1992583\",\"shopName\":\"紫燕百味鸡（星桥店）\",\"sellerId\":\"80024538\",\"platForm\":\"eleme\",\"city\":\"杭州\"},\n" +
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

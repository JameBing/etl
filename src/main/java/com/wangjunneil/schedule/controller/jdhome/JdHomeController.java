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
        /*shopCategory.setShopId("6666");
        shopCategory.setPid(000L);
        shopCategory.setShopCategoryLevel(3);
       // shopCategory.setCreatePin("aaa");//
        shopCategory.setSort(1);
        shopCategory.setShopCategoryName("bb");*/
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
            "{\"shopId\":\"1548068\",\"shopName\":\"紫燕百味鸡（新开寺店）\",\"sellerId\":\"80014091\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795844\",\"shopName\":\"紫燕百味鸡（武侯店）\",\"sellerId\":\"80014098\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795868\",\"shopName\":\"紫燕百味鸡（白马店）\",\"sellerId\":\"80014053\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548056\",\"shopName\":\"紫燕百味鸡（元华店）\",\"sellerId\":\"80014109\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548083\",\"shopName\":\"紫燕百味鸡（石羊店）\",\"sellerId\":\"80014116\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548080\",\"shopName\":\"紫燕百味鸡（黄田坝）\",\"sellerId\":\"80014001\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795877\",\"shopName\":\"紫燕百味鸡（平福店）\",\"sellerId\":\"80014036\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795859\",\"shopName\":\"紫燕百味鸡（建设店）\",\"sellerId\":\"80014077\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548063\",\"shopName\":\"紫燕百味鸡（东升店）\",\"sellerId\":\"80014113\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548081\",\"shopName\":\"紫燕百味鸡（金雁店）\",\"sellerId\":\"80014015\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795897\",\"shopName\":\"紫燕百味鸡（双清店）\",\"sellerId\":\"80014009\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795866\",\"shopName\":\"紫燕百味鸡（九南店）\",\"sellerId\":\"80014051\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795873\",\"shopName\":\"紫燕百味鸡（同诚店）\",\"sellerId\":\"80014007\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795886\",\"shopName\":\"紫燕百味鸡（高笋店）\",\"sellerId\":\"80014042\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795887\",\"shopName\":\"紫燕百味鸡（青羊店）\",\"sellerId\":\"80014022\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795870\",\"shopName\":\"紫燕百味鸡（东城店）\",\"sellerId\":\"80014046\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795881\",\"shopName\":\"紫燕百味鸡（牧电店）\",\"sellerId\":\"80014020\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795848\",\"shopName\":\"紫燕百味鸡（榕声店）\",\"sellerId\":\"80014083\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548065\",\"shopName\":\"紫燕百味鸡（洗面店）\",\"sellerId\":\"80014095\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795904\",\"shopName\":\"紫燕百味鸡（东坡店）\",\"sellerId\":\"80014003\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795851\",\"shopName\":\"紫燕百味鸡（银沙店）\",\"sellerId\":\"80014057\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795862\",\"shopName\":\"紫燕百味鸡（抚琴店）\",\"sellerId\":\"80014055\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548075\",\"shopName\":\"紫燕百味鸡（大南店）\",\"sellerId\":\"80014014\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795903\",\"shopName\":\"紫燕百味鸡（白庙店）\",\"sellerId\":\"80014053\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795856\",\"shopName\":\"紫燕百味鸡（经天店）\",\"sellerId\":\"80014065\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795879\",\"shopName\":\"紫燕百味鸡（逸都店）\",\"sellerId\":\"80014016\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548069\",\"shopName\":\"紫燕百味鸡（冻清店）\",\"sellerId\":\"80014075\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795836\",\"shopName\":\"紫燕百味鸡（和平店）\",\"sellerId\":\"80014120\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548077\",\"shopName\":\"紫燕百味鸡（君平店）\",\"sellerId\":\"80014044\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795885\",\"shopName\":\"紫燕百味鸡（长安店）\",\"sellerId\":\"80014027\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795865\",\"shopName\":\"紫燕百味鸡（泰安店）\",\"sellerId\":\"80014062\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795843\",\"shopName\":\"紫燕百味鸡（科院店）\",\"sellerId\":\"80014107\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548073\",\"shopName\":\"紫燕百味鸡（机投店）\",\"sellerId\":\"80014012\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795909\",\"shopName\":\"紫燕百味鸡（静安店）\",\"sellerId\":\"80014072\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548082\",\"shopName\":\"紫燕百味鸡（双桥店）\",\"sellerId\":\"80014070\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795842\",\"shopName\":\"紫燕百味鸡（大源店）\",\"sellerId\":\"80014117\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795898\",\"shopName\":\"紫燕百味鸡（培风店）\",\"sellerId\":\"80014029\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795841\",\"shopName\":\"紫燕百味鸡（三强店）\",\"sellerId\":\"80014115\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795892\",\"shopName\":\"紫燕百味鸡（晋阳店）\",\"sellerId\":\"80014017\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795880\",\"shopName\":\"紫燕百味鸡（解放店）\",\"sellerId\":\"80014038\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795914\",\"shopName\":\"紫燕百味鸡(龙安店)\",\"sellerId\":\"80014090\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795849\",\"shopName\":\"紫燕百味鸡（西体店）\",\"sellerId\":\"80014050\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548070\",\"shopName\":\"紫燕百味鸡（玉林店）\",\"sellerId\":\"80014097\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548066\",\"shopName\":\"紫燕百味鸡（簇桥店）\",\"sellerId\":\"80014104\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795889\",\"shopName\":\"紫燕百味鸡（横桥店）\",\"sellerId\":\"80014033\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"550169\",\"shopName\":\"紫燕百味鸡（神仙树店）\",\"sellerId\":\"80014108\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795888\",\"shopName\":\"紫燕百味鸡（金鹏店）\",\"sellerId\":\"80014006\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548074\",\"shopName\":\"紫燕百味鸡（长顺店）\",\"sellerId\":\"80014045\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795840\",\"shopName\":\"紫燕百味鸡（长江店）\",\"sellerId\":\"80014119\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795907\",\"shopName\":\"紫燕百味鸡（海桐店）\",\"sellerId\":\"80014087\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795858\",\"shopName\":\"紫燕百味鸡（九里店）\",\"sellerId\":\"80014054\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795911\",\"shopName\":\"紫燕百味鸡（燃灯店）\",\"sellerId\":\"80014082\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795869\",\"shopName\":\"紫燕百味鸡（枫林店）\",\"sellerId\":\"80014081\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795846\",\"shopName\":\"紫燕百味鸡（经华店）\",\"sellerId\":\"80014069\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548059\",\"shopName\":\"紫燕百味鸡（肖二店）\",\"sellerId\":\"80014103\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795857\",\"shopName\":\"紫燕百味鸡（老马店）\",\"sellerId\":\"80014094\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795864\",\"shopName\":\"紫燕百味鸡（东光店）\",\"sellerId\":\"80014067\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548072\",\"shopName\":\"紫燕百味鸡（武城店）\",\"sellerId\":\"80014074\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795871\",\"shopName\":\"紫燕百味鸡（桃蹊店）\",\"sellerId\":\"80014035\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795872\",\"shopName\":\"紫燕百味鸡（贝森店）\",\"sellerId\":\"80014002\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795913\",\"shopName\":\"紫燕百味鸡(喜树店)\",\"sellerId\":\"80014060\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"311820\",\"shopName\":\"紫燕百味鸡（少陵路店）\",\"sellerId\":\"80014013\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548067\",\"shopName\":\"紫燕百味鸡（潮音店）\",\"sellerId\":\"80014100\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795895\",\"shopName\":\"紫燕百味鸡（逸家店）\",\"sellerId\":\"80014131\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795900\",\"shopName\":\"紫燕百味鸡（红光店）\",\"sellerId\":\"80014056\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548061\",\"shopName\":\"紫燕百味鸡（朝阳店）\",\"sellerId\":\"80014066\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795891\",\"shopName\":\"紫燕百味鸡（黄忠店）\",\"sellerId\":\"80014008\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548062\",\"shopName\":\"紫燕百味鸡（长寿店）\",\"sellerId\":\"80014106\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548058\",\"shopName\":\"紫燕百味鸡（芳草店）\",\"sellerId\":\"80014101\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548076\",\"shopName\":\"紫燕百味鸡（文武店）\",\"sellerId\":\"80014025\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795853\",\"shopName\":\"紫燕百味鸡（和美店）\",\"sellerId\":\"80014089\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548060\",\"shopName\":\"紫燕百味鸡（新乐店）\",\"sellerId\":\"80014114\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1548055\",\"shopName\":\"紫燕百味鸡（丽景店）\",\"sellerId\":\"80014123\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795838\",\"shopName\":\"紫燕百味鸡（天华店）\",\"sellerId\":\"80014118\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
            "{\"shopId\":\"1795901\",\"shopName\":\"紫燕百味鸡（来凤店）\",\"sellerId\":\"80014026\",\"platForm\":\"eleme\",\"city\":\"成都\"},\n" +
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

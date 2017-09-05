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
            "{\"shopId\":\"965948\",\"shopName\":\"紫燕百味鸡（下河路店）\",\"sellerId\":\"80016025\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013228\",\"shopName\":\"紫燕百味鸡（兰花店）\",\"sellerId\":\"80016001\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013344\",\"shopName\":\"紫燕百味鸡（花卉园店）\",\"sellerId\":\"80016068\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013216\",\"shopName\":\"紫燕百味鸡（龙凤店）\",\"sellerId\":\"80016107\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013220\",\"shopName\":\"紫燕百味鸡（重客隆店）\",\"sellerId\":\"80016110\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013620\",\"shopName\":\"紫燕百味鸡（凤天路店）\",\"sellerId\":\"80016162\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013837\",\"shopName\":\"紫燕百味鸡（木耳公寓店）\",\"sellerId\":\"80016176\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013213\",\"shopName\":\"紫燕百味鸡（陈家桥店）\",\"sellerId\":\"80016103\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013232\",\"shopName\":\"紫燕百味鸡（歌乐山店）\",\"sellerId\":\"80016077\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965949\",\"shopName\":\"紫燕百味鸡（大江厂店）\",\"sellerId\":\"80016026\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013589\",\"shopName\":\"紫燕百味鸡（山水店）\",\"sellerId\":\"80016152\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013949\",\"shopName\":\"紫燕百味鸡（肖家湾店）\",\"sellerId\":\"80016056\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013349\",\"shopName\":\"紫燕百味鸡（黄沙溪店）\",\"sellerId\":\"80016147\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013135\",\"shopName\":\"紫燕百味鸡（茄子溪店）\",\"sellerId\":\"80016132\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013393\",\"shopName\":\"紫燕百味鸡（大公馆店）\",\"sellerId\":\"80016137\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013419\",\"shopName\":\"紫燕百味鸡（团山堡店）\",\"sellerId\":\"80016088\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013187\",\"shopName\":\"紫燕百味鸡（康居店）\",\"sellerId\":\"80016125\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013938\",\"shopName\":\"紫燕百味鸡（棉花街店）\",\"sellerId\":\"80016054\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965954\",\"shopName\":\"紫燕百味鸡（双碑店)\",\"sellerId\":\"80016031\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013618\",\"shopName\":\"紫燕百味鸡（土湾店）\",\"sellerId\":\"80016127\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965951\",\"shopName\":\"紫燕百味鸡(小龙坎店）\",\"sellerId\":\"80016028\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965943\",\"shopName\":\"紫燕百味鸡（青龙路店）\",\"sellerId\":\"80016019\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013697\",\"shopName\":\"紫燕百味鸡（动物园店）\",\"sellerId\":\"80016131\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013298\",\"shopName\":\"紫燕百味鸡（文星湾店）\",\"sellerId\":\"80016086\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013586\",\"shopName\":\"紫燕百味鸡（鱼嘴店）\",\"sellerId\":\"80016089\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965962\",\"shopName\":\"紫燕百味鸡（田坝店）\",\"sellerId\":\"80016042\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"2221553\",\"shopName\":\"紫燕百味鸡（南山店）\",\"sellerId\":\"80016023\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013223\",\"shopName\":\"紫燕百味鸡（水木年华店）\",\"sellerId\":\"80016116\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"2221551\",\"shopName\":\"紫燕百味鸡（大堰店）\",\"sellerId\":\"80016060\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965938\",\"shopName\":\"紫燕百味鸡（南正街店）\",\"sellerId\":\"80016011\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013602\",\"shopName\":\"紫燕百味鸡（石碾店）\",\"sellerId\":\"80016126\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013222\",\"shopName\":\"紫燕百味鸡（曾家店）\",\"sellerId\":\"80016112\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013406\",\"shopName\":\"紫燕百味鸡（大学城店）\",\"sellerId\":\"80016097\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013094\",\"shopName\":\"紫燕百味鸡（城上城店）\",\"sellerId\":\"80016005\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013070\",\"shopName\":\"紫燕百味鸡（黄桷坪店）\",\"sellerId\":\"80016058\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013373\",\"shopName\":\"紫燕百味鸡（华岩寺店）\",\"sellerId\":\"80016096\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013125\",\"shopName\":\"紫燕百味鸡（石平桥店）\",\"sellerId\":\"80016061\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013532\",\"shopName\":\"紫燕百味鸡（滩子口店）\",\"sellerId\":\"80016100\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965945\",\"shopName\":\"紫燕百味鸡（回兴店）\",\"sellerId\":\"80016021\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013199\",\"shopName\":\"紫燕百味鸡（枇杷山店）\",\"sellerId\":\"80016085\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013049\",\"shopName\":\"紫燕百味鸡（莲花市场店）\",\"sellerId\":\"80016079\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965947\",\"shopName\":\"紫燕百味鸡（江洲路店）\",\"sellerId\":\"80016024\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013224\",\"shopName\":\"紫燕百味鸡（金福店）\",\"sellerId\":\"80016117\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965940\",\"shopName\":\"紫燕百味鸡（金山路店）\",\"sellerId\":\"80016014\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965935\",\"shopName\":\"紫燕百味鸡（建设店）\",\"sellerId\":\"80016006\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013517\",\"shopName\":\"紫燕百味鸡（国会山店）\",\"sellerId\":\"80016010\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013217\",\"shopName\":\"紫燕百味鸡（鹅岭店）\",\"sellerId\":\"80016106\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965961\",\"shopName\":\"紫燕百味鸡（上桥店）\",\"sellerId\":\"80016003\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965956\",\"shopName\":\"紫燕百味鸡（碚峡路店）\",\"sellerId\":\"80016034\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013054\",\"shopName\":\"紫燕百味鸡（马王坪店）\",\"sellerId\":\"80016057\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013641\",\"shopName\":\"紫燕百味鸡（北京路店）\",\"sellerId\":\"80016094\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013541\",\"shopName\":\"紫燕百味鸡（长安店）\",\"sellerId\":\"80016123\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013366\",\"shopName\":\"紫燕百味鸡（阳光海岸店）\",\"sellerId\":\"80016069\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013183\",\"shopName\":\"紫燕百味鸡（东和春天店）\",\"sellerId\":\"80016063\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013063\",\"shopName\":\"紫燕百味鸡（天奇店）\",\"sellerId\":\"80016071\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965959\",\"shopName\":\"紫燕百味鸡（龙兴店）\",\"sellerId\":\"80016072\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013219\",\"shopName\":\"紫燕百味鸡（长翔店）\",\"sellerId\":\"80016109\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965957\",\"shopName\":\"紫燕百味鸡（解放路店）\",\"sellerId\":\"80016036\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013329\",\"shopName\":\"紫燕百味鸡（金岛花园店）\",\"sellerId\":\"80016066\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013665\",\"shopName\":\"紫燕百味鸡（水晶店）\",\"sellerId\":\"80016129\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013214\",\"shopName\":\"紫燕百味鸡（李家沱店）\",\"sellerId\":\"80016104\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965939\",\"shopName\":\"紫燕百味鸡（五小区店）\",\"sellerId\":\"80016013\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013269\",\"shopName\":\"紫燕百味鸡（武陵路店）\",\"sellerId\":\"80016065\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013686\",\"shopName\":\"紫燕百味鸡（人民路店）\",\"sellerId\":\"80016066\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013644\",\"shopName\":\"紫燕百味鸡（金州店）\",\"sellerId\":\"80016128\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013493\",\"shopName\":\"紫燕百味鸡（华宇店）\",\"sellerId\":\"80016046\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013639\",\"shopName\":\"紫燕百味鸡（凯德广场店）\",\"sellerId\":\"80016154\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013226\",\"shopName\":\"紫燕百味鸡（金龙路店）\",\"sellerId\":\"80016064\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013051\",\"shopName\":\"紫燕百味鸡（天街店）\",\"sellerId\":\"80016070\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013471\",\"shopName\":\"紫燕百味鸡（双凤桥店）\",\"sellerId\":\"80016098\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"2221554\",\"shopName\":\"紫燕百味鸡（弹子石店）\",\"sellerId\":\"80016012\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965941\",\"shopName\":\"紫燕百味鸡（沃尔玛店）\",\"sellerId\":\"80016016\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965937\",\"shopName\":\"紫燕百味鸡（香港城店）\",\"sellerId\":\"80016008\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965950\",\"shopName\":\"紫燕百味鸡（汉渝路店）\",\"sellerId\":\"80016027\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013523\",\"shopName\":\"紫燕百味鸡（科园店）\",\"sellerId\":\"80016047\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013663\",\"shopName\":\"紫燕百味鸡（枫丹路店）\",\"sellerId\":\"80016095\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013567\",\"shopName\":\"紫燕百味鸡（国际城店）\",\"sellerId\":\"80016092\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013679\",\"shopName\":\"紫燕百味鸡（石桥铺店）\",\"sellerId\":\"80016049\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013083\",\"shopName\":\"紫燕百味鸡（南桥寺店）\",\"sellerId\":\"80016073\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965955\",\"shopName\":\"紫燕百味鸡(大川店）\",\"sellerId\":\"80016032\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013221\",\"shopName\":\"紫燕百味鸡(双农店)\",\"sellerId\":\"80016111\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013945\",\"shopName\":\"紫燕百味鸡（大支路店）\",\"sellerId\":\"80016055\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965944\",\"shopName\":\"紫燕百味鸡（龙瑞路店）\",\"sellerId\":\"80016020\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013606\",\"shopName\":\"紫燕百味鸡（工学院店）\",\"sellerId\":\"80016009\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013482\",\"shopName\":\"紫燕百味鸡（长生店）\",\"sellerId\":\"80016121\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965936\",\"shopName\":\"紫燕百味鸡（九宫庙店）\",\"sellerId\":\"80016007\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013474\",\"shopName\":\"紫燕百味鸡（学田湾店）\",\"sellerId\":\"80016139\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013142\",\"shopName\":\"紫燕百味鸡（元佳广场店）\",\"sellerId\":\"80016075\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013218\",\"shopName\":\"紫燕百味鸡（宝圣店）\",\"sellerId\":\"80016108\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013225\",\"shopName\":\"紫燕百味鸡（鸳鸯店）\",\"sellerId\":\"80016118\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013429\",\"shopName\":\"紫燕百味鸡（李家沱正街店）\",\"sellerId\":\"80016104\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013684\",\"shopName\":\"紫燕百味鸡（大坪店）\",\"sellerId\":\"80016050\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013115\",\"shopName\":\"紫燕百味鸡（龙头寺店）\",\"sellerId\":\"80016074\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013583\",\"shopName\":\"紫燕百味鸡（康庄店）\",\"sellerId\":\"80016040\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965942\",\"shopName\":\"紫燕百味鸡（上海城店）\",\"sellerId\":\"80016017\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965933\",\"shopName\":\"紫燕百味鸡（杨家坪店）\",\"sellerId\":\"80016041\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013854\",\"shopName\":\"紫燕百味鸡（茶园店）\",\"sellerId\":\"80016177\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013558\",\"shopName\":\"紫燕百味鸡（千叶店）\",\"sellerId\":\"80016142\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013449\",\"shopName\":\"紫燕百味鸡（金观音店）\",\"sellerId\":\"80016138\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013688\",\"shopName\":\"紫燕百味鸡（大同路店）\",\"sellerId\":\"80016052\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"965952\",\"shopName\":\"紫燕百味鸡（陈家湾店）\",\"sellerId\":\"80016029\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
            "{\"shopId\":\"1013229\",\"shopName\":\"紫燕百味鸡（银华路店）\",\"sellerId\":\"80016135\",\"platForm\":\"eleme\",\"city\":\"重庆\"},\n" +
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

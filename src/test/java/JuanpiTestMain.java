import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpUtil;
import com.wangjunneil.schedule.utility.MD5Util;
import com.wangjunneil.schedule.utility.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * 卷皮平台测试类
 *
 * @author Administrator
 * @date 2016/9/5
 */
public class JuanpiTestMain {

//   private static final String TOKEN_URL = "http://59.172.39.253:8101/erpapi/authorize";//测试
//   private static final String BUSINESS_URL = "http://59.172.39.253:8101/erpapi/index";//测试
   private static final String TOKEN_URL = "http://open.juanpi.com/erpapi/authorize";//生产
   private static final String BUSINESS_URL = "http://open.juanpi.com/erpapi/index";//生产

    private static final String APP_KEY = "c605cbe3791297e3dc9bacc67e819a6d";//用于请求TOKEN(对应申请appkey)
    private static final String APP_SECRET = "8793e207e56f1ead4498a8ff84b2a1e5";//用于加密请求参数(对应申请appsecret)
    private static final String J_CUS_KEY = "63BADE3D5B3830F73CF6";//商家key，找卷皮测试或开发获取 生产
    private static final String TOKEN = "5abc30e53a7791ab4cdb202587dc0714b868935d";//生产

    //授权方法类型
    public static final String J_TYPE_OPTIONS = "order_list,order_info,get_express,sgoods_info,sgoods_list,send_goods,get_unid,get_goods_inventory,update_goods_inventory,aftersale_list,aftersale_detail,aftersale_refusereason,update_address,aftersale_handle";//api方法

    //获取商品详情&sku列表时的字段列表可选值
    public static final String SGOODS_INFO_OPTIONS = "skuid,zid,fid,zid_value,fid_value,inventory,his_inventory,sales,sgoodsno,price,cprice,add_time,last_modified";

    public static final Map<String,String> errorCodeMap = new HashMap<String,String>();

    public JuanpiTestMain() {
        errorCodeMap.put("10001", "参数丢失");
        errorCodeMap.put("10002", "非法请求，不在的接口");
        errorCodeMap.put("10003", "不存在的SECRET或SECRET不可用");
        errorCodeMap.put("10004", "TOKEN无效，过期或者不存在");
        errorCodeMap.put("10005", "ERP权限不正确");
        errorCodeMap.put("10006", "ERP帐号被禁用");
        errorCodeMap.put("10007", "没有查询到数据");
        errorCodeMap.put("10008", "不存在的订单");
        errorCodeMap.put("10009", "订单状态不在待发货或备货中");
        errorCodeMap.put("10010", "ERP帐号异常");
        errorCodeMap.put("10011", "商家状态不可用");
        errorCodeMap.put("10012", "不存在此商家");
        errorCodeMap.put("10013", "商家密钥错误");
        errorCodeMap.put("10014", "商家密钥过期");
        errorCodeMap.put("10015", "订单已发货");
        errorCodeMap.put("10016", "订单商品全部售后中");
        errorCodeMap.put("10017", "发货号和快递公司不匹配");
        errorCodeMap.put("10018", "仓库中的商品不能发货");
        errorCodeMap.put("10019", "库存设置保护时间(5分钟)");
        errorCodeMap.put("10020", "减少库存数大于实时库存数");
        errorCodeMap.put("10021", "服务化库存查询错误");
        errorCodeMap.put("10022", "服务化库存设置错误");
        errorCodeMap.put("10023", "入库商品不能修改库存");
        errorCodeMap.put("10024", "用户与商品所属商家不一致");
        errorCodeMap.put("10025", "商品已经下架");
        errorCodeMap.put("10026", "入库商品不可查询");
        errorCodeMap.put("10027", "物流公司不存在");
        errorCodeMap.put("10028", "商品数据错误, 无法同步库存");
        errorCodeMap.put("10029", "商家和订单不一致");
        errorCodeMap.put("10040", "TOKEN失效");
        errorCodeMap.put("10041", "签名错误");
        errorCodeMap.put("10042", "TOKEN权限不足");
        errorCodeMap.put("20000", "操作成功");
        errorCodeMap.put("50000", "服务器错误，如redis写入失败");
        errorCodeMap.put("50001", "scope为空");
    }

    public static void main(String[] args) throws Exception {
//        JuanpiTestMain test = new JuanpiTestMain();
//        System.out.println(errorCodeMap.get("20000").toString());//获取错误码描述

        /*Date date = new Date();
        date.setTime(1473748810L*1000);
        System.out.println(date.toString());*/
        //        System.out.println(DateTimeUtil.parseDateString("2016-08-01 12:00:00").getTime());

//        System.out.println("1,2,3,5,6,9".replaceAll(",","%2C"));//',' 需转义为%2C
//        System.out.println("2015|2016".replaceAll("\\|","%7C"));//|转义%7C

//        getToken();//获取token

//        orderList();//订单列表
//        orderInfo("314721423670027");//获取订单详情
//        getExpress();//获取物流信息
//        sendGoods("314721423670027","shunfeng","顺丰速运", "215047972060");//订单发货处理
//        sgoodsInfo("318329779");//获取商品详情&sku列表  ???
//        getGoodsInventory("318329779");//获取商品库存
//        updateGoodsInventory("318329779",17);//获取商品库存
//        afterSaleList(0,0);//获取售后订单列表
//        afterSaleDetail("514724594461009");//获取售后详情

//        afterSaleHandle("1","1","测试",1.00f,"11不同意");//售后操作

    }

    private static void getToken() {
        String result = HttpUtil.post(TOKEN_URL, "secret=" + APP_KEY + "&scope=" + J_TYPE_OPTIONS + "&type=json");
        System.out.println(result);
        //{"data":{"token":"5abc30e53a7791ab4cdb202587dc0714b868935d","timeout":604800,"expire":1473748810,"scope":"order_list,order_info,get_express,sgoods_info,sgoods_list,send_goods,get_unid,get_goods_inventory,update_goods_inventory,aftersale_list,aftersale_detail,aftersale_refusereason,update_address,aftersale_handle"},"info":"20000","status":1}
        JSONObject json = JSONObject.parseObject(result);
        if ("1".equals(json.getString("status"))) {//成功
            //System.out.println(json.getString("info"));
            String token = json.getString("token");
            String timeout = json.getString("timeout");
            String expire = json.getString("expire");
            String scope = json.getString("scope");

        } else {//失败
            System.out.println(json.getString("info"));
        }
    }

    private static void orderList() {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=order_list&jPagesize=100&jPage=1";//分页时使用结果中的count计算总页数，再循环获取总列表

        String jOrderStatus = "5";//订单状态: 1:等待买家付款,2:等待发货,3:已发货,5:交易成功,6:交易已关闭,9:备货中, 默认值为1 多个用,隔开(',' 需转义为%2C，否则签名不过)
        String startDate = String.valueOf(DateTimeUtil.parseDateString("2016-08-26 00:00:00").getTime()/1000);
        String endDate = String.valueOf(DateTimeUtil.parseDateString("2016-08-31 23:55:00").getTime()/1000);
        String create_time = "";//|转义%7C
        if (null != startDate && !"".equals(startDate)) {
            create_time = startDate;
        }
        if (!"".equals(create_time) && null != endDate && !"".equals(endDate)) {
            create_time += "%7C"+ endDate;
        }

        reqParam += "&jOrderStatus="+jOrderStatus.replaceAll(",","%2C");//',' 需转义为%2C
        reqParam += "&create_time="+create_time;//格式 star_time|end_time，都是 unix 时间戳，后台会以|分割，如果格式错误，系统将会忽略该参数，生成签名时 | 应转换成 url 编码%7C
        reqParam += "&show_detail=1";//默认不展示详情,1 为在列表展示详情

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"count":13,"page":1,"lists":[{"orderno":314721423670027,"createtime":"2016-08-26 00:26:07","paytime":"2016-08-26 00:27:28","shiptime":"2016-08-26 09:36:54","modifytime":"2016-08-31 19:03:19","payamount":22.5,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"13892074805","buyertel":"","buyerid":"","area":610427,"expresscode":"666772910420","remark":"","status":5,"buyerarea":"\u9655\u897f\u7701\u54b8\u9633\u5e02\u5f6c\u53bf","buyeraddress":"\u65b0\u5317\u8857\u5efa\u6750\u5e02\u573a53\u53f7\u5c0f\u5a25\u5e8a\u4e0a\u7528\u54c1","buyername":"\u9ad8\u5c0f\u5a25","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"136****3612","new_area":"\u9655\u897f\u7701|\u54b8\u9633\u5e02|\u5f6c\u53bf","realmoney":"22.50","goodslist":[{"goodsid":"13727225","goods_sku_id":"315335034","goodsname":"\u9f50\u5fc3\u591a\u8272\u6587\u4ef6\u888b(10\u4e2a)","goodsno":"C330","goodsnum":1,"goodsprice":22.5,"goodszvalue":"\u767d\u8272","goodsfvalue":"20\u4e2a\u88c5","goodsimg":"\/\/s1.juancdn.com\/bao\/160815\/7\/8\/57b18aa2151ad1fe418b461b_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"13727225"}]},{"orderno":314721449580358,"createtime":"2016-08-26 01:09:18","paytime":"2016-08-26 01:09:28","shiptime":"2016-08-26 09:36:54","modifytime":"2016-08-28 17:38:48","payamount":13.5,"payexpress":0,"discount":"0","paytype":6,"buyerphone":"13886411363","buyertel":"","buyerid":"","area":421123,"expresscode":"666772912335","remark":"","status":5,"buyerarea":"\u6e56\u5317\u7701\u9ec4\u5188\u5e02\u7f57\u7530\u53bf","buyeraddress":"\u51e4\u5c71\u9547\u9633\u5149\u57ce\u6b65\u884c\u88579-11\u53f7\u7f8e\u8bd7\u6b27","buyername":"\u6768\u4e50\u5349","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"\u5bb6\u79c1\u542c","new_area":"\u6e56\u5317\u7701|\u9ec4\u5188\u5e02|\u7f57\u7530\u53bf","realmoney":"13.50","goodslist":[{"goodsid":"13727225","goods_sku_id":"385335034","goodsname":"\u9f50\u5fc3\u591a\u8272\u6587\u4ef6\u888b(10\u4e2a)","goodsno":"C330","goodsnum":1,"goodsprice":13.5,"goodszvalue":"\u767d\u8272","goodsfvalue":"10\u4e2a\u88c5","goodsimg":"\/\/s1.juancdn.com\/bao\/160815\/7\/8\/57b18aa2151ad1fe418b461b_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"13727225"}]},{"orderno":314721500157086,"createtime":"2016-08-26 02:33:36","paytime":"2016-08-26 02:33:42","shiptime":"2016-08-26 09:45:15","modifytime":"2016-09-01 19:48:36","payamount":9.9,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"15158717159","buyertel":"","buyerid":"","area":411525,"expresscode":"9890146082708","remark":"1\u3001\u4e70\u5bb6\u6307\u5b9a \u90ae\u653f\u56fd\u5185\u5c0f\u5305 \u6536\u8d27\uff0c\u63a8\u8350\u60a8\u4f7f\u7528","status":5,"buyerarea":"\u6cb3\u5357\u7701\u4fe1\u9633\u5e02\u56fa\u59cb\u53bf","buyeraddress":"\u6cb3\u5357\u7701\u56fa\u59cb\u53bf\u5206\u6c34\u4e61\u94c1\u94fa\u6751\u94c1\u94fa\u961f","buyername":"\u4e01\u7ea2\u6885","expressname":"\u90ae\u653f\u56fd\u5185\u5c0f\u5305","sellremark":"","username":"151****7159","new_area":"\u6cb3\u5357\u7701|\u4fe1\u9633\u5e02|\u56fa\u59cb\u53bf","realmoney":"9.90","goodslist":[{"goodsid":"15727505","goods_sku_id":"308321871","goodsname":"\u9f50\u5fc3\u6df7\u88c5\u4e2d\u6027\u7b14(20\u652f)","goodsno":"K3020","goodsnum":1,"goodsprice":9.9,"goodszvalue":"\u9ed1\u8272\u6df7\u88c520\u652f","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160715\/a\/2\/57888d45151ad14c398b45ed_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"15727505"}]},{"orderno":314721647680352,"createtime":"2016-08-26 06:39:28","paytime":"2016-08-26 06:39:36","shiptime":"2016-08-26 09:45:15","modifytime":"2016-09-02 14:01:10","payamount":6.5,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"15145973193","buyertel":"","buyerid":"","area":230622,"expresscode":"9890146084445","remark":"","status":5,"buyerarea":"\u9ed1\u9f99\u6c5f\u7701\u5927\u5e86\u5e02\u8087\u6e90\u53bf","buyeraddress":"\u7231\u601d\u8003\u5bf9\u9762\u7545\u8302\u6e90\u98df\u6742\u5e97","buyername":"\u8463\u51e4\u5a1f","expressname":"\u90ae\u653f\u56fd\u5185\u5c0f\u5305","sellremark":"","username":"151****3193","new_area":"\u9ed1\u9f99\u6c5f\u7701|\u5927\u5e86\u5e02|\u8087\u6e90\u53bf","realmoney":"6.50","goodslist":[{"goodsid":"11727405","goods_sku_id":"368322870","goodsname":"\u9f50\u5fc3\u901a\u7528\u4e2d\u6027\u7b14(12\u652f)","goodsno":"GP306","goodsnum":1,"goodsprice":6.5,"goodszvalue":"12\u652f\u84dd\u8272\uff08\u76d2\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160621\/4\/f\/5768f78d151ad10a298b4581_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"11727405"}]},{"orderno":314721664145329,"createtime":"2016-08-26 07:06:55","paytime":"2016-08-26 07:07:35","shiptime":"2016-08-26 09:45:16","modifytime":"2016-09-02 15:56:50","payamount":7.9,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"13503629011","buyertel":"","buyerid":"","area":230109,"expresscode":"9890146079562","remark":"","status":5,"buyerarea":"\u9ed1\u9f99\u6c5f\u7701\u54c8\u5c14\u6ee8\u5e02\u677e\u5317\u533a","buyeraddress":"\u4e50\u4e1a\u9547","buyername":"\u4e8e\u4e9a\u73b2","expressname":"\u90ae\u653f\u56fd\u5185\u5c0f\u5305","sellremark":"","username":"\u8d85\u51e1\u5353\u8d8a","new_area":"\u9ed1\u9f99\u6c5f\u7701|\u54c8\u5c14\u6ee8\u5e02|\u677e\u5317\u533a","realmoney":"7.90","goodslist":[{"goodsid":"19727605","goods_sku_id":"378325872","goodsname":"\u9f50\u5fc3\u900f\u660e\u6309\u6263\u888b(10\u4e2a)","goodsno":"C318x10","goodsnum":1,"goodsprice":7.9,"goodszvalue":"C318(10\u4e2a\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160715\/8\/f\/5788a1d6151ad197788b45a3_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"19727605"}]},{"orderno":314721707173998,"createtime":"2016-08-26 08:18:37","paytime":"2016-08-26 08:19:01","shiptime":"2016-08-26 09:36:55","modifytime":"2016-08-30 01:35:30","payamount":34.6,"payexpress":0,"discount":"0","paytype":6,"buyerphone":"15697756698","buyertel":"","buyerid":"","area":450924,"expresscode":"666772908781","remark":"","status":5,"buyerarea":"\u5e7f\u897f\u58ee\u65cf\u81ea\u6cbb\u533a\u7389\u6797\u5e02\u5174\u4e1a\u53bf","buyeraddress":"\u77f3\u5357\u9547\u7389\u8d35\u8def444\u53f7\u4e2d\u56fd\u5e73\u5b89\u4eba\u5bff\u4fdd\u9669\u516c\u53f8","buyername":"\u5f20\u94f6\u6f4b","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"156****6698","new_area":"\u5e7f\u897f\u58ee\u65cf\u81ea\u6cbb\u533a|\u7389\u6797\u5e02|\u5174\u4e1a\u53bf","realmoney":"34.60","goodslist":[{"goodsid":"14727495","goods_sku_id":"368320873","goodsname":"\u9f50\u5fc3\u70ab\u5f69\u56de\u5f62\u9488(3\u76d2)","goodsno":"B3509x3","goodsnum":1,"goodsprice":6.9,"goodszvalue":"B3509(3\u76d2\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160715\/2\/7\/5788a6cd151ad1c0098b45ab_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"14727495"},{"goodsid":"10727905","goods_sku_id":"378322873","goodsname":"\u9f50\u5fc3\u56db\u8054\u8d44\u6599\u6587\u4ef6\u67b6","goodsno":"B2024","goodsnum":1,"goodsprice":13.8,"goodszvalue":"B2024(L\u84dd\u8272\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160716\/4\/4\/5789c9ed151ad1f8338b4594_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"10727905"},{"goodsid":"19727605","goods_sku_id":"348326872","goodsname":"\u9f50\u5fc3\u900f\u660e\u6309\u6263\u888b(10\u4e2a)","goodsno":"C318x20","goodsnum":1,"goodsprice":13.9,"goodszvalue":"C318(20\u4e2a\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160715\/4\/b\/5788a1d7151ad1527b8b459b_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"19727605"}]},{"orderno":314721710662121,"createtime":"2016-08-26 08:24:26","paytime":"2016-08-26 08:24:36","shiptime":"2016-08-26 09:36:55","modifytime":"2016-08-30 19:16:12","payamount":19.8,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"13543820161","buyertel":"","buyerid":"","area":442000,"expresscode":"666772904859","remark":"","status":5,"buyerarea":"\u5e7f\u4e1c\u7701\u4e2d\u5c71\u5e02","buyeraddress":"\u5e7f\u4e1c\u7701\u4e2d\u5c71\u5e02\u5927\u6d8c\u9547\u65d7\u5c71\u8def39\u53f7\u5bcc\u57ce\u5c71\u5e84B2\u5e62\u4e00\u5c4212\u5361(\u5065\u5eb7\u7f18\u5e97)","buyername":"\u674e\u674f\u6885","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"135****0161","new_area":"\u5e7f\u4e1c\u7701|\u4e2d\u5c71\u5e02","realmoney":"19.80","goodslist":[{"goodsid":"14727305","goods_sku_id":"338326779","goodsname":"\u9f50\u5fc3\u5546\u52a1\u529e\u516c\u8ba1\u7b97\u5668","goodsno":"c-837c","goodsnum":2,"goodsprice":9.9,"goodszvalue":"c-837c","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160714\/e\/3\/5787576c151ad1fb538b4605_800x800.png","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"14727305"}]},{"orderno":314721717323547,"createtime":"2016-08-26 08:35:32","paytime":"2016-08-26 08:35:40","shiptime":"2016-08-26 09:43:41","modifytime":"2016-08-28 12:34:59","payamount":20.3,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"18101522671","buyertel":"","buyerid":"","area":320281,"expresscode":"666772972988","remark":"","status":5,"buyerarea":"\u6c5f\u82cf\u7701\u65e0\u9521\u5e02\u6c5f\u9634\u5e02","buyeraddress":"\u897f\u5916\u73af\u8def90\u53f7\u20142\u5e1d\u8c6a\u827a\u672f\u73bb\u7483","buyername":"\u80e1\u7ea2\u8273","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"181****2671","new_area":"\u6c5f\u82cf\u7701|\u65e0\u9521\u5e02|\u6c5f\u9634\u5e02","realmoney":"20.30","goodslist":[{"goodsid":"13727515","goods_sku_id":"388322876","goodsname":"\u9f50\u5fc3A1556\u5546\u52a1\u540d\u7247\u5939","goodsno":"a1556","goodsnum":1,"goodsprice":8.8,"goodszvalue":"\u9ed1\u8272A1556","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160719\/2\/f\/578da840151ad123748b45c3_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"13727515"},{"goodsid":"10727325","goods_sku_id":"303535244","goodsname":"\u9f50\u5fc3B5\u7ebf\u570880\u9875\u7b14\u8bb0\u672c","goodsno":"C4517","goodsnum":1,"goodsprice":11.5,"goodszvalue":"\u989c\u8272\u968f\u673a","goodsfvalue":"2\u672c","goodsimg":"\/\/s1.juancdn.com\/bao\/160816\/b\/b\/57b28475151ad178058b45dc_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"10727325"}]},{"orderno":314721728211046,"createtime":"2016-08-26 08:53:41","paytime":"2016-08-26 08:54:16","shiptime":"2016-08-26 09:43:42","modifytime":"2016-08-28 16:16:16","payamount":9.9,"payexpress":0,"discount":"0","paytype":2,"buyerphone":"18224514696","buyertel":"","buyerid":"","area":410122,"expresscode":"666772971286","remark":"","status":5,"buyerarea":"\u6cb3\u5357\u7701\u90d1\u5dde\u5e02\u4e2d\u725f\u53bf","buyeraddress":"\u6cb3\u5357\u7701\u90d1\u5dde\u5e02\u4e2d\u725f\u53bf\u90d1\u5dde\u7535\u529b\u804c\u4e1a\u6280\u672f\u5b66\u9662","buyername":"\u5f20\u6674\u6674","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"jp_7732409e4","new_area":"\u6cb3\u5357\u7701|\u90d1\u5dde\u5e02|\u4e2d\u725f\u53bf","realmoney":"9.90","goodslist":[{"goodsid":"15727985","goods_sku_id":"368325779","goodsname":"\u70ab\u5f69\u901a\u7528\u4e2d\u53f7\u8ba2\u4e66\u673a","goodsno":"3040+3058x2","goodsnum":1,"goodsprice":9.9,"goodszvalue":"\u8ba2\u4e66\u673a+2\u76d2\u8ba2\u4e66\u9488","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160620\/1\/0\/5767b12f151ad1ee028b45d6_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"15727985"}]},{"orderno":314721728740314,"createtime":"2016-08-26 08:54:34","paytime":"2016-08-26 08:54:39","shiptime":"2016-08-26 09:43:42","modifytime":"2016-08-31 19:03:26","payamount":7.9,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"15979926207","buyertel":"","buyerid":"","area":360430,"expresscode":"666772976800","remark":"","status":5,"buyerarea":"\u6c5f\u897f\u7701\u4e5d\u6c5f\u5e02\u5f6d\u6cfd\u53bf","buyeraddress":"\u51c9\u4ead\u5c0f\u533a12\u680b\u798f\u534e\u8d85\u5e02","buyername":"\u9ad8\u534e\u73cd","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"159****6207","new_area":"\u6c5f\u897f\u7701|\u4e5d\u6c5f\u5e02|\u5f6d\u6cfd\u53bf","realmoney":"7.90","goodslist":[{"goodsid":"19727605","goods_sku_id":"378325872","goodsname":"\u9f50\u5fc3\u900f\u660e\u6309\u6263\u888b(10\u4e2a)","goodsno":"C318x10","goodsnum":1,"goodsprice":7.9,"goodszvalue":"C318(10\u4e2a\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160715\/8\/f\/5788a1d6151ad197788b45a3_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"19727605"}]},{"orderno":314721732919465,"createtime":"2016-08-26 09:01:32","paytime":"2016-08-26 09:02:18","shiptime":"2016-08-26 14:33:15","modifytime":"2016-09-06 07:46:26","payamount":13.8,"payexpress":5,"discount":"0","paytype":7,"buyerphone":"13319913980","buyertel":"","buyerid":"","area":650105,"expresscode":"9890146509896","remark":"","status":5,"buyerarea":"\u65b0\u7586\u7ef4\u543e\u5c14\u81ea\u6cbb\u533a\u4e4c\u9c81\u6728\u9f50\u5e02\u6c34\u78e8\u6c9f\u533a","buyeraddress":"\u5eb7\u660e\u56ed\u5c0f\u533a\u4e00\u671f8-2-503","buyername":"\u674e\u7ee7\u4fa0","expressname":"\u90ae\u653f\u56fd\u5185\u5c0f\u5305","sellremark":"","username":"133****3980","new_area":"\u65b0\u7586\u7ef4\u543e\u5c14\u81ea\u6cbb\u533a|\u4e4c\u9c81\u6728\u9f50\u5e02|\u6c34\u78e8\u6c9f\u533a","realmoney":"18.80","goodslist":[{"goodsid":"10727905","goods_sku_id":"378322873","goodsname":"\u9f50\u5fc3\u56db\u8054\u8d44\u6599\u6587\u4ef6\u67b6","goodsno":"B2024","goodsnum":1,"goodsprice":13.8,"goodszvalue":"B2024(L\u84dd\u8272\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160716\/4\/4\/5789c9ed151ad1f8338b4594_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"10727905"}]},{"orderno":314721758307180,"createtime":"2016-08-26 09:43:51","paytime":"2016-08-26 09:43:59","shiptime":"2016-08-26 14:32:55","modifytime":"2016-08-29 21:15:11","payamount":9.9,"payexpress":0,"discount":"0","paytype":7,"buyerphone":"13956928782","buyertel":"","buyerid":"","area":340104,"expresscode":"666773942092","remark":"","status":5,"buyerarea":"\u5b89\u5fbd\u7701\u5408\u80a5\u5e02\u8700\u5c71\u533a","buyeraddress":"\u5408\u80a5\u5e02\u7ecf\u5f00\u533a\u77f3\u95e8\u8def518\u53f7\u5b89\u5fbd\u8f7b\u5de5\u4e1a\u6280\u5e08\u5b66\u9662","buyername":"\u5b59\u5c71\u6625","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"139****8782","new_area":"\u5b89\u5fbd\u7701|\u5408\u80a5\u5e02|\u8700\u5c71\u533a","realmoney":"9.90","goodslist":[{"goodsid":"12727095","goods_sku_id":"318329779","goodsname":"\u5b66\u751f\u5361\u901a\u8f6c\u52a8\u5377\u7b14\u5200","goodsno":"B2432","goodsnum":1,"goodsprice":9.9,"goodszvalue":"B2432(\u989c\u8272\u968f\u673a)","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160714\/2\/f\/57875e84151ad1127c8b4570_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"12727095"}]},{"orderno":314721764092156,"createtime":"2016-08-26 09:53:29","paytime":"2016-08-26 09:53:48","shiptime":"2016-08-26 14:32:56","modifytime":"2016-08-29 17:38:08","payamount":37.87,"payexpress":0,"discount":"0","paytype":6,"buyerphone":"17737036661","buyertel":"","buyerid":"","area":411525,"expresscode":"666773931416","remark":"","status":5,"buyerarea":"\u6cb3\u5357\u7701\u4fe1\u9633\u5e02\u56fa\u59cb\u53bf","buyeraddress":"\u8e0f\u6708\u5bfa\u8857\u5357\u6bb5\u6d88\u9632\u961f\u5927\u95e8\u5bf9\u9762","buyername":"\u90d1\u4e00\u8bfa","expressname":"\u5929\u5929\u5feb\u9012","sellremark":"","username":"\u88f4\u9752\u9752pqq","new_area":"\u6cb3\u5357\u7701|\u4fe1\u9633\u5e02|\u56fa\u59cb\u53bf","realmoney":"37.87","goodslist":[{"goodsid":"10727205","goods_sku_id":"348321779","goodsname":"\u9f50\u5fc3\u6df7\u88c5\u4fbf\u5229\u8d34400\u5f20","goodsno":"D5005","goodsnum":1,"goodsprice":6.77,"goodszvalue":"\u6df7\u8272400\u5f20","goodsfvalue":"\u5355\u4e2a\u88c5","goodsimg":"\/\/s1.juancdn.com\/bao\/160602\/e\/d\/574fe4aa151ad1bb248b4605_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"10727205"},{"goodsid":"11827205","goods_sku_id":"398324876","goodsname":"\u9f50\u5fc3\u7ed8\u753b\u4e13\u7528\u5851\u6599\u5939","goodsno":"A724","goodsnum":1,"goodsprice":11.3,"goodszvalue":"\u84dd\u8272A724","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160719\/6\/3\/578da58f151ad1e2688b4613_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"11827205"},{"goodsid":"14727425","goods_sku_id":"353535245","goodsname":"\u9f50\u5fc3\u5927\u53f79\u5bf8\u94c1\u4e66\u7acb","goodsno":"A1100","goodsnum":1,"goodsprice":19.8,"goodszvalue":"\u7070\uff081\u5bf9\uff09","goodsfvalue":"","goodsimg":"\/\/s1.juancdn.com\/bao\/160816\/6\/6\/57b28cc5151ad16d208b45d9_800x800.jpg","backstatus":"\u65e0\u552e\u540e","backmoney":0,"itemid":"14727425"}]}]}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void orderInfo(String orderNo) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=order_info";

        reqParam += "&jOrderNo="+orderNo;

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"orderno":"314721423670027","createtime":"2016-08-26 00:26:07","paytime":"2016-08-26 00:27:28","shiptime":"2016-08-26 09:36:54","modifytime":"2016-08-31 19:03:19","payamount":"22.50","payexpress":"0.00","discount":"0","paytype":"7","buyerphone":"13892074805","buyertel":"","buyerid":"","area":"610427","expresscode":"666772910420","remark":"","status":5,"buyerarea":"陕西省咸阳市彬县","buyeraddress":"新北街建材市场53号小娥床上用品","buyername":"高小娥","expressname":"天天快递","sellremark":"","username":"136****3612","new_area":"陕西省|咸阳市|彬县","realmoney":"22.50","goodslist":[{"goodsid":"13727225","goods_sku_id":"315335034","goodsname":"齐心多色文件袋(10个)","goodsno":"C330","goodsnum":"1","goodsprice":"22.50","goodszvalue":"白色","goodsfvalue":"20个装","goodsimg":"\/\/s1.juancdn.com\/bao\/160815\/7\/8\/57b18aa2151ad1fe418b461b_800x800.jpg","backstatus":"无售后","backmoney":0,"itemid":"13727225"}]}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void getExpress() {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=get_express";

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"ems":{"id":"6","companyname":"EMS经济快递","code":"ems","comurl":"http:\/\/www.ems.com.cn\/","comtel":"11185","rule":"^\\w{13}$","source":"1"},"fanyukuaidi":{"id":"7","companyname":"凡宇快递","code":"fanyukuaidi","comurl":"http:\/\/www.fanyu56.com.cn\/","comtel":"4006-580-358","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"lianhaowuliu":{"id":"8","companyname":"联昊通","code":"lianhaowuliu","comurl":"http:\/\/www.lhtex.com.cn","comtel":"0769-88620000","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"quanfengkuaidi":{"id":"9","companyname":"全峰快递","code":"quanfengkuaidi","comurl":"http:\/\/www.qfkd.com.cn","comtel":"400-100-0001","rule":"^\\d{12}$","source":"1"},"quanyikuaidi":{"id":"10","companyname":"全一快递","code":"quanyikuaidi","comurl":"http:\/\/www.unitop-apex.com\/","comtel":"400-663-1111","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"city100":{"id":"11","companyname":"城市100","code":"city100","comurl":"","comtel":"010-52932760","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"suer":{"id":"12","companyname":"速尔快递","code":"suer","comurl":"http:\/\/www.sure56.com","comtel":"400-158-9888","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"yuantong":{"id":"13","companyname":"圆通速递","code":"yuantong","comurl":"http:\/\/www.yto.net.cn","comtel":"95554","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"zhongtong":{"id":"14","companyname":"中通快递","code":"zhongtong","comurl":"http:\/\/www.zto.cn","comtel":"95311","rule":"^\\d{12,13}$","source":"1"},"feiyuanvipshop":{"id":"15","companyname":"飞远配送","code":"feiyuanvipshop","comurl":"http:\/\/www.fyps.cn\/","comtel":"400-703-1313","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"zhaijisong":{"id":"16","companyname":"宅急送","code":"zhaijisong","comurl":"http:\/\/www.zjs.com.cn","comtel":"400-6789-000","rule":"^([\\w]{13}|[\\w]{10})$","source":"1"},"yunda":{"id":"17","companyname":"韵达快递","code":"yunda","comurl":"http:\/\/www.yundaex.com","comtel":"95546","rule":"^\\d{13}$","source":"1"},"tiantian":{"id":"18","companyname":"天天快递","code":"tiantian","comurl":"http:\/\/www.ttkdex.com","comtel":"400-188-8888","rule":"^\\w{12}$","source":"1"},"jiajikuaidi":{"id":"19","companyname":"佳吉快递","code":"jiajikuaidi","comurl":"http:\/\/www.jiaji.com\/","comtel":"400-820-5566","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"huitongkuaidi":{"id":"20","companyname":"百世汇通","code":"huitongkuaidi","comurl":"http:\/\/www.800bestex.com","comtel":"400-956-5656","rule":"^\\w{12,14}$","source":"1"},"lianbangkuaidi":{"id":"21","companyname":"联邦快递","code":"lianbangkuaidi","comurl":"http:\/\/cndxp.apac.fedex.com\/dxp.html","comtel":"400-889-1888","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"debangwuliu":{"id":"22","companyname":"德邦物流","code":"debangwuliu","comurl":"http:\/\/www.deppon.com","comtel":"95353","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"zhongtiewuliu":{"id":"23","companyname":"中铁快运","code":"zhongtiewuliu","comurl":"http:\/\/www.cre.cn","comtel":"95572","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"ztky":{"id":"24","companyname":"中铁物流","code":"ztky","comurl":"http:\/\/www.ztky.com","comtel":"400-000-5566","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"xinfengwuliu":{"id":"25","companyname":"信丰物流","code":"xinfengwuliu","comurl":"http:\/\/www.xf-express.com.cn","comtel":"400-830-6333","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"shunfeng":{"id":"26","companyname":"顺丰速运","code":"shunfeng","comurl":"http:\/\/www.sf-express.com","comtel":"95338","rule":"^\\d{12}$","source":"1"},"shentong":{"id":"27","companyname":"申通快递","code":"shentong","comurl":"http:\/\/www.sto.cn","comtel":"95543","rule":"^\\d{12,13}$","source":"1"},"longbanwuliu":{"id":"28","companyname":"龙邦速递","code":"longbanwuliu","comurl":"http:\/\/www.lbex.com.cn","comtel":"021-59218889","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"tiandihuayu":{"id":"29","companyname":"天地华宇","code":"tiandihuayu","comurl":"http:\/\/www.hoau.net","comtel":"400-808-6666","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"kuaijiesudi":{"id":"30","companyname":"快捷快递","code":"kuaijiesudi","comurl":"http:\/\/www.fastexpress.com.cn\/","comtel":"400-830-4888","rule":"^\\d{12,14}$","source":"1"},"xinbangwuliu":{"id":"31","companyname":"新邦物流","code":"xinbangwuliu","comurl":"http:\/\/www.xbwl.cn","comtel":"4008-000-222","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"annengwuliu":{"id":"32","companyname":"安能物流","code":"annengwuliu","comurl":"http:\/\/www.ane56.com\/home\/home.jsp","comtel":"400-104-0088","rule":"","source":"1"},"jiajiwuliu":{"id":"33","companyname":"佳吉快运","code":"jiajiwuliu","comurl":"http:\/\/www.jiaji.com\/","comtel":"400-820-5566","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"ganzhongnengda":{"id":"34","companyname":"能达速递","code":"ganzhongnengda","comurl":"http:\/\/www.nengdaex.com","comtel":"400-6886-765","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"youshuwuliu":{"id":"35","companyname":"优速物流","code":"youshuwuliu","comurl":"http:\/\/www.uc56.com","comtel":"400-1111-119","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"zengyisudi":{"id":"36","companyname":"增益速递","code":"zengyisudi","comurl":"http:\/\/www.zeny-express.com\/","comtel":"4008-456-789","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"cces":{"id":"37","companyname":"CCES","code":"cces","comurl":"http:\/\/www.gto365.com","comtel":"400-111-1123","rule":"","source":"1"},"youzhengguonei":{"id":"38","companyname":"邮政国内小包","code":"youzhengguonei","comurl":"http:\/\/yjcx.chinapost.com.cn","comtel":"11183","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"jinguangsudikuaijian":{"id":"39","companyname":"京广快递","code":"jinguangsudikuaijian","comurl":"http:\/\/www.szkke.com\/","comtel":"0769-88629888","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"UPS":{"id":"40","companyname":"UPS快递","code":"UPS","comurl":"http:\/\/www.ups.com\/cn","comtel":"400-820-8388","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yafengsudi":{"id":"41","companyname":"亚风快递","code":"yafengsudi","comurl":"http:\/\/www.airfex.net\/","comtel":"4001-000-002","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"datianwuliu":{"id":"42","companyname":"大田快运","code":"datianwuliu","comurl":"http:\/\/www.dtw.com.cn\/","comtel":"400-626-1166","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"dhl":{"id":"43","companyname":"DHL代理","code":"dhl","comurl":"http:\/\/www.cn.dhl.com\/","comtel":"","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"youzhengguoji":{"id":"44","companyname":"国际快递查询","code":"youzhengguoji","comurl":"http:\/\/www.ems.com.cn\/","comtel":"","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"anxindakuaixi":{"id":"45","companyname":"安信达快递","code":"anxindakuaixi","comurl":"http:\/\/www.anxinda.com\/","comtel":"400-626-2356","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yuefengwuliu":{"id":"46","companyname":"越丰物流","code":"yuefengwuliu","comurl":"http:\/\/www.yfexpress.com.hk","comtel":"00852-23909969","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"hkpost":{"id":"47","companyname":"香港进口","code":"hkpost","comurl":"http:\/\/www.xianggangjinkou.com\/","comtel":"400-086-0002","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yitongfeihong":{"id":"48","companyname":"一统快递","code":"yitongfeihong","comurl":"http:\/\/yitongfeihong.com","comtel":"","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yibangwuliu":{"id":"49","companyname":"一邦速递","code":"yibangwuliu","comurl":"http:\/\/www.ebon-express.com","comtel":"18688486668","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"guotongkuaidi":{"id":"50","companyname":"国通快递","code":"guotongkuaidi","comurl":"http:\/\/www.gto365.com\/","comtel":"400-111-1123","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"Feikangda":{"id":"51","companyname":"飞康达快运","code":"Feikangda","comurl":"","comtel":"010-84223376","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"saiaodi":{"id":"52","companyname":"赛澳递","code":"saiaodi","comurl":"http:\/\/www.51cod.com\/","comtel":"400-034-5888","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"quanritongkuaidi":{"id":"53","companyname":"全日通快递","code":"quanritongkuaidi","comurl":"http:\/\/www.at-express.com\/","comtel":"020-86298988","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yuntongkuaidi":{"id":"54","companyname":"运通中港物流","code":"yuntongkuaidi","comurl":"http:\/\/www.ytkd168.com\/","comtel":"0769-81156999","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"jiayunmeiwuliu":{"id":"55","companyname":"加运美速递","code":"jiayunmeiwuliu","comurl":"http:\/\/www.tms56.com\/","comtel":"0769-85515555","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yuanzhijiecheng":{"id":"56","companyname":"元智捷诚","code":"yuanzhijiecheng","comurl":"http:\/\/www.yjkd.com\/","comtel":"400-606-0909","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"auspost":{"id":"57","companyname":"澳邮快运","code":"auspost","comurl":"http:\/\/www.auexpress.com.au\/","comtel":"130-007-9988","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"emsguoji":{"id":"58","companyname":"EWE全球快递","code":"emsguoji","comurl":"https:\/\/www.everfast.com.au\/","comtel":"1300096655","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"jiayiwuliu":{"id":"59","companyname":"佳怡物流","code":"jiayiwuliu","comurl":"http:\/\/www.jiayi56.com\/","comtel":"400-631-9999","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"sutongwuliu":{"id":"60","companyname":"速通物流","code":"sutongwuliu","comurl":"http:\/\/www.sut56.com\/","comtel":"4006561185","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yuananda":{"id":"61","companyname":"源安达","code":"yuananda","comurl":"http:\/\/www.yadex.com.cn\/","comtel":"0769-85021875","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"baishiwuliu":{"id":"62","companyname":"百世物流","code":"baishiwuliu","comurl":"http:\/\/www.800best.com","comtel":"4008856561","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"ririshunwuliu":{"id":"63","companyname":"日日顺物流","code":"ririshunwuliu","comurl":"http:\/\/www.rrs.com\/wl\/","comtel":"4009999999","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"hongtaiwuliu":{"id":"64","companyname":"鸿泰物流","code":"hongtaiwuliu","comurl":"http:\/\/www.hnht56.com\/index.html","comtel":"4008607777","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"tengdawuliu":{"id":"65","companyname":"腾达物流","code":"tengdawuliu","comurl":"http:\/\/www.tengdawl.com\/","comtel":"4006337777","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"yuxingwuliu":{"id":"66","companyname":"宇鑫物流","code":"yuxingwuliu","comurl":"http:\/\/www.yx56.cn\/","comtel":"4006005566","rule":"^[0-9a-zA-Z]{1,}$","source":"1"},"pingandatengfei":{"id":"67","companyname":"平安达腾飞","code":"pingandatengfei","comurl":"http:\/\/www.padtf.com\/","comtel":"4009990988","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"yuanchengwuliu":{"id":"68","companyname":"远成物流","code":"yuanchengwuliu","comurl":"http:\/\/www.ycgwl.com\/","comtel":"4008201646","rule":"^[0-9a-zA-Z]{1,}$","source":"2"},"rufengda":{"id":"69","companyname":"如风达","code":"rufengda","comurl":"http:\/\/www.rufengda.com\/","comtel":"4000106660","rule":"^[0-9a-zA-Z]{1,}$","source":"0"},"hengluwuliu":{"id":"70","companyname":"恒路","code":"hengluwuliu","comurl":"http:\/\/www.e-henglu.com\/","comtel":"4001826666","rule":"^([\\w]{13}|[\\w]{10})$","source":"0"},"wanxiangwuliu":{"id":"71","companyname":"万象物流","code":"wanxiangwuliu","comurl":"http:\/\/www.ewinshine.com\/","comtel":"4008208088","rule":"^[\\d\\w\\-]{1,}$","source":"2"},"emsbiaozhun":{"id":"72","companyname":"EMS标准快递","code":"emsbiaozhun","comurl":"http:\/\/www.ems.com.cn\/index.html","comtel":"11183","rule":"","source":"1"}}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void sendGoods(String orderNo, String deliverEname, String deliverCname, String deliverNo) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=send_goods";

        reqParam += "&jOrderNo="+orderNo;
        reqParam += "&jDeliverEname="+deliverEname;
        try {
            reqParam += "&jDeliverCname=" + URLEncoder.encode(deliverCname, "utf-8");//转码
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        reqParam += "&jDeliverNo="+deliverNo;

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }


    private static void sgoodsInfo(String jSgoodsId) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=sgoods_info";

        reqParam += "&field="+SGOODS_INFO_OPTIONS.replaceAll(",","%2C");//',' 需转义为%2C
        reqParam += "&jSgoodsId="+jSgoodsId;//商品id(对应商品和库存接口sgoodsid)

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void getGoodsInventory(String goodsSkuId) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=get_goods_inventory";

        reqParam += "&goodsSkuId="+goodsSkuId.replaceAll(",","%2C");//商品编号，批量用','连接，如：1,2,3,4(',' 需转义为%2C，否则签名不过)

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":[{"goodsskuid":"318329779","goodsno":"B2432","inventory":16}]}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void updateGoodsInventory(String goodsSkuId, int goodsStock) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=update_goods_inventory";

        reqParam += "&goodsSkuId="+goodsSkuId;//商品编号
        reqParam += "&goodsStock="+String.valueOf(goodsStock);//0：按偏移量设置库存 1：直接覆盖设置实时库存 默认为 0
        reqParam += "&setType=1";//0：按偏移量设置库存 1：直接覆盖设置实时库存 默认为 0
//        reqParam += "&goodsNo=";//goodsNo string 否 货号

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void afterSaleList(int aftersaleType, int status) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=aftersale_list&jPagesize=100&jPage=1";//分页时使用结果中的count计算总页数，再循环获取总列表

        if (aftersaleType>0)
            reqParam += "&aftersaleType="+String.valueOf(aftersaleType);//售后类型 1 仅退款 2 退货退款
        if (status > 0)
            reqParam += "&status="+String.valueOf(status);//1 买家申请退款，等待卖家处理 2 卖家审核通过，等待买家寄回 3 卖家审核不通过 4 买家已寄回，等待卖家确认收货 5 卖家同意退款，等待退款到账
        // 6 退款成功 7 卖家拒绝退款 8 售后撤销 9 售后关闭 10 维权中
        String startDate = String.valueOf(DateTimeUtil.parseDateString("2016-08-26 00:00:00").getTime()/1000);
        String endDate = String.valueOf(DateTimeUtil.parseDateString("2016-08-31 23:55:00").getTime()/1000);
        String modefied_time = "";//|转义%7C
        if (null != startDate && !"".equals(startDate)) {
            modefied_time = startDate;
        }
        if (!"".equals(modefied_time) && null != endDate && !"".equals(endDate)) {
            modefied_time += "%7C"+ endDate;
        }
//        reqParam += "&modefied_time="+modefied_time;//起止的修改时间(默认拉最近 7 天，不允许搜索范围超过 7 天)格式 star_time|end_time，都是 unix 时间戳(10 位)，后台会以|分割，如果格式错误，系统将会忽略该参数，生成签名时 | 应转换成url 编码%7C

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"total":"3","list":[{"id":"8200703","type":"2","type_text":"退货退款","backno":"514724594461009","orderno":"314720295287974","userid":"28685168","orderuname":"152****1586","dealstatus":"86","dealstatus_text":"卖家拒绝退款","goods_nums":"1","money":"9.90","reason":"119","reason_text":"收到商品破损\/污渍\/变形等","addtime":"2016-08-29 16:30:46","modifytime":"2016-09-06 17:00:36","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心商务办公计算器","skuid":"338326779"},{"id":"8267601","type":"2","type_text":"退货退款","backno":"514726620211006","orderno":"314717972951891","userid":"3049622","orderuname":"一234517","dealstatus":"85","dealstatus_text":"买家已寄回，等待卖家确认收货","goods_nums":"1","money":"11.50","reason":"22","reason_text":"其他（7天无理由退货）","addtime":"2016-09-01 00:47:01","modifytime":"2016-09-04 11:43:28","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心B5线圈80页笔记本","skuid":"303535244"},{"id":"8305696","type":"1","type_text":"仅退款","backno":"514727956911007","orderno":"314717478463580","userid":"41283681","orderuname":"182****8160","dealstatus":"61","dealstatus_text":"售后撤销","goods_nums":"1","money":"9.50","reason":"29","reason_text":"快递\/物流一直未送达","addtime":"2016-09-02 13:54:51","modifytime":"2016-09-02 17:09:25","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心不锈钢剪刀(2把)","skuid":"360526486"}]}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void afterSaleDetail(String boNo) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=aftersale_detail";

        reqParam += "&boNo="+boNo;

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"id":"8200703","type":"2","type_text":"退货退款","backno":"514724594461009","orderno":"314720295287974","goodsid":"2302521","ordergoodsid":"126075433","userid":"28685168","sellerid":"24112204","amountstatus":"0","right":"0","rightstatus_text":"未维权","goods_nums":"1","orderuname":"152****1586","money":"9.90","reason":"119","reason_text":"收到商品破损\/污渍\/变形等","refundnote":"收到货物损坏了，","dealstatus":"86","dealstatus_text":"卖家拒绝退款","logistics":null,"addtime":"2016-08-29 16:30:46","modifytime":"2016-09-06 17:00:36","goodstitle":"齐心商务办公计算器","skuid":"338326779","goodsprice":"9.90","img":["\/\/s2.juancdn.com\/bao\/160829\/e\/d\/57c3f2a8151ad1bb788b45b5_612x816.jpg?iopcmd=thumbnail&type=8&width=100&height=100%7Ciopcmd=convert&Q=88&dst=jpg","\/\/s2.juancdn.com\/bao\/160829\/c\/2\/57c3f2af151ad1c6718b45e5_612x816.jpg?iopcmd=thumbnail&type=8&width=100&height=100%7Ciopcmd=convert&Q=88&dst=jpg"]}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

    private static void afterSaleHandle(String boNo, String opType, String reasonNote, float boMoney, String refuseReason) {
        String reqParam = "jCusKey=" + J_CUS_KEY + "&token=" + TOKEN + "&type=json&jType=aftersale_handle";

        reqParam += "&boNo="+boNo;//售后编号
        reqParam += "&opType="+opType;//1 商家审核同意售后（单个退货） 2 商家审核拒绝售后（单个退货）3 同意退款（单个退款） 4 拒绝退款（单个退款）
        reqParam += "&modifytime=" + DateTimeUtil.now().getTime()/1000;//售后最终更新的时间戳
        if (!"".equals(reasonNote)) {
            try {
                reqParam += "&reasonNote=" + URLEncoder.encode(reasonNote, "utf-8");//审核说明(同意时可传,不同意时必传) 转码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        if (boMoney>0)
            reqParam += "&boMoney="+String.valueOf(boMoney);//售后退款金额(退款操作时必传)
        if (!"".equals(refuseReason)) {
            try {
                reqParam += "&refuseReason="+URLEncoder.encode(refuseReason, "utf-8");//拒绝原因 ID(拒绝时必传) 转码
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        String result = StringUtil.retParamAsc(reqParam);
        String sign = MD5Util.encrypt32(result + "&code=" + APP_SECRET);//将code(见文档开头)拼接在最尾部并md5加密
        String paramResult = result + "&sign=" + sign;//最终请求参数
        System.out.println("param:"+paramResult);
        String resultStr = HttpUtil.post(BUSINESS_URL, paramResult);
        //{"status":1,"info":"20000","data":{"total":"3","list":[{"id":"8200703","type":"2","type_text":"退货退款","backno":"514724594461009","orderno":"314720295287974","userid":"28685168","orderuname":"152****1586","dealstatus":"86","dealstatus_text":"卖家拒绝退款","goods_nums":"1","money":"9.90","reason":"119","reason_text":"收到商品破损\/污渍\/变形等","addtime":"2016-08-29 16:30:46","modifytime":"2016-09-06 17:00:36","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心商务办公计算器","skuid":"338326779"},{"id":"8267601","type":"2","type_text":"退货退款","backno":"514726620211006","orderno":"314717972951891","userid":"3049622","orderuname":"一234517","dealstatus":"85","dealstatus_text":"买家已寄回，等待卖家确认收货","goods_nums":"1","money":"11.50","reason":"22","reason_text":"其他（7天无理由退货）","addtime":"2016-09-01 00:47:01","modifytime":"2016-09-04 11:43:28","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心B5线圈80页笔记本","skuid":"303535244"},{"id":"8305696","type":"1","type_text":"仅退款","backno":"514727956911007","orderno":"314717478463580","userid":"41283681","orderuname":"182****8160","dealstatus":"61","dealstatus_text":"售后撤销","goods_nums":"1","money":"9.50","reason":"29","reason_text":"快递\/物流一直未送达","addtime":"2016-09-02 13:54:51","modifytime":"2016-09-02 17:09:25","amountstatus":"0","right":"0","rightstatus_text":"未维权","goodstitle":"齐心不锈钢剪刀(2把)","skuid":"360526486"}]}}
        //转码
        System.out.println(StringUtil.ascii2native(resultStr));
    }

}

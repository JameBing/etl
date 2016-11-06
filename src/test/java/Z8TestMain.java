import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.utility.HttpsUtil;
import com.wangjunneil.schedule.utility.MD5Util;
import com.wangjunneil.schedule.utility.StringUtil;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import com.wangjunneil.schedule.common.Constants;

/**
 * 描述
 *
 * @author Administrator
 * @date 2016/9/7
 */
public class Z8TestMain {

    public static final String CALL_BACK_URL = "http://wangjun.bosyun.com/mark/zhe800/callback";

//    private static final String TOKEN_URL = "https://1.202.245.246/oauth/code";//测试
    private static final String TOKEN_URL = "https://openapi.zhe800.com/oauth/code";//生产

    private static final String APP_KEY = "MjEwOTZjN2ItNjcz";
    private static final String APP_SECRET = "YjY2NjY5OTItODBhYS00YzVjLWE0ZmEt";
    private static final String TOKEN = "YjVmYWI3OWUtNzY0YS00MzQ2LTlkYTctNzMzZDQyMGVhN2M5";

    public static void main(String[] args) throws Exception {
        //

//        System.out.println("2016-10-01 00:00:00".replace(" ","T"));
        String startTime = "2016-10-21T00:00:00";
        String endTime = "2016-10-21T23:59:59";
        JSONArray resultArr = new JSONArray();
        orders(startTime, endTime, 1, 0, resultArr);
        System.out.println("jsonArr.size:"+resultArr.size());

////        String url = "https://www.sunpage.com.sg/sso/login.asp";
//        String url = "https://openapi.zhe800.com/oauth/token";
//        String param = "grant_type=authorization_code&client_id=MjEwOTZjN2ItNjcz&redirect_uri=http://wangjun.bosyun.com/mark/zhe800/callback&code=OTIxMD&state=1&client_secret=YjY2NjY5OTItODBhYS00YzVjLWE0ZmEt";
//        System.out.println(HttpsUtil.post(url, param));//TODO 现在还有问题 403==Forbidden  应该返回code错误


//        System.out.println(orders());//查询订单列表
    }

    private static void orders(String startTime, String endTime, int page, int totalSize,JSONArray resultArr) {
        String url = Constants.Z8_URL + "orders.json";
        String reqParam = "page="+String.valueOf(page) +"&per_page=100&start_time=" + startTime + "&end_time=" + endTime + "&sort_rule=0&order_state=0" +
            "&access_token="+TOKEN+"&app_key="+APP_KEY;
        String result = StringUtil.retParamAsc(reqParam);//参数ASCII排序
        String sign = MD5Util.encrypt32(TOKEN + StringUtil.retParamAscAdd(reqParam) + TOKEN).toUpperCase();//各参数ASCII排序，token+多个(key+value)+token进行MD5
        String paramResult = result+"&sign="+sign;
        try {
            String resultStr = HttpsUtil.getFormZ8(url + "?" + paramResult);

            JSONObject json = JSONObject.parseObject(resultStr);
            if (json.containsKey("errorMsg")) {
                System.out.println(json.getString("errorMsg"));
                return;
            }

            if (!json.containsKey("total_num")) return;

            if (totalSize == 0 && json.containsKey("total_num")) {
                totalSize = Integer.parseInt(json.getString("total_num"));
            }

            if(totalSize==0) return;

            int pageSize = totalSize/ 100;
            if(totalSize % 100 > 0)  pageSize += 1;
            JSONArray ordersTemp1 = json.getJSONArray("orders");
            ordersTemp1.stream().forEach(p -> {
                resultArr.add(p);
            });
//            for(int j=0;j<ordersTemp1.size();j++){
//                resultArr.add(ordersTemp1.getJSONObject(j));
//            }
            if(page < pageSize) {
                orders(startTime, endTime, ++page, totalSize, resultArr);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * access_token=YjVmYWI3OWUtNzY0YS00MzQ2LTlkYTctNzMzZDQyMGVhN2M5&app_key=MjEwOTZjN2ItNjcz&end_time=2016-09-07T09:00&order_state=0&page=1&per_page=50&sort_rule=0&start_time=2016-08-01T09:00
         ECE27FBC30837CAE41BCFECC23ED1048
         https://openapi.zhe800.com/api/erp/v2/orders.json?access_token=YjVmYWI3OWUtNzY0YS00MzQ2LTlkYTctNzMzZDQyMGVhN2M5&app_key=MjEwOTZjN2ItNjcz&end_time=2016-09-07T09:00&order_state=0&page=1&per_page=50&sort_rule=0&start_time=2016-08-01T09:00&sign=ECE27FBC30837CAE41BCFECC23ED1048
         */
//        HttpUtil.get(url+"?"+paramResult);
    }



}

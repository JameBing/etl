import com.alibaba.fastjson.JSONObject;
import com.jd.open.api.sdk.DefaultJdClient;
import com.jd.open.api.sdk.JdClient;
import com.jd.open.api.sdk.domain.order.OrderDetailInfo;
import com.jd.open.api.sdk.domain.order.OrderInfo;
import com.jd.open.api.sdk.domain.order.OrderSearchInfo;
import com.jd.open.api.sdk.request.order.OrderGetRequest;
import com.jd.open.api.sdk.request.order.OrderSearchRequest;
import com.jd.open.api.sdk.response.order.OrderGetResponse;
import com.jd.open.api.sdk.response.order.OrderSearchResponse;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.jd.JdCrmOrder;
import com.wangjunneil.schedule.utility.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuzhicheng on 2016/9/2.
 */
public class JdTestMain {
    private static final String appkey = "A2F764131A3C7524D2646829FE84E6EC";
    private static final String secret = "2d98f2efc5cb414fb93b02d6b09917b5";
    private static final String sessionKey = "cf5c6772-d49a-4fa6-870f-eca8f7ba5c3a";

    public static void main(String[] args) throws Exception {
//        queryDetailInfo();
        List<OrderSearchInfo> list = new ArrayList<OrderSearchInfo>();
        queryList(list,1,0);
        System.out.println(list.size());

    }

    private static void queryList(List list,int page,int totalSize) throws com.jd.open.api.sdk.JdException {
        JdClient client = new DefaultJdClient(Constants.JD_SERVICE_URL, sessionKey, appkey, secret);

        String condTime = DateTimeUtil.nowDateString("yyyy-MM-dd HH:mm:ss");
        OrderSearchRequest request = new OrderSearchRequest();
        request.setStartDate("2015-08-01 00:00:00");
        request.setEndDate("2015-08-31 23:59:59");
        request.setDateType("1");
        request.setOrderState(Constants.JD_SYNC_ORDER_STATE);  // 只同步等待出库的订单
        request.setOptionalFields(Constants.JD_ORDER_OPTIONAL_FIELD);
        request.setPage(String.valueOf(page));
        request.setPageSize("100");
        OrderSearchResponse orderSearchResponse = client.execute(request);
        int pageNum = 1;
        if ("0".equals(orderSearchResponse.getCode())) {
            if (totalSize == 0) {
                totalSize = orderSearchResponse.getOrderInfoResult().getOrderTotal();
            }
            List<OrderSearchInfo> tempList = orderSearchResponse.getOrderInfoResult().getOrderInfoList();
            tempList.stream().forEach(p -> {
                list.add(p);
            });
            pageNum = totalSize/100;
            if ( totalSize%100 > 0) {
                pageNum += 1;
            }
            if (page < pageNum) {
                queryList(list,++page,totalSize);
            }
        }
    }

    private static void queryDetailInfo() throws com.jd.open.api.sdk.JdException {
        JdClient client=new DefaultJdClient(Constants.JD_SERVICE_URL, sessionKey, appkey, secret);

        OrderGetRequest request=new OrderGetRequest();

        request.setOrderId("38793253984");
//        request.setOptionalFields("vender_remark,");
        //request.setOrderState( "jingdong" );

        OrderGetResponse response=client.execute(request);
        System.out.println(JSONObject.toJSONString(response.getOrderDetailInfo().getOrderInfo()));

        JdCrmOrder jdCrmOrder = null;
        OrderInfo orderSearchInfo = response.getOrderDetailInfo().getOrderInfo();
        jdCrmOrder = new JdCrmOrder();
//        System.out.println(orderSearchInfo.getOrderStartTime());
//        System.out.println(DateTimeUtil.parseDateString(orderSearchInfo.getOrderStartTime()));
//        System.out.println(DateTimeUtil.parseDateString(orderSearchInfo.getOrderStartTime()).getTime());
//        System.out.println(DateTimeUtil.addHour(DateTimeUtil.parseDateString(orderSearchInfo.getOrderStartTime()), -8));
//        System.out.println(DateTimeUtil.addHour(DateTimeUtil.parseDateString(orderSearchInfo.getOrderStartTime()), -8).getTime());
        jdCrmOrder.setOrder_start_time(DateTimeUtil.addHour(DateTimeUtil.parseDateString(orderSearchInfo.getOrderStartTime()), -8));//TODO
        jdCrmOrder.setOrder_end_time(orderSearchInfo.getOrderEndTime());
        jdCrmOrder.setPayment_confirm_time(orderSearchInfo.getPaymentConfirmTime());
        System.out.println(JSONObject.toJSONString(jdCrmOrder));
    }
}

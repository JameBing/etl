import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-11-25.
 */
public class TestC {

    private static final SystemParam sysPram = new SystemParam("459", "5ca2cf48c1d6dc4253f9d491b2246091");

    public static void main(String[] args) {

        try{
            System.out.println(APIFactory.getPoiAPI().poiOpen(sysPram,"7777"));
        }catch (ApiOpException ex){
            ex.getStackTrace();
        }catch (ApiSysException ex){
            ex.getStackTrace();
        }

//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, -2);
//        System.out.println(cal.getTime());
//        OrderInfo order = new OrderInfo();
//        OrderInfo ordea = new OrderInfo();
//        if (order.equals(ordea))
//        {
//            System.out.println("=");
//        }else {
//            System.out.println("!=");
//        }
//        //输出的是对象的哈希码
//        System.out.println(order);
    }
}

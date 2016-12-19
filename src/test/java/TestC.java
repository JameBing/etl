import com.wangjunneil.schedule.entity.meituan.OrderInfo;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-11-25.
 */
public class TestC {

    public static void main(String[] args) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -2);
        System.out.println(cal.getTime());
        OrderInfo order = new OrderInfo();
        OrderInfo ordea = new OrderInfo();
        if (order.equals(ordea))
        {
            System.out.println("=");
        }else {
            System.out.println("!=");
        }
        //输出的是对象的哈希码
        System.out.println(order);
    }
}

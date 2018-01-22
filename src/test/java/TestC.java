import com.sankuai.meituan.waimai.opensdk.exception.ApiOpException;
import com.sankuai.meituan.waimai.opensdk.exception.ApiSysException;
import com.sankuai.meituan.waimai.opensdk.factory.APIFactory;
import com.sankuai.meituan.waimai.opensdk.vo.SystemParam;
import com.wangjunneil.schedule.entity.meituan.OrderInfo;
import com.wangjunneil.schedule.utility.DateTimeUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016-11-25.
 */
public class TestC{

    private static final SystemParam sysPram = new SystemParam("459", "5ca2cf48c1d6dc4253f9d491b2246091");

    public static void main(String[] args) {
      /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ;
        Calendar cale = Calendar.getInstance();*/

       /* Integer aa = 1502504992;
        Long bb  = Long.parseLong(aa.toString());
      /*  System.out.println("时间ooooooo:"+DateTimeUtil.dateFormat(new Date(bb*1000), "yyyy-MM-dd HH:mm:ss"));*//*
       long time1 = DateTimeUtil.parseDateString("2017-10-17 12:15:00").getTime();
       long time2 = DateTimeUtil.parseDateString("2017-10-17 12:45:00").getTime();
       long aa = time2-time1;
       System.out.println("日期1:"+time1);
        System.out.println("日期2:"+time2);
        System.out.println("差值:"+aa);*/
       /* for(int i=0;i<12;i++){
            cale.set(Calendar.YEAR,2016);
            cale.set(Calendar.MONTH,i+1);
           // cale.set(Calendar.DAY_OF_MONTH, 1);
            System.out.println( cale.get(Calendar.YEAR));
           // cale.set(Calendar.DAY_OF_MONTH, cale.getActualMaximum(Calendar.DAY_OF_MONTH));
            //System.out.println(cale.getTime());
        }
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);

        int hour = cale.get(Calendar.HOUR_OF_DAY);
        int minute = cale.get(Calendar.MINUTE);
        int second = cale.get(Calendar.SECOND);
        int dow = cale.get(Calendar.DAY_OF_WEEK);
        int dom = cale.get(Calendar.DAY_OF_MONTH);
        int doy = cale.get(Calendar.DAY_OF_YEAR);*/
        /*String aa ="http://etl.ziyanfoods.com:8080/mark/tuangou/callBack";
        try {
            System.out.println(java.net.URLEncoder.encode(aa,"utf-8"));
        }catch (Exception e){

        }*/

        /*c.setTime(new Date());
        int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i=1;i<=totalDays;i++){
            c.set(Calendar.Day, i);
            System.out.println(c.getTime());
        }*/

       /* try{
            System.out.println(APIFactory.getPoiAPI().poiOpen(sysPram,"7777"));
        }catch (ApiOpException ex){
            ex.getStackTrace();
        }catch (ApiSysException ex){
            ex.getStackTrace();
        }*/

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

        String str="ABC_001_ABC_ABC";
        System.out.println(str.indexOf("B"));
    }
}

package com.wangjunneil.schedule.service;

import com.wangjunneil.schedule.common.*;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.jd.JdAccessToken;
import com.wangjunneil.schedule.entity.jp.JPAccessToken;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.entity.sys.Status;
import com.wangjunneil.schedule.entity.tm.TmallAccessToken;
import com.wangjunneil.schedule.entity.z8.Z8AccessToken;
import com.wangjunneil.schedule.service.jp.JpApiService;
import com.wangjunneil.schedule.service.sys.SysInnerService;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ConstantException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.function.Function;


/**
 *
 * Created by wangjun on 8/8/16.
 */
@Service
public class SysFacadeService {

    private static Logger log = Logger.getLogger(SysFacadeService.class.getName());

    @Autowired
    private SysInnerService sysInnerService;

    @Autowired
    private JpApiService jpApiService;

    @Autowired
    private SysFacadeService sysFacadeService;

    public Cfg findJdCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_JD);
    }

    public Cfg findTmCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_TM);
    }

    public Cfg findJPCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_JP);
    }
    public Cfg findElemeCfg() {
        return sysInnerService.findCfg(Constants.PLATFORM_WAIMAI_ELEME);
    }

    public Cfg findZ8Cfg() {
        return sysInnerService.findCfg("z8");
    }

    public List<Cfg> findAllCfg() {
        return sysInnerService.findAllCfg();
    }

    public JdAccessToken getJdToken() {
        return sysInnerService.getJdToken();
    }

    public TmallAccessToken getTmToken() {
        return sysInnerService.getTmToken();
    }

    public Z8AccessToken getZ8Token(){
        return sysInnerService.getZ8Token();
    }

    public JPAccessToken getJpToken() throws ScheduleException {
        JPAccessToken jpAccessToken = sysInnerService.getJPToken();
        if (jpAccessToken == null) {
            jpAccessToken = jpApiService.requestToken(findJPCfg());
            sysInnerService.addJPAccessToken(jpAccessToken);
        } else {
            Date expireTime = jpAccessToken.getExpireDate();
            if (DateTimeUtil.now().after(expireTime))
                jpAccessToken = sysInnerService.getJPToken();
        }
        return jpAccessToken;
    }

    public void addPlatform(Cfg cfg,String editType) throws ScheduleException {
        sysInnerService.addPlatform(cfg,editType);
    }

    public void delPlatform(Cfg cfg) throws ScheduleException {
        sysInnerService.delPlatform(cfg);
    }

    public void addPlatformStatus(Status status, String platformType, Boolean scheduleState){
        if(scheduleState) {
            status.setStartup(true);
            if(status.getStartupTime() == null){
                status.setStartupTime(DateTimeUtil.now());
            }
        }else{
            status.setStartup(false);
            if(status.getShutdownTime() == null){
                status.setShutdownTime(DateTimeUtil.now());
            }
        }
        sysInnerService.addPlatformStatus(status, platformType,scheduleState);
    }

    public void initializeReset(Status status){
        sysInnerService.initializeReset(status);
    }

    /*======================================外卖=========================================================*/

    public int getSerialNum(String date,String moudle){
        return sysInnerService.getSerialNum(date,moudle);
    }


    //生成外卖订单编号
    public String getOrderNum(String shopId){
        String strShopId =  shopId.length()>5?shopId.substring(0,5):shopId;
        String date = DateTimeUtil.nowDateString("yyyyMMdd").substring(2,8);
        return  "W" + String.format("%05d", Integer.valueOf(strShopId)) + "99" + date + String.format("%06d",Integer.valueOf(getSerialNum(date,"order")));
    }

    //订单插入
    public void updSynWaiMaiOrder(OrderWaiMai orderWaiMai) throws  ScheduleException{
         sysInnerService.updSynWaiMaiOrder(orderWaiMai);
    }

    //订单插入 list
    public  void  updSynWaiMaiOrder(List<OrderWaiMai> orderWaiMaiList) throws  ScheduleException{
        orderWaiMaiList.forEach(v->{
            sysInnerService.updSynWaiMaiOrder(v);
        });
    }

    //异常处理 匿名函数  ?需要验证代码可行性
    public    Function<Object,Log> functionRtn =(t)->{
        Log log1 = new Log();
        String logId = DateTimeUtil.dateFormat(DateTimeUtil.now(),"yyyyMMddHHmmssSSS");
        log1.setLogId(logId);
        log1.setType("E");
        log1.setDateTime(log1.getDateTime());
        switch (t.getClass().getName().toLowerCase()){
            case "com.wangjunneil.schedule.common.baiduexception":
                BaiDuException baiDuException = (BaiDuException)t;
                log1.setPlatform(Constants.PLATFORM_WAIMAI_BAIDU);
                log1.setMessage(baiDuException.getMessage());
                log1.setRequest(baiDuException.getRequestStr());
                log1.setCatchExName("BaiduException");
                log1.setInnerExName(baiDuException.getInnerExName());
                log1.setStackInfo(baiDuException.getStackInfo());

                break;
            case "com.wangjunneil.schedule.common.jdhomeexception":
                JdHomeException jdHomeException = (JdHomeException)t;
                log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
                log1.setMessage(jdHomeException.getMessage());
                log1.setRequest(jdHomeException.getRequestStr());
                log1.setCatchExName("JdHomeException");
                log1.setInnerExName(jdHomeException.getInnerExName());
                log1.setStackInfo(jdHomeException.getStackInfo());
                break;
            case "com.wangjunneil.schedule.common.elemeexception":
                ElemeException elemeException = (ElemeException)t;
                log1.setPlatform(Constants.PLATFORM_WAIMAI_ELEME);
                log1.setMessage(elemeException.getMessage());
                log1.setRequest(elemeException.getRequestStr());
                log1.setCatchExName("ElemeException");
                log1.setInnerExName(elemeException.getInnerExName());
                log1.setStackInfo(elemeException.getStackInfo());

                break;
            case "com.wangjunneil.schedule.common.meituanexception":
                MeiTuanException meiTuanException = (MeiTuanException)t;
                 log1.setPlatform(Constants.PLATFORM_WAIMAI_MEITUAN);
                 log1.setMessage(meiTuanException.getMessage());
                log1.setRequest(meiTuanException.getRequestStr());
                log1.setCatchExName("MeiTuanException");
                log1.setInnerExName(meiTuanException.getInnerExName());
                log1.setStackInfo(meiTuanException.getStackInfo());
                break;
            case "java.lang.exception":
                Exception exception = (Exception)t;
                 log1.setMessage(exception.getMessage());
                log1.setCatchExName("Exception");
                log1.setInnerExName(exception.getClass().getName());
                StringBuffer sb = new StringBuffer();
                StackTraceElement[] traces = exception.getStackTrace();
                for (int i = 0; i < traces.length; i++) {
                    StackTraceElement element = traces[i];
                    sb.append(element.toString() + "\n");
                }
                log1.setStackInfo(sb.toString());
                break;
            case "com.wangjunneil.schedule.common.scheduleexception":
               ScheduleException scheduleException = (ScheduleException)t;
                log1.setMessage(scheduleException.getMessage());
                log1.setCatchExName("ScheduleException");
                log1.setInnerExName(scheduleException.getClass().getName());
                StringBuffer sb1 = new StringBuffer();
                StackTraceElement[] traces2 = scheduleException.getStackTrace();
                for (int i = 0; i < traces2.length; i++) {
                    StackTraceElement element = traces2[i];
                    sb1.append(element.toString() + "\n");
                }
                log1.setStackInfo(sb1.toString());
                break;
            default:
                break;
        }
        return  log1;
    } ;

    //日志插入
    public void updSynLog(Log log){
          sysInnerService.updSynLog( log);
    }
}

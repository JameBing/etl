package com.wangjunneil.schedule.service;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.common.OrderWaiMai;
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
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


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
}

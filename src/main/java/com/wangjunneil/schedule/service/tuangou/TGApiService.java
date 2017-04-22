package com.wangjunneil.schedule.service.tuangou;

import com.sankuai.sjst.platform.developer.domain.RequestSysParams;
import com.sankuai.sjst.platform.developer.request.*;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.common.TuanGouException;
import com.wangjunneil.schedule.entity.tuangou.AccessToken;
import com.wangjunneil.schedule.entity.tuangou.CouponRequest;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yangyongbing
 * @since on 2017/3/20.
 */
@Service
public class TGApiService {

    @Autowired
    private  TGInnerService tgInnerService;

    public static String signKey = "kto9junv8prif2yu";

    /**
     * 获取验卷信息
     * @param couponCode
     * @param ePoiId
     * @return
     */
    public String couponQueryById (String couponCode,String ePoiId) throws TuanGouException,ScheduleException {
        Map<String,String> params = new HashMap<>();
        //根据门店获取token
        AccessToken accessToken = tgInnerService.findToken(ePoiId);
        if(StringUtil.isEmpty(accessToken)){
            throw  new ScheduleException("门店未完成映射");
        }
        String token = accessToken.getAppAuthToken();
        //系统参数
        RequestSysParams sysParams = new RequestSysParams(signKey, token);
        CipCaterCouponQueryRequest request = new CipCaterCouponQueryRequest();
        request.setRequestSysParams(sysParams);
        request.setCouponCode(couponCode);
        String resultJson = "";
        try {
            resultJson = request.doRequest();
        } catch (Exception e) {
            throw new TuanGouException("签名失败",e);
        }
        return resultJson;
    }


    /**
     * 验卷准备
     * @param couponCode
     * @param ePoiId
     * @return
     */
    public String couponPrepare (String couponCode,String ePoiId) throws TuanGouException,ScheduleException {
        Map<String,String> params = new HashMap<>();
        //根据门店获取token
        AccessToken accessToken = tgInnerService.findToken(ePoiId);
        if(StringUtil.isEmpty(accessToken)){
            throw  new ScheduleException("门店未完成映射");
        }
        String token = accessToken.getAppAuthToken();
        //系统参数
        RequestSysParams sysParams = new RequestSysParams(signKey, token);
        CipCaterCouponConsumptionPrepareRequest request = new CipCaterCouponConsumptionPrepareRequest();
        request.setRequestSysParams(sysParams);
        request.setCouponCode(couponCode);
        String resultJson = "";
        try {
            resultJson = request.doRequest();
        } catch (Exception e) {
            throw new TuanGouException("签名失败",e);
        }
        return resultJson;
    }

    /**
     * 执行验卷
     * @param couponRequest
     * @return
     */
    public String couponConsume(CouponRequest couponRequest) throws TuanGouException,ScheduleException {
        Map<String,String> params = new HashMap<>();
        //根据门店获取token
        AccessToken accessToken = tgInnerService.findToken(couponRequest.getePoiId());
        if(StringUtil.isEmpty(accessToken)){
            throw  new ScheduleException("门店未完成映射");
        }
        String token = accessToken.getAppAuthToken();
        //系统参数
        RequestSysParams sysParams = new RequestSysParams(signKey, token);
        CipCaterCouponConsumeRequest request = new CipCaterCouponConsumeRequest();
        request.setRequestSysParams(sysParams);
        request.setCouponCode(couponRequest.getCouponCode());
        request.setCount(couponRequest.getCount());
        request.seteOrderId(Long.parseLong(couponRequest.geteOrderId()));
        request.seteId(Long.parseLong(couponRequest.getePoiId()));
        request.seteName(accessToken.getEpoiName());
        String resultJson = "";
        try {
            resultJson = request.doRequest();
        } catch (Exception e) {
            throw new TuanGouException("签名失败",e);
        }
        return resultJson;
    }

    /**
     * 撤销验卷
     * @param couponRequest
     * @return
     */
    public String couponCancel(CouponRequest couponRequest) throws TuanGouException,ScheduleException {
        Map<String,String> params = new HashMap<>();
        //根据门店获取token
        AccessToken accessToken = tgInnerService.findToken(couponRequest.getePoiId());
        if(StringUtil.isEmpty(accessToken)){
            throw  new ScheduleException("门店未完成映射");
        }
        String token = accessToken.getAppAuthToken();
        //系统参数
        RequestSysParams sysParams = new RequestSysParams(signKey, token);
        CipCaterCouponConsumptionCancelRequest request = new CipCaterCouponConsumptionCancelRequest();
        request.setRequestSysParams(sysParams);
        request.setCouponCode(couponRequest.getCouponCode());
        request.setType(1);
        request.seteId(Long.parseLong(couponRequest.getePoiId()));
        request.seteName(accessToken.getEpoiName());
        String resultJson = "";
        try {
            resultJson = request.doRequest();
        } catch (Exception e) {
            throw new TuanGouException("签名失败",e);
        }
        return resultJson;
    }

    public String queryListByDate(CouponRequest couponRequest) throws TuanGouException,ScheduleException{
        Map<String,String> params = new HashMap<>();
        //根据门店获取token
        AccessToken accessToken = tgInnerService.findToken(couponRequest.getePoiId());
        if(StringUtil.isEmpty(accessToken)){
            throw  new ScheduleException("门店未完成映射");
        }
        String token = accessToken.getAppAuthToken();
        //系统参数
        RequestSysParams sysParams = new RequestSysParams(signKey, token);
        CipCaterCouponConsumptionHistoryRequest request = new CipCaterCouponConsumptionHistoryRequest();
        request.setRequestSysParams(sysParams);
        String date = DateTimeUtil.dateFormat(DateTimeUtil.formatDateString(couponRequest.getDate(), "yyyy-MM-dd"), "yyyy-MM-dd");
        request.setDate(date);
        request.setOffset(0);
        request.setLimit(10);
        String resultJson = "";
        try {
            resultJson = request.doRequest();
        } catch (Exception e) {
            throw new TuanGouException("签名失败",e);
        }
        return resultJson;
    }


}

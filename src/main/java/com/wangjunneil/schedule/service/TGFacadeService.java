package com.wangjunneil.schedule.service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.JdHomeException;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.common.TuanGouException;
import com.wangjunneil.schedule.entity.common.Log;
import com.wangjunneil.schedule.entity.common.Rtn;
import com.wangjunneil.schedule.entity.common.RtnSerializer;
import com.wangjunneil.schedule.entity.tuangou.CouponRequest;
import com.wangjunneil.schedule.service.tuangou.TGApiService;
import com.wangjunneil.schedule.service.tuangou.TGInnerService;
import com.wangjunneil.schedule.utility.StringUtil;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author yangyongbing
 * @since  2017/3/20.
 */
@Service
public class TGFacadeService {

    @Autowired
    private TGInnerService tgInnerService;

    @Autowired
    private TGApiService tgApiService;

    @Autowired
    private SysFacadeService sysFacadeService;

    /**
     * 已验卷查询
     * @param code 验卷码
     * @return
     */
    public String couponQueryById(String code,String ePoiId){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(code) || StringUtil.isEmpty(ePoiId)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请输入验卷码或者门店编号");
            return gson.toJson(rtn);
        }
        String json = "";
        try {
            json = tgApiService.couponQueryById(code,ePoiId);
        }catch (TuanGouException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(ePoiId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("查询验卷明细失败", ePoiId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"couponCode\":{0},\"ePoiId\":{1}", code,ePoiId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(ePoiId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("查询验卷明细失败",ePoiId));
            }
        return json;
        }
    }


    public String couponPrepare(String code ,String ePoiId){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(code) || StringUtil.isEmpty(ePoiId)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请输入验卷码或者门店编号");
            return gson.toJson(rtn);
        }
        String json = "";
        try {
            json = tgApiService.couponPrepare(code, ePoiId);
        }catch (TuanGouException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(ePoiId.concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("验卷准备失败", ePoiId));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"couponCode\":{0},\"ePoiId\":{1}", code,ePoiId)).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(ePoiId);
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("验卷准备失败",ePoiId));
            }
            return json;
        }
    }

    /**
     * 执行验卷
     * @param request
     * @return
     */
    public String couponConsume(CouponRequest request){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(request)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.getCouponCode())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("验卷码为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.getCount())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("验卷码数量");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.getePoiId())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("门店编号为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.geteOrderId())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("订单编号为空");
            return gson.toJson(rtn);
        }
        String resultJson = "";
        try {
            resultJson = tgApiService.couponConsume(request);
        }catch (TuanGouException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(request.getePoiId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("执行验卷失败", request.getePoiId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"couponCode\":{0},\"ePoiId\":{1},\"eOrderId\":{2}",
                        request.getCouponCode(),request.getePoiId(),request.geteOrderId())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(request.getePoiId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("执行验卷失败",request.getePoiId()));
            }
            return resultJson;
        }
    }


    /**
     * 撤销验卷
     * @param request
     * @return
     */
    public String couponCancel(CouponRequest request){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(request)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.getCouponCode())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("验卷码为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(request.getePoiId())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("门店编号为空");
            return gson.toJson(rtn);
        }
        String resultJson = "";
        try {
            resultJson = tgApiService.couponCancel(request);
        }catch (TuanGouException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(request.getePoiId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("撤销验卷失败", request.getePoiId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"couponCode\":{0},\"ePoiId\":{1}",
                        request.getCouponCode(),request.getePoiId())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(request.getePoiId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("撤销验卷失败",request.getePoiId()));
            }
            return resultJson;
        }
    }

    /**
     * 查询绑定门店某天在新美大的验券历史
     * @param couponRequest
     * @return
     */
    public String queryListByDate(CouponRequest couponRequest){
        Rtn rtn = new Rtn();
        Log log1 = null;
        Gson gson = new GsonBuilder().registerTypeAdapter(Rtn.class,new RtnSerializer()).disableHtmlEscaping().create();
        if(StringUtil.isEmpty(couponRequest)){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求参数为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(couponRequest.getePoiId())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("门店编号为空");
            return gson.toJson(rtn);
        }
        if(StringUtil.isEmpty(couponRequest.getDate())){
            rtn.setCode(-1);
            rtn.setDesc("error");
            rtn.setRemark("请求日期为空");
            return gson.toJson(rtn);
        }
        String resultJson = "";
        try {
            resultJson = tgApiService.queryListByDate(couponRequest);
        }catch (TuanGouException ex){
            rtn.setCode(-997);
            log1 = sysFacadeService.functionRtn.apply(ex);
        }catch (ScheduleException ex){
            rtn.setCode(-999);
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
        }catch (Exception ex){
            log1 = sysFacadeService.functionRtn.apply(ex);
            log1.setPlatform(Constants.PLATFORM_WAIMAI_JDHOME);
            rtn.setCode(-998);
        }finally {
            //有异常产生
            if (log1 !=null){
                log1.setLogId(couponRequest.getePoiId().concat(log1.getLogId()));
                log1.setTitle(MessageFormat.format("门店验卷历史查询失败", couponRequest.getePoiId()));
                if (StringUtil.isEmpty(log1.getRequest()))
                    log1.setRequest("{".concat(MessageFormat.format("\"ePoiId\":{0},\"date\":{1}",couponRequest.getePoiId(),couponRequest.getDate())).concat("}"));
                sysFacadeService.updSynLog(log1);
                rtn.setDynamic(couponRequest.getePoiId());
                rtn.setDesc("发生异常");
                rtn.setLogId(log1.getLogId());
                rtn.setRemark(MessageFormat.format("门店验卷历史查询失败",couponRequest.getePoiId()));
            }
            return resultJson;
        }
    }


    /**
     * token 回调接收
     * @param stringMap token
     * @return
     */
    public String appCallBack( Map<String,String[]> stringMap ){
        if(StringUtil.isEmpty(stringMap)){
            return "{data:\"error\"}";
        }
        String appAuthToken = stringMap.get("appAuthToken")[0];
        String ePoiId = stringMap.get("ePoiId")[0];
        try {
            tgInnerService.addToken(appAuthToken,ePoiId);
        }catch (Exception e){
            return "{data:\"error\"}";
        }
         return "{data:\"success\"}";
    }
}

package com.wangjunneil.schedule.service.jp;

import com.alibaba.fastjson.JSONObject;
import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.common.ScheduleException;
import com.wangjunneil.schedule.entity.jp.JPAccessToken;
import com.wangjunneil.schedule.entity.sys.Cfg;
import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.HttpUtil;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Created by wangjun on 9/6/16.
 */
@Service
public class JpApiService {

    private static Logger log = Logger.getLogger(JpApiService.class.getName());

    public JPAccessToken requestToken(Cfg cfg) throws ScheduleException {
        StringBuffer bodyParam = new StringBuffer("secret=");
        bodyParam.append(cfg.getAppKey()).append("&scope=")
            .append(Constants.JP_AUTHORIZE_SCOPE)
            .append("&type=json");
        String returnJson = HttpUtil.post(Constants.JP_LINK_TOKEN_URL, bodyParam.toString());
        JSONObject jsonObject = JSONObject.parseObject(returnJson);
        int status = jsonObject.getIntValue("status");
        if (status != 1) {
            throw new ScheduleException("jp requestToken failure " + jsonObject.getString("info"));
        }

        JSONObject dataObj = jsonObject.getJSONObject("data");
        JPAccessToken accessToken = new JPAccessToken();
        accessToken.setToken(dataObj.getString("token"));
        accessToken.setTimeout(dataObj.getLongValue("timeout"));
        accessToken.setExpire(dataObj.getLongValue("expire"));
        accessToken.setExpireDate(DateTimeUtil.getExpireDate(accessToken.getExpire()));

        return accessToken;
    }
}

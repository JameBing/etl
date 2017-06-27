package com.wangjunneil.schedule.service;

import com.wangjunneil.schedule.utility.StringUtil;

/**
 * Created by Jame on 2017/6/27.
 */
public class Test {
    public static void main(String[] args) {
        String params = "body={\"errno\":0,\"error\":\"success\",\"data\":{\"source_order_id\":\"W1590299170627043677\"}}&cmd=resp.order.create&encrypt=&secret=9aa9bef2dd361398&source=30916&ticket=3F24F75D-F7D1-48CF-B465-927A309057A0&timestamp=1498547347&version=3";
        params = StringUtil.retParamAsc(params);
        params = StringUtil.chinaToUnicode(params);
        System.out.println(StringUtil.getMD5(params));
    }
}

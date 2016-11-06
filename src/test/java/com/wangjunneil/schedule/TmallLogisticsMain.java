package com.wangjunneil.schedule;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.LogisticsCompany;
import com.taobao.api.request.LogisticsCompaniesGetRequest;
import com.taobao.api.response.LogisticsCompaniesGetResponse;
import com.wangjunneil.schedule.common.Constants;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述
 *
 * @author Administrator
 * @date 2016/8/23
 */
public class TmallLogisticsMain {

    private static final String appkey = "23431719";
    private static final String secret = "308a1c2563be288a03c1b1eae31beeda";
    private static final String sessionKey = "620121400fa99c65443679ZZe287124c49b7d574a21a9152232897825";

    public static void main(String[] args) {

        try {

            Mongo mongo = new Mongo("192.168.1.18", 43221);
            DB db = mongo.getDB("thirdplatform");

            // get a single collection
            DBCollection collection = db.getCollection("sync.tmall.logistics");

            //查询物流公司信息
            TaobaoClient client = new DefaultTaobaoClient(Constants.TMALL_SERVICE_URL, appkey, secret);
            LogisticsCompaniesGetRequest req = new LogisticsCompaniesGetRequest();
            req.setFields("id,code,name,reg_mail_no");
            //req.setIsRecommended(true);
            //req.setOrderMode("offline");
            LogisticsCompaniesGetResponse rsp = client.execute(req);
            List<LogisticsCompany> list =  rsp.getLogisticsCompanies();
            System.out.println(list.size());
            for(LogisticsCompany info : list){
                System.out.println(info.getId()+","+info.getCode()+","+info.getName()+","+info.getRegMailNo());
                // BasicDBObject example
                BasicDBObject document = new BasicDBObject();
                document.put("id", info.getId());
                document.put("code", info.getCode());
                document.put("name", info.getName());
                document.put("reg_mail_no", info.getRegMailNo());
                collection.insert(document);
            }

        } catch (MongoException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }

    }
}

package com.wangjunneil.schedule.entity.tuangou;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;

/**
 * @author yangyongbing
 * @since on 2017/3/20.
 */
@Document(collection ="sync.tuangou.token")
public class AccessToken {

    //token
    private String appAuthToken;

    //业务Id
    private String businessId;

    //门店Id
    private String ePoiId;

    //时间戳
    private String timestamp;

    //门店名称

    private String epoiName;

    public String getAppAuthToken() {
        return appAuthToken;
    }

    public void setAppAuthToken(String appAuthToken) {
        this.appAuthToken = appAuthToken;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getePoiId() {
        return ePoiId;
    }

    public void setePoiId(String ePoiId) {
        this.ePoiId = ePoiId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEpoiName() {
        return epoiName;
    }

    public void setEpoiName(String epoiName) {
        this.epoiName = epoiName;
    }
}

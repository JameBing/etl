package com.wangjunneil.schedule.entity.z8;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by wangjun on 7/28/16.
 */
@Document(collection = "sys.z8.authorize")
public class Z8Authorize {
    private String platform;
    private String appKey;
    private String appSecret;
    private String callback;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}

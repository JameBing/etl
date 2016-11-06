package com.wangjunneil.schedule.entity.tm;

/**
 * Created by xuzhicheng on 2016/8/15.
 */
public class TmallAuthConfig {
    private String appKey;
    private String appSecret;
    private String platformType;

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

    public String getPlatformType() {return platformType;}

    public void setPlatformType(String platformType) {this.platformType = platformType;}
}

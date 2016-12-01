package com.wangjunneil.schedule.entity.jdhome;

import com.wangjunneil.schedule.utility.DateTimeUtil;

import java.util.Date;

/**
 * @author yangyongbing
 * @since 2016/11/30.
 */
public class SignParams {
    private String token;
    private String app_key;
    private String timestamp= DateTimeUtil.dateFormat(new Date(),"yyyy-MM-dd HH:mm:ss");
    private String v="1.0";
    private String format="json";
    private String jd_param_json;

    public SignParams(){

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getApp_key() {
        return app_key;
    }

    public void setApp_key(String app_key) {
        this.app_key = app_key;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getJd_param_json() {
        return jd_param_json;
    }

    public void setJd_param_json(String jd_param_json) {
        this.jd_param_json = jd_param_json;
    }

    public String toString() {
        return "SignParams [token=" + this.token + ", app_key=" + this.app_key + ", timestamp=" + this.timestamp + ", v=" + this.v + ", format=" + this.format + ", jd_param_json=" + this.jd_param_json + "]";
    }
}

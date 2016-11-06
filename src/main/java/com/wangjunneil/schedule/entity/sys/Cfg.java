package com.wangjunneil.schedule.entity.sys;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * Created by wangjun on 8/24/16.
 */
@Document(collection = "sys.cfg")
public class Cfg {
    private String platform;
    private String appKey;
    private String appSecret;
    private String callback;

    private String optionField;

    private Notify notify;
    private Status jdScheduleStatus;
    private Status tmScheduleStatus;
    private Status z8ScheduleStatus;

    public Cfg(String platform, String appKey, String appSecret,String optionField,String callback){
        this.platform = platform;
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.optionField = optionField;
        this.callback = callback;
    }

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

    public Notify getNotify() {
        return notify;
    }

    public void setNotify(Notify notify) {
        this.notify = notify;
    }

    public Status getJdScheduleStatus() {
        return jdScheduleStatus;
    }

    public void setJdScheduleStatus(Status jdScheduleStatus) {
        this.jdScheduleStatus = jdScheduleStatus;
    }

    public Status getTmScheduleStatus() {
        return tmScheduleStatus;
    }

    public void setTmScheduleStatus(Status tmScheduleStatus) {
        this.tmScheduleStatus = tmScheduleStatus;
    }

    public Status getZ8ScheduleStatus() {
        return z8ScheduleStatus;
    }

    public void setZ8ScheduleStatus(Status z8ScheduleStatus) {
        this.z8ScheduleStatus = z8ScheduleStatus;
    }

    public String getOptionField() {
        return optionField;
    }

    public void setOptionField(String optionField) {
        this.optionField = optionField;
    }
}

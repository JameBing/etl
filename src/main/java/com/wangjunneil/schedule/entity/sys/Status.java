package com.wangjunneil.schedule.entity.sys;

import java.util.Date;

/**
 * Created by wangjun on 9/5/16.
 */
public class Status {
    private boolean isStartup;
    private Date startupTime;
    private Date shutdownTime;

    public boolean isStartup() {
        return isStartup;
    }

    public void setStartup(boolean startup) {
        isStartup = startup;
    }

    public Date getStartupTime() {
        return startupTime;
    }

    public void setStartupTime(Date startupTime) {
        this.startupTime = startupTime;
    }

    public Date getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(Date shutdownTime) {
        this.shutdownTime = shutdownTime;
    }
}

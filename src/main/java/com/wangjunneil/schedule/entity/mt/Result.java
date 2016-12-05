package com.wangjunneil.schedule.entity.mt;

/**
 * Created by admin on 2016/12/1.
 */
public class Result {
    private String data;
    private Error error;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }
}

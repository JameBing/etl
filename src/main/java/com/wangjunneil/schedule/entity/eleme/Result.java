package com.wangjunneil.schedule.entity.eleme;


import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016/11/18.
 */
public class Result {
    //返回结果吗
    @SerializedName("code")
    private int code;
    //返回结果
    @SerializedName("data")
    private Object data;
    //返回转态信息
    @SerializedName("message")
    private Object message;
    //返回请求id
    @SerializedName("request_id")
    private String requestid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public String getRequestid() {
        return requestid;
    }

    public void setRequestid(String requestid) {
        this.requestid = requestid;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}

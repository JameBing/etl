package com.wangjunneil.schedule.entity.eleme;


/**
 * Created by admin on 2016/11/18.
 */
public class Result {
    //返回结果吗
    private int code;
    //返回结果
    private Object data;
    //返回转态信息
    private Object message;
    //返回请求id
    private String request_id;

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

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
            "code=" + code +
            ", data=" + data +
            ", message='" + message + '\'' +
            ", request_id='" + request_id + '\'' +
            '}';
    }
}

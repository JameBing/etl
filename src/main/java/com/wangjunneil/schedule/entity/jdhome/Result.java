package com.wangjunneil.schedule.entity.jdhome;

/**
 * @author yangyongbing
 * @since on 2016/12/9.
 * 接口返回结果接收类
 */
public class Result {
    private int code;
    private String msg;
    private String data;
    private Boolean success;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

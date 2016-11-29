package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by yangwanbin on 2016-11-15.
 */
public class Result {

    private String errno;

    private String error;

    private Object data;

    public void setErrno(String errno){
        this.errno = errno;
    }

    public String getErrno(){
        return this.errno;
    }

    public String getError(){
        return this.error;
    }

    public  void setError(String error){
        this.error = error;
    }

    public  void setData(Object data){
        this.data = data;
    }

    public Object GetData(){
        return this.data;
    }
}

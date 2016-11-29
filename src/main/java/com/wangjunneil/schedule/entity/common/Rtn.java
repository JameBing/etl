package com.wangjunneil.schedule.entity.common;

/**
 * Created by yangwanbin on 2016-11-21.
 */
public class Rtn {

    //日志ID
    private String logId;

    //code
    private int code;

    //desc
    private String desc;

    //remark
    private String remark;

    //动态标识
    private String dynamic;


    public void setLogId(String logId){
        this.logId = logId;
    }

    public String getLogId(){
        return  this.logId;
    }

    public void setCode(int code){
        this.code = code;
    }

    public int getCode(){
        return this.code;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return this.remark;
    }

    public void setDynamic(String dynamic){
        this.dynamic = dynamic;
    }

    public String getDynamic(){
        return this.dynamic;
    }
}

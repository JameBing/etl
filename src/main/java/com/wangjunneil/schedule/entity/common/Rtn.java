package com.wangjunneil.schedule.entity.common;

import com.wangjunneil.schedule.utility.StringUtil;

/**
 * Created by yangwanbin on 2016-11-21.
 */
public class Rtn {

    //日志ID
    private String logId;

    //code
    private Integer code;

    //desc
    private String desc;

    //remark
    private String remark;

    //动态标识
    private String dynamic;

    //商品上下架状态
    private Integer status;

    //商品名称
    private String name;
    public void setLogId(String logId){
        this.logId = logId;
    }

    public String getLogId(){
        return  this.logId;
    }

    public void setCode(Integer code){
        this.code = code;
    }

    public Integer getCode(){
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

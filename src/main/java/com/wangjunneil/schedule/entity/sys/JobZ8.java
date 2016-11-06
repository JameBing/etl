package com.wangjunneil.schedule.entity.sys;

import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by wangjun on 8/2/16.
 */
@Document(collection = "sync.z8.job")
public class JobZ8 {
    private String oprType;
    private Date executeTime;
    private String status;
    private String msg;

    public JobZ8(String oprType, Date executeTime) {
        this.oprType = oprType;
        this.executeTime = executeTime;//DateTimeUtil.addHour(executeTime, 8);//本地入库job时间不需要+8小时
    }

    public String getOprType() {
        return oprType;
    }

    public void setOprType(String oprType) {
        this.oprType = oprType;
    }

    public Date getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(Date executeTime) {
        this.executeTime = DateTimeUtil.addHour(executeTime, 8);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

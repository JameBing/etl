package com.wangjunneil.schedule.entity.common;

import com.wangjunneil.schedule.utility.DateTimeUtil;
import com.wangjunneil.schedule.utility.StringUtil;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yangwanbin on 2016/12/7.
 */
//系统日志
@Document(collection = "sync.waimai.logs")
public class Log {

    //日志ID
    private String logId;

    //日志标题
    private String title;

    //日志类别  E:异常 W:警告 I:消息
    private String type;

    //平台标识
    private String platform;

    //日志信息
    private String message;

    //请求体参数
    private String request;

    //捕获异常类型名称
    private String catchExName;

    //内部异常类型名称
    private String innerExName;

    //调用堆栈信息
    private String stackInfo;

    //日志时间
    private String dateTime;

    public  void setLogId(String logId){
        this.logId = logId;
    }

    public String getLogId(){
        return this.logId;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return this.title;
    }

    public void setType(String type){
        this.type = type;
    }

    public  String getType(){
        return this.type;
    }

    public void setPlatform(String platform){
        this.platform = platform;
    }

    public String getPlatform(){
        return  this.platform;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setRequest(String request){
        this.request = request;
    }

    public String getRequest(){
        return this.request;
    }

    public void setCatchExName(String catchExName){
        this.catchExName = catchExName;
    }

    public String getCatchExName(){
        return this.catchExName;
    }

    public void setInnerExName(String innerExName){
        this.innerExName = innerExName;
    }

    public String getInnerExName(){
        return  this.innerExName;
    }

    public void setStackInfo(String stackInfo){
        this.stackInfo = stackInfo;
    }

    public String getStackInfo(){
        return this.stackInfo;
    }

    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public String getDateTime(){
        return StringUtil.isEmpty(this.dateTime)? DateTimeUtil.dateFormat(DateTimeUtil.now(),"yyyy-MM-dd HH:mm:ss"):this.dateTime;
    }
}

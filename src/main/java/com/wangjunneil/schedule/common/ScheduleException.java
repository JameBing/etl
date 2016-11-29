package com.wangjunneil.schedule.common;

/**
 * Created by wangjun on 8/8/16.
 */
public class ScheduleException extends Exception {

    public ScheduleException(){}
    public ScheduleException(String message) {
        super(message);
    }

    private String platform;
    private String innerExName;
    private String message;
    private String requestStr;
    private String stackInfo;

    public String getStackInfo(){
        return this.stackInfo;
    }

    public String getInnerExName(){
        return this.innerExName;
    }

    public String getMessage(){
        return this.message;
    }

    public String getPlatform(){
        return this.platform;
    }

    public String getRequestStr(){
        return this.requestStr;
    }

    public ScheduleException(String platform,String innerExName,String message,String requestStr,StackTraceElement[] traces){
        this.platform = platform;
        this.innerExName = innerExName;
        this.requestStr = requestStr;
        this.message = message;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < traces.length; i++) {
            StackTraceElement element = traces[i];
            sb.append(element.toString() + "\n");
        }
        this.stackInfo = sb.toString();

    }
}

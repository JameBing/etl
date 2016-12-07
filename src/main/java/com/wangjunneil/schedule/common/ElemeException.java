package com.wangjunneil.schedule.common;

/**
 * Created by admin on 2016/11/18.
 */
public class ElemeException extends  Exception {
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

    public String getRequestStr(){
        return this.requestStr;
    }

    public ElemeException(String innerExName, String message, String requestStr, StackTraceElement[] traces) {
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
    public ElemeException(String message, Exception ex) {super(message);}
}

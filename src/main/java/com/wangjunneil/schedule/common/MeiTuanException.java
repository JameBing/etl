package com.wangjunneil.schedule.common;

/**
 * @author liuxin
 * @since 2016.11.28
 */
public class MeiTuanException extends Exception {

    public MeiTuanException(String message, Exception ex){
        super(message);
    }

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

    public MeiTuanException(String innerExName,String message,String requestStr,StackTraceElement[] traces) {
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

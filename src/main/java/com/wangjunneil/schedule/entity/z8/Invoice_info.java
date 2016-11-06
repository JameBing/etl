package com.wangjunneil.schedule.entity.z8;

/**
 * Created by xuzhicheng on 2016/9/19.
 */
public class Invoice_info {
    public String type;      //发票类型
    public String content;   //发票内容
    public String receiver;  //发票抬头


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

}

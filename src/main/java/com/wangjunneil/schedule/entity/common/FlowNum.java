package com.wangjunneil.schedule.entity.common;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by yangwanbin on 2016-11-28.
 */
@Document(collection = "sys.number")
public class FlowNum {

    private String date;

    private String moudle;

    private int flowNum;

    public void setDate(String date){
        this.date = date;
    }

    public String getDate(){
        return  this.date;
    }

    public void setMoudle(String moudle){
        this.moudle = moudle;
    }

    public String getMoudle(){
        return  this.moudle;
    }

    public void setFlowNum(int num){
     this.flowNum = num;
    }

    public int getFlowNum(){
        return this.flowNum;
    }
}

package com.wangjunneil.schedule.entity.baidu;

import org.jasypt.salt.StringFixedSaltGenerator;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Attr {

    private String name;

    private String value;

    public void setName(String name){

        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setValue(String value){
        this.value = value;
    }

    public  String getValue(){
        return this.value;
    }
}




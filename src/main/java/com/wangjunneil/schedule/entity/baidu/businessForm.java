package com.wangjunneil.schedule.entity.baidu;

import com.wangjunneil.schedule.utility.StringUtil;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class businessForm {

    //业态ID
    private String id;

    //业态名称
    private String name;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public void  setName(String name){
        this.name = name;
    }

    public String getName(){
        return  this.name;
    }
}

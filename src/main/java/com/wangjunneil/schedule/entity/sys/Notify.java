package com.wangjunneil.schedule.entity.sys;

/**
 * Created by wangjun on 9/5/16.
 */
public class Notify {
    private String way;
    private String addr;

    public Notify(String way,String addr){
        this.way = way;
        this.addr = addr;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}

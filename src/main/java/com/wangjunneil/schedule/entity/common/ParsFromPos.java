package com.wangjunneil.schedule.entity.common;

/**
 * Created by yangwanbin on 2016-11-24.
 */
public class ParsFromPos {

    private ParsFromPosInner baidu;

    private ParsFromPosInner jdhome;

    private ParsFromPosInner eleme;

    private ParsFromPosInner meituan;

    public void setBaidu(ParsFromPosInner baidu){
        this.baidu = baidu;
    }

    public ParsFromPosInner getBaidu(){
        return this.baidu;
    }

    public void setJdhome(ParsFromPosInner jdhome){

        this.jdhome = jdhome;
    }

    public ParsFromPosInner getJdhome(){
        return  this.jdhome;
    }

    public void  setEleme(ParsFromPosInner eleme){
        this.eleme = eleme;
    }

    public ParsFromPosInner getEleme(){
        return  this.eleme;
    }

    public void setMeituan(ParsFromPosInner meituan){
        this.meituan = meituan;
    }

    public ParsFromPosInner getMeituan(){
        return this.meituan;
    }
}

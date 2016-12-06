package com.wangjunneil.schedule.entity.common;

import java.util.List;

/**
 * Created by yangwanbin on 2016-12-06.
 */
public class ParsFormPos2 {

    private List<ParsFromPosInner> baidu;

    private List<ParsFromPosInner> jdhome;

    private List<ParsFromPosInner> eleme;

    private List<ParsFromPosInner> meituan;

    public void setBaidu(List<ParsFromPosInner> parsFromPosInnerList){
        this.baidu = parsFromPosInnerList;
    }

    public List<ParsFromPosInner> getBaidu(){
        return this.baidu;
    }

    public  void setJdhome(List<ParsFromPosInner> parsFromPosInnerList){
        this.jdhome = parsFromPosInnerList;
    }

    public List<ParsFromPosInner> getJdhome(){
        return this.jdhome;
    }

    public void setEleme(List<ParsFromPosInner> parsFromPosInnerList){
        this.eleme = parsFromPosInnerList;
    }

    public List<ParsFromPosInner> getEleme(){
        return  this.eleme;
    }

    public void setMeituan(List<ParsFromPosInner> parsFromPosInnerList){
        this.meituan = parsFromPosInnerList;
    }

    public List<ParsFromPosInner> getMeituan(){
        return this.meituan;
    }
}

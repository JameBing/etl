package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Category {

    //分类ID
    private String id;

    //分类名称
    private String name;

    //在分类中排序
    private int rank;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setRank(int rank){
        this.rank = rank;
    }

    public int getRank(){
        return  this.rank;

    }

}

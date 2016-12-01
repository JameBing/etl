package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesHandlerImpl;

/**
 * Created by admin on 2016-12-01.
 */
//供应商-经验范围
public class Categorys {

    //分类1
    private String category1;

    //分类1 ID
    @SerializedName("category1_id")
    private String categoryId1;

    //分类2
    private String category2;

    //分类2 ID
    @SerializedName("category2_id")
    private String categoryId2;

    //分类3
    private String category3;

    //分类3 ID
    @SerializedName("category3_id")
    private String categoryId3;

    public void setCategory1(String category1){
        this.category1 = category1;
    }

    public String getCategory1(){
        return this.category1;
    }

    public void setCategoryId1(String categoryId1){
        this.categoryId1 = categoryId1;
    }

    public String getCategoryId1(){
        return this.categoryId1;
    }

    public  void setCategory2(String category2){
        this.category2 = category2;
    }

    public String getCategory2(){
        return this.category2;
    }

    public void setCategoryId2(String categoryId2){
        this.categoryId2 = categoryId2;
    }

    public String getCategoryId2(){
        return this.categoryId2;
    }

    public void setCategory3(String category3){
        this.category3 = category3;
    }

    public String getCategory3(){
        return this.category3;
    }

    public void setCategoryId3(String categoryId3){
        this.categoryId3 = categoryId3;
    }

    public String getCategoryId3(){
        return this.categoryId3;
    }
}

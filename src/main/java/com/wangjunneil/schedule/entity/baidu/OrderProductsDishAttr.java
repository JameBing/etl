package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 2016-12-09.
 */
public class OrderProductsDishAttr {

    //规格
    private  String option ;

    //规格名称
    private String name;

    //百度规格ID
    @SerializedName("baidu_attr_id")
    private String  baiduAttrId;

    //第三方规格ID
    @SerializedName("attr_id")
    private String attrId;


    public void setOption(String option){
        this.option = option;
    }

    public String getOption(){
        return this.option;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setBaiduAttrId(String baiduAttrId){
        this.baiduAttrId = baiduAttrId;
    }

    public String getBaiduAttrId(){
        return this.baiduAttrId;
    }
    public String getAttrId(){
        return this.attrId;
    }

   public void setAttrId(String attrId ){
       this.attrId = attrId;
   }
}

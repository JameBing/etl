package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import com.wangjunneil.schedule.utility.StringUtil;
import org.omg.PortableInterceptor.ServerRequestInfo;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class Supplier {

    //供应商ID
    @SerializedName("supplier_id")
    private String supplierId;

    //供应商名称
    private String name;

    //品牌
    private String brand;

    //供应商状态
    private  String status;

    //供应商类型
    @SerializedName("category_name")
    private String  categoryName;

    //标签
    @SerializedName("extend_flag")
    private String  extendFlag;

    //业态
    @SerializedName("business_form")
    private BusinessForm businessForm;

    //经验范围
    private Categorys[] categorys;

    public void setSupplierId(String supplierId){
        this.supplierId = supplierId;
    }

    public String getSupplierId(){
        return this.supplierId;
    }

    public void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return this.name;
    }

    public void setBrand(String brand){
        this.brand = brand;
    }

    public String getBrand(){
        return this.brand;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return  this.status;
    }

    public void setCategoryName(String categoryName){
        this.categoryName = categoryName;
    }

    public String getCategoryName(){
        return this.categoryName;
    }

    public void setExtendFlag(String extendFlag){
        this.extendFlag = extendFlag;
    }

    public String getExtendFlag(){
        return this.extendFlag;
    }

    public void setBusinessForm(BusinessForm businessForm){
        this.businessForm = businessForm;
    }

    public BusinessForm getBusinessForm(){
        return  this.businessForm;
    }

    public void setCategorys(Categorys[] categorys){
        this.categorys = categorys;
    }

    public Categorys[] getCategorys(){
        return this.categorys;
    }
}

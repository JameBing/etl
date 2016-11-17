package com.wangjunneil.schedule.entity.baidu;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Norms {

    private String dishNormsId;

    private String dishId;

    private String wid;

    private String name;

    private String nameValue;

    private String currentPrice;

    private String disCount;

    private int storeNum;

    private int leftNum;

    private String status;

    private String createTime;

    private String supplierDishNormsId;

    public void setDishNormsId(String dishNormsId){

        this.dishNormsId = dishNormsId;
    }

    public  String getDishNormsId(){
        return  this.dishNormsId;
    }

    public void setDishId(String dishId){
        this.dishId = dishId;
    }

    public String getDishId(){
        return  this.dishId;
    }

    public void setWid(String wid){
        this.wid = wid;
    }

    public String getWid(){
        return this.wid;
    }

    public  void setName(String name){
        this.name = name;
    }

    public  String getName(){
        return this.name;
    }

    public  void setNameValue(String nameValue){
        this.nameValue = nameValue;
    }

    public String getNameValue(){
        return this.nameValue;
    }

    public  void setCurrentPrice(String currentPrice){
        this.currentPrice = currentPrice;
    }

    public String getCurrentPrice(){
        return this.currentPrice;
    }

    public  void setDisCount(String disCount){
        this.disCount = disCount;
    }

    public  String getDisCount(){
        return this.disCount;
    }

    public  void setStoreNum(int storeNum){
        this.storeNum = storeNum;
    }

    public int getStoreNum(){

        return this.storeNum;
    }

    public void setLeftNum(int leftNum){
        this.leftNum = leftNum;
    }

    public  int getLeftNum(){
        return this.leftNum;
    }

    public  void setStatus(String status){
        this.status = status;
    }

    public  String getStatus(){
        return this.status;
    }

    public  void setCreateTime(String createTime){
        this.createTime = createTime;
    }

    public  String getCreateTime(){
        return this.createTime;
    }

    public  void  setSupplierDishNormsId(String supplierDishNormsId){
        this.supplierDishNormsId = supplierDishNormsId;
    }

    public  String getSupplierDishNormsId(){
        return  this.supplierDishNormsId;
    }
}


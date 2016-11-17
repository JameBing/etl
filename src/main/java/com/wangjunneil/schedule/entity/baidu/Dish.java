package com.wangjunneil.schedule.entity.baidu;


import org.jasypt.salt.StringFixedSaltGenerator;

import java.util.List;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Dish {

   //合作方商户唯一 ID
   private String shopId;

    //菜品唯一编号
   private String dishId;

   //菜品名称
   private String name;

   //条形码编号
   private String upc;

   //菜品价格，单位：分
   private int price;

   //菜品图片，小于3M，尺寸大于等于640*640px
   private String pic;

   //最小起订份数
   private int minOrderNum;

   //单份所需餐盒数
   private int packageBoxNum;

   //描述
   private String description;

   //可售时间“i”为 1 表示周一，为 7 表示周日
   private List<AvailableTime[]> availableTimesList;

   //菜品每日库存，每日0点恢复库存
   private int stock;

   //分类信息
   private List<Category> categoryList;

   //菜品规格
   private List<Norms> normsList;

   //菜品属性
   private  List<Attr> attrsList;

   //平台菜品ID
   private String baiduDishId;

   private String wid;

   private int leftNum;

   private String status;

   public void setShopId(String shopId){
       this.shopId = shopId;
   }

   public String getShopId(){
       return this.shopId;
   }

   public void setDishId(String dishId){
       this.dishId = dishId;
   }

   public String getDishId(){
       return this.dishId;
   }

   public void setUpc(String upc){
       this.upc = upc;
   }

   public String getUpc(){
       return this.upc;
   }

   public void setName(String name){
       this.name = name;
   }

   public String getName(){
       return  this.name;
   }

   public void setPrice(int price){
       this.price = price;
   }

   public int getPrice(){
       return this.getPrice();
   }

   public void setPic(String pic){
       this.pic = pic;
   }

   public String getPic(){
       return this.pic;
   }

   public void setMinOrderNum(int minOrderNum){
       this.minOrderNum = minOrderNum;
   }

   public int getMinOrderNum(){
       return this.minOrderNum;
   }

   public  void setPackageBoxNum(int packageBoxNum){
       this.packageBoxNum = packageBoxNum;
   }

   public int getPackageBoxNum(){
       return this.packageBoxNum;
   }

   public  void setDescription(String description){
       this.description = description;
   }

   public String getDescription(){
       return this.description;
   }

   public  void setAvailableTimesList(List<AvailableTime[]> availableTimesList){
       this.availableTimesList = availableTimesList;
   }

   public List<AvailableTime[]> getAvailableTimesList(){
       return this.availableTimesList;
   }

   public void setStock(int stock){
       this.stock = stock;
   }

   public int getStock(){
       return this.stock;
   }

   public void setCategoryList(List<Category> categoryList){
       this.categoryList = categoryList;
   }

   public List<Category> getCategoryList(){
       return this.categoryList;
   }

   public void setNormsList(List<Norms> normsList){
       this.normsList = normsList;
   }

   public List<Norms> getNormsList(){
       return  this.normsList;
   }

   public  void setAttrsList(List<Attr> attrsList){
       this.attrsList = attrsList;
   }

   public  List<Attr> getAttrsList(){
       return  this.attrsList;
   }

   public  void  setWid(String wid){
     this.wid = wid;
   }

   public  String getWid(){
       return  this.wid;
   }

   public void setLeftNum(int leftNum){
       this.leftNum = leftNum;
   }

   public int getLeftNum(){
       return  this.leftNum;
   }

   public void  setStatus(String status){
       this.status = status;
   }

   public String getStatus(){
       return  this.status;
   }


   public void setBaiduDishId(String baiduDishId){
       this.baiduDishId = baiduDishId;
   }

   public String getBaiduDishId(){
       return this.baiduDishId;
   }
}

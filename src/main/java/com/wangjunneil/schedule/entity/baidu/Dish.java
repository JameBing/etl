package com.wangjunneil.schedule.entity.baidu;


import com.google.gson.annotations.SerializedName;
import com.wangjunneil.schedule.utility.StringUtil;
import org.jasypt.salt.StringFixedSaltGenerator;

import javax.smartcardio.ATR;
import java.util.List;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class Dish {

   //合作方商户唯一 ID
   @SerializedName("shop_id")
   private String shopId;

   //百度门店ID,与shop_id二选一
    @SerializedName("baidu_shop_id")
   private String baiduShopId;

    //菜品唯一编号
    @SerializedName("dish_id")
   private String dishId;

    //百度Dish ID
    @SerializedName("baidu_dish_id")
    private String baiduDishId;

   //菜品名称
   private String name;

   //条形码编号
   private String upc;

   //菜品价格，单位：分
   private String price;

   //菜品图片，小于3M，尺寸大于等于640*640px
   private String pic;

   //最小起订份数
    @SerializedName("min_order_num")
   private int minOrderNum;

   //单份所需餐盒数
    @SerializedName("package_box_num")
   private int packageBoxNum;

   //描述
   private String description;

   //可售时间“i”为 1 表示周一，为 7 表示周日
    @SerializedName("available_times")
   private List<AvailableTime[]> availableTime;

   //菜品每日库存，每日0点恢复库存
   private String stock;

   //分类信息
    @SerializedName("category")
   private Category[] categories;

   //菜品规格
   private Norms[] norms;

   //菜品属性
    @SerializedName("attr")
   private Attr[] attrs;

   private String wid;

   private int leftNum;

   private String status;

   public void setShopId(String shopId){
       this.shopId = shopId;
   }

   public String getShopId(){
       return this.shopId;
   }

   public void setBaiduShopId(String baiduShopId){
       this.baiduDishId = baiduShopId;
   }

   public String getBaiduShopId(){
       return this.baiduShopId;
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

   public void setPrice(String price){
       this.price = price;
   }

   public String getPrice(){
       return this.price;
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

   public  void setAvailableTime(List<AvailableTime[]> availableTime){
       this.availableTime = availableTime;
   }

   public List<AvailableTime[]> getAvailableTime(){
       return this.availableTime;
   }

   public void setStock(String stock){
       this.stock = stock;
   }

   public String getStock(){
       return this.stock;
   }

   public void setCategories(Category[] categories){
       this.categories = categories;
   }

   public Category[] getCategories(){
       return this.categories;
   }

   public void setNorms(Norms[] norms){
       this.norms = norms;
   }

   public Norms[] getNorms(){
       return  this.norms;
   }

   public  void setAttrs(Attr[] attrs){
       this.attrs = attrs;
   }

   public  Attr[] getAttrs(){
       return  this.attrs;
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

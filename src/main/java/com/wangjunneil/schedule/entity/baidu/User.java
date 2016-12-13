package com.wangjunneil.schedule.entity.baidu;

import com.google.gson.annotations.SerializedName;
import com.wangjunneil.schedule.utility.StringUtil;

/**
 * Created by yangwanbin on 2016-11-16.
 */
public class User {

    //顾客姓名
    private String name;

    //顾客电话
    private String phone;

    //顾客性别 1,男 2 女
    private int gender;

    //送餐地址
    private String address;

    //客户所在省份
    private String province;

    //客户所在城市
    private String city;

    //客户所在区
    private String district;

    //百度地图经纬度信息
    private Coord coord;

    //高德地图经纬度信息
    @SerializedName("coord_amap")
    private CoordAmap coordAmap;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return this.phone;
    }

    public void setGender(int gender){
        this.gender = gender;
    }

    public int getGender(){
        return  this.gender;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return this.address;
    }

    public void setCoord(Coord coord){
        this.coord = coord;
    }

    public Coord getCoord(){
        return  this.coord;
    }

    public void setCoordAmap(CoordAmap coordAmap){
        this.coordAmap = coordAmap;
    }

    public CoordAmap getCoordAmap(){
        return this.coordAmap;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}

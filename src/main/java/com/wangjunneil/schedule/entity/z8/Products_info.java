package com.wangjunneil.schedule.entity.z8;

import java.util.List;

/**
 * Created by xuzhicheng on 2016/9/19.
 */
public class Products_info {
    public String id;      //商品id
    public String name;    //商品标题
    public String short_name;     //商品简称
    public String county;    //区县
    public int count;    //购买数量
    public String price;    //单价(现价)
    public String num;      //货号
    public String seller_no;    // SKU商家编码
    public String shelf;    //货位
    public String image;    //图片
    public String url;      //链接
    public int refund_status;   //最后一次退款状态 1退款中 2退款成功 3退款关闭 10折800介入 11维权-退款成功 12维权-退款关闭
    public List<Sku_info> sku;    //sku属性信息
    public String sku_num;      //折800sku标示
    public String sku_id;   //sku级别的业务标识ID,一般是商品id+_+sku_num,如果sku_num为空则用000000占位

    public String getId() {
        return id;
    }

    public void setId(String id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getSeller_no() {
        return seller_no;
    }

    public void setSeller_no(String seller_no) {
        this.seller_no = seller_no;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(int refund_status) {
        this.refund_status = refund_status;
    }

    public List<Sku_info> getSku() {
        return sku;
    }

    public void setSku(List<Sku_info> sku) {
        this.sku = sku;
    }

    public String getSku_num() {
        return sku_num;
    }

    public void setSku_num(String sku_num) {
        this.sku_num = sku_num;
    }

    public String getSku_id() {
        return sku_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }
}

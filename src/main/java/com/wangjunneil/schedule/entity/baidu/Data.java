package com.wangjunneil.schedule.entity.baidu;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by yangwanbin on 2016-12-01.
 */
@Document(collection = "sync.waimai.order")
public class Data {

    //合作方ID
    private String source;

    //shop
    private Shop shop;

    //order
    private Order order;

    //user
    private User user;

    //supplier
    private Supplier supplier;

    //products
    private List<Products> products;

    //discount
    private List<Discount> discount;

    public void setSource(String source){
        this.source = source;
    }

    public String getSource(){
        return this.source;
    }

    public void setShop(Shop shop){
        this.shop = shop;
    }

    public Shop getShop(){
        return this.shop;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    public Order getOrder(){
        return this.order;
    }

    public void setUser(User user){
        this.user = user;
    }

    public  User getUser(){
        return this.user;
    }

    public void setSupplier(Supplier supplier){
        this.supplier = supplier;
    }

    public Supplier getSupplier(){
        return this.supplier;
    }

    public void setProducts(List<Products> products){
        this.products = products;
    }

    public List<Products> getProducts(){
        return this.products;
    }

    public void setDiscount(List<Discount> discount){
        this.discount = discount;
    }

    public List<Discount> getDiscount(){
        return this.discount;
    }
}

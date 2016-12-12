package com.wangjunneil.schedule.entity.baidu;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Created by yangwanbin on 2016-12-01.
 */
public class Data {

    //合作方ID
    private String source;

    //shop
    private OrderShop shop;

    //order
    private Order order;

    //user
    private User user;

    //supplier
    private Supplier supplier;

    //products
    private List<OrderProductsDish[]> products;

    //discount
    private List<Discount> discount;

    public void setSource(String source){
        this.source = source;
    }

    public String getSource(){
        return this.source;
    }

    public void setShop(OrderShop shop){
        this.shop = shop;
    }

    public OrderShop getShop(){
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

    public void setProducts(List<OrderProductsDish[]> products){
        this.products = products;
    }

    public List<OrderProductsDish[]> getProducts(){
        return this.products;
    }

    public void setDiscount(List<Discount> discount){
        this.discount = discount;
    }

    public List<Discount> getDiscount(){
        return this.discount;
    }
}

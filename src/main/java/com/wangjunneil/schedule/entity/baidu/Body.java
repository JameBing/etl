package com.wangjunneil.schedule.entity.baidu;

import javax.jws.soap.SOAPBinding;
import java.net.PortUnreachableException;
import java.util.List;

/**
 * Created by yangwanbin on 2016-11-17.
 */
public class Body {

    //shop
    private Shop shop;

    //order
    private Order order;

    //user
    private User user;

    //products
    private List<Products> products;

    //discount
    private List<Discount> discount;

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


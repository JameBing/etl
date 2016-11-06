package com.wangjunneil.schedule.entity.jd;

import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangjun on 8/2/16.
 */
@Document(collection = "sync.jd.member")
public class JdCrmMember {
    private String customer_pin;            //用户在京东的会员账号
    private String grade;                   //会员等级， 1:一星会员，2:二星会员 3:三星会员 4:四星会员 5:五星会员
    private int trade_count;                //交易成功笔数
    private BigDecimal trade_amount ;       //交易成功的金额，单位为元
    private int close_trade_count;          //交易关闭的的笔数 退货订单量 	否
    private BigDecimal close_trade_amount;  //交易关闭的金额 退货订单金额，单位为元  	否
    private int item_num;                   //购买的商品件数 	否
    private BigDecimal avg_price;           //平均客单价，单位为元  	否
    private Date last_trade_time;           //最后交易时间。非sdk调用 请自行转换long到date

    public String getCustomer_pin() {
        return customer_pin;
    }

    public void setCustomer_pin(String customer_pin) {
        this.customer_pin = customer_pin;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getTrade_count() {
        return trade_count;
    }

    public void setTrade_count(int trade_count) {
        this.trade_count = trade_count;
    }

    public BigDecimal getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(BigDecimal trade_amount) {
        this.trade_amount = trade_amount;
    }

    public int getClose_trade_count() {
        return close_trade_count;
    }

    public void setClose_trade_count(int close_trade_count) {
        this.close_trade_count = close_trade_count;
    }

    public BigDecimal getClose_trade_amount() {
        return close_trade_amount;
    }

    public void setClose_trade_amount(BigDecimal close_trade_amount) {
        this.close_trade_amount = close_trade_amount;
    }

    public int getItem_num() {
        return item_num;
    }

    public void setItem_num(int item_num) {
        this.item_num = item_num;
    }

    public BigDecimal getAvg_price() {
        return avg_price;
    }

    public void setAvg_price(BigDecimal avg_price) {
        this.avg_price = avg_price;
    }

    public Date getLast_trade_time() {
        return last_trade_time;
    }

    public void setLast_trade_time(Date last_trade_time) {
        this.last_trade_time = last_trade_time;
    }
}

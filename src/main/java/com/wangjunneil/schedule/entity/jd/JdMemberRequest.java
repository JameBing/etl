package com.wangjunneil.schedule.entity.jd;

/**
 * Created by zhangfei on 8/15/16.
 */
public class JdMemberRequest {
    private String customerPin ;
    private String grade;
    private String minTradeTime;
    private String maxTradeTime;
    private String minTradeCount;
    private String maxTradeCount;
    private String avgPrice;
    private String minTradeAmount;

    public String getCustomerPin() {
        return customerPin;
    }

    public void setCustomerPin(String customerPin) {
        this.customerPin = customerPin;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMinTradeTime() {
        return minTradeTime;
    }

    public void setMinTradeTime(String minTradeTime) {
        this.minTradeTime = minTradeTime;
    }

    public String getMaxTradeTime() {
        return maxTradeTime;
    }

    public void setMaxTradeTime(String maxTradeTime) {
        this.maxTradeTime = maxTradeTime;
    }

    public String getMinTradeCount() {
        return minTradeCount;
    }

    public void setMinTradeCount(String minTradeCount) {
        this.minTradeCount = minTradeCount;
    }

    public String getMaxTradeCount() {
        return maxTradeCount;
    }

    public void setMaxTradeCount(String maxTradeCount) {
        this.maxTradeCount = maxTradeCount;
    }

    public String getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(String avgPrice) {
        this.avgPrice = avgPrice;
    }

    public String getMinTradeAmount() {
        return minTradeAmount;
    }

    public void setMinTradeAmount(String minTradeAmount) {
        this.minTradeAmount = minTradeAmount;
    }
}

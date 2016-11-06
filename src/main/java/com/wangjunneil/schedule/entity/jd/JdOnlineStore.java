package com.wangjunneil.schedule.entity.jd;

import com.wangjunneil.schedule.common.Constants;
import com.wangjunneil.schedule.entity.sys.Status;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by wangjun on 8/1/16.
 */
@Document(collection = "sync.jd.store")
public class JdOnlineStore {
    private String platform = Constants.PLATFORM_JD;
    private long vender_id;  // 商家编号
    private long shop_id;    // 店铺编号
    private String shop_name;   // 店铺名称
    private Date open_time;     // 开店时间
    private String logo_url;    // logo地址
    private String brief;       // 店铺简介
    private long cate_main;      // 主营类目编号
    private String cate_main_name;  // 主营类目名称
    private int col_type;        // 商家类型:0：SOP 1：FBP 2：LBP 5：SOPL

    private Status jdScheduleStatus;
    private Status tmScheduleStatus;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public long getVender_id() {
        return vender_id;
    }

    public void setVender_id(long vender_id) {
        this.vender_id = vender_id;
    }

    public long getShop_id() {
        return shop_id;
    }

    public void setShop_id(long shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public Date getOpen_time() {
        return open_time;
    }

    public void setOpen_time(Date open_time) {
        this.open_time = open_time;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public long getCate_main() {
        return cate_main;
    }

    public void setCate_main(long cate_main) {
        this.cate_main = cate_main;
    }

    public String getCate_main_name() {
        return cate_main_name;
    }

    public void setCate_main_name(String cate_main_name) {
        this.cate_main_name = cate_main_name;
    }

    public int getCol_type() {
        return col_type;
    }

    public void setCol_type(int col_type) {
        this.col_type = col_type;
    }

    public Status getJdScheduleStatus() {
        return jdScheduleStatus;
    }

    public void setJdScheduleStatus(Status jdScheduleStatus) {
        this.jdScheduleStatus = jdScheduleStatus;
    }

    public Status getTmScheduleStatus() {
        return tmScheduleStatus;
    }

    public void setTmScheduleStatus(Status tmScheduleStatus) {
        this.tmScheduleStatus = tmScheduleStatus;
    }
}

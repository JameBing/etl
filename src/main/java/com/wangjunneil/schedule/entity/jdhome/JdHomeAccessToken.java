package com.wangjunneil.schedule.entity.jdhome;

import com.wangjunneil.schedule.common.Constants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author yangyongbing
 * @since 2016/11/29.
 */
    @Document(collection = "sync.multi.token")
    public class JdHomeAccessToken {

    private String platform = Constants.PLATFORM_WAIMAI_JDHOME;

    private String username;

    private String code; //回填码

    private String token;

    private int expires_in;

    private String token_type;

    private String time;

    private String uid;

    private String user_nick;

    private Date expire_Date;

    private String companyId;

    private String appKey;

    private String appSecret;

    private String callback;

    private List<Shop> shopIds;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

    public Date getExpire_Date() {
        return expire_Date;
    }

    public void setExpire_Date(Date expire_Date) {
        this.expire_Date = expire_Date;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public List<Shop> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<Shop> shopIds) {
        this.shopIds = shopIds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}

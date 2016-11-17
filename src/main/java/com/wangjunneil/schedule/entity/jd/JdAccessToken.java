package com.wangjunneil.schedule.entity.jd;

import com.wangjunneil.schedule.common.Constants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * Created by wangjun on 7/28/16.

    */
    @Document(collection = "sync.multi.token")
    public class JdAccessToken {

        private String platform = Constants.PLATFORM_JD;

        private String username;

        private String access_token;

        private int expires_in;

        private String refresh_token;

        private String token_type;

        private String time;
    private String uid;

    private String user_nick;

    private Date expire_Date;

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

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
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
}

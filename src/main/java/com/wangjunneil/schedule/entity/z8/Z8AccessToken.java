package com.wangjunneil.schedule.entity.z8;

import com.wangjunneil.schedule.common.Constants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Document(collection = "sync.multi.token")
public class Z8AccessToken {

    private String platform = Constants.PLATFORM_Z800;;
    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private long re_expires_in;
    private String user_id;
    private String user_nick;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public long getRe_expires_in() {
        return re_expires_in;
    }

    public void setRe_expires_in(long re_expires_in) {
        this.re_expires_in = re_expires_in;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }

}

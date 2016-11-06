package com.wangjunneil.schedule.entity.jp;

import com.wangjunneil.schedule.common.Constants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * Created by wangjun on 9/6/16.
 */
@Document(collection = "sync.multi.token")
public class JPAccessToken {
    private String platform = Constants.PLATFORM_JP;
    private String token;
    private long timeout;
    private long expire;
    private Date expireDate;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}

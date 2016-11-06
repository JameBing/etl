package com.wangjunneil.schedule.entity.sys;

import com.wangjunneil.schedule.utility.DateTimeUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * Created by wangjun on 8/8/16.
 */
@Document(collection = "sys.usr")
public class User {
    @Id
    private long uid;
    private String username;
    private String password;
    private Date createTime;
    private Date expireTime;
    private int expireIn;

    public User() {  }

    public User(String username, String password, int expireIn) {
        this.username = username;
        this.password = password;
        this.expireIn = expireIn;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = DateTimeUtil.addHour(createTime, 8);
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = DateTimeUtil.addHour(expireTime, 8);
    }

    public int getExpireIn() {
        return expireIn;
    }

    public void setExpireIn(int expireIn) {
        this.expireIn = expireIn;
    }

}

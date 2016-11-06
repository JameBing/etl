package com.wangjunneil.schedule.entity.tm;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created by wangjun on 8/25/16.
 */
@Document(collection = "sync.tmall.message")
public class TmallOrderMessage {
    private long id;
    private long userId;
    private String topic;
    private String pubAppKey;
    private Date pubTime;
    private Date outgoingTime;
    private String userNick;
    private TmallOrderContent content;

    private String status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getPubAppKey() {
        return pubAppKey;
    }

    public void setPubAppKey(String pubAppKey) {
        this.pubAppKey = pubAppKey;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public Date getOutgoingTime() {
        return outgoingTime;
    }

    public void setOutgoingTime(Date outgoingTime) {
        this.outgoingTime = outgoingTime;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }

    public TmallOrderContent getContent() {
        return content;
    }

    public void setContent(TmallOrderContent content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

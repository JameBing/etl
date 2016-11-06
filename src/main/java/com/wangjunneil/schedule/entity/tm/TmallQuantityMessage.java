package com.wangjunneil.schedule.entity.tm;

import java.util.List;

/**
 * Created by wangjun on 9/14/16.
 */
public class TmallQuantityMessage {

    private long updateType; // 1,全量; 2,增量

    private String channel;

    private List<TmallQuantity> deatil;

    public long getUpdateType() {
        return updateType;
    }

    public void setUpdateType(long updateType) {
        this.updateType = updateType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public List<TmallQuantity> getDeatil() {
        return deatil;
    }

    public void setDeatil(List<TmallQuantity> deatil) {
        this.deatil = deatil;
    }
}

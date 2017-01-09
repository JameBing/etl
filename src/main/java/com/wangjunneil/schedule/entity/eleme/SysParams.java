package com.wangjunneil.schedule.entity.eleme;


import com.google.gson.annotations.SerializedName;

public class SysParams {
    @SerializedName("consumer_key")
    private String consumer_key; //商家key
    @SerializedName("timestamp")
    private long timestamp;   //请求时间戳
    @SerializedName("sig")
    private String sig;     //签名

    public String getConsumer_key() {
        return consumer_key;
    }

    public void setConsumer_key(String consumer_key) {
        this.consumer_key = consumer_key;
    }

    public long getTimestamp() {
        if (timestamp == 0 ) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

}

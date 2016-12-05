package com.wangjunneil.schedule.entity.mt;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2016/12/1.
 */
public class SysParams {
    private long timestamp;
    private String app_id;
    private String sig;

    public Long getTimestamp() {
        if(timestamp == 0){
            this.timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getSig() {
        return sig;
    }

    public void setSig(String sig) {
        this.sig = sig;
    }

    /**
     * 计算MD5
     * @param input
     * @return
     */
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext.toUpperCase();
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

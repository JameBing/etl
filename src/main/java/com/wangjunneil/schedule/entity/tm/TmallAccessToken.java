package com.wangjunneil.schedule.entity.tm;

import com.wangjunneil.schedule.common.Constants;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 *
 * Created by wangjun on 7/28/16.
 */
@Document(collection = "sync.multi.token")
public class TmallAccessToken {

    private String platform = Constants.PLATFORM_TM;
    private String access_token;
    private String token_type;
    private long expires_in;
    private String refresh_token;
    private long re_expires_in;
    private long r1_expires_in;
    private long r2_expires_in;
    private long w1_expires_in;
    private long w2_expires_in;
    private String taobao_user_nick;
    private String taobao_user_id;
    private String sub_taobao_user_id;
    private String sub_taobao_user_nick;

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

    public long getR1_expires_in() {
        return r1_expires_in;
    }

    public void setR1_expires_in(long r1_expires_in) {
        this.r1_expires_in = r1_expires_in;
    }

    public long getR2_expires_in() {
        return r2_expires_in;
    }

    public void setR2_expires_in(long r2_expires_in) {
        this.r2_expires_in = r2_expires_in;
    }

    public long getW1_expires_in() {
        return w1_expires_in;
    }

    public void setW1_expires_in(long w1_expires_in) {
        this.w1_expires_in = w1_expires_in;
    }

    public long getW2_expires_in() {
        return w2_expires_in;
    }

    public void setW2_expires_in(long w2_expires_in) {
        this.w2_expires_in = w2_expires_in;
    }

    public String getTaobao_user_nick() {
        return taobao_user_nick;
    }

    public void setTaobao_user_nick(String taobao_user_nick) {
        this.taobao_user_nick = taobao_user_nick;
    }

    public String getTaobao_user_id() {
        return taobao_user_id;
    }

    public void setTaobao_user_id(String taobao_user_id) {
        this.taobao_user_id = taobao_user_id;
    }

    public String getSub_taobao_user_id() {
        return sub_taobao_user_id;
    }

    public void setSub_taobao_user_id(String sub_taobao_user_id) {
        this.sub_taobao_user_id = sub_taobao_user_id;
    }

    public String getSub_taobao_user_nick() {
        return sub_taobao_user_nick;
    }

    public void setSub_taobao_user_nick(String sub_taobao_user_nick) {
        this.sub_taobao_user_nick = sub_taobao_user_nick;
    }
}

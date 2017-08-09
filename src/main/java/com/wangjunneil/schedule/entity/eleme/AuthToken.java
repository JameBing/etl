package com.wangjunneil.schedule.entity.eleme;

import com.wangjunneil.schedule.common.Constants;
import eleme.openapi.sdk.api.entity.user.OAuthorizedShop;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * @author yangyongbing
 * @since 2017-07-04
 */
@Document(collection = "sync.multi.token")
public class AuthToken {
    private String platform = Constants.PLATFORM_WAIMAI_ELEME;
    //授权码
    private String code;
    //授权码状态
    private String state;
    //token信息
    private String token;
    //token类型
    private String token_type;
    //token有效期
    private Long expires_in;
    //token更新值
    private String refresh_token;
    //作用域
    private String scope;

    private Date expire_Date;
    //商户Id
    private String uid;

    private List<OAuthorizedShop> shopIds;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Date getExpire_Date() {
        return expire_Date;
    }

    public void setExpire_Date(Date expire_Date) {
        this.expire_Date = expire_Date;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<OAuthorizedShop> getShopIds() {
        return shopIds;
    }

    public void setShopIds(List<OAuthorizedShop> shopIds) {
        this.shopIds = shopIds;
    }
}


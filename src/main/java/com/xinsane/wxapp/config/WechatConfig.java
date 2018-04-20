package com.xinsane.wxapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WechatConfig {

    private String appid, secret;

    public String getAppid() {
        return appid;
    }

    @Value("${wechat.appid}")
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    @Value("${wechat.secret}")
    public void setSecret(String secret) {
        this.secret = secret;
    }
}

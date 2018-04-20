package com.xinsane.wxapp.pojo;

import java.util.Date;

public class Token {
    private Integer id;
    private String token;
    private String openid;
    private String sessionKey;
    private Date createTime;
    private String data;

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", createTime=" + createTime +
                ", data='" + data + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
    public Token setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getToken() {
        return token;
    }
    public Token setToken(String token) {
        this.token = token;
        return this;
    }
    public String getOpenid() {
        return openid;
    }
    public Token setOpenid(String openid) {
        this.openid = openid;
        return this;
    }
    public String getSessionKey() {
        return sessionKey;
    }
    public Token setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public Token setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public String getData() {
        return data;
    }
    public Token setData(String data) {
        this.data = data;
        return this;
    }
}
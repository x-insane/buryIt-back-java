package com.xinsane.buryit.pojo;

import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private String password;
    private String nickname;
    private String avatar;
    private Date createTime;
    private Date updateTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Integer getId() {
        return id;
    }
    public User setId(Integer id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public User setName(String name) {
        this.name = name;
        return this;
    }
    public String getPassword() {
        return password;
    }
    public User setPassword(String password) {
        this.password = password;
        return this;
    }
    public String getNickname() {
        return nickname;
    }
    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }
    public String getAvatar() {
        return avatar;
    }
    public User setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public User setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
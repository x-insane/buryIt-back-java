package com.xinsane.wxapp.pojo;

import java.util.Date;

public class Source {
    private Integer id;
    private Integer userId;
    private String type;
    private Integer packageId;
    private Date createTime;
    private Date deleteTime;

    private String text;
    private String imageUrl;
    private String voiceUrl;

    @Override
    public String toString() {
        return "Source{" +
                "id=" + id +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", packageId=" + packageId +
                ", createTime=" + createTime +
                ", deleteTime=" + deleteTime +
                ", text='" + text + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", voiceUrl='" + voiceUrl + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }
    public Source setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }
    public Source setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getType() {
        return type;
    }
    public Source setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getPackageId() {
        return packageId;
    }
    public Source setPackageId(Integer packageId) {
        this.packageId = packageId;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }
    public Source setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }
    public Source setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public String getText() {
        return text;
    }
    public Source setText(String text) {
        this.text = text;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public Source setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getVoiceUrl() {
        return voiceUrl;
    }
    public Source setVoiceUrl(String voiceUrl) {
        this.voiceUrl = voiceUrl;
        return this;
    }
}
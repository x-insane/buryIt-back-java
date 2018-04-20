package com.xinsane.wxapp.pojo;

import java.util.Date;

public class Package {
    private Integer id;
    private Integer userId;
    private String title;
    private Boolean realMode;
    private String type;
    private Integer packageTotalNum;
    private Integer packageOpenNum;
    private Integer cycleTime;
    private String description;
    private String sender;
    private String receiver;
    private Double longitude;
    private Double latitude;
    private Integer deep;
    private String password;
    private Date createTime;
    private Date publishTime;
    private Date canOpenTime;
    private Date deleteTime;
    private String dataCache;

    @Override
    public String toString() {
        return "Package{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", realMode=" + realMode +
                ", type='" + type + '\'' +
                ", packageTotalNum=" + packageTotalNum +
                ", packageOpenNum=" + packageOpenNum +
                ", cycleTime=" + cycleTime +
                ", description='" + description + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", deep=" + deep +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", publishTime=" + publishTime +
                ", canOpenTime=" + canOpenTime +
                ", deleteTime=" + deleteTime +
                ", dataCache='" + dataCache + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Package setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Package setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Package setTitle(String title) {
        this.title = title;
        return this;
    }

    public Boolean getRealMode() {
        return realMode;
    }

    public Package setRealMode(Boolean realMode) {
        this.realMode = realMode;
        return this;
    }

    public String getType() {
        return type;
    }

    public Package setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getPackageTotalNum() {
        return packageTotalNum;
    }

    public Package setPackageTotalNum(Integer packageTotalNum) {
        this.packageTotalNum = packageTotalNum;
        return this;
    }

    public Integer getPackageOpenNum() {
        return packageOpenNum;
    }

    public Package setPackageOpenNum(Integer packageOpenNum) {
        this.packageOpenNum = packageOpenNum;
        return this;
    }

    public Integer getCycleTime() {
        return cycleTime;
    }

    public Package setCycleTime(Integer cycleTime) {
        this.cycleTime = cycleTime;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Package setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getSender() {
        return sender;
    }

    public Package setSender(String sender) {
        this.sender = sender;
        return this;
    }

    public String getReceiver() {
        return receiver;
    }

    public Package setReceiver(String receiver) {
        this.receiver = receiver;
        return this;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Package setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Package setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Integer getDeep() {
        return deep;
    }

    public Package setDeep(Integer deep) {
        this.deep = deep;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Package setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Package setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public Package setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
        return this;
    }

    public Date getCanOpenTime() {
        return canOpenTime;
    }

    public Package setCanOpenTime(Date canOpenTime) {
        this.canOpenTime = canOpenTime;
        return this;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public Package setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public String getDataCache() {
        return dataCache;
    }

    public Package setDataCache(String dataCache) {
        this.dataCache = dataCache;
        return this;
    }
}
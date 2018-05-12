package com.xinsane.buryit.pojo;

import java.util.Date;

public class EventNotification {
    private Integer id;
    private Integer userId;
    private Integer eventId;
    private Boolean isRead;
    private Date deleteTime;

    private String eventType;
    private String data;
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public EventNotification setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public EventNotification setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getEventId() {
        return eventId;
    }

    public EventNotification setEventId(Integer eventId) {
        this.eventId = eventId;
        return this;
    }

    public Boolean getRead() {
        return isRead;
    }

    public EventNotification setRead(Boolean read) {
        isRead = read;
        return this;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public EventNotification setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public EventNotification setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getData() {
        return data;
    }

    public EventNotification setData(String data) {
        this.data = data;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public EventNotification setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
}
package com.xinsane.buryit.pojo;

import java.util.Date;

public class Event {
    private Integer id;
    private Integer packageId;
    private Integer relatedUserId;
    private String type;
    private String data;
    private Date time;

    public Integer getId() {
        return id;
    }
    public Event setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getPackageId() {
        return packageId;
    }
    public Event setPackageId(Integer packageId) {
        this.packageId = packageId;
        return this;
    }

    public Integer getRelatedUserId() {
        return relatedUserId;
    }
    public Event setRelatedUserId(Integer relatedUserId) {
        this.relatedUserId = relatedUserId;
        return this;
    }

    public String getType() {
        return type;
    }
    public Event setType(String type) {
        this.type = type;
        return this;
    }

    public String getData() {
        return data;
    }
    public Event setData(String data) {
        this.data = data;
        return this;
    }

    public Date getTime() {
        return time;
    }
    public Event setTime(Date time) {
        this.time = time;
        return this;
    }
}
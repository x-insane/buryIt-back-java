package com.xinsane.buryit.pojo;

import java.util.Date;

public class FileUpload {
    private Integer id, userId;
    private String path;
    private Date uploadTime;

    public Integer getId() {
        return id;
    }
    public FileUpload setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }
    public FileUpload setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getPath() {
        return path;
    }
    public FileUpload setPath(String path) {
        this.path = path;
        return this;
    }

    public Date getUploadTime() {
        return uploadTime;
    }
    public FileUpload setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }
}

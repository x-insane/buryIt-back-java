package com.xinsane.buryit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileConfig {
    private String uploadRootPath;

    public String getUploadRootPath() {
        return uploadRootPath;
    }
    @Value("${upload.path}")
    public void setUploadRootPath(String uploadRootPath) {
        this.uploadRootPath = uploadRootPath;
    }
}

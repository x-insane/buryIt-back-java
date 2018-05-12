package com.xinsane.buryit.service;

import com.xinsane.buryit.pojo.FileUpload;
import com.xinsane.buryit.service.transfer.Transfer;

public interface FileService {
    Transfer uploadFile(FileUpload fileUpload);
}

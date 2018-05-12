package com.xinsane.buryit.service.impl;

import com.xinsane.buryit.mapper.FileUploadMapper;
import com.xinsane.buryit.pojo.FileUpload;
import com.xinsane.buryit.service.FileService;
import com.xinsane.buryit.service.transfer.BaseTransfer;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    private final FileUploadMapper fileUploadMapper;

    @Autowired
    public FileServiceImpl(FileUploadMapper fileUploadMapper) {
        this.fileUploadMapper = fileUploadMapper;
    }

    @Override
    public Transfer uploadFile(FileUpload fileUpload) {
        int rows = fileUploadMapper.insert(fileUpload);
        if (rows != 1)
            return new BaseTransfer().setError(400).setMsg("写入文件上传信息失败");
        return new BaseTransfer();
    }
}

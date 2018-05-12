package com.xinsane.buryit.controller.api;

import com.google.gson.JsonObject;
import com.xinsane.buryit.config.FileConfig;
import com.xinsane.buryit.controller.api.common.ApiController;
import com.xinsane.buryit.pojo.FileUpload;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.FileService;
import com.xinsane.buryit.service.UserService;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping(value = "/api/file",
        produces = "application/json;charset=UTF-8",
        method = RequestMethod.POST)
@ResponseBody
public class FileController extends ApiController {
    private final UserService userService;
    private final FileService fileService;
    private final FileConfig fileConfig;

    @Autowired
    public FileController(UserService userService, FileService fileService, FileConfig fileConfig) {
        this.userService = userService;
        this.fileService = fileService;
        this.fileConfig = fileConfig;
    }

    @RequestMapping(value = "upload")
    public String upload(String token, MultipartFile file) throws IOException {
        User user = userService.getUserByToken(token);
        if (user == null)
            return error(403, "授权失败");
        if (file == null || file.isEmpty())
            return error(401, "未选择文件");
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String relativePath;
        switch (file.getContentType()) {
            case "image/jpeg":
                relativePath = "/image/" + uuid + ".jpg";
                break;
            case "audio/amr":
                relativePath = "/voice/" + uuid + ".amr";
                break;
            default:
                return error(402, "不支持的文件格式");
        }
        File savedFile = new File(fileConfig.getUploadRootPath() + relativePath);
        System.out.println("A file saved at " + savedFile.getAbsolutePath());
        file.transferTo(savedFile);
        Transfer transfer = fileService.uploadFile(new FileUpload().setUserId(user.getId()).setPath(relativePath));
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.addProperty("url", relativePath);
        return object.toString();
    }

}

package com.xinsane.buryit.controller.api;

import com.google.gson.*;
import com.xinsane.buryit.controller.api.common.ApiController;
import com.xinsane.buryit.pojo.Source;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.PackageService;
import com.xinsane.buryit.service.SourceService;
import com.xinsane.buryit.service.UserService;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/source",
        produces = { "application/json;charset=UTF-8" },
        method = RequestMethod.POST)
@ResponseBody
public class SourceController extends ApiController {

    private final SourceService sourceService;
    private final UserService userService;
    private final PackageService packageService;

    @Autowired
    public SourceController(SourceService sourceService, UserService userService, PackageService packageService) {
        this.sourceService = sourceService;
        this.userService = userService;
        this.packageService = packageService;
    }

    @RequestMapping("/create")
    public String create(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer;
        switch (data.getType()) {
            case "text":
                transfer = sourceService.addText(data);
                break;
            case "image":
                transfer = sourceService.addImage(data);
                break;
            case "voice":
                transfer = sourceService.addVoice(data);
                break;
            default:
                return error(400, "无法识别的类型");
        }
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.addProperty("id", data.getId());
        return object.toString();
    }

    @RequestMapping("/modify")
    public String modify(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer;
        switch (data.getType()) {
            case "text":
                transfer = sourceService.updateText(data);
                break;
            case "image":
                transfer = sourceService.updateImage(data);
                break;
            case "voice":
                transfer = sourceService.updateVoice(data);
                break;
            default:
                return error(400, "无法识别的类型");
        }
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("/delete")
    public String delete(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = sourceService.deleteSource(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("/list")
    public String list(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        JsonElement array = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .toJsonTree(sourceService.getByPackageId(data.getPackageId()));
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.add("sources", array);
        return object.toString();
    }

    static class RequestData extends Source {
        String token;
    }
}

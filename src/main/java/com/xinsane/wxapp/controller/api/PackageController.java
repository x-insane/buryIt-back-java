package com.xinsane.wxapp.controller.api;

import com.google.gson.JsonObject;
import com.xinsane.wxapp.controller.api.common.ApiController;
import com.xinsane.wxapp.pojo.Package;
import com.xinsane.wxapp.pojo.User;
import com.xinsane.wxapp.service.PackageService;
import com.xinsane.wxapp.service.UserService;
import com.xinsane.wxapp.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/package",
        produces = { "application/json;charset=UTF-8" },
        method = RequestMethod.POST)
@ResponseBody
public class PackageController extends ApiController {

    private final PackageService packageService;
    private final UserService userService;

    @Autowired
    public PackageController(PackageService packageService, UserService userService) {
        this.packageService = packageService;
        this.userService = userService;
    }

    @RequestMapping("/create")
    public String create(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = packageService.insert(data);
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
        Transfer transfer = packageService.modify(data);
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
        Transfer transfer = packageService.delete(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    static class RequestData extends Package {
        String token;
    }

}

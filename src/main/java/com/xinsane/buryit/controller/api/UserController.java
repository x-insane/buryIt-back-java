package com.xinsane.buryit.controller.api;

import com.google.gson.JsonObject;
import com.xinsane.buryit.controller.api.common.ApiController;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.UserService;
import com.xinsane.buryit.service.transfer.TokenTransfer;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/user/wx",
        produces = { "application/json;charset=UTF-8" },
        method = RequestMethod.POST)
@ResponseBody
public class UserController extends ApiController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
    public String login(@RequestBody RequestData data) {
        if (data.code == null || data.code.isEmpty())
            return error(101, "缺少参数");
        TokenTransfer transfer = userService.loginByWxCode(data.code);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        JsonObject obj = new JsonObject();
        obj.addProperty("error", 0);
        obj.addProperty("token", transfer.getToken());
        return obj.toString();
    }

    @RequestMapping("/modify-info")
    public String modify(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setId(user.getId());
        Transfer transfer = userService.modifyUserInfo(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    static class RequestData extends User {
        String code;
        String token;
        public void setCode(String code) {
            this.code = code;
        }
        public void setToken(String token) {
            this.token = token;
        }
    }

}

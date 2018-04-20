package com.xinsane.wxapp.controller.api;

import com.google.gson.JsonObject;
import com.xinsane.wxapp.controller.api.common.ApiController;
import com.xinsane.wxapp.service.UserService;
import com.xinsane.wxapp.service.transfer.TokenTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/user",
        produces = { "application/json;charset=UTF-8" },
        method = RequestMethod.POST)
@ResponseBody
public class UserController extends ApiController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/wxlogin")
    public String wxlogin(@RequestBody RequestData data) {
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

    static class RequestData {
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

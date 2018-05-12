package com.xinsane.buryit.controller.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xinsane.buryit.controller.api.common.ApiController;
import com.xinsane.buryit.pojo.EventNotification;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.NotificationService;
import com.xinsane.buryit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/notification",
        produces = "application/json;charset=UTF-8",
        method = RequestMethod.POST)
@ResponseBody
public class NotificationController extends ApiController {
    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @RequestMapping("list")
    public String list(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        List<EventNotification> notifications = notificationService.getNotifications(user.getId(), data.lastId);
        JsonElement array = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
            .toJsonTree(notifications);
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.add("notifications", array);
        return object.toString();
    }

    private static class RequestData {
        String token;
        Integer lastId;
    }
}

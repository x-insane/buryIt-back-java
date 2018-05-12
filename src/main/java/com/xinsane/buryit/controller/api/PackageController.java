package com.xinsane.buryit.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xinsane.buryit.controller.api.common.ApiController;
import com.xinsane.buryit.pojo.Event;
import com.xinsane.buryit.pojo.Package;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.EventService;
import com.xinsane.buryit.service.PackageService;
import com.xinsane.buryit.service.UserService;
import com.xinsane.buryit.service.transfer.Transfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/package",
        produces = { "application/json;charset=UTF-8" },
        method = RequestMethod.POST)
@ResponseBody
public class PackageController extends ApiController {
    private final PackageService packageService;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public PackageController(PackageService packageService, UserService userService, EventService eventService) {
        this.packageService = packageService;
        this.userService = userService;
        this.eventService = eventService;
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

    @RequestMapping("/access_content")
    public String access_content(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = packageService.access(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("/list")
    public String list(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        JsonElement own = gson.toJsonTree(packageService.listOwnPackages(user.getId()));
        JsonElement watch = gson.toJsonTree(packageService.listWatchPackages(user.getId()));
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.add("own", own);
        object.add("watch", watch);
        return object.toString();
    }

    @RequestMapping("bury")
    public String bury(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = packageService.bury(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("dig")
    public String dig(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = packageService.dig(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("query_map")
    public String query_map(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        List<Package> packages = packageService.queryWithCircle(data, data.distance);
        JsonElement array = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()
                .toJsonTree(packages);
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.add("packages", array);
        return object.toString();
    }

    @RequestMapping("rebury")
    public String rebury(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        data.setUserId(user.getId());
        Transfer transfer = packageService.rebury(data);
        if (transfer.getError() != 0)
            return error(transfer.getError(), transfer.getMsg());
        return ok();
    }

    @RequestMapping("list_event")
    public String list_event(@RequestBody RequestData data) {
        User user = userService.getUserByToken(data.token);
        if (user == null)
            return error(100, "授权失败");
        List<Event> events = eventService.list(data.getId());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        JsonElement array = gson.toJsonTree(events);
        JsonObject object = new JsonObject();
        object.addProperty("error", 0);
        object.add("events", array);
        return object.toString();
    }

    static class RequestData extends Package {
        String token;
        Integer distance;
    }

}

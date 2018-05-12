package com.xinsane.buryit.service.impl;

import com.google.gson.JsonObject;
import com.xinsane.buryit.mapper.EventMapper;
import com.xinsane.buryit.mapper.NotificationMapper;
import com.xinsane.buryit.mapper.PackageMapper;
import com.xinsane.buryit.pojo.Event;
import com.xinsane.buryit.pojo.Package;
import com.xinsane.buryit.pojo.User;
import com.xinsane.buryit.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    private final EventMapper eventMapper;
    private final PackageMapper packageMapper;
    private final NotificationMapper notificationMapper;

    @Autowired
    public EventServiceImpl(EventMapper eventMapper, PackageMapper packageMapper, NotificationMapper notificationMapper) {
        this.eventMapper = eventMapper;
        this.packageMapper = packageMapper;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<Event> list(Integer packageId) {
        if (packageId == null)
            return new ArrayList<>();
        return eventMapper.list(packageId);
    }

    @Override
    public boolean addBuryEvent(Package pack) {
        JsonObject data = new JsonObject();
        data.addProperty("type", pack.getType());
        data.addProperty("deep", pack.getDeep());
        data.addProperty("latitude", pack.getLatitude());
        data.addProperty("longitude", pack.getLongitude());
        return 1 == eventMapper.insert(new Event()
                .setRelatedUserId(pack.getUserId())
                .setPackageId(pack.getId())
                .setType("bury")
                .setData(data.toString()));
    }

    @Override
    public boolean addDigEvent(Package pack, Integer currentUserId) {
        Event event = new Event()
                .setRelatedUserId(currentUserId)
                .setPackageId(pack.getId())
                .setType("dig")
                .setData("{}");
        boolean success = 1 == eventMapper.insert(event);
        if (success) {
            if (!currentUserId.equals(pack.getUserId()))
                notificationMapper.insert(event.getId(), pack.getUserId());
            for (User user : packageMapper.listWatchers(pack.getId())) {
                if (!currentUserId.equals(user.getId()))
                    notificationMapper.insert(event.getId(), user.getId());
            }
        }
        return success;
    }

    @Override
    public boolean addOverdueEvent(Package pack) {
        Event event = new Event()
                .setRelatedUserId(0)
                .setPackageId(pack.getId())
                .setType("overdue")
                .setData("{}");
        boolean success = 1 == eventMapper.insert(event);
        if (success) {
            notificationMapper.insert(event.getId(), pack.getUserId());
            for (User user : packageMapper.listWatchers(pack.getId()))
                notificationMapper.insert(event.getId(), user.getId());
        }
        return success;
    }

    @Override
    public boolean addReburyEvent(Package pack, Integer currentUserId) {
        JsonObject data = new JsonObject();
        data.addProperty("deep", pack.getDeep());
        data.addProperty("latitude", pack.getLatitude());
        data.addProperty("longitude", pack.getLongitude());
        return 1 == eventMapper.insert(new Event()
                .setRelatedUserId(currentUserId)
                .setPackageId(pack.getId())
                .setType("rebury")
                .setData(data.toString()));
    }

    @Override
    public boolean addDeleteEvent(Package pack) {
        Event event = new Event()
                .setRelatedUserId(pack.getUserId())
                .setPackageId(pack.getId())
                .setType("delete")
                .setData("{}");
        boolean success = 1 == eventMapper.insert(event);
        if (success) {
            for (User user : packageMapper.listWatchers(pack.getId()))
                notificationMapper.insert(event.getId(), user.getId());
        }
        return success;
    }
}

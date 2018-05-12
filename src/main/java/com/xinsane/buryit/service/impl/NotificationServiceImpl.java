package com.xinsane.buryit.service.impl;

import com.xinsane.buryit.mapper.NotificationMapper;
import com.xinsane.buryit.pojo.EventNotification;
import com.xinsane.buryit.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Autowired
    public NotificationServiceImpl(NotificationMapper notificationMapper) {
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<EventNotification> getNotifications(Integer userId, Integer lastId) {
        if (userId == null)
            return new ArrayList<>();
        if (lastId == null)
            lastId = 0;
        return notificationMapper.list(userId, lastId);
    }

}

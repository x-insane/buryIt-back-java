package com.xinsane.buryit.service;

import com.xinsane.buryit.pojo.EventNotification;

import java.util.List;

public interface NotificationService {
    /**
     * 获取id大于lastId的所有通知
     * @param userId 用户id
     * @param lastId 基准id，只会获取通知id大于这个id的通知，获取全部通知设置此参数为0
     * @return 所有符合条件的通知
     */
    List<EventNotification> getNotifications(Integer userId, Integer lastId);
}

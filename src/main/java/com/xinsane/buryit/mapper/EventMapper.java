package com.xinsane.buryit.mapper;

import com.xinsane.buryit.pojo.Event;

import java.util.List;

public interface EventMapper {
    List<Event> list(Integer packageId);
    int insert(Event event);
}

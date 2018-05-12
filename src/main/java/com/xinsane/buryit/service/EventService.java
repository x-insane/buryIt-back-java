package com.xinsane.buryit.service;

import com.xinsane.buryit.pojo.Event;
import com.xinsane.buryit.pojo.Package;

import java.util.List;

public interface EventService {
    List<Event> list(Integer packageId);
    boolean addBuryEvent(Package pack);
    boolean addDigEvent(Package pack, Integer currentUserId);
    boolean addOverdueEvent(Package pack);
    boolean addReburyEvent(Package pack, Integer currentUserId);
    boolean addDeleteEvent(Package pack);
}

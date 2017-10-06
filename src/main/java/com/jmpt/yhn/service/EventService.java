package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.Event;

/**
 * Created by yhn on 2017/9/29.
 */
public interface EventService {
    Event save(Event event);
    Event  findEvent(String eventId);
}

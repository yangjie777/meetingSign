package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by yhn on 2017/9/29.
 */
public interface EventService {
    Event save(Event event);
    Event  findEvent(String eventId);
    Page<Event> findEventByOpenid(String openid,Pageable pageable);
}

package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.dao.EventRepository;
import com.jmpt.yhn.entity.Event;
import com.jmpt.yhn.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhn on 2017/9/29.
 */
@Service
public class EventServiceImpl  implements EventService{
    @Autowired
    private EventRepository repository;
    @Override
    public Event save(Event event) {
        return repository.save(event);
    }

    @Override
    public Event findEvent(String eventId) {
        return repository.findByEventId(eventId);
    }
}

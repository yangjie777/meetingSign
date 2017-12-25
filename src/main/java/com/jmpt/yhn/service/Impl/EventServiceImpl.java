package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.dao.EventRepository;
import com.jmpt.yhn.entity.Event;
import com.jmpt.yhn.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
/**
 * Created by yhn on 2017/9/29.
 */
@Service
@Slf4j
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

    @Override
    public Page<Event> findEventByOpenid(String openid, Pageable pageable) {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");   //降序----根据创建时间来进行降序
        log.info("page={},size={}",pageable.getPageNumber(),pageable.getPageSize());
        PageRequest request = new PageRequest(pageable.getPageNumber(),pageable.getPageSize(),sort);  //第0页，取一条
        Page<Event> eventPage = repository.findByOpenid(openid,request);
        return eventPage;
    }
}

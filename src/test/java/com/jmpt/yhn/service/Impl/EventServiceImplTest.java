package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.entity.Event;
import com.jmpt.yhn.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/10/18.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventServiceImplTest {
    @Autowired
    private EventService eventService;
    @Test
    public void findEventByOpenid() throws Exception {
      PageRequest request = new PageRequest(0,10);  //第0页，取一条
      Page<Event> result = eventService.findEventByOpenid("321",request);
        log.info("result={},size={},totalPages={}",result.getContent(),result.getSize(),result.getTotalPages());
    }
}
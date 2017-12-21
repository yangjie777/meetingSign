package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EventServiceTest {
    @Autowired
    private EventService eventService;
    @Test
    public void findEventByOpenid() throws Exception {
        PageRequest request =new PageRequest(1,2);
        Page<Event> orderDTOPage = eventService.findEventByOpenid("321",request);
        System.out.println(orderDTOPage.getContent());
    }
}
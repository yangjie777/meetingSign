package com.jmpt.yhn.dao;

import com.jmpt.yhn.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventRepositoryTest {
    @Autowired
    private EventRepository eventRepository;
    @Test
    public void findByOpenid() throws Exception {
        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        PageRequest request = new PageRequest(0,3,sort);  //第0页，取一条
        Page<Event> result = eventRepository.findByOpenid("321",request);
        System.out.println(result.getContent());
    }

}
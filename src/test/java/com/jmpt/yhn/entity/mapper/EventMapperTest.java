package com.jmpt.yhn.entity.mapper;

import com.jmpt.yhn.entity.Event;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/10/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EventMapperTest {
    @Autowired
    private EventMapper eventMapper;  //IDEA自带报错。
    @Test
    public void insertByMap() throws Exception {
        Map<String,Object> map = new HashMap<>();
       map.put("id","666");
       map.put("event_id","666");
       map.put("event_content","666");
        map.put("openid","321");
        int result = eventMapper.insertByMap(map);
        Assert.assertEquals(1,result);
    }
    @Test
    public void insertByObject(){
        Event event = new Event();
        event.setOpenid("321");
        event.setCreateTime(new Date());
        event.setEndTime(new Date());
        event.setEventContent("哈哈哈");
        event.setEventId("123123123");
        event.setId(999);
        int result = eventMapper.insertByObject(event);
        Assert.assertEquals(1,result);

    }
    @Test
    public void findByEventId(){
        Event event = eventMapper.findByEventId(10);
        log.info("event={}",event);
        Assert.assertNotNull(event);
    }
    @Test
    public void updateEvent(){
        int result  = eventMapper.updateEvent("更新完成",999);
        Assert.assertEquals(1,result);
    }
    @Test
    public void updateByObject(){
        Event event = new Event();
        event.setOpenid("321");
        event.setCreateTime(new Date());
        event.setEndTime(new Date());
        event.setEventContent("嘻嘻嘻");
        event.setEventId("22222222");
        event.setId(999);
        int result  = eventMapper.updateByObject(event);
        Assert.assertEquals(1,result);
    }
}
package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.service.MeetingAndUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/9/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingAndUserServiceImplTest {
    @Autowired
    private MeetingAndUserService service;
    @Test
    public void findByMeetingId() throws Exception {
        System.out.println(service.findByMeetingId("654321"));
    }

    @Test
    public void findByOpenid() throws Exception {
    }
    @Test
    public void save(){

    }

}
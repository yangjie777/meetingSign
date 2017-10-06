package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.service.MeetingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/9/7.
 */
@RunWith(SpringRunner.class)
@SpringBootTest

public class MeetingServiceImplTest {
    @Test
    public void deteleMeeting() throws Exception {
    }

    @Autowired
    private MeetingService service;
    @Test
    public void findMyMeeting() throws Exception {
        System.out.println( service.findMyMeeting("qwertyuiop"));
    }
    @Test
    public void save() throws Exception {
        Meeting meeting = new Meeting();
        meeting.setCreateTime(new Date());
        meeting.setUpdateTime(new Date());
        meeting.setMeetingId("235245345");
        meeting.setMeetingName("三个会议");
        meeting.setOpenid("qwertyuiop");
        service.save(meeting);
        Assert.assertNotNull(meeting);
    }
    @Test
    public void detele(){


    }
    @Test
    public void findByMeetingId(){
        System.out.println(service.findByMeetId("1504860306114278404"));
    }
}
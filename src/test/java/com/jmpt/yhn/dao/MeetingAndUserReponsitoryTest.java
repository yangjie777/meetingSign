package com.jmpt.yhn.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/9/9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MeetingAndUserReponsitoryTest {
    @Autowired
    private MeetingAndUserReponsitory reponsitory;
    @Test
    public void findByOpenidAndAndMeetingId() throws Exception {
        System.out.println( reponsitory.findByOpenidAndAndMeetingId("o4eWZ06xzHW6mc2gMZMYVDEtx1zk","1504942476000575155"));
    }
    @Test
    public void test(){
        double i = 0.1;
        double j = 0.2;
        System.out.println(i+j);
    }
}
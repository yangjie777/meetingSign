package com.jmpt.yhn.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by yhn on 2017/9/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoReponsitoryTest {
    @Autowired
    private UserInfoReponsitory reponsitory;
    @Test
    public void findByOpenid() throws Exception {
        System.out.println(reponsitory.findByOpenid("abc"));
    }

}
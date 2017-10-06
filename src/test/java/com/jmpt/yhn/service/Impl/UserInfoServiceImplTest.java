package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.UserInfoService;
import org.junit.Assert;
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
public class UserInfoServiceImplTest {
    @Autowired
  private UserInfoService service;
    @Test
    public void findOne() throws Exception {
        System.out.println(service.findOne("abc"));
    }

    @Test
    public void save() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername("要好那");
        userInfo.setOpenid("abc");
        service.save(userInfo);
        Assert.assertNotNull(userInfo);
    }

}
package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.dao.UserInfoReponsitory;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by yhn on 2017/9/7.
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoReponsitory reponsitory;
    @Override
    public UserInfo findOne(String openid) {
        return reponsitory.findByOpenid(openid);
    }

    @Override
    public UserInfo save(UserInfo userInfo) {
        UserInfo userInfoi = new UserInfo();
        try {
             userInfoi = reponsitory.save(userInfo);
        }catch(Exception e){
            e.printStackTrace();
            log.error("【可能姓名存在emjoy】");
            userInfoi.setUsername(null); //不设置姓名
            return reponsitory.save(userInfoi);
        }
        return userInfoi;

    }
}

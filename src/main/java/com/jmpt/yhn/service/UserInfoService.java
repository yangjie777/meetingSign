package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.UserInfo;


/**
 * Created by yhn on 2017/9/7.
 */
public interface UserInfoService {
    UserInfo findOne(String openid);
    UserInfo save(UserInfo userInfo);
}

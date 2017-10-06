package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.EventAndUserInfo;

import java.util.List;

/**
 * Created by yhn on 2017/9/29.
 */
public interface EventAndUserInfoService {
    EventAndUserInfo save(EventAndUserInfo eventAndUserInfo);
    List<EventAndUserInfo>  findEventAndUerInfoList(String openid);
}

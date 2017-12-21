//package com.jmpt.yhn.service.Impl;
//
//import com.jmpt.yhn.dao.EventAndUserInfoRepository;
//import com.jmpt.yhn.entity.EventAndUserInfo;
//import com.jmpt.yhn.service.EventAndUserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * Created by yhn on 2017/9/29.
// */
//@Service
//public class EventAndUserInfoServiceImpl implements EventAndUserInfoService{
//    @Autowired
//    private EventAndUserInfoRepository repository;
//
//    @Override
//    public EventAndUserInfo save(EventAndUserInfo eventAndUserInfo) {
//        return repository.save(eventAndUserInfo);
//    }
//
//    @Override
//    public List<EventAndUserInfo> findEventAndUerInfoList(String openid) {
//        return repository.findByOpenid(openid);
//    }
//}

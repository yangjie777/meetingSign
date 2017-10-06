package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.dao.MeetingAndUserReponsitory;
import com.jmpt.yhn.dao.MeetingSignReponsitory;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.service.MeetingAndUserService;
import com.jmpt.yhn.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
@Service
@Transactional
public class MeetingAndUserServiceImpl implements MeetingAndUserService{
    @Autowired
    private MeetingAndUserReponsitory reponsitory;
    @Override
    public List<MeetingAndUser> findByMeetingId(String meetingId) {
        return reponsitory.findByMeetingId(meetingId);
    }

    @Override
    public List<MeetingAndUser> findByOpenid(String openid) {
        return reponsitory.findByOpenid(openid);
    }
   public MeetingAndUser save(MeetingAndUser meetingAndUser){
       return reponsitory.save(meetingAndUser);
   }

    @Override
    public MeetingAndUser findMeeting(String openid, String meetingId) {
      return reponsitory.findByOpenidAndAndMeetingId(openid,meetingId);
    }

    @Override
    public void deleteMeetingAndUser(String meetingId) {
        reponsitory.removeByMeetingId(meetingId);
    }
}

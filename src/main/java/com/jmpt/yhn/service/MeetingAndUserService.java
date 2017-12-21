package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;

import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
public interface MeetingAndUserService {
    List<MeetingAndUser> findByMeetingId(String meetingId);  //通过会议id查询会议
    List<MeetingAndUser> findByOpenid(String openid);  //根据openid查询已参加的会议
    MeetingAndUser save(MeetingAndUser meetingAndUser);
    MeetingAndUser findMeeting(String openid,String meetingId);
    void deleteMeetingAndUser(String meetingId);
    List<MeetingAndUser> findById(String id);
}

package com.jmpt.yhn.service;

import com.jmpt.yhn.entity.Meeting;

import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
public interface MeetingService {
    List<Meeting> findMyMeeting(String openid);  //查询自己发起的会议
    Meeting findByMeetId(String meetingId);
    Meeting save(Meeting meeting);  //发起会议
    void deteleMeeting(String meetingId);
}

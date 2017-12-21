package com.jmpt.yhn.dao;


import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
public interface MeetingAndUserReponsitory extends JpaRepository<MeetingAndUser,String>{
    List<MeetingAndUser> findByMeetingId(String meetingId);  //通过会议id查询会议
    List<MeetingAndUser> findByOpenid(String openid);  //根据openid查询已参加的会议
    MeetingAndUser findByOpenidAndAndMeetingId(String openid,String meetingId);
   void removeByMeetingId(String meetingId);
}

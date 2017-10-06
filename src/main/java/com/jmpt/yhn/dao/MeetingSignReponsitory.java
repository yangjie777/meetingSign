package com.jmpt.yhn.dao;

import com.jmpt.yhn.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
public interface MeetingSignReponsitory extends JpaRepository<Meeting,String>{
    Meeting findByMeetingId(String meetingId);
    List<Meeting> findByOpenid(String openid);
    void removeByMeetingId(String meetingId);
}

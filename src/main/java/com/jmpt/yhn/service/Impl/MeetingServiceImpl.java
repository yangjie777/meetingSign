package com.jmpt.yhn.service.Impl;

import com.jmpt.yhn.dao.MeetingSignReponsitory;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

/**
 * Created by yhn on 2017/9/7.
 */
@Service
@Transactional
public class MeetingServiceImpl implements MeetingService{
    @Autowired
    private MeetingSignReponsitory reponsitory;
    @Override
    public List<Meeting> findMyMeeting(String openid) {
        return reponsitory.findByOpenid(openid);
    }

    @Override
    public Meeting findByMeetId(String meetingId) {
        return reponsitory.findByMeetingId(meetingId);
    }

    @Override
    public Meeting save(Meeting meeting) {
        return reponsitory.save(meeting);
    }
    @Override
    public void deteleMeeting(String meetingId) {
        reponsitory.removeByMeetingId(meetingId);
    }

}

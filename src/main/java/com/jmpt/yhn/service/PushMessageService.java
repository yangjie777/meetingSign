package com.jmpt.yhn.service;

import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.UserInfo;

/**
 * Created by yhn on 2017/9/6.
 */
public interface PushMessageService {
    //签到成功
    void successSign(Meeting meeting, UserInfo userInfo, String meetingUsername);
    void successSetEvent(NoticeDTO noticeDTO);
    void noiteEvent(NoticeDTO noticeDTO);
    void aliyunSms(NoticeDTO noticeDTO)throws Exception;
}

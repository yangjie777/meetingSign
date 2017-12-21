package com.jmpt.yhn.controller;

import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.MeetingAndUserService;
import com.jmpt.yhn.service.MeetingService;
import com.jmpt.yhn.service.PushMessageService;
import com.jmpt.yhn.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created by yhn on 2017/9/9.
 */
@RestController
@RequestMapping("/sign")
@Slf4j
public class MeetingJoinController {
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private MeetingAndUserService meetingAndUserService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PushMessageService pushMessageService;
    @GetMapping("/join")
    public ModelAndView join(@RequestParam("meetingId") String meetingId,
                             @RequestParam("openid") String openid,
                             @RequestParam("imgHead") String imgHead,
                             Map<String, Object> map) {
        try {
            Meeting meeting = meetingService.findByMeetId(meetingId);
            UserInfo userInfo = userInfoService.findOne(meeting.getOpenid());  //这是会议发起者
            UserInfo myUserInfo = userInfoService.findOne(openid);   //用户本身
            map.put("meeting", meeting);
            map.put("openid", openid);
            map.put("nickName", userInfo.getUsername());
            map.put("mynickName",myUserInfo.getUsername());
            map.put("telphone",myUserInfo.getTelphone());
            return new ModelAndView("page/meetingJoin", map);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/joinSuccess")
    public void joinSuccess(@RequestParam("meetingId") String meetingId,
                            @RequestParam("openid") String openid,
                            @RequestParam("username") String username,
                            @RequestParam("telphone") String telphone,
                            @RequestParam(value = "status",defaultValue = "0")  String status,
                            HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            MeetingAndUser meeting = meetingAndUserService.findMeeting(openid, meetingId);
            Meeting meeting1 = meetingService.findByMeetId(meetingId);
            if(String.valueOf(meeting1.getMeetingStatus()).equals("1")) {
                if (meeting == null) {
                    log.info("【用户签到】未签到。openid={}", openid);
                    UserInfo userInfo = userInfoService.findOne(openid);
                    MeetingAndUser meetingAndUser = new MeetingAndUser();
                    meetingAndUser.setOpenid(openid);
                    meetingAndUser.setCreateTime(new Date());
                    meetingAndUser.setMeetingId(meetingId);
                    meetingAndUser.setTelphone(telphone);
                    meetingAndUser.setName(username);
                    if (status.equals("1")) {     //本来想如果提交信息和个人信息不符合就提示是否修改，留着
                        userInfo.setUsername(username);
                        userInfo.setTelphone(telphone);
                        userInfoService.save(userInfo);
                    }
                    meetingAndUserService.save(meetingAndUser);
                    //微信通知签到成功
                    UserInfo userInfo1 = userInfoService.findOne(meeting1.getOpenid());  //这是发起者
                    pushMessageService.successSign(meeting1,userInfo,userInfo1.getUsername());
                    log.info("【用户签到】已签到。openid={},nickname={}", openid, userInfo.getUsername());
                    writer.print("恭喜您签到成功");
                } else {
                    writer.print("您已经签到过了");
                }
            }
            else{
                writer.print("抱歉，该会议已停止签到");
            }
        }catch(Exception e){
            e.printStackTrace();
            log.error("【用户签到】签到失败");
            writer.print("异常");
        }finally{
            writer.flush();
            writer.close();
        }
    }
}
package com.jmpt.yhn.controller;

import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.MeetingAndUserService;
import com.jmpt.yhn.service.MeetingService;
import com.jmpt.yhn.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yhn on 2017/9/10.
 */
@RestController
@RequestMapping("/userInfo")
@Slf4j
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MeetingAndUserService meetingAndUserService;
    @Autowired
    private MeetingService meetingService;
   @GetMapping("/lookUserInfo")
    public ModelAndView lookUserInfo(@RequestParam("openid") String openid,
                             Map<String,Object> map){
       UserInfo userInfo =  userInfoService.findOne(openid);
       map.put("userInfo",userInfo);
       map.put("openid",openid);
       return new ModelAndView("page/userInfo",map);
   }
   @GetMapping("/saveUserInfo")
   public ModelAndView saveUserInfo(@RequestParam("openid") String openid,
                            @RequestParam("username") String username,
                            @RequestParam("telphone") String telphone,
                            Map<String,Object> map){
       UserInfo userInfo = userInfoService.findOne(openid);
       userInfo.setUsername(username);
       userInfo.setTelphone(telphone);
       userInfoService.save(userInfo);
       map.put("msg","保存个人信息成功！");
       map.put("url","/meetingSign/userInfo/lookUserInfo?openid="+openid);
       return new ModelAndView("common/success",map);
   }
    @GetMapping("/joinedMeeting")
   public ModelAndView joinedMeeting(@RequestParam("openid") String openid
                                        ,Map<String,Object> map){
        List<MeetingAndUser> meetingAndUserList = meetingAndUserService.findByOpenid(openid);
        List<Meeting> meetingList = new ArrayList<Meeting>();
        for(MeetingAndUser meetingAndUser :meetingAndUserList){
            meetingList.add(meetingService.findByMeetId(meetingAndUser.getMeetingId()));
        }
        System.out.println(meetingList);
        map.put("meetingList",meetingList);
        map.put("openid",openid);
        return new ModelAndView("page/joinedMeeting",map);
   }
}

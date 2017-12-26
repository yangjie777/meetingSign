package com.jmpt.yhn.controller;

import com.jmpt.yhn.config.UrlConfig;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.MeetingAndUserService;
import com.jmpt.yhn.service.MeetingService;
import com.jmpt.yhn.service.PushMessageService;
import com.jmpt.yhn.service.UserInfoService;
import com.jmpt.yhn.vo.WxJsVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
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
    @Autowired
    private WxMpService wxMpService; //已配置完成
    @Autowired
    private UrlConfig urlConfig;
    @GetMapping("/join")
    public ModelAndView join(@RequestParam("meetingId") String meetingId,
                             @RequestParam("openid") String openid,
                             @RequestParam("imgHead") String imgHead,
                             Map<String, Object> map) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
            Meeting meeting = meetingService.findByMeetId(meetingId);
            UserInfo userInfo = userInfoService.findOne(meeting.getOpenid());  //这是会议发起者
            UserInfo myUserInfo = userInfoService.findOne(sessionOpenid);   //用户本身
            //js jdk
            String testUrlgetAccessToken = urlConfig.getMeeting()+"/meetingSign/sign/join?meetingId="+meetingId+"&openid="+openid+"&imgHead="+imgHead;
            log.info("【查看是否获取到accessToken】,accessToken={}",wxMpService.getAccessToken());  //获得accessToken;
            String ticket =  wxMpService.getJsapiTicket();
            log.info("【查看是否获取到ticket】,ticket={}",ticket);  //获得accessToken;
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(testUrlgetAccessToken);
            log.info("【获得参数为:】appid={},nonceStr={},signature={},timestamp={},url={}"
                    ,wxJsapiSignature.getAppId(),wxJsapiSignature.getNonceStr(),wxJsapiSignature.getSignature(),wxJsapiSignature.getTimestamp(),wxJsapiSignature.getUrl());
            WxJsVO wxJsVO = new WxJsVO();
            BeanUtils.copyProperties(wxJsapiSignature,wxJsVO);
            wxJsVO.setTimestamp(String.valueOf(wxJsapiSignature.getTimestamp()));
            log.info("【copy参数为:】appid={},nonceStr={},signature={},timestamp={},url={}"
                    ,wxJsVO.getAppId(),wxJsVO.getNonceStr(),wxJsVO.getSignature(),wxJsVO.getTimestamp(),wxJsVO.getUrl());
            map.put("wxJsVO",wxJsVO);
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
                            @RequestParam("accuracy") String accuracy,
                            @RequestParam("location") String location,
                            HttpServletResponse response) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        try {
            writer = response.getWriter();
            MeetingAndUser meeting = meetingAndUserService.findMeeting(sessionOpenid, meetingId);
            Meeting meeting1 = meetingService.findByMeetId(meetingId);
            if(String.valueOf(meeting1.getMeetingStatus()).equals("1")) {
                if (meeting == null) {
                    log.info("【用户签到】未签到。openid={}", sessionOpenid);
                    UserInfo userInfo = userInfoService.findOne(sessionOpenid);
                    MeetingAndUser meetingAndUser = new MeetingAndUser();
                    meetingAndUser.setOpenid(openid);
                    meetingAndUser.setCreateTime(new Date());
                    meetingAndUser.setMeetingId(meetingId);
                    meetingAndUser.setTelphone(telphone);
                    meetingAndUser.setName(username);
                    meetingAndUser.setAccuracy(accuracy);
                    meetingAndUser.setLocation(location);
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
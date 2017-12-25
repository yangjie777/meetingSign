package com.jmpt.yhn.controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.jmpt.yhn.config.UrlConfig;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.MeetingAndUser;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.form.MeetingForm;
import com.jmpt.yhn.service.MeetingAndUserService;
import com.jmpt.yhn.service.MeetingService;
import com.jmpt.yhn.service.UserInfoService;
import com.jmpt.yhn.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by yhn on 2017/9/7.
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class MeetingController {    //     /user/indexMeeting
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private MeetingAndUserService meetingAndUserService;
    @Autowired
    private UrlConfig urlConfig;
    @GetMapping("/indexMeeting")   //创建会议
    public ModelAndView index(@RequestParam("openid") String openid,
                               @RequestParam(value = "imgHead",defaultValue = "")  String imgHead,
                               Map<String,Object> map){

//        map.put("imgHead",imgHead);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        UserInfo userInfo = userInfoService.findOne(sessionOpenid);
        map.put("openid",sessionOpenid);
        map.put("username",userInfo.getUsername());
        map.put("telphone",userInfo.getTelphone());
        return new ModelAndView("page/index",map);
    }
   @PostMapping("/createMeeting")
    public ModelAndView create(@Valid MeetingForm form,
                               BindingResult bindingResult,
                               Map<String,Object> map,
                               HttpServletRequest request){
       if(bindingResult.hasErrors()) {
           log.error("【创建会议Controller】异常，result={}", bindingResult.getFieldError().getDefaultMessage());
       }
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
       Meeting meeting = new Meeting();
       BeanUtils.copyProperties(form,meeting);
       meeting.setMeetingId(KeyUtil.genUniqueKey());
       meeting.setOpenid(sessionOpenid);
       meeting.setCreateTime(new Date());
       meeting.setUpdateTime(new Date());
       if("1".equals(form.getMeetingStatus())){
            meeting.setMeetingStatus(1);
       }
       else{
           meeting.setMeetingStatus(2);
        }

       int width = 350;   //图片的大小
       int height = 350;
       String format = "jpg";
       String path  = request.getSession().getServletContext().getRealPath("/picture");
       System.out.println("路径："+path);
       //创建二维码内容
       String content = urlConfig.getMeeting()+"/meetingSign/wechat/authorize?returnUrl="+urlConfig.getMeeting()+"/meetingSign/sign/join?meetingId="+meeting.getMeetingId();
       //定义二维码参数
       HashMap hints = new HashMap();
       hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");  //定义内容的编码
       hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);   //误差校正:一般M
       hints.put(EncodeHintType.MARGIN, 2);//设置边距:如边框空白
       try {
           BitMatrix bitMatrix  = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hints);
           path = path+meeting.getMeetingId()+".jpg";
           Path file = new File(path).toPath();
           MatrixToImageWriter.writeToPath(bitMatrix, format, file);
       } catch (WriterException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (IOException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       }
       meeting.setQRcodeImg(request.getContextPath()+"/picture"+meeting.getMeetingId()+".jpg");
       meetingService.save(meeting);
       map.put("msg","创建会议成功！");
       map.put("url","/meetingSign/user/indexMeeting?openid="+form.getOpenid()+"&nickname="+form.getNickname());
       return new ModelAndView("common/success",map);
   }
   @GetMapping("/myMeeting")
    public ModelAndView myMeeting(@RequestParam("openid") String openid,
                                  Map<String,Object> map){
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
       List<Meeting> meetingList =  meetingService.findMyMeeting(sessionOpenid);
       map.put("meetingList",meetingList);
       map.put("openid",sessionOpenid);
       return new ModelAndView("page/myMeeting",map);
   }
   @GetMapping("/deteleMeeting")
    public ModelAndView detele(@RequestParam("meetingId") String meetingId,
                               @RequestParam("openid") String openid,
                               Map<String,Object> map){
       ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        Meeting meeting =  meetingService.findByMeetId(meetingId);
        if(meeting==null){
            map.put("msg","删除会议失败！");
            map.put("url","/meetingSign/user/myMeeting?openid="+sessionOpenid);
            return new ModelAndView("common/error",map);
        }
        if(!meeting.getOpenid().equals(sessionOpenid)){
            map.put("msg","删除会议失败！");
            map.put("url","/meetingSign/user/myMeeting?openid="+sessionOpenid);
            return new ModelAndView("common/error",map);
        }
        List<MeetingAndUser> meetingAndUserList =meetingAndUserService.findByMeetingId(meetingId);
        for(MeetingAndUser meetingAndUser :meetingAndUserList){
                meetingAndUserService.deleteMeetingAndUser(meetingAndUser.getMeetingId());
        }
       meetingService.deteleMeeting(meetingId);
       return new ModelAndView("redirect:/user/myMeeting?openid="+sessionOpenid);
   }
   @GetMapping("/displayCode")
   public ModelAndView displayCode(@RequestParam("openid") String openid,
                                   @RequestParam("meetingId") String meetingId,
                                   Map<String,Object> map){
            Meeting meeting =  meetingService.findByMeetId(meetingId);
            String path = meeting.getQRcodeImg();
            map.put("path",path);
            return new ModelAndView("page/displayCode",map);
   }
    @GetMapping("/meetingJoined")
    public ModelAndView meetingJoined(@RequestParam("openid") String openid,
                                      @RequestParam("meetingId") String meetingId,
                                      Map<String,Object> map){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        List<MeetingAndUser> meetingAndUsersList = meetingAndUserService.findByMeetingId(meetingId);
        if(!meetingAndUsersList.isEmpty()){    //判断是否为本人的会议
            Meeting meeting = meetingService.findByMeetId(meetingAndUsersList.get(0).getMeetingId());
            if(!meeting.getOpenid().equals(sessionOpenid)){
                map.put("msg","错误操作！");
                map.put("url","/meetingSign/user/myMeeting?openid="+openid);
                return new ModelAndView("common/error",map);
            }
        }
        Meeting meeting = meetingService.findByMeetId(meetingId);
        map.put("meeting",meeting);
        map.put("meetingAndUsersList",meetingAndUsersList);
        map.put("openid",openid);
     return new ModelAndView("page/meetingDetail",map);
    }
    @GetMapping("/changeMeetingStatus")
    public ModelAndView changeMeetingStatus(@RequestParam("openid") String openid,
                                            @RequestParam("meetingId") String meetingId,
                                            @RequestParam("meetingStatus") String meetingStatus,
                                            Map<String,Object> map){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
          Meeting meeting =  meetingService.findByMeetId(meetingId);
          if(meeting.getOpenid().equals(sessionOpenid)){
              if(!meetingStatus.equals(String.valueOf(meeting.getMeetingStatus()))){
                  map.put("msg","会议状态请勿重复修改！");
                  map.put("url","/meetingSign/user/meetingJoined?openid="+openid+"&meetingId="+meetingId);
                  return new ModelAndView("common/error",map);
              }else{
                  if(meetingStatus.equals("1")){   //应该改为枚举
                        meeting.setMeetingStatus(2);
                  }
                  else{
                      meeting.setMeetingStatus(1);
                  }
                  meetingService.save(meeting);
                  map.put("msg","修改会议状态成功！");
                  map.put("url","/meetingSign/user/meetingJoined?openid="+openid+"&meetingId="+meetingId);
                  return new ModelAndView("common/success",map);
              }
          }
           map.put("msg","修改会议状态失败！");
           map.put("url","/meetingSign/user/meetingJoined?openid="+openid+"&meetingId="+meetingId);
           return new ModelAndView("common/error",map);
    }
}

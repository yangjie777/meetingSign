package com.jmpt.yhn.controller;

import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.entity.Event;
import com.jmpt.yhn.entity.EventAndUserInfo;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.form.EventForm;
import com.jmpt.yhn.quartzEvent.QuartEventDemo;
import com.jmpt.yhn.service.*;
import com.jmpt.yhn.utils.KeyUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yhn on 2017/9/28.
 */
@RestController
@RequestMapping("/index")
@Slf4j
@Data
public class IndexController {
    @Autowired
    private QuartzService quartzService;
    @Autowired
    private PushMessageService pushMessageService;
    @Autowired
    private EventAndUserInfoService eventAndUserInfoService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EventService eventService;
    @GetMapping("/Demo")   //首页
    public ModelAndView index(@RequestParam("openid") String openid,
                              @RequestParam("imgHead") String imgHead,
                              @RequestParam(value = "nickname",defaultValue = "") String nickname,
                              Map<String,Object> map){
        UserInfo userInfo = userInfoService.findOne(openid);
        map.put("openid",openid);
        map.put("imgHead",imgHead);
        map.put("nickname",nickname);
        map.put("phoneNumber",userInfo.getTelphone());
        SimpleDateFormat sdf = new SimpleDateFormat(("yyyy-MM-dd"));
        Date nowTime = new Date();
        map.put("nowTime",sdf.format(nowTime));
        return new ModelAndView("page/index2",map);
    }
    @PostMapping("/successEvent")
    public ModelAndView success(@Valid EventForm eventForm, BindingResult bindingResult,
                                Map<String,Object> map) throws SchedulerException,ParseException{
//        Date start = new Date();
//        start.setTime(start.getTime()+10000L);
//        quartzService.eventSetSuccess(QuartEventDemo.class,start);   //测试代码成功
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/quartzDemo/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
        String endTimeStr = eventForm.getEventDate()+" "+eventForm.getEventTime()+":00";
        System.out.println("查看时间格式："+endTimeStr);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date endTime = sdf.parse(endTimeStr);
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setEventContent(eventForm.getEventContent());
        noticeDTO.setOpenid(eventForm.getOpenid());
        noticeDTO.setEndTime(endTime);
        noticeDTO.setCreateTime(new Date());
        noticeDTO.setNickname(eventForm.getNickname());
        noticeDTO.setPhoneNumber(eventForm.getPhoneNumber());
        UserInfo userInfo = userInfoService.findOne(eventForm.getOpenid());
        userInfo.setTelphone(eventForm.getPhoneNumber());
        userInfoService.save(userInfo);
        Map<String, Object> result = null;
        try {
           result = quartzService.eventSetSuccess(QuartEventDemo.class, endTime, noticeDTO);  //设置时间成功
        }catch(Exception e){
            e.printStackTrace();
        }
        log.info("设置时间返回的结果:result={}",result);
        if(result.get("result").equals(false)){   //设置失败
            map.put("msg",result.get("msg"));
            map.put("url","/quartzDemo/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
        pushMessageService.successSetEvent(noticeDTO);  //实时通知
        //存储数据
        Event event = new Event();
        BeanUtils.copyProperties(noticeDTO,event);
        String key = KeyUtil.genUniqueKey();
        event.setEventId(key);
        try {
            eventService.save(event);
        }catch (Exception e){
            map.put("msg","您设置的内容包含特殊符号，导致异常，但我们仍到点通知您！！");
            map.put("url","/quartzDemo/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
        EventAndUserInfo eventAndUserInfo = new EventAndUserInfo();
        eventAndUserInfo.setEventId(key);
        eventAndUserInfo.setOpenid(eventForm.getOpenid());
        eventAndUserInfoService.save(eventAndUserInfo);
        map.put("msg","创建事件提醒成功");
        map.put("url","/quartzDemo/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/myTask")   //首页
    public ModelAndView myTask(@RequestParam("openid") String openid,
                               @RequestParam("imgHead") String imgHead,
                               @RequestParam(value = "nickname",defaultValue = "") String nickname,
                               Map<String,Object> map) {
       List<EventAndUserInfo> eventAndUserInfoList =  eventAndUserInfoService.findEventAndUerInfoList(openid);
       List<String> eventIdList = new ArrayList<String>();
       for(EventAndUserInfo eventAndUserInfo:eventAndUserInfoList){
           eventIdList.add(eventAndUserInfo.getEventId());
       }
       List<Event> eventList = new ArrayList<Event>();
        for(String eventId :eventIdList){
           Event event =  eventService.findEvent(eventId);
           eventList.add(event);
        }
        Collections.reverse(eventList);
        map.put("eventList",eventList);
        map.put("openid",openid);
        map.put("imgHead",imgHead);
        map.put("nickname",nickname);
        return new ModelAndView("page/mytask",map);
    }
}
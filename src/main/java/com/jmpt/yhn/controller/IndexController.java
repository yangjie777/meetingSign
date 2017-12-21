package com.jmpt.yhn.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jmpt.yhn.VO.EventVO;
import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.entity.Event;
//import com.jmpt.yhn.entity.EventAndUserInfo;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.form.EventForm;
import com.jmpt.yhn.quartzEvent.QuartEventDemo;
import com.jmpt.yhn.service.*;
import com.jmpt.yhn.utils.KeyUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.quartz.SchedulerException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
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
//    @Autowired
//    private EventAndUserInfoService eventAndUserInfoService;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userid = (String) attributes.getRequest().getSession().getAttribute("userid");
        if(!userid.equals(eventForm.getOpenid())){
            log.error("身份验证不正确，openid={}",userid);
            map.put("msg","身份验证不正确");
            map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
//        String endTimeStr = eventForm.getEventDate()+" "+eventForm.getEventTime()+":00";
        //时间格式----
//        System.out.println("查看时间格式1："+eventForm.getEventDate());
//        String eventDate = eventForm.getEventDate().substring(0,11);   //未格式化的日期(包含空格)；
//        eventDate.replace("/","-"); //将斜杠修改为横杠
//        String eventTime = eventForm.getEventDate().substring(11);    //未格式化的时间(未空格)；
//        String eventEndTime;    //已格式化的时间
//        if(eventTime.indexOf("上午")!=-1){    //包含上午的字符
//            eventEndTime = eventTime.substring(2)+":00";
//        }
//        else{  //下午的时间要加12小时
//            String starthour = eventTime.substring(2,4);  //未格式化时间
//            String endhour = String.valueOf(Integer.valueOf(eventTime.substring(2,4))+12);   //+12小时
//            eventTime.replace(starthour,endhour);  //替换成功
//             eventEndTime = eventTime.substring(2)+":00";
//        }
        String eventTime = eventForm.getEventDate().replace("T"," ")+":00";
        System.out.println("查看时间格式："+eventTime);
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        Date endTime = sdf.parse(eventTime);
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
            map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
        pushMessageService.successSetEvent(noticeDTO);  //实时通知
        //存储数据
        Event event = new Event();
        BeanUtils.copyProperties(noticeDTO,event);
        event.setOpenid(eventForm.getOpenid());  //
        String key = KeyUtil.genUniqueKey();
        event.setEventId(key);
        try {
            eventService.save(event);
        }catch (Exception e){
            map.put("msg","您设置的内容包含特殊符号，导致异常，但我们仍到点通知您！！");
            map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
//        EventAndUserInfo eventAndUserInfo = new EventAndUserInfo();
//        eventAndUserInfo.setEventId(key);
//        eventAndUserInfo.setOpenid(eventForm.getOpenid());
//        eventAndUserInfoService.save(eventAndUserInfo);
        map.put("msg","创建事件提醒成功");
        map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
        return new ModelAndView("common/success",map);
    }
    @GetMapping("/myTask")   //see my TaskList
    public ModelAndView myTask(@RequestParam("openid") String openid,
                               @RequestParam("imgHead") String imgHead,
                               @RequestParam(value = "nickname",defaultValue = "") String nickname,
                               @RequestParam(value = "page",defaultValue ="1" ) Integer page,
                               @RequestParam(value = "size",defaultValue ="7") Integer size,
                               Map<String,Object> map) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userid = (String) attributes.getRequest().getSession().getAttribute("userid");
        if(!userid.equals(openid)){
            log.error("身份验证不正确，openid={}",userid);
            map.put("msg","身份验证不正确");
            map.put("url","/meetingSign/index/Demo?openid="+userid+"&imgHead="+imgHead+"&nickname="+nickname);
            return new ModelAndView("common/error",map);
        }
        PageRequest request = new PageRequest(page-1,size);
        Page<Event> result = eventService.findEventByOpenid(openid,request);
        if(page<result.getTotalPages()){
            map.put("isHasPage","1");
        }
        else{
            map.put("isHasPage","0");
        }
        map.put("eventList",result.getContent());
        map.put("size",size);
        map.put("page",page);
        map.put("openid",openid);
        map.put("imgHead",imgHead);
        map.put("nickname",nickname);
        return new ModelAndView("page/mytask",map);
    }
    @GetMapping("/reoloadManyContent")
    public EventVO reoloadManyContent(@RequestParam("openid") String openid,
                                   @RequestParam(value = "page") String page,
                                   @RequestParam(value = "size") String size,
                                   HttpServletResponse response)throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String userid = (String) attributes.getRequest().getSession().getAttribute("userid");
        if(!userid.equals(openid)){
            log.error("身份验证不正确，openid={}",userid);
            return null;
        }
        response.setCharacterEncoding("UTF-8");
        log.info("【切面】,page={},size={}",page,size);
        PageRequest request = new PageRequest(Integer.valueOf(page)-1,Integer.valueOf(size));
        Page<Event> result = eventService.findEventByOpenid(openid,request);
        EventVO eventVO = new EventVO();
        eventVO.setEventList(result.getContent());
        eventVO.setPage(page);
        eventVO.setSize(size);
        if((Integer.valueOf(page)-1)<result.getTotalPages()){
            eventVO.setIsHasPage("1");
        }
        else{
            eventVO.setIsHasPage("0");
        }
        return eventVO;
    }
}
package com.jmpt.yhn.controller;
import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.entity.Event;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.form.EventForm;
import com.jmpt.yhn.quartzEvent.QuartEventDemo;
import com.jmpt.yhn.service.*;
import com.jmpt.yhn.utils.KeyUtil;
import com.jmpt.yhn.vo.EventVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        UserInfo userInfo = userInfoService.findOne(sessionOpenid);
        map.put("openid",openid);
        map.put("imgHead",userInfo.getImgHead());
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

        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/meetingSign/index/Demo?openid="+eventForm.getOpenid()+"&imgHead="+eventForm.getImgHead()+"&nickname="+eventForm.getNickname());
            return new ModelAndView("common/error",map);
        }
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
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        UserInfo userInfo = userInfoService.findOne(sessionOpenid);
        PageRequest request = new PageRequest(page-1,size);
        Page<Event> result = eventService.findEventByOpenid(sessionOpenid,request);
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
        map.put("imgHead",userInfo.getImgHead());
        map.put("nickname",nickname);
        return new ModelAndView("page/mytask",map);
    }
    @GetMapping("/reoloadManyContent")
    public EventVO reoloadManyContent(@RequestParam("openid") String openid,
                                      @RequestParam(value = "page") String page,
                                      @RequestParam(value = "size") String size,
                                      HttpServletResponse response)throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String sessionOpenid = (String)attributes.getRequest().getSession().getAttribute("openid");
        response.setCharacterEncoding("UTF-8");
        log.info("【分页】,page={},size={}",page,size);
        PageRequest request = new PageRequest(Integer.valueOf(page)-1,Integer.valueOf(size));
        Page<Event> result = eventService.findEventByOpenid(sessionOpenid,request);
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
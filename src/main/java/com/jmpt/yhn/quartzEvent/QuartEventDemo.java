package com.jmpt.yhn.quartzEvent;

import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.service.PushMessageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.text.SimpleDateFormat;

/**
 * Created by yhn on 2017/9/29.
 */
@Slf4j
@Data
@Component
public class QuartEventDemo implements Job,Serializable{   //只需建立一个类，然后将需要做的事注入进来就行
    private NoticeDTO noticeDTO = new NoticeDTO();
    @Autowired
    private PushMessageService pushMessageService;  //注入失败?（已搞定）
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException{
       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        JobDataMap jobDetail =  jobExecutionContext.getTrigger().getJobDataMap();
        String openid = (String) jobDetail.get("openid");
        String createTime = (String) jobDetail.get("createTime");
        String endTime = (String) jobDetail.get("endTime");
        String eventContent = (String) jobDetail.get("eventContent");
        String phoneNumber = (String) jobDetail.get("phoneNumber");
        String nickname = (String) jobDetail.get("nickname");
        try {
            noticeDTO.setCreateTime(format.parse(createTime));
            noticeDTO.setEndTime(format.parse(endTime));
        }catch (Exception e){
            e.printStackTrace();
        }
        noticeDTO.setOpenid(openid);
        noticeDTO.setEventContent(eventContent);
        noticeDTO.setNickname(nickname);
        noticeDTO.setPhoneNumber(phoneNumber);       log.info("【传值是否成功】noticeDTO={}",noticeDTO);
        pushMessageService.noiteEvent(noticeDTO);
        try {
            pushMessageService.aliyunSms(noticeDTO);
        }catch (Exception e){
            log.error("发送短信失败");
        }


    }
}


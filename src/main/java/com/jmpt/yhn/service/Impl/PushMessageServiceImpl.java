package com.jmpt.yhn.service.Impl;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.jmpt.yhn.dto.NoticeDTO;
import com.jmpt.yhn.entity.Meeting;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by yhn on 2017/9/6.
 */
@Service
@Scope("prototype")
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {
    @Autowired
    private WxMpService wxMpService;
    public void successSign(Meeting meeting, UserInfo userInfo, String meetingUsername) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("ma4J1FehwVPhgrQsITeDS0jxevnCTZAtlzqGoF-5__o");
        templateMessage.setToUser(userInfo.getOpenid());
        SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " );
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","恭喜您签到成功"),
                new WxMpTemplateData("keyword1",meeting.getMeetingName()),
                new WxMpTemplateData("keyword2",meeting.getMeetingLocated()),
                new WxMpTemplateData("keyword3",meetingUsername),
                new WxMpTemplateData("keyword4", sdf.format(new Date())),
                new WxMpTemplateData("keyword5",userInfo.getUsername()),
                new WxMpTemplateData("keyword6",userInfo.getTelphone()),
                new WxMpTemplateData("remark","发起者可在【我的会议】的详情查看您的签到情况")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        }catch(WxErrorException e){
            log.error("【微信模板消息】发送失败，{}",e);
        }
    }


    @Override
    public void successSetEvent(NoticeDTO noticeDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("6wSO4Dwp_5Qb3rWRH5jPe_wRgC8_ZlBCQ1MTV7-rKbo");
        templateMessage.setToUser(noticeDTO.getOpenid());
        SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","请查看您的信息是否填写正确，我们将在指定的时间给你通知事件消息"),
                new WxMpTemplateData("keyword1",sdf.format(noticeDTO.getCreateTime())),
                new WxMpTemplateData("keyword2",sdf.format(noticeDTO.getEndTime())),
                new WxMpTemplateData("keyword3",noticeDTO.getEventContent()),
                new WxMpTemplateData("remark","come from 楠尼玛的通知")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);

        }catch(WxErrorException e){
            log.error("【微信模板消息】发送失败，{}",e);
             }
    }
    @Override
    public void noiteEvent(NoticeDTO noticeDTO) {
        System.out.println("成功调用此方法");
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId("akGcWy6Dy8-lotC3uL7THt0R6Qe9D0lOMJTGQlzXxCA");
        templateMessage.setToUser(noticeDTO.getOpenid());
        SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd HH:mm:ss" );
        List<WxMpTemplateData> data = Arrays.asList(
                new WxMpTemplateData("first","您的设置事件的时间到了！！！！"),
                new WxMpTemplateData("keyword1",sdf.format(noticeDTO.getCreateTime())),
                new WxMpTemplateData("keyword2",sdf.format(noticeDTO.getEndTime())),
                new WxMpTemplateData("keyword3",noticeDTO.getEventContent()),
                new WxMpTemplateData("remark","come from 楠尼玛的通知")
        );
        templateMessage.setData(data);
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch(WxErrorException e){
            log.error("【微信模板消息】发送失败，{}",e);
        }
    }
    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAIVUtbzSJNu2ei";
    static final String accessKeySecret = "f9XSEIZ6d53lfaprbzkL4FNdiDhypw";
    @Override
    public void aliyunSms(NoticeDTO noticeDTO)  throws Exception{
        //发短信
        SendSmsResponse response = sendSms(noticeDTO.getNickname(),noticeDTO.getPhoneNumber());
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + response.getCode());
        System.out.println("Message=" + response.getMessage());
        System.out.println("RequestId=" + response.getRequestId());
        System.out.println("BizId=" + response.getBizId());
        Thread.sleep(3000L);
        //查明细
        if(response.getCode() != null && response.getCode().equals("OK")) {
            QuerySendDetailsResponse querySendDetailsResponse = querySendDetails(response.getBizId(),noticeDTO.getPhoneNumber());
            System.out.println("短信明细查询接口返回数据----------------");
            System.out.println("Code=" + querySendDetailsResponse.getCode());
            System.out.println("Message=" + querySendDetailsResponse.getMessage());
            int i = 0;
            for(QuerySendDetailsResponse.SmsSendDetailDTO smsSendDetailDTO : querySendDetailsResponse.getSmsSendDetailDTOs())
            {
                System.out.println("SmsSendDetailDTO["+i+"]:");
                System.out.println("Content=" + smsSendDetailDTO.getContent());
                System.out.println("ErrCode=" + smsSendDetailDTO.getErrCode());
                System.out.println("OutId=" + smsSendDetailDTO.getOutId());
                System.out.println("PhoneNum=" + smsSendDetailDTO.getPhoneNum());
                System.out.println("ReceiveDate=" + smsSendDetailDTO.getReceiveDate());
                System.out.println("SendDate=" + smsSendDetailDTO.getSendDate());
                System.out.println("SendStatus=" + smsSendDetailDTO.getSendStatus());
                System.out.println("Template=" + smsSendDetailDTO.getTemplateCode());
            }
            System.out.println("TotalCount=" + querySendDetailsResponse.getTotalCount());
            System.out.println("RequestId=" + querySendDetailsResponse.getRequestId());
        }

    }
    public static SendSmsResponse sendSms(String name,String phoneNumber) throws Exception {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("时间任务调度系统");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_101110036");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        String str = new String(name.getBytes(), "UTF-8");
        request.setTemplateParam("{\"name\":\"***\"}");

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        return sendSmsResponse;
    }
    public static QuerySendDetailsResponse querySendDetails(String bizId,String phoneNumber) throws Exception {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phoneNumber);
        //可选-流水号
        request.setBizId(bizId);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);

        //hint 此处可能会抛出异常，注意catch
        QuerySendDetailsResponse querySendDetailsResponse = acsClient.getAcsResponse(request);

        return querySendDetailsResponse;
    }

}

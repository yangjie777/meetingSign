package com.jmpt.yhn.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yhn on 2017/9/7.
 */
@Data
public class MeetingForm {
    @NotEmpty(message = "未授权登录")
    private String openid;
    @NotEmpty(message = "请输入会议名称")
    private String meetingName;
    private String meetingLocated;
    private String meetingStatus;
    private String nickname;
}

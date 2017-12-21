package com.jmpt.yhn.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by yhn on 2017/9/29.
 */
@Data
public class EventForm {
    private String nickname;
    private String phoneNumber;
    private String imgHead;
    @NotEmpty(message = "未登陆不可操作")
    private String openid;
    @NotEmpty(message = "日期不能为空")
    private String eventDate;
    @NotEmpty(message = "时间不能为空")
    private String eventTime;
    @NotEmpty(message = "内容不能为空")
    private String eventContent;

}

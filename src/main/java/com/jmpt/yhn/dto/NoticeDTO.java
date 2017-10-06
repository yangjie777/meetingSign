package com.jmpt.yhn.dto;

import lombok.Data;

import java.util.Date;

/**
 * Created by yhn on 2017/9/29.
 */
@Data
public class NoticeDTO {
    private String openid;
    private String nickname;
    private Date createTime;
    private Date endTime;
    private String eventContent;
    private String phoneNumber;
}

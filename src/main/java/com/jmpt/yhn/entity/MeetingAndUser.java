package com.jmpt.yhn.entity;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by yhn on 2017/9/7.
 */
@Entity
@Data
@DynamicUpdate    //自动更新时间字段
public class MeetingAndUser {
    @Id
    @GeneratedValue
    private Integer id;
    private String meetingId;  //会议id
    private String openid;  //签到用户微信id
    private String name;    //签到名
    private Date createTime;  //签到时间
    private String telphone;  //手机号码
}

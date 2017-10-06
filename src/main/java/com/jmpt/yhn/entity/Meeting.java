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
public class Meeting {
    @Id
    @GeneratedValue
    private Integer id;   //只起到排序的作用
    private String meetingName;  //会议名称
    private String meetingId;   //会议id
    private String openid;   //会议发起者
    private String meetingLocated; //会议地点或者简介（随便啦）
    private String QRcodeImg;  //二维码
    private Integer meetingStatus;  //会议状态   1. 启动签到 2. 停止签到
    private Date createTime;   //创建时间
    private Date updateTime;   //更新时间

}

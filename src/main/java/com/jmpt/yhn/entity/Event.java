package com.jmpt.yhn.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by yhn on 2017/9/29.
 */
@Entity
@Data
@DynamicUpdate    //自动更新时间字段
public class Event {
    @Id
    @GeneratedValue
    private Integer id;   //排序作用
    private String eventId;
    private Date createTime;  //创建时间
    private Date endTime;  //更新时间
    private String eventContent;   //提醒事件内容
}

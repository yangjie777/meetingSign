package com.jmpt.yhn.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by yhn on 2017/9/29.
 */
@Entity
@Data
@DynamicUpdate    //自动更新时间字段
public class EventAndUserInfo {
    @Id
    @GeneratedValue
    private Integer id;   //排序作用
    private String eventId;
    private String openid;
}

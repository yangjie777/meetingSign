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
public class UserInfo {
    @Id
    @GeneratedValue
    private Integer id;   //排序作用
    private String username;  //用户名，只是微信名，与签到名不符
    private String password;  // 密码，未使用，暂留
    private String imgHead;  //头像
    private String telphone;  //手机号码
    private String openid;  //微信openid
    private Date createTime;  //创建时间
    private Date updateTime;  //更新时间
}

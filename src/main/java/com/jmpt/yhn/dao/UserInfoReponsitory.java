package com.jmpt.yhn.dao;

import com.jmpt.yhn.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yhn on 2017/9/7.
 */
public interface UserInfoReponsitory extends JpaRepository<UserInfo,String>{
    UserInfo findByOpenid(String openid);  //根据openid 查询用户信息
}

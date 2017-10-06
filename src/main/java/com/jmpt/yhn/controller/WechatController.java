package com.jmpt.yhn.controller;
import com.jmpt.yhn.config.UrlConfig;
import com.jmpt.yhn.constant.CookiesConstant;
import com.jmpt.yhn.constant.RedisConstant;
import com.jmpt.yhn.entity.UserInfo;
import com.jmpt.yhn.exception.meetingSignAuthorizeException;
import com.jmpt.yhn.service.UserInfoService;
import com.jmpt.yhn.utils.CookiesUtil;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by yhn on 2017/8/4.
 */
@Controller
@RequestMapping("/wechat")
@Slf4j
public class WechatController {
    @Autowired
    private WxMpService wxMpService; //已配置完成
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UrlConfig urlConfig;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl){
        //1.配置
        //2.调用方法
        String url = urlConfig.getMeeting()+"/meetingSign/wechat/userInfo";
        String redirectUrl =  wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));
//        log.info("【微信网页授权】获取code，result={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl){
       WxMpOAuth2AccessToken wxMpOAuth2AccessToken = new WxMpOAuth2AccessToken();
        WxMpUser wxMpUser = null;
            try{
                wxMpOAuth2AccessToken =  wxMpService.oauth2getAccessToken(code);
                wxMpUser =  wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken,null);
            }catch (WxErrorException e){
                log.info("【微信网页授权】{}",e);
                e.printStackTrace();
            }
         String openId = wxMpOAuth2AccessToken.getOpenId();
        //存储用户信息
        UserInfo userInfo = userInfoService.findOne(openId);
        if(userInfo==null){
            UserInfo user = new UserInfo();
            user.setImgHead(wxMpUser.getHeadImgUrl());
            user.setOpenid(openId);
            user.setUsername(null);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            userInfoService.save(user);
        }
        //设置token至redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;
        try {
            redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openId, expire, TimeUnit.SECONDS);
        }catch(Exception e){
                e.printStackTrace();
            throw new meetingSignAuthorizeException();
        }
        //设置token至cookies
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response =  attributes.getResponse();
        CookiesUtil.set(response, CookiesConstant.TOKEN,token,1800);
         if(returnUrl.indexOf("?")!=-1){  //包含问号,说明回调地址带参
             return "redirect:" +returnUrl+"&openid="+openId+"&imgHead="+wxMpUser.getHeadImgUrl();
         }
         return "redirect:" +returnUrl+"?openid="+openId+"&imgHead="+wxMpUser.getHeadImgUrl();
    }
}

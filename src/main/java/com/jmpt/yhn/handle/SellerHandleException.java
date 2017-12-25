package com.jmpt.yhn.handle;

import com.jmpt.yhn.config.UrlConfig;
import com.jmpt.yhn.exception.meetingSignAuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhn on 2017/9/6.
 */
@ControllerAdvice
@Slf4j
public class SellerHandleException {
    @Autowired
    private  UrlConfig urlConfig;
    //  拦截登陆异常
    @ExceptionHandler(value = meetingSignAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        log.warn("访问异常，捕获异常，回调URL，url={}",url);
        StringBuilder stringBuilder = new StringBuilder("redirect:");
        stringBuilder.append(urlConfig.getMeeting());     //域名
        stringBuilder.append("/meetingSign/wechat/authorize?returnUrl=");
        stringBuilder.append(url);
        log.warn("访问异常，跳转页面，URL={}",stringBuilder.toString());
        return new ModelAndView(stringBuilder.toString());
    }
}

package com.jmpt.yhn.handle;

import com.jmpt.yhn.config.UrlConfig;
import com.jmpt.yhn.exception.meetingSignAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by yhn on 2017/9/6.
 */
@ControllerAdvice
public class SellerHandleException {
    @Autowired
    private  UrlConfig urlConfig;
    //  拦截登陆异常
    @ExceptionHandler(value = meetingSignAuthorizeException.class)
    public ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redirect:"
                .concat(urlConfig.getMeeting())
                .concat("/meetingSign/wechat/authorize")
                .concat("?returnUrl=")
                .concat(urlConfig.getMeeting())
                .concat("/meetingSign/user/indexMeeting"));
    }
}

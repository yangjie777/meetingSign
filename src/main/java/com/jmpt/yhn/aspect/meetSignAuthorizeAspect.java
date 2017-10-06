package com.jmpt.yhn.aspect;

import com.jmpt.yhn.constant.CookiesConstant;
import com.jmpt.yhn.constant.RedisConstant;
import com.jmpt.yhn.exception.meetingSignAuthorizeException;
import com.jmpt.yhn.utils.CookiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by yhn on 2017/9/12.
 */
@Aspect
@Component
@Slf4j
public class meetSignAuthorizeAspect {
    @Autowired
    private StringRedisTemplate template;
   @Pointcut("execution(public * com.jmpt.yhn.controller.*.*(..))&&"+"!execution(public * com.jmpt.yhn.controller.Wechat*.*(..))")
    public void verify(){} //验证
    @Before("verify()")
    public void doerify(){    //方法的具体实现\
        try {
            //获得request
            System.out.println("执行切面");
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            //查询cookie
            Cookie cookie = CookiesUtil.get(request, CookiesConstant.TOKEN);
            if (cookie == null) {
                log.warn("【登陆校验】查询不到cookie");
                throw new meetingSignAuthorizeException();
            }
            //去redis 查询
            String tokenValue = template.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue()));
            if (StringUtils.isEmpty(tokenValue)) {
                log.warn("【登陆校验】Redis查询不到token");
                throw new meetingSignAuthorizeException();
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new meetingSignAuthorizeException();
        }
    }
}

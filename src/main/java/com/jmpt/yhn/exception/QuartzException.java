package com.jmpt.yhn.exception;

import com.jmpt.yhn.enums.ResultEnum;

/**
 * Created by yhn on 2017/8/1.
 */
public class QuartzException extends RuntimeException{
    private Integer code;
    public QuartzException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public QuartzException(Integer code, String message){
        super(message);
        this.code = code;
    }
}

package com.jmpt.yhn.enums;

import lombok.Getter;

/**
 * Created by yhn on 2017/8/1.
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"成功"),
    ;
    private Integer code;
    private String message;
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

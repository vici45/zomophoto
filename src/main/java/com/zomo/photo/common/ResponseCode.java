package com.zomo.photo.common;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("success",0),
    ERROR("error",1);
    private String msg;
    private Integer code;

    ResponseCode(String msg, Integer code) {
        this.msg = msg;
        this.code = code;
    }
}

package com.xiaoyou.face.service;

import lombok.Getter;

/**
 * @author lenyuqin
 * @data 2020/12/16
 */
@Getter
public enum Is_Sign {
    //打卡成功
    TURE(1),
    //打卡失败
    FALSE(0);

    private final Integer code;


    Is_Sign(Integer code) {
        this.code = code;
    }


}

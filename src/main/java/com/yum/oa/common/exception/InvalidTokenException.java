package com.yum.oa.common.exception;

/**
 * 无效的token异常
 * @description
 * @author: gaoyu
 * @create: 2020-08-06
 * @version: 0.0.1
 **/
public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException(String msg) {
        super(msg);
    }
}
